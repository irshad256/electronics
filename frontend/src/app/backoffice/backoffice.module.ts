import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminDashComponent } from './admin-dash/admin-dash.component';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { ProductsComponent } from './products/products.component';
import { CategoriesComponent } from './categories/categories.component';
import { RouterModule } from '@angular/router';
import { BackofficeLayoutComponent } from './layout/layout.component';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    AdminDashComponent,
    ManageUserComponent,
    ProductsComponent,
    CategoriesComponent,
    BackofficeLayoutComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
  ]
})
export class BackofficeModule { }
