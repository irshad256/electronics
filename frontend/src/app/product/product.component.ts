import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/services';
import { ActivatedRoute } from '@angular/router';
import { ProductDto } from '../services/models';
import { ApiConfiguration } from '../services/api-configuration';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute
  ) { }

  product!: ProductDto;
  apiConfig: ApiConfiguration = new ApiConfiguration();

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.productService.getProductForCode({
        code: params.get('productCode') as string
      }).subscribe({
        next: (res) => {
          this.product = res;
        },
        error: (err) => {
          console.log(err);
        }
      })
    });
  }

}
