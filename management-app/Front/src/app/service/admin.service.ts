import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminService {
  constructor(private http: HttpClient) {}

  getUsersToActivate(): Observable<any[]> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(
      'https://localhost:8081/admin/usersToActivate',
      {
        headers,
      }
    );
  }

  activateUser(userId: number): Observable<any> {
    console.log('Here is the userID ' + userId);
    const url = `https://localhost:8081/admin/activateUser/${userId}`;
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.patch<any>(url, null, { headers });
  }
}
