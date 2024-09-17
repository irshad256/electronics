import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductDto } from 'src/app/services/models';
import { BackofficeService } from 'src/app/services/services';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {

  constructor(
    private backofficeService: BackofficeService,
    private router: Router
  ) { }

  products!: Array<ProductDto>;
  product: ProductDto = { code: '', name: '', description: '', stock: 0, active: false };
  selectedFile!: Blob;

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement; // Type assertion
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }
  
  onSubmit() {
    this.backofficeService.addProduct({
      body: {
        productDto:this.product,
        file:this.selectedFile
      }
    }).subscribe();
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
    this.backofficeService.getAllProduct().subscribe({
      next: (res) => {
        this.products = res;
      }
    })
  }

}
