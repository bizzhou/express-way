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
import 'rxjs/Rx';


@Component({
  selector: 'app-multicity-flight-search',
  templateUrl: './multicity-flight-search.component.html',
  styleUrls: ['./multicity-flight-search.component.css'],
  providers: [FlightService, UserControlService, LoginService]
})
export class MulticityFlightSearchComponent implements OnInit {

  flightSearchArr: FlightSearch[];
  flightSearchRes: any[] = [];
  dataLoaded: number = 0;
  bidPrice: number;
  index: number = 0;

  constructor(private dialog: MatDialog, private loginService: LoginService, private userControlService: UserControlService, private route: Router, private http: Http, private flightService: FlightService) {
  }

  ngOnInit() {

    let flightSearches = localStorage.getItem('flightSearchArr');
    this.flightSearchArr = JSON.parse(flightSearches) as FlightSearch[];

    localStorage.removeItem('flightSearchArr');
    console.log(this.flightSearchArr);

    this.flightSearchArr.forEach(fs => {


      this.flightService.getOneWaySearch(fs)
        .subscribe(fsRes => {
          this.flightSearchRes.push(fsRes);
          this.dataLoaded++;
        });

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

  buildInclude(result, element, includeDetails) {

    let inc = new Include();

    inc.airlineId = element.airlineId;
    inc.deptDate = element.departureTime;
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

  buyTicket(item: any) {

    console.log("hhhiie");

    let id = parseInt(this.loginService.getCurrentUser().person_id);

    localStorage.setItem('include', JSON.stringify(item));

    console.log(id);

    this.userControlService.getUserProfile(id)
      .subscribe(cust => {

        cust = cust as Customer;

        let resv: Reservation;
        let inc: Include;
        let ret = {};

        let dialog = this.dialog.open(UserReservationDialogComponent, {
          height: '700px',
          width: '500px',
          data: ret
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
        alert("Login first");
      });

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
        alert("Login First");
      });


  }

  makeResv(resv: Reservation, includeArray: Include[]) {

    this.flightService.oneWayReserve(resv, includeArray).subscribe(res => {
      console.log(res);
    });

  }


}
