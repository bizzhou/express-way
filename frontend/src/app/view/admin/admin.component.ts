import { Component, OnInit } from '@angular/core';
import { ManagerService } from '../../service/manager.service'; 
import { Http } from '@angular/http';
import { Router, ActivatedRoute, RouterStateSnapshot } from '@angular/router';


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
  providers: [ManagerService]
})
export class AdminComponent implements OnInit {

  constructor(private http: Http, private managerService: ManagerService, private route: Router) { }

  year: string;
  month: string;
  monthlyEarning: number;
  monthlyReportFlag: boolean;


  mostFreqFilghtFlag: boolean;
  flights: any =  [];

  getReport(){

    
    this.managerService.getMonthlySalesResport(this.year, this.month)
      .subscribe(res =>{
        console.log(res);
        this.monthlyEarning = res;
      });

    console.log(this.monthlyEarning);

    this.monthlyReportFlag = true;
    
  }

  getMostFrequentFlight(){
    this.mostFreqFilghtFlag = true;

    this.managerService.getMostActiveFlights().subscribe(res =>{
      this.flights = res;
    });

    console.log(this.flights);

  }




  ngOnInit() {
  }

}
