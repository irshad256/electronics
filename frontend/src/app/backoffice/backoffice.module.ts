import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminDashComponent } from './admin-dash/admin-dash.component';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { ProductsComponent } from './products/products.component';
import { CategoriesComponent } from './categories/categories.component';



@NgModule({
  declarations: [
    AdminDashComponent,
    ManageUserComponent,
    ProductsComponent,
    CategoriesComponent
  ],
  imports: [
    CommonModule
  ]
})
export class BackofficeModule { }
