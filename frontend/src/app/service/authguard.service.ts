import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { LoginService } from './login.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private loginService: LoginService) {}

  canActivate() {
    if (this.loginService.getToken() != null) {
        console.log("Done");
        return true;
    }

    this.router.navigate(['/login']);
    return false;
  }
}