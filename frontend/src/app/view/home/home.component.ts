import { Component, Input, Output, OnInit } from '@angular/core';

import { FlightService } from '../../service/flight.service';
import { Observable } from 'rxjs/Observable';
import { Headers, Http, Response } from '@angular/http';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';
import { DataService } from '../../service/data.service';
import { Leg } from '../../model/leg';

const FLIGHT_CONTROL_API = 'http://localhost:8080/';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [FlightService, DataService]
})
export class HomeComponent implements OnInit {

  flight: any = {};
  flightSearch: any = {};
  legs: Leg[];
  flightSearchResult: Leg[];

  classType: string;
  fareType: string;

  constructor(private data: DataService, private http: Http, private flightService: FlightService, private route: Router) {
  }

  ngOnInit() {
    this.data.currentResultSubject.subscribe(res => {
      this.flightSearchResult = res;
    });
  }

  submitOneWaySearch(): any {

    this.flightService.getOneWaySearch(this.flightSearch)
      .subscribe(res => {

        this.flightSearchResult = res as Leg[];
        console.log(this.flightSearchResult);
        this.data.changeFlightResultSubject(this.flightSearchResult);

      });

    this.route.navigate(['flights']);

  }

}
