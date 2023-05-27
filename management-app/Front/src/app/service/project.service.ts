import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from '../model/project';
import { AETPRDTO } from '../dto/add-engineer-to-project-request-dto';

@Injectable({ providedIn: 'root' })
export class ProjectService {
  constructor(private http: HttpClient) {}

  allUsers(): Observable<any[]> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>('https://localhost:8081/project/all', {
      headers,
    });
  }
  managerProjects(): Observable<any[]> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(
      'https://localhost:8081/project/allManagerProject',
      {
        headers,
      }
    );
  }
  addEnginer(requestDto: any): Observable<any[]> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<any[]>(
      'https://localhost:8081/project/addEngineer',
      requestDto,
      {
        headers,
      }
    );
  }

  getAvailableEngineers(projectId: number) {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(
      `https://localhost:8081/project/${projectId}/engineer/available`,
      { headers }
    );
  }

  removeEngineer(projectId: number, softwareEngineerId: number) {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete<any[]>(
      `https://localhost:8081/project/${projectId}/removeEngineer/${softwareEngineerId}`,
      { headers }
    );
  }
  getProjects(): Observable<any[]> {
    return this.http.get<any[]>('https://localhost:8081/project/all');
  }
  engineerProjects(): Observable<any[]> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(
      'https://localhost:8081/softwareEngineer/allEngineerProject',
      { headers }
    );
  }
}
