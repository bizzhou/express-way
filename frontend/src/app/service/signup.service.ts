import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Customer } from '../model/customer';
const signUpApi = 'http://localhost:8080/signup'

@Injectable()
export class SignUpService {

    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) {
    }

    signup(customer : Customer){
        console.log(customer);
        return  this.http.post(signUpApi, customer).map(res => res.text()).toPromise();
    }

};