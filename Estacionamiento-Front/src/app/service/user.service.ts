import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = environment.userUrl

  constructor(private httpClient: HttpClient, private loginService: LoginService) { }

  private getHeaders(): HttpHeaders {
    const token = this.loginService.getToken();
    if (token) {
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    } else {
      return new HttpHeaders();
    }
  }
  
  obtenerCuenta(phoneNumber: string): Observable<any> {
    const url = `${this.apiUrl}/${phoneNumber}/saldo`;
    const headers = this.getHeaders();
    return this.httpClient.get<any>(url, { headers });
  }

  obtenerVehiculos(phoneNumber: string): Observable<any>{
    const url = `${this.apiUrl}/${phoneNumber}/ListVehicles`;
    const headers = this.getHeaders();
    return this.httpClient.get<any>(url, { headers });
  }

  agregarUsuario(usuario: any) {
    const url = `${this.apiUrl}/add`;
    return this.httpClient.post<any>(url, usuario); 
  }

  cargarSaldo(phoneNumber: string, monto: number): Observable<any> {
    const url = `${this.apiUrl}/${phoneNumber}/sumar`;
    const headers = this.getHeaders();
    return this.httpClient.post(url, monto, { headers: headers });
  }

}
