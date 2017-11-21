import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { User } from '../model/user';
import { Observable } from 'rxjs/Observable';
import { Customer } from '../model/customer';

export const TOKEN_NAME: string = 'jwtToken';
const loginApi = 'http://localhost:8080/login'

@Injectable()
export class LoginService {

    private headers = new Headers({ 'Content-Type': 'application/json' });
    private token: string;
    private user: User;

    constructor(private http: Http) {
    }

    getToken(): string {
        return localStorage.getItem(TOKEN_NAME);
    }

    setToken(token: string): void {
        localStorage.setItem(TOKEN_NAME, token);
    }

    setRole(role: string): void {
        return localStorage.setItem('role', role);
    }

    getRole(): string {
        return localStorage.getItem('role');
    }

    setPersonId(id: number): void {
        localStorage.setItem("person_id", id.toString());
    }

    getPersonId(): string {
        return localStorage.getItem("person_id");
    }

    setUsername(username: string): void {
        localStorage.setItem("username", username);
    }

    getUsername(): string {
        return localStorage.getItem("username");
    }

    setCurrentUser(): void {
        console.log(this.getUsername());
        this.user = { username: this.getUsername(), role: this.getRole(), person_id: this.getPersonId() };
    }

    getCurrentUser(): User {
        this.user = { username: this.getUsername(), role: this.getRole(), person_id: this.getPersonId() };
        return this.user;
    }

    getUserProfile(id: number): Observable<Customer> {
        return this.http.get("http://localhost:8080/user/" + id).map(res => res.json());
    }


    login(username: string, password: string): Promise<String> {
        console.log(username + " " + password);

        var credential = JSON.stringify({
            username: username,
            password: password
        });

        return this.http.post(loginApi, credential, { headers: this.headers })
            .map(res => res.text())
            .toPromise();
    }

    logout() {
        localStorage.removeItem("person_id");
        localStorage.removeItem(TOKEN_NAME);
        localStorage.removeItem("role");
        localStorage.removeItem("username");
    }

}