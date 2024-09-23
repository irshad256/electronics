import { Component, OnInit } from '@angular/core';
import { CategoryDto } from 'src/app/services/models';
import { BackofficeService } from 'src/app/services/services';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {

  constructor(
    private backofficeService: BackofficeService
  ) { }

  categories!: Array<CategoryDto>;
  category: CategoryDto = {name: '', description: '', code: '', superCategories:[]};
  error: string = '';

  openModel() {
    const model = document.getElementById('add-category-model');
    if(model != null) {
      model.style.display = 'block';
    }
  }

  closeModel() {
    const model = document.getElementById('add-category-model');
    if(model != null) {
      model.style.display = 'none';
    }
  }

  onSubmit() {
    if(!this.category.code){
      this.error = "Code cannot be empty!!!";
      return;
    }
    this.backofficeService.createCategory({
      body: this.category
    }).subscribe({
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

  ngOnInit(): void {
    this.backofficeService.getAllCategories().subscribe({
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
