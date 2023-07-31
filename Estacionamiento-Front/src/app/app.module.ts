import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { SigninComponent } from './signin/signin.component';

import { FormsModule } from '@angular/forms'; // para trabajar con formularios
import {HttpClientModule} from '@angular/common/http'; // para poder trabajar con servicios rest
import { UserService } from './user.service';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { AccountComponent } from './account/account.component';
import { MovimentComponent } from './moviment/moviment.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SigninComponent,
    VehiclesComponent,
    AccountComponent,
    MovimentComponent
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
