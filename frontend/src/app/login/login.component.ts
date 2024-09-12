import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/services';
import { AuthenticationRequest } from '../services/models';
import { TokenService } from '../services/token/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) { }

  authRequest: AuthenticationRequest = {
    email : '',
    password: ''
  }
  errMsgEmail: Array<string> = [];
  errMsgPswd: Array<string> = [];
  errMsg: Array<String> = [];
  

  home() {
    this.router.navigate(['home']);
  }

  login() {
    this.errMsgEmail = [];
    this.errMsgPswd = [];
    this.authService.login({
      body: this.authRequest
    }).subscribe({
      next: (response) => {
        this.tokenService.token = response.token as string;
        this.router.navigate(['home']);
      },
      error: (err) => {
        if(err.error.errors) {
          err.error.errors.email ? this.errMsgEmail.push(err.error.errors.email) : this.errMsgEmail = [];
          err.error.errors.password ? this.errMsgPswd.push(err.error.errors.password) : this.errMsgPswd = [];
        } else {
          err.error.businessExceptionDescription ? this.errMsg.push(err.error.businessExceptionDescription) : this.errMsg = [];
        }
      }
    })
  }

  resetPassword() {
    this.router.navigate(['forgot-password']);
  }

  register() {
    this.router.navigate(['login/register'])
  }

  ngOnInit(): void {
  }

}
