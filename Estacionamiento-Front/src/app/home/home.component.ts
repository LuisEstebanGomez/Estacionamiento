import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../user.service';
import { ParkingService } from '../parking.service';
import { LoginService } from '../login.service';
import { animationFrameScheduler } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  
  phoneNumber: string="";
  saldo: number= 0;
  vehiculos: any;
  seleccionado: string="";
  dominio:string="";
  inicio: boolean=true;
  activo: boolean=false;
  fin: boolean=false;
  eligio: boolean=false;
  nopuede:boolean=false;

  constructor(private route: ActivatedRoute,private usuarioService:UserService,private parkingService:ParkingService,private loginService:LoginService) { }
 
  async ngOnInit() {
    this.phoneNumber=this.loginService.getPhoneNumberFromToken();
    this.obtenerSaldoCuenta();
    await this.tieneEstacionamientoActivo();
    if(this.dominio !== ""){
        this.activo=true;
    }
    this.obtenerVehiculos(); 
  }

  obtenerSaldoCuenta() {
    this.usuarioService.obtenerCuenta(this.phoneNumber)
      .subscribe(response => {        
        this.saldo = response;
        console.log(this.saldo);
      });
  }

  obtenerVehiculos(){
    this.usuarioService.obtenerVehiculos(this.phoneNumber)
    .subscribe(response=> {
      this.vehiculos= response;
      console.log(this.vehiculos);});
  }

  async tieneEstacionamientoActivo() {
    // console.log("Voy a chequear si tiene un estacionamiento activo");
    try {
      const estacionamiento = await this.parkingService.estacionamientoActivo(this.phoneNumber).toPromise();   
      this.dominio = estacionamiento.patente;
    } catch (error) {
      console.error('Error al obtener el estacionamiento activo:', error);      
    }
  }

  iniciarEstacionamiento() {
    console.log("inicio un estacionamiento " + this.seleccionado + " telefono " + this.phoneNumber);
  
    if (this.seleccionado !== "") {
      this.parkingService.iniciarEstacionamiento(this.phoneNumber, this.seleccionado).subscribe(
        () => {
        
          this.dominio = this.seleccionado;
          this.activo = true;
          this.inicio = false;
          this.fin = true;
          this.eligio = false;
        },
        (error) => {
          this.activo = false;
          this.eligio = false;
        }
      );
    } else {
      this.eligio = true; 
    }
  }

  finalizarEstacionamiento(){
    this.parkingService.finalizarEstacionamiento(this.phoneNumber).subscribe();
    this.activo=false;
    this.fin=false;
    this.inicio=true;
    window.location.reload();
  }

  logout(){
    this.loginService.logout();
    
  }  
}
