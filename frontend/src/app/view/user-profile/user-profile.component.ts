import { Component, OnInit } from '@angular/core';

import { LoginService } from '../../service/login.service';
import { UserControlService } from '../../service/user-control.service';
import { Http, Response } from '@angular/http';
import { Customer } from '../../model/customer';
import {MatTableDataSource} from "@angular/material";
import {CustomerControlService} from "../../service/customer-control.service";

@Component({
  selector: 'app-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
  providers: [LoginService, UserControlService, CustomerControlService]
})
export class UserProfileComponent implements OnInit {

  constructor(private logInservice: LoginService, private http: Http, private userControlService: UserControlService,
              private customerControlService: CustomerControlService) { }

  user: Customer;
  name: string;
  loaded: boolean;
  // account_number: string;



  /**
   * Get current user information from the database based on his personId
   */
  getUser(): any {

    let id = parseInt(this.logInservice.getCurrentUser().person_id);
    this.userControlService.getUserProfile(id).subscribe(
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
    // this.getBestSellerFlights();

  }

  updateUser() {
    this.userControlService.updateUser(this.user);
    window.location.reload();
  }


}
