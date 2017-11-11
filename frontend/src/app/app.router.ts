import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './view/login/login.component';
import { SignupComponent } from './view/signup/signup.component';
import { HomeComponent } from './view/home/home.component';
import { FlightsSearchComponent } from './view/flights-search/flights-search.component';
import { AdminComponent } from './view/admin/admin.component';
import { UserControlComponent } from './view/user-control/user-control.component';
import { EmployeeControlComponent } from './view/employee-control/employee-control.component';
import { AuthGuard } from './service/authguard.service';

export const router: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'home', component: HomeComponent },
    { path: 'flights', component: FlightsSearchComponent },
    { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] },
    { path: 'user/control', component: UserControlComponent },
    { path: 'employee/control', component: EmployeeControlComponent }
];

export const AppRoute: ModuleWithProviders = RouterModule.forRoot(router);