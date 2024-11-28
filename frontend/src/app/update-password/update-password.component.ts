import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../services/services';
import { AuthService } from '../services/auth/auth.service';
import { UpdateProfileRequest } from '../services/models';

@Component({
    selector: 'app-update-password',
    templateUrl: './update-password.component.html',
    styleUrls: ['./update-password.component.scss'],
    standalone: false
})
export class UpdatePasswordComponent implements OnInit {

  constructor(
    private router: Router,
    private accountService: AccountService,
    private authService: AuthService
  ) { }

  email!: string;
  cnfPassword!: string;
  profileRequest: UpdateProfileRequest = {
    email : '',
    oldPassword : '',
    newPassword : ''
  }
  errMsg: Array<String> = [];

  cancel() {
    this.router.navigate(['home']);
  }

  save() {
    this.profileRequest.email = this.email
    if(this.cnfPassword===this.profileRequest.newPassword){
      this.accountService.updatePassword({
        body: this.profileRequest
      }).subscribe({
        next: (res) => {
          console.log(res);
        },
        error: (err) => {
          console.log(err);
        }
      })
    }
    else {
      this.errMsg = ["Password doesn't match"];
    }
  }
  
  home() {
    this.router.navigate(['home']);
  }

  ngOnInit(): void {
    this.email = this.authService.getEmail();
  }

}
