import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Certificate } from 'crypto';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CertificateService {
  constructor(private http: HttpClient) {}

  
     getUsers(): Observable<Certificate[]> {
      return this.http.get<Certificate[]>('http://localhost:8082/certificate/all');
     }
}
