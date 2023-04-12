import { Component, OnInit } from '@angular/core';
import { CertificateService } from 'src/app/service/certificate.service';
import { CertificateDto } from 'src/app/dto/Certificate.dto';
@Component({
  selector: 'app-certificate-list',
  templateUrl: './certificate-list.component.html',
  styleUrls: ['./certificate-list.component.css'],
})
export class CertificateListComponent implements OnInit {
  public certificates: CertificateDto[] = [];
  constructor(private certificateService: CertificateService) {}
  public isValid: number = 0;

  ngOnInit(): void {
    this.certificateService.getAllCertificates().subscribe((res) => {
      this.certificates = res;
    });
  }
  revoke(cert: CertificateDto): void {
    this.certificateService.revokeCertificate(cert).subscribe();
  }
  download(cert: CertificateDto): void {
    this.certificateService.downloadCertificate(cert).subscribe();
  }
  validate(cert: CertificateDto): void {
    this.certificateService.validateCertificate(cert).subscribe((res) => {
      console.log('res', res);
      if (res) {
        this.isValid = 1;
      } else {
        this.isValid = 2;
      }
    });
  }
}
