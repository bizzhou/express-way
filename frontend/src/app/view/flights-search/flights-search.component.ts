import { Component, OnInit } from '@angular/core';
import { FlightService } from '../../service/flight.service';
import { Flight } from '../../model/flight';
import { Leg } from '../../model/leg';
import { Http } from '@angular/http';
import { UserControlService } from '../../service/user-control.service';
import { LoginService } from '../../service/login.service';
import { Customer } from '../../model/customer';
import { Auction } from '../../model/auction';
import { Include } from '../../model/include';

import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';
import { Reservation } from '../../model/reservation';

@Component({
  selector: 'app-flights-search',
  templateUrl: './flights-search.component.html',
  styleUrls: ['./flights-search.component.css'],
  providers: [FlightService, UserControlService, LoginService]
})

export class FlightsSearchComponent implements OnInit {

  flights: any[];
  dataLoaded: boolean;
  flightSearch: any;
  flightInformation: any[];
  bidPrice: number;

  constructor(private loginService: LoginService, private userControlService: UserControlService, private activateRoute: ActivatedRoute, private route: Router, private http: Http, private flightService: FlightService) { }

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

  // build reservatio object for backend
  buildReservation(cust: Customer, item: any) {

    let reservation = new Reservation();
    reservation.accountNumber = cust.account_number;
    reservation.totalFare = item.fare;

    // two percent booking fee;
    reservation.bookingFare = 20.0;

    // customer rep ssn: need to work on this later
    // reservation.customer_rep_ssn = "";

    // reservation.airline_id = item.airlineId;
    // reservation.flight_number = item.flightNumber;
    // reservation.leg_number = item.legNumber;
    // reservation.passenger_lname = cust.last_name;
    // reservation.passenger_fname = cust.first_name;

    // //Todo: sample data, need to change this to correct data;
    // reservation.dept_date = item.departureTime;
    // reservation.seat_number = "100";
    // reservation.flight_class = item.classType;
    // reservation.meal = "fish";
    // reservation.from_stop_num = item.legNumber;

    return reservation;
  }

  timeConverter(dateString: string) {
    let a = new Date(dateString);
    let year = a.getFullYear();
    let month = a.getMonth() + 1;
    let date = a.getDate();
    let hour = a.getHours();
    let min = a.getMinutes();
    let sec = a.getSeconds();
    let time = year + '-' + month + '-' + date + ' ' + hour + ':' + min + ':' + sec;
    return time;
  }

  buildInclude(result, element) {

    console.log(element);

    let inc = new Include();

    inc.airlineId = element.airlineId;
    inc.deptDate = this.timeConverter(element.departureTime);
    inc.firstName = result.first_name;
    inc.lastName = result.last_name;
    inc.flightClass = element.classType;
    inc.flightNumber = element.flightNumber;
    inc.legNumber = element.legNumber;
    // should change to leg number-1??
    inc.fromStop = element.legNumber == 1 ? 1 : element.legNumber - 1;
    inc.meal = "fish";
    inc.seatNumber = 33;

    return inc;

  }



  //build auction object for backend
  buildAuction(cust: Customer, item: any) {

    let auction = new Auction();
    auction.accountNumber = cust.account_number;
    auction.airlineId = item.airlineId;
    auction.bidPrice = this.bidPrice;
    auction.depatureDate = item.departureTime;
    auction.flightClass = item.classType;
    auction.flightNumber = item.flightNumber;
    auction.legNumber = item.legNumber;

    return auction;

  }


  bidTicket(item: any) {

    let id = parseInt(this.loginService.getCurrentUser().person_id);

    this.userControlService.getUserProfile(id)
      .subscribe(res => {
        res = res as Customer;
        console.log(res);

        // make multi-stop auction
        if (item.length > 1) {



          item.forEach(element => {
            this.bidPrice = parseInt(prompt("Enter bid price"));
            let auction = this.buildAuction(res, element);
            console.log(auction);

            this.flightService.reverseBid(auction).subscribe(res => {
              console.log(res);
            });

          });
          //make one stop auction
        } else {

          this.bidPrice = parseInt(prompt("Enter bid price"));

          let auction = this.buildAuction(res, item);
          this.flightService.reverseBid(auction).subscribe(res => {
            console.log(res);
          });
        }
      },
      error => {
        console.log(error);
      });


  }

  buyTicket(item: any) {

    let id = parseInt(this.loginService.getCurrentUser().person_id);

    this.userControlService.getUserProfile(id)
      .subscribe(cust => {
        cust = cust as Customer;
        console.log(cust);


        // make multi-stop reservation
        if (item.length > 1) {

          item.forEach(element => {


            let resv = this.buildReservation(cust, element);
            let inc = this.buildInclude(cust, element)
            console.log(resv);
            this.flightService.oneWayReserve(resv, inc).subscribe(res => {
              console.log(res);
            });
          });

          //make one stop reservation
        } else {
          let resv = this.buildReservation(cust, item);
          let inc = this.buildInclude(cust, item);
          this.flightService.oneWayReserve(resv, item).subscribe(res => {
            console.log(res);
          });
        }


      },
      error => {
        console.log(error);
      });

  }

}
