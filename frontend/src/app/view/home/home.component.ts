import { Component, Input, Output, OnInit } from '@angular/core';

// import {Http} from '@angular/http';
import {FlightService} from '../../service/flight.service';
import {Observable} from 'rxjs/Observable';
import { Headers, Http, Response } from '@angular/http';

import { Leg } from '../../model/leg';
import {MatTableDataSource} from '../../service/table-data-source';


const FLIGHT_CONTROL_API = 'http://localhost:8080/';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [FlightService]
})
export class HomeComponent implements OnInit {

  flight: any = {};
  flightSearch: any = {};
  legs: Leg[];

  dataSource: MatTableDataSource<Leg>;

  classOption = [
    { value: 'economy', viewValue: 'Economy' },
    { value: 'business', viewValue: 'Business' },
    { value: 'first', viewValue: 'First' }
  ];

  fareOption = [
    { value: 'adult', viewValue: 'Adult' },
    { value: 'child', viewValue: 'Child' }
  ];

  constructor(private http: Http, private flightService: FlightService) {
  }

  ngOnInit() {
  }

  submitOneWaySearch(): any {

    this.flightService.getOneWaySearch(this.flightSearch);

  }


}
