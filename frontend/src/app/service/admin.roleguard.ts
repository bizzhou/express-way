import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

@Injectable()
export class AdminRoleGuard implements CanActivate {
    constructor(private router: Router, private loginService: LoginService) { }

    // canLoad()


    //check if the user can login to the system.
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

        if(localStorage.getItem('role') == 'admin'){
            return true;
        }

        this.router.navigate(['home']);
        return false;

        
    }


}