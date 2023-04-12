import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  username: string = "";
  password: string = "";

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  onSignIn(): void {
    this.authService.login(this.username, this.password).subscribe(
      (result) => {
        // If login is successful, navigate to user list page
        this.router.navigate(['/user-list']);
      },
      (error) => {
        console.log('Login failed:', error);
        // Show error message to user
      }
    );
  }

  onRegister(): void {
    this.router.navigate(['/registration']);
  }
  

}
