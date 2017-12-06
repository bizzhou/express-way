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
import { DataService } from '../../service/data.service';
import { collectExternalReferences } from '@angular/compiler/src/output/output_ast';
import 'rxjs/Rx';


@Component({
  selector: 'app-multicity-flight-search',
  templateUrl: './multicity-flight-search.component.html',
  styleUrls: ['./multicity-flight-search.component.css'],
  providers: [FlightService, UserControlService, LoginService, DataService]
})
export class MulticityFlightSearchComponent implements OnInit {

  flightSearchArr: FlightSearch[];
  flightSearchRes: any[];

  constructor(private dataService: DataService, private dialog: MatDialog, private loginService: LoginService, private userControlService: UserControlService, private activateRoute: ActivatedRoute, private route: Router, private http: Http, private flightService: FlightService) {
  }


  ngOnInit() {
    // this.dataService.flightData.subscribe(message => this.flightSearchArr = message)
  }

  newMessage() {
    this.dataService.flightData.subscribe(message => this.flightSearchArr = message)
    console.log(this.flightSearchArr);
  }


  // ngOnInit() {

    // console.log(this.flightSearchArr);

    // this.activateRoute.queryParams
    //   .subscribe(params => {

    //     this.flightSearchArr = params as FlightSearch[];
    //     console.log(this.flightSearchArr);

    //     this.flightSearchArr.forEach(fs => {

    //       console.log(fs);

    //       this.flightService.getOneWaySearch(this.flightSearchArr)
    //         .subscribe(res => {
    //           this.flightSearchRes.push(res);
    //         });

    //       this.dataLoaded = true;

    //     });

    //   });

  // }



}
