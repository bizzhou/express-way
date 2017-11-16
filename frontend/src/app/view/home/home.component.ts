import { Component, Input, Output, OnInit} from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { FlightSearch } from '../../model/flight-search';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  flight: any = {};

  classOption = [
    {value: 'economy', viewValue: 'Economy'},
    {value: 'business', viewValue: 'Business'},
    {value: 'first', viewValue: 'First'}
  ];

  fareOption = [
    {value: 'adult', viewValue: 'Adult'},
    {value: 'child', viewValue: 'Child'}
  ];

  constructor() { }

  ngOnInit() {
  }

  submitOneWaySearch() {
    console.log(this.flight);

  }


}
