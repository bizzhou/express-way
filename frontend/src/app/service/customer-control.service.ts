import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Headers, Http, Response, URLSearchParams } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Customer } from '../model/customer';
import { Employee } from '../model/employee';
import { Include } from '../model/include';
import {HttpParams} from "@angular/common/http";

const CUSTOMER_CONTROL_API = 'http://localhost:8080/';

@Injectable()
export class CustomerControlService {

    errorHandler(error): any {
        console.log(error);
        return Observable.throw(error.json.error || 'Server error');
    }

  constructor(private http: Http) {
  }

  getTravelItinerary(account_number: string, resvNumber: string) {
    let param = new URLSearchParams();
    param.append('resvNumber', resvNumber);
    return this.http.post(CUSTOMER_CONTROL_API + account_number + '/reservations/itinerary', param)
      .map(res => res.json());
  }

  getBestSellerFlights(account_number: string) {
    return this.http.get(CUSTOMER_CONTROL_API + account_number + '/flight/best-seller')
      .map(res => res.json());
  }
}
