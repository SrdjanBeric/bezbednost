import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-email-page',
  templateUrl: './login-email-page.component.html',
  styleUrls: ['./login-email-page.component.css'],
})
export class LoginEmailPageComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  submitForm(form: NgForm) {
    this.authService.loginViaEmail(form.value.email).subscribe(
      (response) => {
        const role = this.authService.getRole();
        if (role === 'ADMIN') {
          this.router.navigate(['/admin']);
        }
        console.log('You have successfuly logged in!');
      },
      (error) => {
        console.log('Login failed:', error);
        // Show error message to user
      }
    );
  }
}
