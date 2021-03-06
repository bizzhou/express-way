import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginComponent } from './view/login/login.component';
import { SignupComponent } from './view/signup/signup.component';
import { HomeComponent } from './view/home/home.component';
import { AppRoute } from './app.router';
import { HeaderComponent } from './view/header/header.component';
import { FooterComponent } from './view/footer/footer.component';
import { MaterialModule } from './MaterialModule';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlightsSearchComponent } from './view/flights-search/flights-search.component';
import { HttpModule } from '@angular/http';
import { AdminComponent } from './view/admin/admin.component';
import { EmployeeControlComponent } from './view/employee-control/employee-control.component';
import { UserControlComponent } from './view/user-control/user-control.component';
import { UserDialogComponent } from './view/user-dialog/dialog.component';
import { EmployeeDialogComponent } from './view/employee-dialog/employee-dialog.component';
import { EmployeeSignupComponent } from './view/employee-signup/employee-signup.component';
import { UserProfileComponent } from './view/user-profile/user-profile.component';
import { EmployeeProfileComponent } from './view/employee-profile/employee-profile.component';
import { FlightService } from './service/flight.service';
import { UserBidsComponent } from './view/user-bids/user-bids.component';
import { ManagerBidsComponent } from './view/manager-bids/manager-bids.component';
import { UserReservationDialogComponent } from './view/user-reservation-dialog/user-reservation-dialog.component';
import { ReservationHistoryComponent } from './view/reservation-history/reservation-history.component';
import { RoundtripFlightSearchComponent } from './view/roundtrip-flight-search/roundtrip-flight-search.component';
import { MulticityFlightSearchComponent } from './view/multicity-flight-search/multicity-flight-search.component';
import { AuthGuard } from './service/authguard.service';
import { AdminRoleGuard } from './service/admin.roleguard';
import { EmployeeRoleGuard } from './service/employee.roleguard';
import { HelpComponent } from './view/help/help.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    FlightsSearchComponent,
    AdminComponent,
    EmployeeControlComponent,
    UserControlComponent,
    UserDialogComponent,
    EmployeeDialogComponent,
    EmployeeSignupComponent,
    UserProfileComponent,
    EmployeeProfileComponent,
    UserBidsComponent,
    ManagerBidsComponent,
    UserReservationDialogComponent,
    ReservationHistoryComponent,
    RoundtripFlightSearchComponent,
    MulticityFlightSearchComponent,
    HelpComponent,
  ],
  imports: [
    BrowserModule,
    HttpModule,
    AppRoute,
    MaterialModule,
    BrowserAnimationsModule,
    FormsModule
  ],
  entryComponents: [UserDialogComponent, EmployeeDialogComponent, UserReservationDialogComponent],
  providers: [AuthGuard, AdminRoleGuard, EmployeeRoleGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
