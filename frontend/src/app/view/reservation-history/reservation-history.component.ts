import { Component, OnInit } from '@angular/core';
import { UserControlService } from '../../service/user-control.service';
import { LoginService } from '../../service/login.service';
import { MatTableDataSource } from '../../service/table-data-source';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Customer } from '../../model/customer';
import { Reservation } from '../../model/reservation';

@Component({
  selector: 'app-reservation-history',
  templateUrl: './reservation-history.component.html',
  styleUrls: ['./reservation-history.component.css'],
  providers: [UserControlService, LoginService]
})
export class ReservationHistoryComponent implements OnInit {

  dataSource: MatTableDataSource<any>;
  displayedColumns = ['resv_num', 'airline', 'flight_num', 'fare', 'from', 'to', 'date', 'first', 'last', 'cancel'];

  constructor(private userService: UserControlService, private loginService: LoginService) { }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  cancel(element: any) {
    console.log(element);

    this.userService.cancelReservation(element.reservation_number);
  }

  ngOnInit() {
    let id = parseInt(this.loginService.getCurrentUser().person_id);

    this.userService.getUserProfile(id)
      .subscribe(res => {

        // get current user
        res = res as Customer;

        // get user bids
        this.userService.getAllReservationByAccountNumber(res.account_number).subscribe(response => {

          this.dataSource = new MatTableDataSource(response);
          console.log(this.dataSource);

        });

      });

  }

}
