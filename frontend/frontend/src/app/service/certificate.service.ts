import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Certificate } from '../data/certificate';
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

  createCertificate(
    createCertificateDto: CreateCertificateDto
  ): Observable<CreateCertificateDto> {
    return this.http.post<CreateCertificateDto>(
      `${this.baseUrl}/create`,
      createCertificateDto
    );
  }

  revokeCertificate(CertificateDto: CertificateDto): Observable<any> {
    return this.http.post<any>(
      `${this.baseUrl}/revoke/v2`,
      CertificateDto.serialNumberSubject
    );
  }

  validateCertificate(CertificateDto: CertificateDto): Observable<any> {
    return this.http.get<any>(
      `${this.baseUrl}/valid/${CertificateDto.serialNumberSubject}`
    );
  }

  getAllCertificates(): Observable<any> {
    return this.http.get<CertificateDto[]>(`${this.baseUrl}/all`);
  }
  downloadCertificate(CertificateDto: CertificateDto): any {
    const url = `${this.baseUrl}/download/${CertificateDto.serialNumberSubject}`;
    this.http.get(url, { responseType: 'blob' }).subscribe((blob) => {
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = `${CertificateDto.serialNumberSubject}.cer`;
      link.click();
      window.URL.revokeObjectURL(link.href);
    });
  }
}
