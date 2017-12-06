import { Component, OnInit } from '@angular/core';
import { ManagerService } from '../../service/manager.service';
import { Http } from '@angular/http';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';
import { MatTableDataSource } from '../../service/table-data-source';
import {Employee} from "../../model/employee";
import { EmployeeDialogComponent } from '../employee-dialog/employee-dialog.component';
import {UserControlService} from "../../service/employee.service";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';



@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
  providers: [ManagerService, UserControlService]
})
export class AdminComponent implements OnInit {

  constructor(private http: Http, private managerService: ManagerService,
              private route: Router, private dialog: MatDialog) { }

  year: string;
  month: string;
  monthlyEarning: number;
  monthlyReportFlag: boolean;

  customerAccount: string;
  customerRevFlag: boolean;
  customerRevDisplayCol = ['account', 'resv_num', 'booking_fee'];
  customerRevDataSource: MatTableDataSource<any>;


  city: string;
  cityRevFlag: boolean;
  cityRevDisplayCol = ['resv_num', 'booking_fee'];
  cityRevDataSource: MatTableDataSource<any>;
  cityRevTot: number = 0;

  airlineRev: string;
  flightRev: string;
  flightRevFlag: boolean;
  flightRevDisplayCol = ['resv_num', 'booking_fee'];
  flightRevDataSource: MatTableDataSource<any>;
  flightTot: number = 0;


  airlineReservation: string;
  flightReservation: string;
  flightReservationFlag: boolean;
  flightReservationDisplayCol = ['account', 'booking_fee', 'custrep_ssn', 'resv_date', 'resv_num', 'total_fare'];
  flightReservationDataSource: MatTableDataSource<any>;

  displayedColumns = ['Airline', 'Flight Number', 'Date of Week', 'SeatCap'];
  dataSource: MatTableDataSource<any>;

  // get all employees
  employees: Employee[];
  allEmployees: MatTableDataSource<Employee>;
  allEmployeesCol = ['id', 'firstname', 'lastname', 'hourlyRate', 'telephone', 'edit/delete'];


  mostRevEmpDisplayCol = ['ssn', 'first_name', 'last_name', 'hourly_rate'];
  mostRevEmpDataSource: MatTableDataSource<any>

  mostSpentCustDisplayCol = ['id', 'account_number', 'first_name', 'last_name', 'username', 'telephone'];

  // mostSpentCustDisplayCol = ['id'];
  mostSpendCustDataSource: MatTableDataSource<any>;

  listOfAllFlights: MatTableDataSource<any>;
  listOfAllFlightsCol = ['airline', 'flight_number', 'date_of_week', 'seating_capacity',
  'from_airport', 'to_airport'];
  mostActiveFlightsCol = ['airline_id', 'flight_number', 'from_airport', 'to_airport'];

  flightFromAirportID: string;
  flightFromAirportFlag: boolean;
  flightFromAirportDisplayCol = ['to_airport', 'airline', 'flightnumber', 'arrive_time', 'depature_time', 'leg'];
  flightFromAirportDataSouce: MatTableDataSource<any>


  ngOnInit() {

  }
  // filter employees
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.allEmployees.filter = filterValue;
  }

  getEmployeeInformation(): any {
    this.managerService.getEmployees()
      .subscribe(
        data => {
          console.log(data);
          this.employees = data as Employee[];
          this.allEmployees = new MatTableDataSource(this.employees);
        },
        error => console.log("Can't fetch employee list from Database")
      )
  }

  // delete employee
  delete(element) {
    this.managerService.deleteEmployee(element.id);
  }

  // edit employee info
  edit(element) {

    let dialog = this.dialog.open(EmployeeDialogComponent, {
      height: '700px',
      width: '600px',
      data: element
    });

    dialog.afterClosed().subscribe(result => {
      this.managerService.updateEmployee(result);
    });

  }


  getReport() {
    this.managerService.getMonthlySalesResport(this.year, this.month)
      .subscribe(res => {
        console.log(res);
        this.monthlyEarning = res;
      });

    console.log(this.monthlyEarning);

    this.monthlyReportFlag = true;
  }

  getRevByCity() {

    this.managerService.getRevenueByCity(this.city)
      .subscribe(res => {
        this.cityRevDataSource = new MatTableDataSource<Element>(res);

        this.cityRevDataSource.data.forEach(e => {
          this.cityRevTot += e.booking_fee;
        });

      });

    this.cityRevFlag = true;
  }

  getRevByFlight() {
    this.managerService.getRevenueByFlight(this.airlineRev, this.flightRev)
      .subscribe(res => {
        this.flightRevDataSource = new MatTableDataSource<Element>(res);

        this.flightRevDataSource.data.forEach(e => {
          this.flightTot += e.booking_fee;
        });
      });

    this.flightRevFlag = true;
  }


  getRevByCust() {
    this.managerService.getRevenueByCustomerAccount(this.customerAccount)
      .subscribe(res => {
        this.customerRevDataSource = new MatTableDataSource<Element>(res);
        console.log(this.customerRevDataSource);
      });

    this.customerRevFlag = true;
  }

  getResvByFlight() {
    this.managerService.getReservationByFlight(this.airlineReservation, this.flightReservation)
      .subscribe(res => {
        this.flightReservationDataSource = new MatTableDataSource<Element>(res);
      });

    this.flightReservationFlag = true;

  }


  getMostFrequentFlight() {

    this.managerService.getMostActiveFlights().subscribe(res => {
      this.dataSource = new MatTableDataSource<Element>(res);
      console.log(this.dataSource);
    }, error => {
      console.log("reloading Flight");
      // this.getMostFrequentFlight();
    });

  }

  getMostRevEmployee() {
    this.managerService.getEmployeeWithMostRevenue()
      .subscribe(res => {
        let array = [];
        array.push(res);
        this.mostRevEmpDataSource = new MatTableDataSource<Element>(array);
      }, error => {
        console.log("reloading empl");
        // this.getMostRevEmployee();
      });
  }

  getMostSpentCust() {

    this.managerService.getCustomerWithMostRevenue()
      .subscribe(res => {
        let custArray = [];
        custArray.push(res);
        this.mostSpendCustDataSource = new MatTableDataSource<Element>(custArray);
        console.log(this.mostSpendCustDataSource);
      }, error => {
        console.log("reloading");
        // this.getMostSpentCust()
      });

  }


  getAllFlights() {

    this.managerService.getAllFlights()
      .subscribe(res => {
        console.log(res);
        this.listOfAllFlights = new MatTableDataSource<Element>(res);
      });
  }

  getFlightFromAirport() {

    this.managerService.getFlightFromAirPort(this.flightFromAirportID)
      .subscribe(res => {
        console.log(res);
        this.flightFromAirportDataSouce = new MatTableDataSource<Element>(res);
        console.log(this.flightFromAirportDataSouce);
        this.flightFromAirportFlag = true;
      });

  }


}
