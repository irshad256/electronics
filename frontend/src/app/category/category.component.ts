import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/services';
import { CategoryDto, ProductDto } from '../services/models';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiConfiguration } from '../services/api-configuration';
import { AuthService } from '../services/auth/auth.service';

@Component({
    selector: 'app-category',
    templateUrl: './category.component.html',
    styleUrls: ['./category.component.scss'],
    standalone: false
})
export class CategoryComponent implements OnInit {

  constructor(
    private categoryService: CategoryService,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) { }

  category!: CategoryDto;
  categoryCode: String | null = '';
  products: any;
  apiConfig: ApiConfiguration = new ApiConfiguration();
  isLoggedIn: boolean = false;

  productPage(code: any) {
     this.router.navigate(['p/' + code])
  }

  ngOnInit(): void {
     // Access the `categoryCode` parameter from the URL
     this.activatedRoute.paramMap.subscribe(params => {
      this.categoryService.getCategory({
        code: params.get('categoryCode') as string
      }).subscribe({
        next: (res) => {
          this.category = res;
          this.products = res.products;
        },
        error: (err) => {
          console.log(err);
        }
      })
    }); 
    this.isLoggedIn = this.authService.isLoggedIn();
  }
}
