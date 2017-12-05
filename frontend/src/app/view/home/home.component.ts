import { Component, Input, Output, OnInit } from '@angular/core';

import { FlightService } from '../../service/flight.service';
import { Observable } from 'rxjs/Observable';
import { Headers, Http, Response } from '@angular/http';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';

import { Leg } from '../../model/leg';
import { FlightSearch } from '../../model/flight-search';

const FLIGHT_CONTROL_API = 'http://localhost:8080/';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [FlightService]
})
export class HomeComponent implements OnInit {

  flightSearch: any = {};

  classType: string;
  fareType: string;

  constructor(private http: Http, private flightService: FlightService, private route: Router) {
  }

  ngOnInit() {
  }


  // Delegate the search to flight search
  submitOneWaySearch(): any {
    if (this.flightSearch != null) {
      this.route.navigate(["flights"], { queryParams: this.flightSearch });
    }
  }


  submitTwoWaySearch(): any {
    if (this.flightSearch != null) {
      this.route.navigate(["flights/roundtrip"], { queryParams: this.flightSearch });
    }
  }



}
