import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent {

  constructor (public dialog: MatDialogRef<DialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any){
  }

  update():void{
    this.dialog.close();
  }

}
