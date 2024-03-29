import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class ParkingService {

  private apiUrl = environment.parkingURL;
  private apiUser = environment.userUrl;

  constructor(private httpClient: HttpClient, private loginService:LoginService) { }
  
  private getHeaders(): HttpHeaders {
    const token = this.loginService.getToken();
    if (token) {
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    } else {
      return new HttpHeaders();
    }
  }

  estacionamientoActivo(phoneNumber:string): Observable<any>{
    const url = `${this.apiUser}/${phoneNumber}/estacionamientoActivo`;
    const headers = this.getHeaders();
    return this.httpClient.get<any>(url, { headers });
  }

  iniciarEstacionamiento(phoneNumber:string,patente:string): Observable<any>{
      const url = `${this.apiUrl}/${phoneNumber}/${patente}/start`
      console.log("Llega hasta aca al meto iniciar estacionamiento")
      const headers = this.getHeaders();
      console.log(headers)
      return this.httpClient.post<any>(url, {}, { headers: headers });
  }

  finalizarEstacionamiento(phoneNumber:string): Observable<any>{
    const url = `${this.apiUrl}/${phoneNumber}/finish`
    const headers = this.getHeaders();
     return this.httpClient.post(url, {}, { headers: headers });
}
  
}
