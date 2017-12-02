import { Component, Input, Output, OnInit } from '@angular/core';

// import {Http} from '@angular/http';
import { FlightService } from '../../service/flight.service';
import { Observable } from 'rxjs/Observable';
import { Headers, Http, Response } from '@angular/http';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';

import { Leg } from '../../model/leg';
import { MatTableDataSource } from '../../service/table-data-source';


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
  flightSearchResult: any = [];

  dataSource: MatTableDataSource<Leg>;


  classType: string;
  fareType: string;

  constructor(private http: Http, private flightService: FlightService, private route: Router) {
  }

  ngOnInit() {
  }

  submitOneWaySearch(): any {

    console.log(this.classType);
    console.log(this.fareType);
    console.log(this.flightSearch);
    // this.flightSearch.depatureDate = this.flightSearch.depatureDate.toISOString();

    this.flightService.getOneWaySearch(this.flightSearch).subscribe(res => {
      this.flightSearch = res;
      this.flightService.setFlightSearchResult(this.flightSearch);
      // this.route.navigate(['flights']);
      this.route.navigateByUrl("/flights");
    });


  }


}
