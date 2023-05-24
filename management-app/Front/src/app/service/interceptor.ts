import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse,
} from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}
  originalAuthToken = '';

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const authToken = localStorage.getItem('access_token');
    if (authToken) {
      console.info(this.originalAuthToken.length === 0);
      console.info(this.originalAuthToken);
      if (this.originalAuthToken.length === 0) {
        this.originalAuthToken = authToken;
        console.log(
          `trenutno nemam originalni token i sad sam mu dodao ovaj prvi token sto sam dobio`
        );
      }
      // OVO IZKOMENTARISI PA OPET PROBAJ DA VIDIS DAL RADI
      // if (authToken !== this.originalAuthToken) {
      //   return new Observable<HttpEvent<any>>((observer) => {
      //     this.authService.logout(); // Log out the user
      //     this.originalAuthToken = '';
      //     observer.complete(); // Complete the observer to prevent further execution
      //   });
      //}

      // Clone the request and add the authorization header
      const authReq = req.clone({
        setHeaders: { Authorization: `Bearer ${authToken}` },
      });
      console.log(`Preparing to send the next request: `);

      return next.handle(authReq).pipe(
        catchError((error: HttpErrorResponse) => {
          //OVO SAM DODAO
          if (error.status === 401 && authToken !== this.originalAuthToken) {
            this.authService.logout(); // Log out the user
            this.originalAuthToken = '';
          }
          //OVO SAM DODAO
          if (error.status === 401 && !req.url.includes('refresh-token')) {
            // If the request returns a 401 Unauthorized status code, it means the access token is expired
            // Attempt to refresh the access token using the refresh token
            return this.authService.refreshToken().pipe(
              switchMap((response) => {
                const newAuthToken = response.accessToken;
                // Update the access token in local storage
                localStorage.setItem('access_token', newAuthToken);

                // Clone the original request with the new access token
                const newAuthReq = req.clone({
                  setHeaders: { Authorization: `Bearer ${newAuthToken}` },
                });

                // Retry the request with the new access token
                return next.handle(newAuthReq).pipe(
                  catchError((refreshError) => {
                    if (refreshError.status === 401) {
                      // The new access token has also expired
                      this.authService.logout(); // Log out the user
                    }
                    return throwError(refreshError);
                  })
                );
              }),
              catchError((refreshError) => {
                this.authService.logout();

                // Return the refresh token error
                return throwError(refreshError);
              })
            );
          }

          // For other error codes, simply return the error
          return throwError(error);
        })
      );
    }

    // If there is no access token, proceed with the original request
    return next.handle(req);
  }
}
