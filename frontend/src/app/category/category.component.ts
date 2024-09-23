import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/services';
import { CategoryDto } from '../services/models';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  constructor(
    private categoryService: CategoryService,
    private activatedRoute: ActivatedRoute
  ) { }

  category!: CategoryDto;
  categoryCode: String | null = '';

  ngOnInit(): void {
     // Access the `categoryCode` parameter from the URL
     this.activatedRoute.paramMap.subscribe(params => {
      this.categoryService.getCategory({
        code: params.get('categoryCode') as string
      }).subscribe({
        next: (res) => {
          this.category = res;
        },
        error: (err) => {
          console.log(err);
        }
      })
    }); 
  }
}
