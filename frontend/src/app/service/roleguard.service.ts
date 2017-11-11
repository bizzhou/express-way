import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

@Injectable()
export class RoleGuard implements CanActivate {
    constructor(private router: Router, private loginService: LoginService) { }

    // canLoad()


    //check if the user can login to the system.
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return true;
    }

    
}