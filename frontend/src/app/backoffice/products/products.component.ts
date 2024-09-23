import { Component, OnInit } from '@angular/core';
import { ApiConfiguration } from 'src/app/services/api-configuration';
import { Category, ProductDto } from 'src/app/services/models';
import { BackofficeService, CategoryService, ProductService } from 'src/app/services/services';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {

  constructor(
    private backofficeService: BackofficeService,
    private productService: ProductService,
    private categoryService: CategoryService
  ) {}

  products: Array<ProductDto> = [];
  selectedFile!: File;
  product: ProductDto = { code: '', name: '', description: '', stock: 0, active: false, price: 0 };
  categories!: Array<Category>;
  categoryCode: string = '';
  error: string = '';
  apiConfig: ApiConfiguration = new ApiConfiguration();

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement; // Type assertion
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }
  
  onSubmit() {
    let paylod = {
      body:{
        productDto: this.product,
        image:this.selectedFile
      }
    }
    console.log(paylod)
    this.backofficeService.addProduct(paylod).subscribe({
      next: (res) => {
        console.log(res);
        this.closeModel();
        location.reload();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }


  openModel() {
    const model = document.getElementById('add-product-model');
    if(model != null) {
      model.style.display = 'block';
    }
  }

  closeModel() {
    const model = document.getElementById('add-product-model');
    if(model != null) {
      model.style.display = 'none';
    }
  }

  ngOnInit(): void {
    this.productService.getAllProduct().subscribe({
      next: (res) => {
        this.products = res;
      },
      error: (err) => {
        console.log(err);
      }
    });
    this.categoryService.getAllCategories().subscribe({
      next: (res) => {
        this.categories = res;
      },
      error: (err) => {
        console.log(err);
        this.error = "Something went wrong!!!";
      }
    })
  }

}
