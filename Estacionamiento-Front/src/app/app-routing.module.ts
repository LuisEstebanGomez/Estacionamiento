import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SigninComponent } from './signin/signin.component';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { AccountComponent } from './account/account.component';
import { MovimentComponent } from './moviment/moviment.component';
const routes: Routes = [
  {path:'',component:LoginComponent},
  {path:'home',component:HomeComponent},
  {path:'signin',component:SigninComponent},
  {path: 'vehicles', component:VehiclesComponent },
  {path: 'account', component:AccountComponent},
  {path: 'moviment', component:MovimentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
