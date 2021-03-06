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
import { MulticityFlightSearchComponent } from './view/multicity-flight-search/multicity-flight-search.component';
import { AdminRoleGuard } from './service/admin.roleguard';
import { EmployeeRoleGuard } from './service/employee.roleguard';
import { AuthGuard } from './service/authguard.service';
import { HelpComponent } from './view/help/help.component';

export const router: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'home', component: HomeComponent },
    { path: 'help', component: HelpComponent},
    { path: 'flights', component: FlightsSearchComponent },
    { path: 'flights/roundtrip', component: RoundtripFlightSearchComponent },
    { path: 'flights/multicity', component: MulticityFlightSearchComponent },
    { path: 'admin', component: AdminComponent, canActivate: [AdminRoleGuard] },
    { path: 'user/control', component: UserControlComponent},
    { path: 'employee/control', component: EmployeeControlComponent, canActivate: [EmployeeRoleGuard] },
    { path: 'employee/signup', component: EmployeeSignupComponent, canActivate: [AdminRoleGuard] },
    { path: 'user/profile', component: UserProfileComponent, canActivate: [AuthGuard] },
    { path: 'user/bids', component: UserBidsComponent, canActivate: [AuthGuard] },
    { path: 'bids', component: ManagerBidsComponent, canActivate: [AdminRoleGuard] },
    { path: 'user/reservations', component: ReservationHistoryComponent, canActivate: [AuthGuard] },
];


export const AppRoute: ModuleWithProviders = RouterModule.forRoot(router);