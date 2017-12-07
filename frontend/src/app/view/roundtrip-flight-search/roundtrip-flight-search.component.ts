import { Component, OnInit } from '@angular/core';

import { FlightSearch } from '../../model/flight-search';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';
import { Reservation } from '../../model/reservation';
import { FlightService } from '../../service/flight.service';
import { Flight } from '../../model/flight';
import { Leg } from '../../model/leg';
import { Http } from '@angular/http';
import { UserControlService } from '../../service/user-control.service';
import { LoginService } from '../../service/login.service';
import { Customer } from '../../model/customer';
import { Auction } from '../../model/auction';
import { Include } from '../../model/include';
import { MatTableDataSource } from '../../service/table-data-source';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material'
import { UserReservationDialogComponent } from '../user-reservation-dialog/user-reservation-dialog.component';


@Component({
  selector: 'app-roundtrip-flight-search',
  templateUrl: './roundtrip-flight-search.component.html',
  styleUrls: ['./roundtrip-flight-search.component.css'],
  providers: [FlightService, UserControlService, LoginService]
})
export class RoundtripFlightSearchComponent implements OnInit {

  constructor(private dialog: MatDialog, private loginService: LoginService, private userControlService: UserControlService, private activateRoute: ActivatedRoute, private route: Router, private http: Http, private flightService: FlightService) { }

  flightSearches: FlightSearch[] = [];
  flights: any[];
  dataLoaded: boolean;

  fromTicket: any;
  toTicket: any;
  fromTicketFlag: boolean;
  toTicketFlag: boolean;

  bidFromSearch: any;
  bidToSearch: any;
  bidFromSearchFlag: boolean;
  bidToSearchFlag: boolean;

  bidPrice: number;

  ngOnInit() {

    this.activateRoute.queryParams
      .subscribe(params => {

        let toSearch = params as FlightSearch;
        this.flightSearches.push(toSearch)
        let returnSearch = Object.assign({}, toSearch);
        returnSearch.depatureDate = toSearch.returnDate;
        returnSearch.fromAirport = toSearch.toAirport;
        returnSearch.toAirport = toSearch.fromAirport;
        this.flightSearches.push(returnSearch);

      });

    this.flightService.getRoundTripSearch(this.flightSearches)
      .subscribe(res => {
        this.flights = res;
        this.dataLoaded = true;
      });

  }

  buyFromTicket(item: any) {
    this.fromTicket = item;
    this.fromTicketFlag = true;
    this.checkTickets();
  }

  buyToTicket(item: any) {
    this.toTicket = item;
    this.toTicketFlag = true;
    this.checkTickets();
  }

  bidFromTicket(item: any) {
    this.bidFromSearch = item;
    this.bidFromSearchFlag = true;
    this.checkBidTickets();
  }

  bidToTicket(item: any) {
    this.bidToSearch = item;
    this.bidToSearchFlag = true;
    this.checkBidTickets();
  }

  checkBidTickets() {

    if (this.bidToSearchFlag && this.bidFromSearchFlag) {

      let id = parseInt(this.loginService.getCurrentUser().person_id);

      this.userControlService.getUserProfile(id)
        .subscribe(res => {

          res = res as Customer;

          this.makeBid(res, this.bidFromSearch);

          this.makeBid(res, this.bidToSearch);

        });
    }

  }

  makeBid(cust: Customer, item: any) {

    if (item.length > 1) {

      item.forEach(element => {

        this.bidPrice = parseInt(prompt("Enter bid price"));
        let auction = this.buildAuction(cust, element);
        console.log(auction);

        this.flightService.reverseBid(auction)
          .subscribe(res => {
          });

      });
      //make one stop auction
    } else {

      this.bidPrice = parseInt(prompt("Enter bid price"));

      let auction = this.buildAuction(cust, item);

      this.flightService.reverseBid(auction)
        .subscribe(res => {
        });

    }

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


  checkTickets() {

    if (this.fromTicketFlag && this.toTicketFlag) {

      let id = parseInt(this.loginService.getCurrentUser().person_id);

      this.userControlService.getUserProfile(id)
        .subscribe(res => {

          res = res as Customer;

          let ret = {};

          localStorage.setItem('fromInclude', JSON.stringify(this.fromTicket));
          localStorage.setItem('toInclude', JSON.stringify(this.toTicket));

          let dialog = this.dialog.open(UserReservationDialogComponent, {
            height: '700px',
            width: '500px',
            data: ret
          });

          // make reservation
          dialog.afterClosed().subscribe(includeDetails => {

            this.makeOneWayResv(res, this.fromTicket, includeDetails);

            includeDetails.seatNumber = includeDetails.returnSeatNumber;
            console.log(includeDetails);

            this.makeOneWayResv(res, this.toTicket, includeDetails);
            this.route.navigate(['home']);

          });

        });

    }

  }

  makeOneWayResv(cust: Customer, item: any, includeDetails: any) {

    let resv: Reservation;
    let inc: Include;
    let includeArray: Include[] = [];

    if (item.length > 1) {

      resv = this.buildMutiStopResvation(cust, item);

      item.forEach(element => {

        inc = this.buildInclude(cust, element, includeDetails);
        includeArray.push(inc);

      });

      this.makeResv(resv, includeArray);

      //make one stop reservation
    } else {

      resv = this.buildReservation(cust, item);
      inc = this.buildInclude(cust, item, includeDetails);
      includeArray.push(inc);
      this.makeResv(resv, includeArray);

    }

  }

  makeResv(resv: Reservation, includeArray: Include[]) {
    this.flightService.oneWayReserve(resv, includeArray).subscribe(res => {
      console.log(res);
    });
  }

  buildReservation(cust: Customer, item: any) {

    let reservation = new Reservation();
    reservation.accountNumber = cust.account_number;
    reservation.totalFare = item.fare;
    reservation.bookingFare = 20.0;

    return reservation;

  }

  buildMutiStopResvation(cust: Customer, item: any) {

    let reservation = new Reservation();
    reservation.accountNumber = cust.account_number;
    reservation.totalFare = 0;
    reservation.bookingFare = 20.0;

    item.forEach(e => {
      reservation.totalFare += e.fare;
    });

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

  buildInclude(result, element, includeDetails) {

    let inc = new Include();

    inc.airlineId = element.airlineId;
    inc.deptDate = this.timeConverter(element.departureTime);
    inc.flightClass = element.classType;
    inc.flightNumber = element.flightNumber;
    inc.legNumber = element.legNumber;
    // should change to leg number-1??
    inc.fromStop = element.legNumber == 1 ? 1 : element.legNumber - 1;

    inc.firstName = includeDetails.firstName;
    inc.lastName = includeDetails.lastName;
    inc.meal = includeDetails.meal;
    inc.seatNumber = includeDetails.seatNumber;

    return inc;

  }


}
