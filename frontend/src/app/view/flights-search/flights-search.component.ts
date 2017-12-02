import { Component, OnInit } from '@angular/core';
import { FlightService } from '../../service/flight.service';
import { Flight } from '../../model/flight';
import { Http } from '@angular/http';
import { DataService } from '../../service/data.service';
import { Leg } from '../../model/leg';

@Component({
  selector: 'app-flights-search',
  templateUrl: './flights-search.component.html',
  styleUrls: ['./flights-search.component.css'],
  providers: [FlightService, DataService]
})

export class FlightsSearchComponent implements OnInit {

  flights: Leg[];
  dataLoaded: boolean;
  flightSearch: any;

  constructor(private data: DataService, private http: Http, private flightService: FlightService) { }

  ngOnInit() {

    console.log(this.data.currentResultSubject);
    this.data.currentResultSubject.subscribe(res =>{
      this.flightSearch = this.data.currentResultSubject;
    });
    this.dataLoaded = true;

  }

  ab() {
    console.log(this.flights);
  }

}
