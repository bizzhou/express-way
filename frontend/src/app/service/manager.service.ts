import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams } from '@angular/http';
import { User } from '../model/user';
import { Observable } from 'rxjs/Observable';
import { Customer } from '../model/customer';
import { Auction } from '../model/auction';

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

        return this.http.post(managerApi + "revenue", param)
            .map(res => res.json());

    }

    getEmployeeWithMostRevenue() {

        return this.http.get(managerApi + "revenue/employee-most-revenue")
            .map(res => res.json());

    }

    getCustomerWithMostRevenue() {
        return this.http.get(managerApi + "revenue/customer-most-spent")
            .map(res => res.json());
    }


    getRevenueByCity(city: string) {

        let param = new URLSearchParams();
        param.append("destinationCity", city);

        return this.http.post(managerApi + "revenue", param)
            .map(res => res.json());

    }

    getReservationByCustomer(account: string) {

        let param = new URLSearchParams();
        param.append("customerAccount", account);

        return this.http.post(managerApi + "revenue", param)
            .map(res => res.json());

    }

    getReservationByFlight(airline: string, flightNumber: string) {
        let param = new URLSearchParams();
        param.append("airline", airline);
        param.append("flightNumber", flightNumber);

        return this.http.post(managerApi + "reservation", param)
            .map(res => res.json());
    }

    getMostActiveFlights() {
        return this.http.get(managerApi + "flight/most-freq-flights")
            .map(res => res.json());
    }

    getFlightFromAirPort(airportID: string) {
        let param = new URLSearchParams();
        param.append("airportId", airportID);

        return this.http.post(managerApi + "flight/get-flights-for-airport", param)
            .map(res => res.json());
    }

    getAllBids() {
        return this.http.get(managerApi + "bids")
            .map(res => res.json());
    }

    bidsToResv(auction: Auction) {
        return this.http.post(managerApi + "auctionToResv", auction)
            .map(res => res.json());

    }

    getAllFlights() {
        return this.http.get(managerApi + "all-flights")
          .map(res => res.json());
    }
}
