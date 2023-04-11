import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost:8082/auth/login'; // ?

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.loginUrl, {username, password}).pipe(
      tap(response => {
        // Save the JWT token and expiration date in local storage
        const expiresAt = new Date(response.expiresIn * 1000 + Date.now());
        localStorage.setItem('access_token', response.accessToken);
        localStorage.setItem('expires_at', JSON.stringify(expiresAt));
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
