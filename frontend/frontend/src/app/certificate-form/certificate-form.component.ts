import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CertificateService } from '../service/certificate.service';
import { CreateCertificateDto } from '../dto/Create-certificate.dto';
import { CertificateDto } from '../dto/Certificate.dto';
import { User } from '../data/user';
import { UserService } from '../service/user.service';
import { CertificateType } from '../data/certificate-type';

@Component({
  selector: 'app-certificate-form',
  templateUrl: './certificate-form.component.html',
  styleUrls: ['./certificate-form.component.css']
})
export class CertificateFormComponent implements OnInit {

  CertificateType = CertificateType;

  createCertificateDto: CreateCertificateDto = {
    commonNameSubject: '',
    nameSubject: '',
    surnameSubject: '',
    usernameSubject: '',
    countrySubject: '',
    startDate: undefined,
    endDate: undefined,
    authoritySubject: '',
    issuerSerialNumber: '',
    ownerUsername: ''
  };

  certificates: CertificateDto[] = [];
  users: User[]=[];
  

  constructor(private certificateService: CertificateService,private userService: UserService) {}

  ngOnInit(): void {
    this.certificateService.getMyCertificates().subscribe(certs => {
      this.certificates = certs;
    },(error) => {
      console.log(error)
    });

    this.userService.getUsers().subscribe( users => {
      this.users = users;
    }, (error) => {
      console.log(error);
    })
  }

  onSubmit() {
    this.certificateService.createCertificate(this.createCertificateDto).subscribe(() => {
      // handle success
      console.log("Successfully created certificate")
    }, (error) => {
      // handle error
      console.log(error)
    });
  }
  

}