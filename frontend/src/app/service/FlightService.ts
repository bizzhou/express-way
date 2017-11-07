import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Headers, Http, Response} from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';


// import { Configuration } from '../config';
import { Flight } from '../model/flight';

const flightApi = 'http://localhost:3000/flights';

@Injectable()
export class FlightService {

    errorHandler(error): any {
        console.log(error);
        return Observable.throw(error.json.error || 'Server error');
    }

    constructor(private http: Http) {
    }

    getFlights(): Observable<Flight []> {
        return this.http.get(flightApi).map(res => res.json()).catch(this.errorHandler);
    }

}