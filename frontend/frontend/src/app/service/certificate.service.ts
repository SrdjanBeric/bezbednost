import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateCertificateDto } from '../dto/Create-certificate.dto';
import { CertificateDto } from '../dto/Certificate.dto';

@Injectable({ providedIn: 'root' })
export class CertificateService {
  constructor(private http: HttpClient) {}

  private baseUrl = 'http://localhost:8082/certificate';

  getMyCertificates(): Observable<CertificateDto[]> {
    return this.http.get<CertificateDto[]>(`${this.baseUrl}/myCertificates`);
  }

  createCertificate(createCertificateDto: CreateCertificateDto):Observable<CreateCertificateDto>{
    return this.http.post<CreateCertificateDto>(`${this.baseUrl}/create`,createCertificateDto);
  }
  
}
