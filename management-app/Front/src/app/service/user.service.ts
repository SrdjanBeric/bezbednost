import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}

  getUsers(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8081/user/all');
  }
  getUser(id: number): Observable<User> {
    return this.http.get<User>('http://localhost:8081/user/' + id);
  }


}
