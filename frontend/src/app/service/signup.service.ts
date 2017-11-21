import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Customer } from '../model/customer';
import { Employee } from '../model/employee';
const signUpApi = 'http://localhost:8080/'

@Injectable()
export class SignUpService {

    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) {
    }

    signup(customer: Customer) {
        console.log(customer);
        return this.http.post(signUpApi + "signup", customer)
            .map(res => res.json()).toPromise();
    }

    employeeSignup(employee: Employee) {
        return this.http.post(signUpApi + "employee/signup", employee)
            .map(res => res.json()).toPromise();
    }

};