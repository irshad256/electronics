import { Component, OnInit } from '@angular/core';
import { UserDto } from 'src/app/services/models';
import { BackofficeService } from 'src/app/services/services';

@Component({
    selector: 'app-manage-user',
    templateUrl: './manage-user.component.html',
    styleUrls: ['./manage-user.component.scss'],
    standalone: false
})
export class ManageUserComponent implements OnInit {

  constructor(
    private backofficeService: BackofficeService
  ) { }

  users!: Array<UserDto>;

  ngOnInit(): void {
    this.backofficeService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      }
    });
  }

}
