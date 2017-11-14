import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Headers, Http, Response} from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

// import { Configuration } from '../config';
import { User } from '../model/user';

const userControlApi = 'http://localhost:3000/user';

@Injectable()
export class UserControlService {

    errorHandler(error): any {
        console.log(error);
        return Observable.throw(error.json.error || 'Server error');
    }

    constructor(private http: Http) {
    }

    getUsers(): Observable<User []> {
        return this.http.get(userControlApi).map(res => res.json()).catch(this.errorHandler);
    }

}