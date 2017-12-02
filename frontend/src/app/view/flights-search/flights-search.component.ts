import { Component, OnInit } from '@angular/core';
import { FlightService } from '../../service/flight.service';
import { Flight } from '../../model/flight';
import { Http } from '@angular/http';

@Component({
  selector: 'app-flights-search',
  templateUrl: './flights-search.component.html',
  styleUrls: ['./flights-search.component.css'],
  providers: [FlightService]
})

export class FlightsSearchComponent implements OnInit {

  flights: Flight[];

  constructor(private http: Http, private flightService: FlightService) { }


  ngOnInit() {
    console.log("Flight search");
    console.log(this.flightService.getFlightSearchResult());
  }

  ab() {
    console.log(this.flights);
  }

}
