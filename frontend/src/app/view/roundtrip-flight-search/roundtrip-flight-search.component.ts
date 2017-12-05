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
        console.log(this.flights);
      });
  }

}
