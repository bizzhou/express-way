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

    getMonthlySalesResport(year: string, month: string){

        let param = new URLSearchParams();
        param.append("year", year);
        param.append("month", month);
        
        return this.http.post(managerApi + "monthly-sales-report", param).subscribe(res => {
            console.log(res.json());
            res.json();
        });

    }

    getListRevenueByFlight(airline: string, flightNumber: string){

        let param = new URLSearchParams();
        param.append("airline", airline);
        param.append("flightNumber", flightNumber);

        return this.http.post(managerApi + "revenue", param).subscribe(res =>{
            console.log(res.json());
            res.json();
        })

    }


    // http://localhost:8080/manager/revenue?destinationCity=New%20York
    getListRevenueByCity(city:string){

        let param = new URLSearchParams();
        param.append("destinationCity", city);

        return this.http.post(managerApi + "revenue", param).subscribe(res =>{
            console.log(res.json());
            res.json();
        });

    }

}