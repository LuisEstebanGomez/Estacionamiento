import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class MovimentService {
  private apiUrl = environment.accountURL

  constructor(private httpClient: HttpClient, private loginService:LoginService) {}

  private getHeaders(): HttpHeaders {
    const token = this.loginService.getToken();
    if (token) {
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    } else {
      return new HttpHeaders();
    }
  }

  getMovimientos(phoneNumber: string): Observable<any[]> {
    const url = `${this.apiUrl}/${phoneNumber}/movements`;
    const headers = this.getHeaders();
    return this.httpClient.get<any[]>(url, { headers });
  }
}