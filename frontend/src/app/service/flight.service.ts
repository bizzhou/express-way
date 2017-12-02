import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Headers, Http, Response } from '@angular/http';
import { Injectable, Input, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs/Observable';

// import { Configuration } from '../config';
import { Flight } from '../model/flight';
import { Leg } from '../model/leg';
import { FlightSearch } from '../model/flight-search';

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

    reserve(reservation: any){


        

    }

    getOneWaySearch(flightSearch: any) {

        return this.http.post(FLIGHT_CONTROL_API + '/flight/search', flightSearch)
            .map(res => res.json())
            .catch(this.errorHandler);
    }

}
