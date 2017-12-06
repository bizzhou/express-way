import { Component, OnInit } from '@angular/core';
import { UserControlService } from '../../service/user-control.service';
import { LoginService } from '../../service/login.service';
import { MatTableDataSource } from '../../service/table-data-source';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Customer } from '../../model/customer';
import { Reservation } from '../../model/reservation';
import {CustomerControlService} from "../../service/customer-control.service";


@Component({
  selector: 'app-reservation-history',
  templateUrl: './reservation-history.component.html',
  styleUrls: ['./reservation-history.component.css'],
  providers: [UserControlService, LoginService, CustomerControlService]
})
export class ReservationHistoryComponent implements OnInit {

  dataSource: MatTableDataSource<any>;
  displayedColumns = ['resv_num', 'airline', 'flight_num', 'fare', 'from', 'to', 'date', 'first', 'last', 'cancel'];

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

  user: Customer;

  constructor(private userService: UserControlService,
              private loginService: LoginService,
              private customerControlService: CustomerControlService) { }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  cancel(element: any) {
    console.log(element);

    this.userService.cancelReservation(element.reservation_number);
  }

  ngOnInit() {
    let id = parseInt(this.loginService.getCurrentUser().person_id);

    this.userService.getUserProfile(id)
      .subscribe(res => {

        // get current user
        res = res as Customer;
        this.user = res as Customer;
        // get user bids
        this.userService.getAllReservationByAccountNumber(res.account_number).subscribe(response => {

          this.dataSource = new MatTableDataSource(response);
          console.log(this.dataSource);

        });

      });

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
