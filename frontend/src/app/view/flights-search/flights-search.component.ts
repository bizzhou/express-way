import { Component, OnInit, Input } from '@angular/core';
import { FlightService } from '../../service/FlightService';
import { Flight } from '../../model/Flights';
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

  getFlightResults(): any {
    this.flightService.getFlights()
      .subscribe(
        data => { this.flights = data as Flight[]},
        // data => this.flights = data,
        // data => console.log(data),
        error => console.log("Canot get flight data"),
        () => console.log("Done fetching flights") 
      )
  }

  ngOnInit() {
      this.getFlightResults();
  }

  ab(){
    console.log(this.flights);
  }




}
