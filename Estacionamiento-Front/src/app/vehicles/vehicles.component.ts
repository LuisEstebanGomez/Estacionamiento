import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VehicleService } from '../vehicle.service';
import { LoginService } from '../login.service';
import { UserService } from '../user.service';


@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit{
  phoneNumber: string="";
  vehicles: any[] = [];
  patente=""
  mensajeError=""
  me:boolean=false;
  displayedColumns: string[] = ['Patente'];
  saldo: number=0;
  
  constructor(private router:Router, private vehicleService: VehicleService, private loginService:LoginService, private usuarioService:UserService) {

  }

  ngOnInit() {
    
    this.phoneNumber=this.loginService.getPhoneNumberFromToken();  
    this.obtenerSaldoCuenta();
    this.cargarVehiculos();
  }
  
  cargarVehiculos() {
    this.vehicleService.getVehicles(this.phoneNumber).subscribe(
      (data) => {
        this.vehicles = data;
      },
      (error) => {
        console.error('Error al obtener los vehicles', error);
      }
    );
  }

  agregarVehiculo(){
    this.patente=this.patente.toUpperCase();
    if(this.verificarPatente()){ 
        console.log(this.patente);
        this.vehicleService.agregarVehiculo(this.phoneNumber,this.patente).subscribe();
        window.location.reload();
  }
    else{
      this.mensajeError = "LA PATENTE DEBE CUMPLIR EL FORMATO AAA111 O AA111AA";
      this.me=true;
    }
  }

  verificarPatente() {
    //  tres dígitos, y dos letras en mayúsculas
    const formato1 = /^[A-Z]{2}\d{3}[A-Z]{2}$/;
    // tres letras en mayúsculas seguidas de tres dígitos
    const formato2 = /^[A-Z]{3}\d{3}$/;
  
    // Verificar si la patente cumple con alguno de los dos formatos
    return formato1.test(this.patente) || formato2.test(this.patente);
  }
  
  obtenerSaldoCuenta() {
    this.usuarioService.obtenerCuenta(this.phoneNumber)
      .subscribe(response => {        
        this.saldo = response;
        console.log(this.saldo);
      });
  }

  logout(){
    this.loginService.logout();
  }  

}
