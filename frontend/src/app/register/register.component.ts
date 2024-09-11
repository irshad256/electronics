import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(private router:Router) { }

  home() {
    this.router.navigate(['home']);
  }

  login() {
    this.router.navigate(['login']);
  }

  termsAndConditions() {
    this.router.navigate(['terms-and-conditions']);
  }

  ngOnInit(): void {
  }

}
