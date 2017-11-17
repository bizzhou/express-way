import { Component, OnInit } from '@angular/core';

import { UserControlService } from '../../service/user-control.service';
import { Customer } from '../../model/customer';
import { Http } from '@angular/http';
import { MatTableDataSource } from '../../service/table-data-source';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EmployeeDialogComponent } from '../employee-dialog/employee-dialog.component';
import { Employee } from '../../model/employee';


@Component({
  selector: 'app-employee-control',
  templateUrl: './employee-control.component.html',
  styleUrls: ['./employee-control.component.css'],
  providers: [UserControlService]
})
export class EmployeeControlComponent implements OnInit {

  employees: Employee[];
  dataSource: MatTableDataSource<Employee>;
  displayedColumns = ['id', 'firstname', 'lastname', 'hourlyRate', 'telephone', 'edit/delete'];

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  constructor(private http: Http, private userControlService: UserControlService, private dialog: MatDialog) { }

  getEmployeeInformation(): any{
    this.userControlService.getEmployee()
      .subscribe(
        data =>{
          this.employees = data as Employee[];
          this.dataSource = new MatTableDataSource(this.employees);
        },
        error => console.log("Can't fetch employee list from Database")
      )
  }

  ngOnInit() {
    this.getEmployeeInformation();
  }

  delete(element) {
    this.userControlService.deleteEmployee(element.id);
  }


  edit(element) {

    let dialog = this.dialog.open(EmployeeDialogComponent, {
      height: '700px',
      width: '600px',
      data : element
    });

    dialog.afterClosed().subscribe(result =>{
      this.userControlService.updateEmployee(result);
    });

  }



}
