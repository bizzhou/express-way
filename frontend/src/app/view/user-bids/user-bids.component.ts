import { Component, OnInit } from '@angular/core';
import { UserControlService } from '../../service/user-control.service';
import { LoginService } from '../../service/login.service';
import { MatTableDataSource } from '../../service/table-data-source';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Customer } from '../../model/customer';
import { Include } from '../../model/include';
import { UserReservationDialogComponent } from '../user-reservation-dialog/user-reservation-dialog.component';

@Component({
  selector: 'app-user-bids',
  templateUrl: './user-bids.component.html',
  styleUrls: ['./user-bids.component.css'],
  providers: [UserControlService, LoginService]
})
export class UserBidsComponent implements OnInit {

  dataSource: MatTableDataSource<any>;
  displayedColumns = ['account', 'reservation_num', 'airline', 'flight', 'deptDate', 'class', 'leg', 'nyop', 'isAccepted', 'reserve'];

  constructor(private dialog: MatDialog, private userService: UserControlService, private loginService: LoginService) { }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  buildInclude(result, element) {

    let inc = new Include();

    inc.airlineId = element.airline_id;
    inc.deptDate = this.timeConverter(element.dept_date);
    inc.firstName = result.firstName;
    inc.lastName = result.lastName;
    inc.flightClass = element.class;
    inc.flightNumber = element.flight_num;
    inc.legNumber = element.leg_number;
    // should change to leg number-1??
    inc.fromStop = element.leg_number;
    inc.reservationNumber = element.reservation_number;
    inc.meal = result.meal;
    inc.seatNumber = result.seatNumber;

    return inc;

  }

  timeConverter(dateString: string){
    let a = new Date(dateString);
    let year = a.getFullYear();
    let month = a.getMonth() + 1;
    let date = a.getDate();
    let hour = a.getHours();
    let min = a.getMinutes();
    let sec = a.getSeconds();
    let time = year + '-' + month + '-' + date + ' ' + hour + ':' + min + ':' + sec ;
    return time;
  }

  makeReservation(element) {

    console.log(element);

    let ret = {};

    let dialog = this.dialog.open(UserReservationDialogComponent, {
      height: '700px',
      width: '500px',
      data: ret
    });

    dialog.afterClosed().subscribe(result => {
      console.log(result);
      let inc = this.buildInclude(result, element);

      console.log(inc);
      // make call to backend
      this.userService.insertIntoInclude(inc).subscribe(res => {

        if (res == true) {
          alert("Done");
        } else {
          alert("Error");
        }

      });

    });

  }

  ngOnInit() {

    let id = parseInt(this.loginService.getCurrentUser().person_id);

    this.userService.getUserProfile(id)
      .subscribe(res => {

        // get current user
        res = res as Customer;

        // get user bids
        this.userService.getUserBids(res.account_number).subscribe(response => {

          this.dataSource = new MatTableDataSource(response);

        });

      });
  }

}
