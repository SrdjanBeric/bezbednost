import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private loginUrl = 'http://localhost:8081/auth/login'; // ?
  private loginUrlEmail = 'http://localhost:8081/auth/loginViaEmail';

  constructor(private http: HttpClient, private router: Router) {}

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

        const role = this.getRole(); // Get the role value
        console.log(`Ovo je rola nakon logovanja: ` + role); // Print the role value for testing
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

  declare atob: (input: string) => string;

  getRole() {
    const token = localStorage.getItem('access_token');
    if (token) {
      const base64Url = token!.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
          })
          .join('')
      );

      const payload = JSON.parse(jsonPayload);
      return payload.role;
    }
    return null;
  }

  isLoggedIn(): boolean {
    // Check if the current time is past the token's expiration date
    const expiresAt = JSON.parse(localStorage.getItem('expires_at') || 'null');
    return Date.now() < expiresAt;
  }

  isUserLoggedIn() {
    const accessToken = localStorage.getItem('access_token');
    return !!accessToken; // Returns true if access token exists, false otherwise
  }

  logout(): void {
    // Remove the JWT token and expiration date from local storage
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('expires_at');

    console.log(this.getRole());
    console.log(`uspesno izlogovan`);
    this.router.navigate(['/login-email']);
  }
}
