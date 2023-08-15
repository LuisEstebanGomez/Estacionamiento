import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './auth/login/login.component';
import { SigninComponent } from './auth/signin/signin.component';
import { VehiclesComponent } from './pages/vehicles/vehicles.component';
import { AccountComponent } from './pages/account/account.component';
import { MovimentComponent } from './pages/moviment/moviment.component';
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
