import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-dialog',
  templateUrl: './employee-dialog.component.html'
})
export class EmployeeDialogComponent {

  constructor (public dialog: MatDialogRef<EmployeeDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any){
  }

  update():void{
    this.dialog.close();
  }

}
