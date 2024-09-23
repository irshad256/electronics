import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-backoffice-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class BackofficeLayoutComponent implements OnInit {

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  fullName!: string;

  logout() {
    this.authService.logout();
    this.router.navigate(['home'])
  }

  isLoggedIn(): boolean {
     return this.authService.isLoggedIn();
  }
 
  backofficeHome() {
    return this.router.navigate(['backoffice']);
  }

  storefrontHome() {
    return this.router.navigate(['home']);
  }

  ngOnInit(): void {
    this.fullName = this.authService.getFullName();
  }

}
