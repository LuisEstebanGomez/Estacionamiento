import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

import { Router } from '@angular/router';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})


export class SigninComponent implements OnInit {

  errorInicio:boolean = false;
  loading:boolean = false;
  ok:boolean = false;
  me:boolean= false;
  mensajeError:string = "";

  usuario: any={};
  constructor(private userService: UserService,private router:Router) {

   }

   agregarUsuario(): void {
    if (this.validar()) {
      this.userService.agregarUsuario(this.usuario).subscribe(
        (response: any) => {
          console.log("error")
        },
        (error: any) => {
          if (error && error.status === 200) {     
            console.log('usuario Agregado');
            this.ok=true;
            this.me = false;
          } else {
            console.log('Error al crear el usuario:', error);
            this.mensajeError = "El usuario ya se encuentra registrado";
            this.me = true;
            this.ok = false;
          }
          
        }
      );
    } else {
      this.me = true;
      console.log("Error al crear el usuario");
      this.mensajeError = "Error al crear el usuario - Ejemplo Teléfono 2215425008";
      this.ok = false;
    }
  }

   validar(){
      const nick=this.usuario.phoneNumber;
      return /^\d+$/.test(nick) && nick.length === 10;
   }

  ngOnInit(): void { 
  }

  regresar(){
    location.href="/login";
  }


 
}
