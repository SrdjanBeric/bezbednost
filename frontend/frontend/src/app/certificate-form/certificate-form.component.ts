import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CertificateService } from '../service/certificate.service';
import { CreateCertificateDto } from '../dto/Create-certificate.dto';
import { CertificateDto } from '../dto/Certificate.dto';
import { User } from '../data/user';
import { UserService } from '../service/user.service';
import { CertificateType } from '../data/certificate-type';
import jwtDecode, { JwtPayload} from 'jwt-decode';

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
  isAdmin: boolean = false;

  
  

  constructor(private certificateService: CertificateService,private userService: UserService) {}

  ngOnInit(): void {
    this.certificateService.getMyCertificates().subscribe(certs => {
      this.certificates = certs.filter(x=> x.authoritySubject != "ee");
    },(error) => {
      console.log(error)
    });

    this.userService.getAvailableUsers().subscribe( users => {
      this.users = users;
    }, (error) => {
      console.log(error);
    })

    const token = localStorage.getItem("access_token");
    if (token !== null) {
      const decodedToken = jwtDecode(token) as JwtPayload;
      const sub = decodedToken.sub;
      if (sub === "admin") {
        this.isAdmin = true;
      }
    }
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