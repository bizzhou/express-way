import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { Http } from '@angular/http';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginService]
})

export class LoginComponent implements OnInit {

  private username: string;
  private password: string;

  loading = false;
  returnUrl: string;

  constructor(private http: Http, private loginService: LoginService, private route: Router) {
  }

  /**
   * Clear any remaining item in the localStorage.
   */
  ngOnInit() {
    this.loginService.logout();
  }

  logMeIn() {

    this.loginService
      .login(this.username, this.password)
      .then(response => {
        let ret = JSON.parse(response as string);

        // save some useful information to the localStorage
        this.loginService.setToken(ret.token);
        this.loginService.setRole(ret.role);
        this.loginService.setPersonId(ret.id);
        this.loginService.setUsername(this.username);

        this.loginService.setCurrentUser();

        if (this.loginService.getRole() == 'admin')
          this.route.navigate(['admin']);

        else
          this.route.navigate(['home']);

        window.location.reload();

      })
      .catch(error => alert("Wrong credential"));

  }

}
