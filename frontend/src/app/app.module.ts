import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

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
import {HttpModule} from '@angular/http';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    FlightsSearchComponent,
  ],
  imports: [
    BrowserModule,
    HttpModule,
    AppRoute,
    MaterialModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
