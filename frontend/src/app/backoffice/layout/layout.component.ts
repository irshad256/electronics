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
 

  backofficeHome() {
    return this.router.navigate(['backoffice']);
  }

  ngOnInit(): void {
    this.fullName = this.authService.getFullName();
  }

}
