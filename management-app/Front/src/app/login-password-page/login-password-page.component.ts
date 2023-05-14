import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login-password-page',
  templateUrl: './login-password-page.component.html',
  styleUrls: ['./login-password-page.component.css'],
})
export class LoginPasswordPageComponent implements OnInit {
  constructor(private authService: AuthService) {}

  ngOnInit(): void {}

  submitForm(form: NgForm) {
    console.log(form.value.username);
    console.log(
      JSON.stringify({
        username: form.value.username,
        password: form.value.password,
      })
    );
    this.authService.login(form.value.username, form.value.password).subscribe(
      (response) => {
        console.log('You have successfuly logged in!');
      },
      (error) => {
        console.log('Login failed:', error);
        // Show error message to user
      }
    );
  }
}
