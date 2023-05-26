import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-password-page',
  templateUrl: './login-password-page.component.html',
  styleUrls: ['./login-password-page.component.css'],
})
export class LoginPasswordPageComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router) {}

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
        const role = this.authService.getRole();
        if (role === 'ADMIN') {
          this.router.navigate(['/admin']);
        }
        else 
        {
          this.router.navigate(['/user-profile'])
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
