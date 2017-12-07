import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { Router } from '@angular/router';

@Injectable()
export class EmployeeRoleGuard implements CanActivate {
    constructor(private router: Router) { }

    //check if the user can login to the system.
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

        console.log(localStorage.getItem('role'));

        if (localStorage.getItem('role') == 'employee') {
            return true;
        }

        this.router.navigate(['home']);
        return false;

    }


}