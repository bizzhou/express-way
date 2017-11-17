import { Component, OnInit } from '@angular/core';

import { LoginService } from '../../service/login.service';
import { Http, Response } from '@angular/http';
import { Customer } from '../../model/customer';

@Component({
  selector: 'app-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
  providers: [LoginService]
})
export class UserProfileComponent implements OnInit {

  constructor(private logInservice: LoginService, private http: Http) { }

  user: Customer;
  name: string;
  loaded: boolean;

  getUser(): any {
    this.logInservice.getUserProfile(10).subscribe(
      res => {
        this.user = res as Customer;
        this.name = this.user.first_name + ' ' + this.user.last_name
        this.loaded = true;
        console.log(this.user);
      },
      error => {
        console.log(error);
      }
    );
  }

  ngOnInit() {
    this.getUser();
  }



}
