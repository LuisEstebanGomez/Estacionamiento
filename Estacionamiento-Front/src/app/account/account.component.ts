import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { ActivatedRoute } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent {

  phoneNumber: string="";
  cta: any;
  saldo: number= 0;
  fin: boolean=false;
  monto: number=0;
  error: boolean=false;

  constructor(private route: ActivatedRoute,private usuarioService:UserService, private loginService:LoginService){ }
  
  ngOnInit() {
    this.phoneNumber=this.loginService.getPhoneNumberFromToken();  
    this.obtenerSaldoCuenta();
  }

  obtenerSaldoCuenta() {
    this.usuarioService.obtenerCuenta(this.phoneNumber)
      .subscribe(response => {        
        this.saldo = response;
        console.log(this.saldo);
      });
  }

  cargarSaldo(){
    console.log(this.monto);
    if(this.monto < 100){
        this.error=true;
    }else{
      this.usuarioService.cargarSaldo(this.phoneNumber,this.monto).subscribe();     
      this.error=false;
      window.location.reload();
    }  
 }

 logout(){
  this.loginService.logout();
  }  

}  



