import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-admin-dash',
  templateUrl: './admin-dash.component.html',
  styleUrls: ['./admin-dash.component.scss']
})
export class AdminDashComponent implements OnInit{

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  fullName!: string;
 

  backofficeHome() {
    return this.router.navigate(['backoffice']);
  }

  ngOnInit(): void {
    this.fullName = this.authService.getFullName();
  }

}
