import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

   private apiUrl = environment.userUrl;

  constructor(private http: HttpClient, private loginService:LoginService, private httpClient:HttpClient) {}

  private getHeaders(): HttpHeaders {
    
    const token = this.loginService.getToken();
    if (token) {
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    } else {
      return new HttpHeaders();
    }
  }

  getVehicles(phoneNumber: string): Observable<any[]> {
    const url = `${this.apiUrl}/${phoneNumber}/ListVehicles`;
    const headers = this.getHeaders();
    return this.httpClient.get<any[]>(url, { headers });
  }

  agregarVehiculo(phoneNumber: string, patente: string): Observable<any> {
    const url = `${this.apiUrl}/${phoneNumber}/add`;
    const headers = this.getHeaders();
    return this.httpClient.post(url, {patente}, { headers: headers });
  }
  
}
