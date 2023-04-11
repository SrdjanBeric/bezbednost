import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../data/user';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}
     getUsers(): Observable<User[]> {
       return this.http.get<User[]>('http://localhost:8082/user/all');
     }

     /*login(email: string, password: string): Observable<User> {
       return this.http.post<User>('http://localhost:8082/User/login', {
         email,
         password,
       });
     }
    updateUser(id: number, User: User): Observable<User> {
      return this.http.put<User>(
        'http://localhost:8082/User/update/' + id,
        User
      );
    }*/
}
