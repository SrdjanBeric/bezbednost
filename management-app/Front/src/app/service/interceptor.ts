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

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const authToken = localStorage.getItem('access_token');
    console.log(`OVDE SE ISTO NESTO DESAVA`);

    if (authToken) {
      // Clone the request and add the authorization header
      const authReq = req.clone({
        setHeaders: { Authorization: `Bearer ${authToken}` },
      });
      console.log(`BLABLA NESTO SE DESAVA`);

      return next.handle(authReq).pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 401 && !req.url.includes('refresh-token')) {
            console.log(`Usao sam u 401 error pokusavm token da dobavim`);
            // If the request returns a 401 Unauthorized status code, it means the access token is expired
            // Attempt to refresh the access token using the refresh token
            return this.authService.refreshToken().pipe(
              switchMap((response) => {
                const newAuthToken = response.access_token;
                // Update the access token in local storage
                localStorage.setItem('access_token', newAuthToken);

                // Clone the original request with the new access token
                const newAuthReq = req.clone({
                  setHeaders: { Authorization: `Bearer ${newAuthToken}` },
                });

                // Retry the request with the new access token
                return next.handle(newAuthReq);
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
