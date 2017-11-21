import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Headers, Http, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Customer } from '../model/customer';
import { Employee } from '../model/employee';

const USER_CONTROL_API = 'http://localhost:8080/';

@Injectable()
export class UserControlService {

    errorHandler(error): any {
        console.log(error);
        return Observable.throw(error.json.error || 'Server error');
    }

    constructor(private http: Http) {
    }

    getEmployees(): Observable<Employee[]> {
        return this.http.get(USER_CONTROL_API + 'get-employee')
            .map(res => res.json()).catch(this.errorHandler);
    }

    deleteEmployee(id: number) {
        this.http.delete(USER_CONTROL_API + 'employee/delete/' + id).subscribe(res => {
            window.location.reload();
        });
    }

    updateEmployee(employee: any) {
        this.http.put(USER_CONTROL_API + 'employee/' + employee.id, employee).subscribe(res => {
        });
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
        if(customer !== undefined){
            this.http.put(USER_CONTROL_API + 'update/' + customer.id, customer).subscribe(res => {});
        }
    }

}