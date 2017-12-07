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
  twoWaySeatNumber: number[];
  twoway: boolean;

  constructor(private flightService: FlightService, public dialog: MatDialogRef<UserReservationDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  reserveTicket(): void {
    this.dialog.close();
  }

  checkTwoWay() {

    if (localStorage.getItem('fromInclude') != null && JSON.parse(localStorage.getItem('toInclude')){

      this.twoway = true;

      console.log("twoway");
      let fromInc = JSON.parse(localStorage.getItem('fromInclude'));
      let toInc = JSON.parse(localStorage.getItem('toInclude'));
      localStorage.removeItem('fromInclude');
      localStorage.removeItem('toInclude');

      console.log(fromInc);
      console.log(toInc);

      let airline = fromInc.airlineId;
      let flightNumber = fromInc.flightNumber;
      let classType = fromInc.classType;

      if (fromInc.length > 1) {
        airline = fromInc[0].airlineId;
        flightNumber = fromInc[0].flightNumber;
        classType = fromInc[0].classType;
      }

      this.flightService.getRemainingSeats(airline, flightNumber, classType)
        .subscribe(res => {
          this.seatNumbers = res;
        });


      let returnAirline = toInc.airlineId;
      let returnFlightNumber = toInc.flightNumber;
      let returnClassType = toInc.classType;

      if (toInc.length > 1) {
        returnAirline = toInc[0].airlineId;
        returnFlightNumber = toInc[0].flightNumber;
        returnClassType = toInc[0].classType;
      }

      this.flightService.getRemainingSeats(returnAirline, returnFlightNumber, returnClassType)
        .subscribe(res => {
          this.twoWaySeatNumber = res;
        });


    }


  }

  checkOneWay() {

    if (localStorage.getItem('include') != null) {

      console.log("one way");

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


  ngOnInit() {
    this.checkOneWay();
    this.checkTwoWay();
  }

}
