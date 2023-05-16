import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class GuestGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const isLoggedIn = this.authService.isUserLoggedIn();

    if (isLoggedIn) {
      // User is logged in, redirect to another page
      console.log(
        `Sorry you are already logged in so you can't access those pages`
      );
      return false;
    } else {
      // User is not logged in, allow access to the route
      return true;
    }
  }
}
