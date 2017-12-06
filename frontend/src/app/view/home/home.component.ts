import { Component, Input, Output, OnInit } from '@angular/core';

import { FlightService } from '../../service/flight.service';
import { Observable } from 'rxjs/Observable';
import { Headers, Http, Response } from '@angular/http';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';

import { Leg } from '../../model/leg';
import { FlightSearch } from '../../model/flight-search';
import { DataService } from '../../service/data.service';

const FLIGHT_CONTROL_API = 'http://localhost:8080/';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [FlightService, DataService]
})
export class HomeComponent implements OnInit {

  flightSearch: any = {};

  classType: string;
  fareType: string;
  arr: FlightSearch[] = [];

  constructor(private dataService: DataService, private http: Http, private flightService: FlightService, private route: Router) {
  }

  ngOnInit() {
    this.arr.push(new FlightSearch());
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

  addAnotherCity() {
    let fs = new FlightSearch();
    this.arr.push(fs);
  }

  submitMutiCity() {

    this.arr.forEach(e => {
      e.classType = this.flightSearch.classType;
      e.fareType = this.flightSearch.fareType;
      e.depatureDate = this.timeConverter(e.depatureDate);
    })

    this.dataService.updateDate(this.arr);
    console.log(this.dataService.flightData);

    this.route.navigate(["flights/multicity"]);

  }


  timeConverter(d: string) {

    let a = new Date(d);
    let year = a.getFullYear();
    let month = a.getMonth() + 1;
    let date = a.getDate();
    let hour = a.getHours();
    let min = a.getMinutes();
    let sec = a.getSeconds();
    let time = year + '-' + month + '-' + date + ' ' + hour + ':' + min + ':' + sec;

    return time;

  }

}
