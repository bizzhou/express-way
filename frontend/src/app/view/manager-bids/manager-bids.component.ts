import { Component, OnInit } from '@angular/core';
import { ManagerService } from '../../service/manager.service';
import { MatTableDataSource } from '../../service/table-data-source';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Auction } from '../../model/auction';

@Component({
  selector: 'app-manager-bids',
  templateUrl: './manager-bids.component.html',
  styleUrls: ['./manager-bids.component.css'],
  providers: [ManagerService]
})
export class ManagerBidsComponent implements OnInit {

  dataSource: MatTableDataSource<any>;
  displayedColumns = ['account', 'airline', 'flight', 'deptDate', 'class', 'leg', 'nyop', 'isAccepted',];

  constructor(private managerService: ManagerService) { }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  buildAuction(e: any) {

    let auction = new Auction();

    auction.accountNumber = e.account_num;
    auction.airlineId = e.airline_id;
    auction.flightClass = e.class;
    auction.depatureDate = e.dept_date;
    auction.flightNumber = e.flight_num;
    auction.legNumber = e.leg_number;
    auction.bidPrice = e.NYOP;

    return auction;

  }

  updateStatus() {

    // for each user, check if manager accept the nyop
    this.dataSource.data.forEach(e => {
      if (e.is_accepted == true) {
        let auc = this.buildAuction(e);
        this.managerService.bidsToResv(auc).subscribe(res => {
          window.location.reload();
        });
      }
    });
  }

  ngOnInit() {
    this.managerService.getAllBids()
      .subscribe(res => {
        // load data before render view
        this.dataSource = new MatTableDataSource(res as Auction[]);
      });
  }

}

