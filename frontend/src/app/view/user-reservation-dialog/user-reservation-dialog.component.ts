import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FlightService } from '../../service/flight.service';

@Component({
  selector: 'app-user-reservation-dialog',
  templateUrl: './user-reservation-dialog.component.html',
  styleUrls: ['./user-reservation-dialog.component.css'],
  providers: [FlightService]
})
export class UserReservationDialogComponent implements OnInit {

  seatNumbers: number[];

  constructor(private flightService: FlightService, public dialog: MatDialogRef<UserReservationDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  reserveTicket(): void {
    this.dialog.close();
  }


  ngOnInit() {

    let inc = JSON.parse(localStorage.getItem('include'));
    localStorage.removeItem('include');

    console.log(inc);

    let airline = inc.airlineId;
    let flightNumber = inc.flightNumber;
    let classType = inc.classType;

    if (inc.length > 1) {
      airline = inc[0].airlineId;
      flightNumber = inc[0].flightNumber;
      classType = inc[0].classType;
    }

    this.flightService.getRemainingSeats(airline, flightNumber, classType)
      .subscribe(res => {
        this.seatNumbers = res;
        console.log(this.seatNumbers);
      });

  }

}
