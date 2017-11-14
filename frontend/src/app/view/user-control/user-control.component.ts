import { Component, OnInit, Inject } from '@angular/core';

import { UserControlService } from '../../service/user-control.service';
import { User } from '../../model/user';
import { Http } from '@angular/http';
import { MatTableDataSource } from '../../service/table-data-source';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-user-control',
  templateUrl: './user-control.component.html',
  styleUrls: ['./user-control.component.css'],
  providers: [UserControlService]
})
export class UserControlComponent implements OnInit {

  users: User[];
  dataSource: MatTableDataSource<User>;
  displayedColumns = ['firstname', 'lastname', 'username', 'address', 'phone', 'edit/delete'];


  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  constructor(private http: Http, private userControlService: UserControlService, public dialog: MatDialog) {
  }

  openDialog($event, element){
    let dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      width: '250px',
      data: { name: element.firstname , username: element.username }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  getUserInformation(): any {
    this.userControlService.getUsers()
      .subscribe(
      data => {
        this.users = data as User[];
        this.dataSource = new MatTableDataSource(this.users);
        console.log(this.dataSource);
        console.log(this.users);
      },
      error => console.log("Can't fetch user from database")
      )
  }

  ngOnInit() {
    this.getUserInformation();
  }

  delete(event, element){
    console.log(event);
    console.log(element);
  }

  edit($event, element){
    console.log(element);
  }

}

@Component({
  selector: 'dialog-overview-example-dialog',
  templateUrl: 'dialog-overview-example-dialog.html',
})
export class DialogOverviewExampleDialog {

  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
