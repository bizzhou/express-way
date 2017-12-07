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
import { MatTableDataSource } from '../../service/table-data-source';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material'
import { UserReservationDialogComponent } from '../user-reservation-dialog/user-reservation-dialog.component';

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

  constructor(private dialog: MatDialog, private loginService: LoginService, private userControlService: UserControlService, private activateRoute: ActivatedRoute, private route: Router, private http: Http, private flightService: FlightService) { }

  ngOnInit() {

    this.activateRoute.queryParams
      .subscribe(params => {
        this.flightSearch = params;
      });

    this.flightService.getOneWaySearch(this.flightSearch)
      .subscribe(res => {
        this.flights = res;
        this.dataLoaded = true;
      });

  }

  // build reservatio object for backend
  buildReservation(cust: Customer, item: any) {

    console.log(item);
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

  //build auction object for backend
  buildAuction(cust: Customer, item: any) {
    console.log(item);

    let auction = new Auction();
    auction.accountNumber = cust.account_number;
    auction.airlineId = item.airlineId;
    auction.bidPrice = this.bidPrice;
    auction.depatureDate = this.timeConverter(item.departureTime);
    auction.flightClass = item.classType;
    auction.flightNumber = item.flightNumber;
    auction.legNumber = item.legNumber;

    console.log(auction);

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

        let resv: Reservation;
        let inc: Include;
        let ret = {};

        localStorage.setItem('include', JSON.stringify(item));

        let dialog = this.dialog.open(UserReservationDialogComponent, {
          height: '700px',
          width: '500px',
          data: ret,
        });

        dialog.afterClosed().subscribe(includeDetails => {

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

        });

      },
      error => {
        console.log(error);
      });

  }

  makeResv(resv: Reservation, includeArray: Include[]) {

    this.flightService.oneWayReserve(resv, includeArray).subscribe(res => {
      console.log(res);
    });

  }


}
