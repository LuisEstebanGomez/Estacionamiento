import { Component } from '@angular/core';
import { MovimentService } from '../../service/moviment.service';
import { LoginService } from '../../service/login.service';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-moviment',
  templateUrl: './moviment.component.html',
  styleUrls: ['./moviment.component.css']
})
export class MovimentComponent {
  phoneNumber: string="";
  saldo: number=0;
  movimientos: any[] = [];
  
 
  displayedColumns: string[] = ['id', 'descripcion', 'monto', 'fecha']; 
  
  constructor(private movimentService: MovimentService, private loginService:LoginService, private usuarioService: UserService) { }

  
  ngOnInit() {
    this.phoneNumber=this.loginService.getPhoneNumberFromToken();
  
    this.cargarMovimientos();
  }
  
  cargarMovimientos() {
    this.movimentService.getMovimientos(this.phoneNumber).subscribe(
      (data) => {
        this.movimientos = data;
      },
      (error) => {
        console.error('Error al obtener los movimientos', error);
      }
    );
  }

  formatearFecha(fecha: string): string {
    return new Date(fecha).toLocaleString('es-ES', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false 
    });
  }


}
