import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}
  //loggedInUser: Users;

  //   getUsers(): Observable<Users[]> {
  //     return this.http.get<Users[]>('http://localhost:8082/users/all');
  //   }

  //   login(email: string, password: string): Observable<Users> {
  //     return this.http.post<Users>('http://localhost:8082/users/login', {
  //       email,
  //       password,
  //     });
  //   }

  //   updateUser(id: number, users: UsersDTO): Observable<Users> {
  //     return this.http.put<Users>(
  //       'http://localhost:8082/users/update/' + id,
  //       users
  //     );
  //   }
}
