import { Component, OnInit } from '@angular/core';
import { UserControlService } from '../../service/user-control.service';
import { LoginService } from '../../service/login.service';

@Component({
  selector: 'app-user-bids',
  templateUrl: './user-bids.component.html',
  styleUrls: ['./user-bids.component.css'],
  providers: [UserControlService, LoginService]
})
export class UserBidsComponent implements OnInit {

  

  constructor(private userService: UserControlService) { }

  ngOnInit() {

    // Get all bids of user
    this.userService.getUserBids("jdoe-1").subscribe(res =>{
      console.log(res);
    });

  }

}
