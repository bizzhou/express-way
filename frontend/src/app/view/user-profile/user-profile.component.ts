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

  // travel itinerary
  resvNumber: string;
  travelItineraryFlag: boolean;
  travelItineraryResult: MatTableDataSource<any>;
  travelItineraryResultCol = ['airline_id','flight_number', 'from_airport', 'departure_time', 'to_airport', 'arrival_time'];

  // best-seller list of flights
  bestSellerFlightFlag: boolean;
  bestSellerFlights: MatTableDataSource<any>;
  bestSellerFlightsCol = ['airline_id', 'flight_number', 'from_airport', 'to_airport'];

  // personalized flight suggestion list
  personalizedFlights: MatTableDataSource<any>;

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

  getTravelItinerary() {
    this.customerControlService.getTravelItinerary(this.user.account_number, this.resvNumber)
      .subscribe(res => {
        this.travelItineraryResult = new MatTableDataSource<Element>(res);
        console.log(res);
      });

    this.travelItineraryFlag = true;
  }

  getBestSellerFlights() {
    this.customerControlService.getBestSellerFlights(this.user.account_number)
      .subscribe(res => {
      this.bestSellerFlights = new MatTableDataSource<Element>(res);
      console.log(this.bestSellerFlights);
    }, error => {
      console.log("error on getting best-seller list of flights");
    });
    this.bestSellerFlightFlag = true;
  }

  getPersonalizedFlights() {
    this.customerControlService.getPersonalizedFlights(this.user.account_number)
      .subscribe(res => {
      this.personalizedFlights = new MatTableDataSource<Element>(res);
      console.log(this.personalizedFlights);
    }, error => {
      console.log("error on getting personalized list of flights");
    });
  }


}
