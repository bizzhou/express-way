import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { User } from '../../model/user';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  providers: [LoginService]
})
export class HeaderComponent implements OnInit {

  private currentUser: User;
  userAuthenticated: boolean;
  employeeAuthenticate: boolean;
  constructor(private loginService: LoginService) { }

  ngOnInit() {

    this.loginService.setCurrentUser();
    this.currentUser = this.loginService.getCurrentUser();

    if (this.currentUser.username !== null && this.currentUser.person_id != null) {

      if (this.currentUser.role == "user") this.userAuthenticated = true;
      if (this.currentUser.role == "employee") this.employeeAuthenticate = true;

    } else {

      this.userAuthenticated = false;
      this.employeeAuthenticate = false;

    }

    console.log(this.userAuthenticated);

  }

  logMeOut() {

    this.loginService.logout();
    window.location.reload();

  }

}
