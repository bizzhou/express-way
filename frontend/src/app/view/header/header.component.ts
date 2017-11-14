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
  authenticated: boolean;
  constructor(private loginService: LoginService) { }

  ngOnInit() {

    this.loginService.setCurrentUser();
    this.currentUser = this.loginService.getCurrentUser();

    if(this.currentUser.username !== null && this.currentUser.person_id != null){
      console.log("user");
      console.log(this.currentUser);
      this.authenticated = true;
    }else{
      console.log("non user");
      this.authenticated = false;
    }

    console.log(this.authenticated);

  }

  logMeOut(){

    console.log("Logging out");
    this.loginService.logout();
    window.location.reload();

  }

}
