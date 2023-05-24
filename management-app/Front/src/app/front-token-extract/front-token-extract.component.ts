import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-front-token-extract',
  templateUrl: './front-token-extract.component.html',
  styleUrls: ['./front-token-extract.component.css'],
})
export class FrontTokenExtractComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const token = this.route.snapshot.paramMap.get('token')!;
    console.log(token);
    this.authService.loginViaToken(token).subscribe(
      (response) => {
        console.log(`here is the response:`);
        console.log(response);
        localStorage.setItem('access_token', response.accessToken);
        localStorage.setItem('refresh_token', response.refreshToken);
        console.log(`refresh token ${response.refreshToken}`);
        console.log(`access token: ${response.accessToken}`);
      },
      (error) => {
        console.log(`There was some sort of error`);
      }
    );
  }
}
