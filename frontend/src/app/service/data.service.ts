import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { FlightSearch } from '../model/flight-search';

@Injectable()
export class DataService {

    private data = new Subject<FlightSearch[]>();
    flightData = this.data.asObservable();

    updateDate(jsonData) {
        this.data.next(jsonData);
        console.log(this.data);
    }

} 