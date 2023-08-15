import { Component } from '@angular/core';
import { UserService } from '../../service/user.service';
import { ActivatedRoute } from '@angular/router';
import { LoginService } from '../../service/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavBarComponent {

  phoneNumber: string="";
  cta: any;
  saldo: number= 0;
  fin: boolean=false;
  monto: number=0;
  error: boolean=false;

  constructor(private route: ActivatedRoute,private usuarioService:UserService, private loginService:LoginService, private router: Router){ }
  
  ngOnInit() {
    this.phoneNumber=this.loginService.getPhoneNumberFromToken();  
    
  }

  navigateToHome() {
    this.router.navigate(['/home']);
  }
  
  navigateToAccount() {
    this.router.navigate(['/account']);
  }
  
  navigateToMovimientos() {
    this.router.navigate(['/moviment']); 
  }
  
  navigateToVehicles() {
    this.router.navigate(['/vehicles']);
  }

  logout(){
    this.loginService.logout();
    }  

}
