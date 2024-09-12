import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationRequest } from '../services/models';
import { AuthenticationService } from '../services/services';
import { error } from 'console';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

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
          console.log(err);
        }
      })
    }

  ngOnInit(): void {
  }

}
