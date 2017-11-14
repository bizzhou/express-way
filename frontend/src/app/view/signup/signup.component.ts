import { Component } from '@angular/core';
import { SignUpService } from '../../service/signup.service';

import { Http } from '@angular/http';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  providers: [SignUpService]
})
export class SignupComponent {

  customer : any = {};

  constructor(private http: Http, private signUpServie: SignUpService, private route: Router) {
  }

  register(){

    this.signUpServie.signup(this.customer)
        .then(response => {
          if(response == 'true'){
            this.route.navigate(['login']);
          } else {
            console.log("Input not valid");
          }
        })
        .catch(error => console.log(error));


  }


}
