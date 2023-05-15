import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthService } from './auth.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const allowedRoles = route.data.allowedRoles;
    console.log(`dozvoljenje role` + allowedRoles);
    const userRole = this.authService.getRole();
    console.log(`User Rola` + userRole);

    if (allowedRoles.includes(userRole)) {
      return true; // Allow access to the route
    } else {
      this.router.navigate(['/login']); // Redirect to login page or another page for unauthorized users
      return false; // Deny access to the route
    }
  }
}
