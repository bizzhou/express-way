import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Headers, Http, Response} from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Customer } from '../model/customer';

const USER_CONTROL_API = 'http://localhost:3000/user';

@Injectable()
export class UserControlService {

    errorHandler(error): any {
        console.log(error);
        return Observable.throw(error.json.error || 'Server error');
    }

    constructor(private http: Http) {
    }

    getUsers(): Observable<Customer []> {
        return this.http.get(USER_CONTROL_API)
                   .map(res => res.json()).catch(this.errorHandler);
    }

    // deleteUser(id: number): Observable<any>{
    //     return this.http.delete(USER_CONTROL_API + '/' + id).map((res: Response) => res.json()); 
    // }

    deleteUser(id: number) {
        this.http.delete(USER_CONTROL_API + '/' + id).subscribe(res => {

        });
    }

}