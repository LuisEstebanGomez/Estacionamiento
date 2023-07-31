import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpHeaders} from '@angular/common/http';
import { LoginService } from '../login.service';
import { FormsModule } from '@angular/forms';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Token, verifyHostBindings } from '@angular/compiler';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {
  phoneNumber: string = '';
  password: string = '';
  errorInicio: boolean = false;

  constructor(private loginService: LoginService,private router:Router) { }
  
  login() {
    
    if(this.varificarPhoneNumber()){  
      this.loginService.login(this.phoneNumber, this.password).subscribe(
        (response) => {
        // El token se encuentra en el encabezado 'Bearer <token>'
        console.log("LOGIN EXITOSO")
        console.log(this.loginService.getToken());
        console.log(this.loginService.getPhoneNumberFromToken())
        this.router.navigate(['/home'])
        },
        (error) => {
      
        console.error('Error inicio de session favor verificar usuario y contraseña');
        this.errorInicio=true;
      }
      );
    }else{
      console.log("verificar usuario y contraseña");
      this.errorInicio=true;
    }
}

varificarPhoneNumber() { 
  return /^\d+$/.test(this.phoneNumber) && this.phoneNumber.length === 10;
}

crearCuenta(){
  location.href='/signin';
}

}

// /: Delimitador que indica que se trata de una expresión regular en JavaScript.
// ^: Coincide con el inicio del string.
// \d: Representa cualquier dígito numérico del 0 al 9.
// +: Significa que el dígito numérico debe aparecer una o más veces.
// $: Coincide con el final del string.