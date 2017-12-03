import { Component, OnInit } from '@angular/core';
import { ManagerService} from '../../service/manager.service';
import { MatTableDataSource } from '../../service/table-data-source';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-manager-bids',
  templateUrl: './manager-bids.component.html',
  styleUrls: ['./manager-bids.component.css'],
  providers: [ManagerService]
})
export class ManagerBidsComponent implements OnInit {

  // auctions: any = [];
  dataSource: MatTableDataSource<any>;
  displayedColumns = ['account', 'airline', 'flight', 'deptDate', 'class','leg', 'nyop' ,'isAccepted', ];

  constructor(private managerService: ManagerService) { }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  updateStatus(){
    console.log(this.dataSource);
  }


  ngOnInit() {
    this.managerService.getAllBids()
      .subscribe(res =>{
        this.dataSource = new MatTableDataSource(res);
      });
  }

}
