import { Component } from '@angular/core';

import { SignUpService } from '../../service/signup.service';

import { Http } from '@angular/http';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';

@Component({
  selector: 'app-employee-signup',
  templateUrl: './employee-signup.component.html',
  styleUrls: ['./employee-signup.component.css'],
  providers: [SignUpService]
})
export class EmployeeSignupComponent {

  constructor(private http: Http, private signUpService: SignUpService, private route: Router) { }

  employee: any = {};

  register() {

    this.signUpService.employeeSignup(this.employee)
      .then(response => {

        console.log(response);

        if (response == true) {
          this.route.navigate(['login']);
        } else {
          console.log("Input not valid, please user another username/password");
        }
      })
      .catch(error => console.log(error));

  }

}
