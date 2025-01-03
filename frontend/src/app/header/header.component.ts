import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { CategoryService } from '../services/services';
import { CategoryDto } from '../services/models';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss'],
    standalone: false
})
export class HeaderComponent implements OnInit {
  constructor(
    private router:Router,
    private authService: AuthService,
    private categoryService: CategoryService
  ) { }

  fullName!: string;
  categories: Array<CategoryDto> = []
  isOpened: boolean = false;

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

  categoryPage(code: any){
    this.router.navigate(['c/' + code])
  }

  orderHistory() {
    this.router.navigate(['order-history']);
  }
  wishlist() {
    this.router.navigate(['wishlist']);
  }
  pesonalDetails() {
    this.router.navigate(['personal-details']);
  }
  updatePassword() {
    this.router.navigate(['update-password']);
  }

  ngOnInit(): void {
    this.fullName = this.authService.getFullName();
    this.categoryService.getAllCategories().subscribe({
      next: (res) => {
        this.categories = res;
      }
    })
  }

}
