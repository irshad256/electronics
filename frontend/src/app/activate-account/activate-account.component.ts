import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/services';
import { Router } from '@angular/router';

@Component({
    selector: 'app-activate-account',
    templateUrl: './activate-account.component.html',
    styleUrls: ['./activate-account.component.scss'],
    standalone: false
})
export class ActivateAccountComponent implements OnInit {

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) { }

  message: any;
  isOkay: boolean = true;
  submitted:boolean = false;

  redirectToLogin() {
    this.router.navigate(['login'])
  }

  onCodeCompleted(token: string) {
    this.authService.activateAccount({
      token: token
    }).subscribe({
      next: ()=>{
        this.submitted = true;
        this.isOkay = true;
      },
      error: (err) => {
        console.log(err);
        this.submitted = true;
        this.isOkay = false;
      }
    })
  }

  ngOnInit(): void {
  }

}
