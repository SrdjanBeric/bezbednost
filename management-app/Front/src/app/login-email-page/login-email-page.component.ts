import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login-email-page',
  templateUrl: './login-email-page.component.html',
  styleUrls: ['./login-email-page.component.css'],
})
export class LoginEmailPageComponent implements OnInit {
  constructor(private authService: AuthService) {}

  ngOnInit(): void {}

  submitForm(form: NgForm) {
    this.authService.loginViaEmail(form.value.email).subscribe(
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
