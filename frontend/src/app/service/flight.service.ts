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
import { Include } from '../model/include';

const FLIGHT_CONTROL_API = 'http://localhost:8080';

@Injectable()
export class FlightService {

  eventEmitter: EventEmitter<any> = new EventEmitter();

  errorHandler(error): any {
    alert(error);
    return Observable.throw(error.json.error || 'Server error');
  }

  bidErrorHandler(error): any {
    alert("Cann't bid more than once!");
    return Observable.throw(error.json.error || 'Server error');
  }

  constructor(private http: Http) {
  }

  oneWayReserve(reservation: Reservation, inc: Include) {
    let context = {
      "reservation": {},
      "include": {}
    };
    context.reservation = reservation;
    context.include = inc;


    return this.http.post(FLIGHT_CONTROL_API + "/one-way-resv", context)
      .map(res => res.json())
      .catch(this.errorHandler);
  }

  timeConverter(dateString: string) {
    let a = new Date(dateString);
    let year = a.getFullYear();
    let month = a.getMonth() + 1;
    let date = a.getDate();
    let hour = a.getHours();
    let min = a.getMinutes();
    let sec = a.getSeconds();
    let time = year + '-' + month + '-' + date + ' ' + hour + ':' + min + ':' + sec;
    return time;
  }

  getOneWaySearch(flightSearch: any) {

    let date = this.timeConverter(flightSearch.depatureDate);
    console.log(date);

    let chagedDateObject = Object.assign({}, flightSearch);
    chagedDateObject.depatureDate = date;

    console.log(chagedDateObject);

    return this.http.post(FLIGHT_CONTROL_API + '/flight/search', chagedDateObject)
      .map(res => res.json())
      .catch(this.errorHandler);

  }

  reverseBid(auction: Auction) {

    return this.http.post(FLIGHT_CONTROL_API + '/reverse-bid', auction)
      .map(res => res.json())
      .catch(this.bidErrorHandler);

  }

}
