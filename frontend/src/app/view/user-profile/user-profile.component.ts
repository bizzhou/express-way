import { Component, OnInit } from '@angular/core';

import { LoginService } from '../../service/login.service';
import { UserControlService } from '../../service/user-control.service';
import { Http, Response } from '@angular/http';
import { Customer } from '../../model/customer';
import {MatTableDataSource} from "@angular/material";
import {CustomerControlService} from "../../service/customer-control.service";
import {User} from "../../model/user";
import {Employee} from "../../model/employee";

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
  employee: Employee;
  name: string;
  loaded: boolean;
  // account_number: string;
  private currentUser: User;
  userAuthenticated: boolean;
  employeeAuthenticate: boolean;

  ngOnInit() {

    this.getUser();

  }

  /**
   * Get current user information from the database based on his personId
   */
  getUser(): any {

    this.determineUser();
    let id = parseInt(this.currentUser.person_id);
    if (this.userAuthenticated == true) {
      this.userControlService.getUserProfile(id).subscribe(
        res => {
          this.user = res as Customer;
          this.name = this.user.first_name + ' ' + this.user.last_name;
          this.loaded = true;
          console.log(this.user);
        },
        error => {
          console.log(error);
        }
      );
    }
    else if(this.employeeAuthenticate) {
      this.userControlService.getEmployeeProfile(id).subscribe(
        res => {
          this.employee = res as Employee;
          this.name = this.employee.username;
          this.loaded = true;
          console.log(this.employee);
        },
        error => {
          console.log(error);
        }
      );
    }
    console.log("person id : " + id);

  }

  // if it is customer or employee
  determineUser() {
    this.currentUser = this.logInservice.getCurrentUser();
    if (this.currentUser.username !== null && this.currentUser.person_id != null) {

      if (this.currentUser.role == "user") this.userAuthenticated = true;
      if (this.currentUser.role == "employee") this.employeeAuthenticate = true;

    } else {

      this.userAuthenticated = false;
      this.employeeAuthenticate = false;

    }
  }


  updateUser() {
    this.userControlService.updateUser(this.user);
    window.location.reload();
  }


}
