import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private loginUrl = 'http://localhost:8081/auth/login'; // ?
  private loginUrlEmail = 'http://localhost:8081/auth/loginViaEmail';

  constructor(private http: HttpClient) {}

  signup(registrationRequest: any): Observable<any> {
    const url = 'http://localhost:8081/auth/signup';
    return this.http.post(url, registrationRequest);
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.loginUrl, { username, password }).pipe(
      tap((response) => {
        // Save the JWT token and expiration date in local storage
        console.log(response);

        localStorage.setItem('access_token', response.accessToken);
        localStorage.setItem('refresh_token', response.accessToken);
      })
    );
  }

  loginViaEmail(email: string): Observable<any> {
    console.log(this.loginUrlEmail);
    return this.http.get<any>(this.loginUrlEmail + '/' + email).pipe(
      tap((response) => {
        console.log(response);
      })
    );
  }

  isLoggedIn(): boolean {
    // Check if the current time is past the token's expiration date
    const expiresAt = JSON.parse(localStorage.getItem('expires_at') || 'null');
    return Date.now() < expiresAt;
  }

  logout(): void {
    // Remove the JWT token and expiration date from local storage
    localStorage.removeItem('access_token');
    localStorage.removeItem('expires_at');
  }
}
