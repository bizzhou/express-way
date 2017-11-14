import { Component, OnInit } from '@angular/core';

import { UserControlService } from '../../service/user-control.service';
import { User } from '../../model/user';
import { Http } from '@angular/http';
// import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-user-control',
  templateUrl: './user-control.component.html',
  styleUrls: ['./user-control.component.css'],
  providers: [UserControlService]
})
export class UserControlComponent implements OnInit {

  users: User[];

  constructor(private http: Http, private userControlService: UserControlService) {
  }

  applyFilter(filterValue: string) {
  }

  getUserInformation(): any {
    this.userControlService.getUsers()
      .subscribe(
      data => {
        this.users = data as User[];
        console.log(this.users);
      },
      error => console.log("Can't fetch user from database")
      )
  }

  ngOnInit() {
    this.getUserInformation();
  }

}


