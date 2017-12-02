import { Component, OnInit } from '@angular/core';
import { FlightService } from '../../service/flight.service';
import { Flight } from '../../model/flight';
import { Leg } from '../../model/leg';
import { Http } from '@angular/http';
import { LoginService } from '../../service/login.service';
import { Customer } from '../../model/customer';

import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';
import { Reservation } from '../../model/reservation';

@Component({
  selector: 'app-flights-search',
  templateUrl: './flights-search.component.html',
  styleUrls: ['./flights-search.component.css'],
  providers: [FlightService, LoginService]
})

export class FlightsSearchComponent implements OnInit {

  flights: any[];
  dataLoaded: boolean;
  flightSearch: any;
  flightInformation: any[];

  constructor(private loginService: LoginService, private activateRoute: ActivatedRoute, private route: Router, private http: Http, private flightService: FlightService) { }

  ngOnInit() {
    this.activateRoute.queryParams
      .subscribe(params => {
        console.log(params);
        this.flightSearch = params;
      });

    this.flightService.getOneWaySearch(this.flightSearch)
      .subscribe(res => {
        this.flights = res;
        console.log(this.flights);
        this.dataLoaded = true;
      });

  }

  buildReservation(cust: Customer, item: any) {

    console.log(cust);

    let reservation = new Reservation();
    reservation.account_number = cust.account_number;
    reservation.total_fare = item.fare;

    // two percent booking fee;
    reservation.booking_fee = 40.5;

    // customer rep ssn: need to work on this later
    // reservation.customer_rep_ssn = "";

    reservation.airline_id = item.airlineId;
    reservation.flight_number = item.flightNumber;
    reservation.leg_number = item.legNumber;
    reservation.passenger_lname = cust.last_name;
    reservation.passenger_fname = cust.first_name;

    //Todo: sample data, need to change this to correct data;
    reservation.dept_date = "2017-11-11";
    reservation.seat_number = "100";
    reservation.flight_class = item.classType;
    reservation.meal = "fish";
    reservation.from_stop_num = item.legNumber;

    return reservation;
  }

  buy_ticket(item: any) {

    let id = parseInt(this.loginService.getCurrentUser().person_id);

    this.loginService.getUserProfile(id).subscribe(
      res => {
        res = res as Customer;
        console.log(res);


        // make multi-stop reservation
        if (item.length > 1) {

          item.forEach(element => {


            let resv = this.buildReservation(res, element);
            console.log(resv);
            this.flightService.oneWayReserve(resv).subscribe(res => {
              console.log(res);
            });
          });

          //make one stop reservation
        } else {
          let resv = this.buildReservation(res, item);
          this.flightService.oneWayReserve(resv).subscribe(res => {
            console.log(res);
          });
        }


      },
      error => {
        console.log(error);
      }
    );

    console.log(item);



  }

}
