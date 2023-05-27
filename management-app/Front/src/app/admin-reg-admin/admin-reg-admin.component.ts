import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-admin-reg-admin',
  templateUrl: './admin-reg-admin.component.html',
  styleUrls: ['./admin-reg-admin.component.css'],
})
export class AdminRegAdminComponent implements OnInit {
  constructor(private authSevice: AuthService) {}

  username: string = '';
  password: string = '';

  ngOnInit(): void {}

  submitForm(form: NgForm) {
    if (form.invalid || form.value.password !== form.value.confirmPassword) {
      console.log(`Your form is invalid`);
    } else {
      const registrationRequest = {
        email: form.value.email,
        //username: form.value.username,
        username: this.username, // changed
        password: this.password,
        //password: form.value.password,
        roleName: form.value.roleName,
        address: form.value.adress,
      };

      this.authSevice.signup(registrationRequest).subscribe(
        (response) => {
          console.log('User successfully registered');
        },
        (error) => {
          console.log('Error registering user', error);
        }
      );
    }
  }
}
