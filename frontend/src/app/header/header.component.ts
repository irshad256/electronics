import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {


  constructor(
    private router:Router,
    private authService: AuthService
  ) { }

  login() {
    this.router.navigate(['login']);
  }

  logout() {
    this.authService.logout();
  }

  isLoggedIn(): boolean {
     return this.authService.isLoggedIn();
  }

  home() {
    this.router.navigate(['home']);
  }

  ngOnInit(): void {
  }

}
