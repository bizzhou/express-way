import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams } from '@angular/http';
import { User } from '../model/user';
import { Observable } from 'rxjs/Observable';
import { Customer } from '../model/customer';
import { Auction } from '../model/auction';
import {Employee} from "../model/employee";
// import { EmployeeDialogComponent } from '../employee-dialog/employee-dialog.component';
// import { EmployeeDialogComponent } from '../view/employee-dialog/employee-dialog.component';


const managerApi = 'http://localhost:8080/manager/';
const LOCAL_HOST = 'http://localhost:8080/';


@Injectable()
export class ManagerService {

    constructor(private http: Http) {
    }

  errorHandler(error): any {
    console.log(error);
    return Observable.throw(error.json.error || 'Server error');
  }

  getEmployees(): Observable<Employee[]> {
    return this.http.get(LOCAL_HOST + 'get-employee')
      .map(res => res.json()).catch(this.errorHandler);
  }

  deleteEmployee(id: number) {
    this.http.delete(LOCAL_HOST + 'employee/delete/' + id).subscribe(res => {
      window.location.reload();
    });
  }

  updateEmployee(employee: any) {
    this.http.put(LOCAL_HOST + 'employee/' + employee.id, employee).subscribe(res => {
    });
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

    getRevenueByCustomerAccount(account: string) {

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

    getOnTimeFlights() {
      return this.http.get(managerApi + "flight/ontime")
        .map(res => res.json());
    }

    getDelayedFlights() {
      return this.http.get(managerApi + "flight/delay")
        .map(res => res.json());
    }
}
