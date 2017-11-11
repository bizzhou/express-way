import { Injectable } from '@angular/core';
import {Http, Response, Headers} from '@angular/http';

export const TOKEN_NAME: string = 'jwtToken';
const loginApi = 'http://localhost:8080/login'
@Injectable()
export class LoginService{

    private headers = new Headers({ 'Content-Type': 'application/json' });
    private token : string;

    constructor(private http: Http){
    }    

    getToken():string{
        return localStorage.getItem(TOKEN_NAME);
    }

    setToken(token: string):void{
        localStorage.setItem(TOKEN_NAME, token);
    }

    setRole(role: string):void{
        return localStorage.setItem('role', role);
    }

    getRole():string{
        return localStorage.getItem('role');
    }


    // log user in.
    login(username:string, password: string): Promise<String> {
        console.log(username + " " + password);

        var credential = JSON.stringify({
            username: username,
            password: password
        });

        return this.http.post(loginApi, credential, { headers: this.headers })
                        .map(res => res.text())
                        .toPromise();

                        
    }




}