import { Component, OnInit } from '@angular/core';
import { CertificateService } from 'src/app/service/certificate.service';
import { CertificateDto } from 'src/app/dto/Certificate.dto';
@Component({
  selector: 'app-user-certificates',
  templateUrl: './user-certificates.html'})
export class UserCertificates implements OnInit {

  public certificates:CertificateDto[]=[];
  constructor(private certificateService:CertificateService) { }

  ngOnInit(): void {
    this.certificateService.getMyCertificates().subscribe(res=>
      {
        this.certificates=res;
      });
  }
  revoke(cert:CertificateDto):void{
    this.certificateService.revokeCertificate(cert).subscribe();
  }
  download(cert:CertificateDto):void{
    this.certificateService.downloadCertificate(cert).subscribe();
  }
}
