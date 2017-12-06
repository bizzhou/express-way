import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Headers, Http, Response, URLSearchParams } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Customer } from '../model/customer';
import { Employee } from '../model/employee';
import { Include } from '../model/include';
import { HttpParams } from "@angular/common/http";

const USER_CONTROL_API = 'http://localhost:8080/';

@Injectable()
export class UserControlService {

    errorHandler(error): any {
        console.log(error);
        return Observable.throw(error.json.error || 'Server error');
    }

    constructor(private http: Http) {
    }

    // deleteEmployee(id: number) {
    //     this.http.delete(USER_CONTROL_API + 'employee/delete/' + id).subscribe(res => {
    //         window.location.reload();
    //     });
    // }
    //
    // updateEmployee(employee: any) {
    //     this.http.put(USER_CONTROL_API + 'employee/' + employee.id, employee).subscribe(res => {
    //     });
    // }

    getCustomerEmails() {
        return this.http.get(USER_CONTROL_API + 'employee/customer/email-list')
            .map(res => res.json());
    }

    getFlightSuggestion(customerId: string) {
        let param = new URLSearchParams();
        param.append("customerId", customerId);

        return this.http.post(USER_CONTROL_API + 'employee/customer/flight-suggestions', param)
            .map(res => res.json());
    }

    getUsers(): Observable<Customer[]> {
        return this.http.get(USER_CONTROL_API + 'get-users')
            .map(res => res.json()).catch(this.errorHandler);
    }

    deleteUser(id: number) {
        console.log(USER_CONTROL_API + 'delete/' + id);

        this.http.delete(USER_CONTROL_API + 'delete/' + id).subscribe(res => {
            window.location.reload();
            alert("Done");
        });
    }

    updateUser(customer: any) {
        if (customer !== undefined) {
            this.http.put(USER_CONTROL_API + 'update/' + customer.id, customer).subscribe(res => { });
        }
    }

    getUserBids(account: string) {
        return this.http.get(USER_CONTROL_API + 'user/' + account + "/bids")
            .map(res => res.json())
            .catch(this.errorHandler);
    }

    getUserProfile(id: number): Observable<Customer> {
        return this.http.get(USER_CONTROL_API + "user/" + id).map(res => res.json());
    }

    insertIntoInclude(inc: Include) {
        return this.http.post(USER_CONTROL_API + 'user/bid-reservation', inc)
            .map(res => res.json())
            .catch(this.errorHandler)
    }

    getAllReservationByAccountNumber(accountNumber: string) {
        return this.http.get(USER_CONTROL_API + accountNumber + "/reservations/history")
            .map(res => res.json())
            .catch(this.errorHandler);
    }

    cancelReservation(resvNumber: number) {

        this.http.delete(USER_CONTROL_API + 'user/reservation/delete/' + resvNumber).subscribe(res => {
            window.location.reload();
        });
    }


}
