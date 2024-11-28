import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationRequest } from '../services/models';
import { AuthenticationService } from '../services/services';
import { error } from 'console';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
    standalone: false
})
export class RegisterComponent {

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) { }

  registrationRequest: RegistrationRequest={
    title: '',
    email: '',
    firstName: '',
    lastName: '',
    password: '',
    confirmPassword: '',
    termsAndConditionCheck: false
  }
  errMsgTitle: Array<string> = [];
  errMsgFirstname: Array<string> = [];
  errMsgLastName: Array<string> = [];
  errMsgEmail: Array<string> = [];
  errMsgPswd: Array<string> = [];
  errMsgCnfPswd: Array<string> = [];

  home() {
    this.router.navigate(['home']);
  }

  login() {
    this.router.navigate(['login']);
  }

  termsAndConditions() {
    this.router.navigate(['terms-and-conditions']);
  }

  register() {
    this.errMsgTitle = [];
    this.errMsgFirstname = [];
    this.errMsgLastName = [];
    this.errMsgEmail = [];
    this.errMsgPswd = [];
    this.errMsgCnfPswd = [];
    this.authService.register(
      {
        body: this.registrationRequest
      }
    ).subscribe({
      next: (res)=> {
        console.log(res);
        this.router.navigate(['activate-account'])
      },
      error: (err) => {
        if(err.error.errors) {
          err.error.errors.title ? this.errMsgTitle.push(err.error.errors.title) : this.errMsgTitle = [];
          err.error.errors.firstName ? this.errMsgFirstname.push(err.error.errors.firstName) : this.errMsgFirstname = [];
          err.error.errors.lastName ? this.errMsgLastName.push(err.error.errors.lastName) : this.errMsgLastName = [];
          err.error.errors.email ? this.errMsgEmail.push(err.error.errors.email) : this.errMsgEmail = [];
          err.error.errors.password ? this.errMsgPswd.push(err.error.errors.password) : this.errMsgPswd = [];
          err.error.errors.confirmPassword ? this.errMsgCnfPswd.push(err.error.errors.confirmPassword) : this.errMsgCnfPswd = [];
        }
      }
    })
  }

}
