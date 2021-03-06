import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { User } from '../../model/user';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';


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
  adminAuthenticate:boolean;

  constructor(private loginService: LoginService, private route: Router) { }

  /**
   * if user is logged in show user,
   * else if employee is logged in show employee,
   * else show singup.
   */
  ngOnInit() {

    this.loginService.setCurrentUser();
    this.currentUser = this.loginService.getCurrentUser();

    if (this.currentUser.username !== null && this.currentUser.person_id != null) {

      if (this.currentUser.role == "user") this.userAuthenticated = true;
      if (this.currentUser.role == "employee") this.employeeAuthenticate = true;
      if (this.currentUser.role == "admin") this.adminAuthenticate = true;

    } else {

      this.userAuthenticated = false;
      this.employeeAuthenticate = false;
      this.adminAuthenticate = false;

    }

  }

  logMeOut() {
    this.loginService.logout();
    this.route.navigate(['home']);
    window.location.reload();
  }

}
