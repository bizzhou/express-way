import { Component, OnInit, Inject } from '@angular/core';

import { UserControlService } from '../../service/user-control.service';
import { Customer } from '../../model/customer';
import { Http } from '@angular/http';
import { MatTableDataSource } from '../../service/table-data-source';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-user-control',
  templateUrl: './user-control.component.html',
  styleUrls: ['./user-control.component.css'],
  providers: [UserControlService]
})
export class UserControlComponent implements OnInit {

  users: Customer[];
  dataSource: MatTableDataSource<Customer>;
  displayedColumns = ['id', 'firstname', 'lastname', 'email', 'telephone', 'edit/delete'];

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  constructor(private http: Http, private userControlService: UserControlService, private dialog: MatDialog) {
  }

  getUserInformation(): any {
    this.userControlService.getUsers()
      .subscribe(
      data => {
        this.users = data as Customer[];
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

  delete(element){

    console.log(element);
    console.log(typeof(element.id));
    this.userControlService.deleteUser(element.id);

  }

  edit(element){
    console.log(element);
    
    let dialog = this.dialog.open(DialogComponent, {
      width: '600px',
      data : element
    });

    dialog.afterClosed().subscribe(result =>{
      element.zipcode = result;
      console.log(element);
    });

  }

}
