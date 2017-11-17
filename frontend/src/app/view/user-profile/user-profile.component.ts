import { Component, OnInit } from '@angular/core';

import { LoginService } from '../../service/login.service';
import { UserControlService } from '../../service/user-control.service';
import { Http, Response } from '@angular/http';
import { Customer } from '../../model/customer';

@Component({
  selector: 'app-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
  providers: [LoginService, UserControlService]
})
export class UserProfileComponent implements OnInit {

  constructor(private logInservice: LoginService, private http: Http, private userControlService: UserControlService) { }

  user: Customer;
  name: string;
  loaded: boolean;


  getUser(): any {

    // let id = this.logInservice.getCurrentUser().person_id;
    
    let id = parseInt(this.logInservice.getCurrentUser().person_id);
    console.log(id);
    
    this.logInservice.getUserProfile(id).subscribe(
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

    this.getUser( );

  }

  updateUser() {
    this.userControlService.updateUser(this.user);
    window.location.reload();
  }

}
