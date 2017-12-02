import { Component, OnInit } from '@angular/core';
import { FlightService } from '../../service/flight.service';
import { Flight } from '../../model/flight';
import { Leg } from '../../model/leg';
import { Http } from '@angular/http';
import { DataService } from '../../service/data.service';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';

@Component({
  selector: 'app-flights-search',
  templateUrl: './flights-search.component.html',
  styleUrls: ['./flights-search.component.css'],
  providers: [FlightService, DataService]
})

export class FlightsSearchComponent implements OnInit {

  flights: any[];
  dataLoaded: boolean;
  flightSearch: any;
  flightInformation: any[];

  constructor(private activateRoute: ActivatedRoute, private route: Router, private data: DataService, private http: Http, private flightService: FlightService) { }

  ngOnInit() {
    this.activateRoute.queryParams
      .subscribe(params => {
        console.log(params);
        this.flightSearch = params;
      });

    this.flightService.getOneWaySearch(this.flightSearch)
      .subscribe(res => {
        this.flights = res;
        console.log(this.flights);
        this.dataLoaded = true;
      });

  }

  buy_ticket(item:any) {
    console.log("..");
    console.log(item);
  }

}
