import { Component, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-user-reservation-dialog',
  templateUrl: './user-reservation-dialog.component.html',
  styleUrls: ['./user-reservation-dialog.component.css']
})
export class UserReservationDialogComponent {

  constructor(public dialog: MatDialogRef<UserReservationDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  reserveTicket(): void {
    this.dialog.close();
  }


}
