import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { SigninComponent } from './auth/signin/signin.component';

import { FormsModule } from '@angular/forms'; // para trabajar con formularios
import {HttpClientModule} from '@angular/common/http'; // para poder trabajar con servicios rest
import { UserService } from './service/user.service';
import { VehiclesComponent } from './pages/vehicles/vehicles.component';
import { AccountComponent } from './pages/account/account.component';
import { MovimentComponent } from './pages/moviment/moviment.component';
import { NavBarComponent } from './pages/navbar/navbar.component';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SigninComponent,
    VehiclesComponent,
    AccountComponent,
    MovimentComponent,
    NavBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
