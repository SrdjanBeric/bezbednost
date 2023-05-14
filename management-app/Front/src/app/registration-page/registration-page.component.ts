import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css'],
})
export class RegistrationPageComponent implements OnInit {
  constructor(private authSevice: AuthService) {}

  ngOnInit(): void {}

  submitForm(form: NgForm) {
    if (form.invalid || form.value.password !== form.value.confirmPassword) {
      console.log(`Your form is invalid`);
    } else {
      const registrationRequest = {
        email: form.value.email,
        username: form.value.username,
        password: form.value.password,
        roleName: form.value.roleName,
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
