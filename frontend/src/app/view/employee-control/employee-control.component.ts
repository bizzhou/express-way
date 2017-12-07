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

  // get customers
  customers: Customer[];
  customerList: MatTableDataSource<Customer>;
  customerListCol = ['id', 'firstname', 'lastname', 'telephone', 'edit/delete'];


  dataSource: MatTableDataSource<Employee>;
  displayedColumns = ['id', 'firstname', 'lastname', 'hourlyRate', 'telephone', 'edit/delete'];

  customerEmails: MatTableDataSource<any>;
  customerEmailsCol = ['account_number', 'email'];

  // for displaying at the employee level
  employees: Employee[];
  allEmployees: MatTableDataSource<Employee>;
  allEmployeesCol = ['id', 'firstname', 'lastname', 'telephone'];


  // for flight suggestions
  customerId: string;
  displayFlightSugFlag: boolean;
  listOfFlightSuggestion: MatTableDataSource<any>;
  listOfFlightSuggestionCol = ['airline_id', 'flight_number', 'from_airport', 'to_airport'];

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.customerList.filter = filterValue;
  }

  constructor(private http: Http, private userControlService: UserControlService, private dialog: MatDialog) { }



  ngOnInit() {

  }

  // get all employees
  getEmployeeInformation(): any {
    this.userControlService.getEmployees()
      .subscribe(
        data => {
          console.log(data);
          this.employees = data as Employee[];
          this.allEmployees = new MatTableDataSource(this.employees);
        },
        error => console.log("Can't fetch employee list from Database")
      )
  }


  getCustomers() {
    this.userControlService.getUsers()
      .subscribe(
        data => {
          console.log(data);
          this.customers = data as Customer[];
          this.customerList = new MatTableDataSource(this.customers);
        },
        error => console.log("Can't fetch employee list from Database")
      )

  }
  // edit customer info
  editCustomer(element) {

    let dialog = this.dialog.open(EmployeeDialogComponent, {
      height: '700px',
      width: '600px',
      data: element
    });

    dialog.afterClosed().subscribe(result => {
      this.userControlService.updateUser(result);
      window.location.reload();
    });
  }

  // delete customer
  deleteCustomer(element) {
    this.userControlService.deleteUser(element.id);
  }

  getCustomerEmails() {
    this.userControlService.getCustomerEmails()
      .subscribe(res => {
        console.log(res);
        this.customerEmails = new MatTableDataSource<Element>(res);
      },
      error => console.log("Component: can't fetch customer email list from Database")
      );
  }

  getFlightSuggestion() {
    this.userControlService.getFlightSuggestion(this.customerId)
      .subscribe(res => {
        this.listOfFlightSuggestion = new MatTableDataSource<Element>(res);
        console.log(res);
      });

    this.displayFlightSugFlag = true;
  }



}
