import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Headers, Http, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

// import { Configuration } from '../config';
import { Flight } from '../model/flight';
import { Leg } from '../model/leg';
import { FlightSearch } from '../model/flight-search';


const FLIGHT_CONTROL_API = 'http://localhost:8080';

@Injectable()
export class FlightService {

    public flightSearchResult: any = [];

    setFlightSearchResult(result: any[]) {
        console.log(result);
        this.flightSearchResult = result;
        console.log(this.flightSearchResult);
    }

    getFlightSearchResult() {
        console.log(this.flightSearchResult);
        return this.flightSearchResult;
    }

    errorHandler(error): any {
        console.log(error);
        return Observable.throw(error.json.error || 'Server error');
    }

    constructor(private http: Http) {
    }

    getOneWaySearch(flightSearch: any): Observable<Leg[]> {
        return this.http.post(FLIGHT_CONTROL_API + '/flight/search', flightSearch)
            .map(res => res.json())
            .catch(this.errorHandler);
    }

}
