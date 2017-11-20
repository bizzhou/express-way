import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams } from '@angular/http';
import { User } from '../model/user';
import { Observable } from 'rxjs/Observable';
import { Customer } from '../model/customer';

const managerApi = 'http://localhost:8080/manager/';


@Injectable()
export class ManagerService {

    constructor(private http: Http) {
    }

    getMonthlySalesResport(year: string, month: string) {

        // set the request parameters
        let param = new URLSearchParams();
        param.append("year", year);
        param.append("month", month);

        return this.http.post(managerApi + "monthly-sales-report", param)
            .map(res => res.json());

    }

    getRevenueByFlight(airline: string, flightNumber: string) {

        let param = new URLSearchParams();
        param.append("airline", airline);
        param.append("flightNumber", flightNumber);

        return this.http.post(managerApi + "revenue", param).subscribe(res => {
            res.json();
        });

    }

    getEmployeeWithMostRevenue() {

        return this.http.get(managerApi + "revenue/employee-most-revenue").subscribe(res => {
            console.log(res.json());
            res.json();
        });

    }

    getCustomerWithMostRevenue(){

        return this.http.get(managerApi + "revenue/customer-most-spent").subscribe(res => {
            console.log(res.text());
            res.text();
        });

    }


    getRevenueByCity(city: string) {

        let param = new URLSearchParams();
        param.append("destinationCity", city);

        return this.http.post(managerApi + "revenue", param).subscribe(res => {
            // console.log(res.json());
            console.log(res);
            // res.json();
        });

    }

    getReservationByCustomerName(account: string) {

        let param = new URLSearchParams();
        param.append("customerAccount", account);

        return this.http.post(managerApi + "revenue", param).subscribe(res => {
            console.log(res.json());
            res.json();
        })

    }

    getReservationByFlight(airline: string, flightNumber: string) {
        let param = new URLSearchParams();
        param.append("airline", airline);
        param.append("flightNumber", flightNumber);

        return this.http.post(managerApi + "reservation", param).subscribe(res => {
            res.json();
        });
        
    }


    getMostActiveFlights() {
        return this.http.get(managerApi + "flight/most-freq-flights").subscribe(res => {
            res.json();
        })
    }

}   