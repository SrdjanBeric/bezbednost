import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}

  getUsers(): Observable<any[]> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>('https://localhost:8081/user/all', { headers });
  }
  getUser(id: number): Observable<User> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<User>('https://localhost:8081/user/' + id, {
      headers,
    });
  }

  updateUser(user: any) {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put<any>('https://localhost:8081/user/update', user, {
      headers,
    });
  }

  myInfo() {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>('https://localhost:8081/user/myInfo', {
      headers,
    });
  }
  getMyInfo(): Observable<User> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<User>('https://localhost:8081/user/myInfo', { headers });
  }
  update(user:User):Observable<any>
{
  const token = localStorage.getItem('access_token');
  const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  console.log(user);

  return this.http.put<any>('https://localhost:8081/user/update',user, { headers }) ;
}
getSkills(): Observable<any[]> {
  const token = localStorage.getItem('access_token');
  const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  return this.http.get<any[]>('https://localhost:8081/softwareEngineer/allSkills',{headers});
}
updateSkills(skills:String[]): Observable<any[]> {
  const token = localStorage.getItem('access_token');
  const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  return this.http.put<any[]>('https://localhost:8081/softwareEngineer/updateSkills',skills,{headers});
}
uploadCV(file:File): Observable<any[]> {
  const formData = new FormData();
  formData.append('file', file);
   const token = localStorage.getItem('access_token');
  const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  return this.http.post<any[]>('https://localhost:8081/file/upload',formData,{headers});
}


}
