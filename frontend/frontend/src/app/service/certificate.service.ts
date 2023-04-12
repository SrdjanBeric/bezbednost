import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
 import { Certificate } from '../data/certificate';
import { Observable } from 'rxjs';
import { CreateCertificateDto } from '../dto/Create-certificate.dto';
import { CertificateDto } from '../dto/Certificate.dto';
import {saveAs} from 'file-saver';

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
  
  revokeCertificate(CertificateDto:CertificateDto): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/revoke`,CertificateDto);
  }
  getAllCertificates():Observable<any>
  {
    return this.http.get<CertificateDto[]>(`${this.baseUrl}/all`);
  }
  downloadCertificate(CertificateDto:CertificateDto):any{
    /*this.http.get('this.baseUrl}/download', {responseType: "blob", headers: {}})
    .subscribe(blob => {
      saveAs(blob, 'download.cer');
    });*/
  }
}
