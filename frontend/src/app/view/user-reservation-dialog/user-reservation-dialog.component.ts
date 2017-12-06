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

  constructor(private flightService: FlightService, public dialog: MatDialogRef<UserReservationDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  reserveTicket(): void {
    this.dialog.close();
  }


  ngOnInit() {

    this.flightService.getRemainingSeats("AA", "111", "first")
      .subscribe(res =>{
        console.log(res);
      });
    
  }

}
