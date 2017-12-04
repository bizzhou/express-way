import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Headers, Http, Response } from '@angular/http';
import { Injectable, Input, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Flight } from '../model/flight';
import { Leg } from '../model/leg';
import { Auction } from '../model/auction';
import { FlightSearch } from '../model/flight-search';
import { Reservation } from '../model/reservation';

const FLIGHT_CONTROL_API = 'http://localhost:8080';

@Injectable()
export class FlightService {

  eventEmitter: EventEmitter<any> = new EventEmitter();

  errorHandler(error): any {
    console.log(error);
    return Observable.throw(error.json.error || 'Server error');
  }

  constructor(private http: Http) {
  }

  oneWayReserve(reservation: Reservation) {
    return this.http.post(FLIGHT_CONTROL_API + "/one-way-resv", reservation)
      .map(res => res.json())
      .catch(this.errorHandler);
  }

  getOneWaySearch(flightSearch: any) {

    return this.http.post(FLIGHT_CONTROL_API + '/flight/search', flightSearch)
      .map(res => res.json())
      .catch(this.errorHandler);
  }

  reverseBid(auction: Auction) {

    return this.http.post(FLIGHT_CONTROL_API + '/reverse-bid', auction)
      .map(res => res.json())
      .catch(this.errorHandler);

  }

}
