import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';

const api = "http://localhost:3030"

@Injectable()
export class AuthService {


  constructor(private http: Http) { }
}