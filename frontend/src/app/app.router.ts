import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './view/login/login.component';
import { SignupComponent } from './view/signup/signup.component';
import { HomeComponent } from './view/home/home.component';
import { FlightsSearchComponent } from './view/flights-search/flights-search.component';
import { RoundtripFlightSearchComponent } from './view/roundtrip-flight-search/roundtrip-flight-search.component';
import { AdminComponent } from './view/admin/admin.component';
import { UserControlComponent } from './view/user-control/user-control.component';
import { EmployeeControlComponent } from './view/employee-control/employee-control.component';
import { EmployeeSignupComponent } from './view/employee-signup/employee-signup.component';
import { UserProfileComponent } from './view/user-profile/user-profile.component';
import { UserBidsComponent } from './view/user-bids/user-bids.component';
import { ManagerBidsComponent } from './view/manager-bids/manager-bids.component';
import { ReservationHistoryComponent } from './view/reservation-history/reservation-history.component';

export const router: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'home', component: HomeComponent },
    { path: 'flights', component: FlightsSearchComponent },
    { path: 'flights/roundtrip', component: RoundtripFlightSearchComponent },
    // { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] },
    { path: 'admin', component: AdminComponent },
    { path: 'user/control', component: UserControlComponent },
    { path: 'employee/control', component: EmployeeControlComponent },
    { path: 'employee/signup', component: EmployeeSignupComponent },
    { path: 'user/profile', component: UserProfileComponent },
    { path: 'user/bids', component: UserBidsComponent },
    { path: 'bids', component: ManagerBidsComponent },
    { path: 'user/reservations', component: ReservationHistoryComponent },

];

export const AppRoute: ModuleWithProviders = RouterModule.forRoot(router);