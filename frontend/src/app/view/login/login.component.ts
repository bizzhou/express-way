import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { Http } from '@angular/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginService]
})

export class LoginComponent implements OnInit {

  private username: string;
  private password: string;

  constructor(private http: Http, private loginService: LoginService) {
  }

  // check if the jwt token is till valid.
  ngOnInit() {
  }

  logMeIn() {

    this.loginService
        .login(this.username, this.password)
        .then(response => {
          
          let ret = JSON.parse(response as string);
          this.loginService.setToken(ret.token);


        })
        .catch(error => console.log("Wrong credential"));
  }

  

}
