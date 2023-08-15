import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from '../environments/environments';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

    private apiUrl = environment.authURL;
    private tokenKey = 'auth_token';

    constructor(private http: HttpClient, private router: Router) { }

    login(phoneNumber: string, password: string): Observable<any> {
      const user = { phoneNumber, password };
      return this.http.post(`${this.apiUrl}`, user,{ observe: 'response' }).pipe(
        tap((response: any) => {
          console.log("Respuesta completa:", response);
          const token = response.headers.get('Authorization')
          console.log("token ", token);
          //const token2 = response.headers.get('Authorization');
          localStorage.setItem(this.tokenKey, token);      
          //console.log("TOKEN :",token2)
          if (token) {
    
            localStorage.setItem(this.tokenKey, token);
            console.log("TOKEN :",token)
          }
        }),
        // catchError(this.handleError)
      );
    }

    getToken(): string | null {
      // Obtener el token del LocalStorage
      return localStorage.getItem(this.tokenKey);
    }

    getPhoneNumberFromToken(): string {
      const token = this.getToken();
      if (token) {
        // Decodificar  (en base64)
        const payloadBase64 = token.split('.')[1];
        const payload = JSON.parse(atob(payloadBase64));
        return payload.sub || null; // phoneNumber se encuentra en el campo "sub"
      }
      return "";
    }
  
    logout(): void {
      localStorage.removeItem(this.tokenKey);
      this.router.navigate(['/login']);
    }
    
}
