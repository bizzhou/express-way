import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

@Injectable()
export class EmployeeRoleGuard implements CanActivate {
    constructor(private router: Router, private loginService: LoginService) { }

    //check if the user can login to the system.
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

        console.log(localStorage.getItem('role'));
        console.log('adsfioio');

        if (localStorage.getItem('role') == 'employee') {
            console.log('...ei');
            return true;
        }

        this.router.navigate(['home']);
        return false;

    }


}