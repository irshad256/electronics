import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './login/login.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { RegisterComponent } from './register/register.component';
import { TermsAndConditionsComponent } from './terms-and-conditions/terms-and-conditions.component';
import { ActivateAccountComponent } from './activate-account/activate-account.component';
import { AuthGuard } from './auth/auth.guard';
import { AdminDashComponent } from './backoffice/admin-dash/admin-dash.component';
import { RoleGuard } from './auth/role.guard';
import { ProductsComponent } from './backoffice/products/products.component';
import { CategoriesComponent } from './backoffice/categories/categories.component';
import { BackofficeLayoutComponent } from './backoffice/layout/layout.component';
import { ManageUserComponent } from './backoffice/manage-user/manage-user.component';
import { CategoryComponent } from './category/category.component';

const routes: Routes = [
  { 
    path:'', 
    component:LayoutComponent,
    children:[
      {
        path: 'home',
        component: HomeComponent
      },
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
      },
      {
        path: 'login',
        component: LoginComponent,
        canActivate: [AuthGuard] 
      },
      {
        path: 'forgot-password',
        component: ResetPasswordComponent,
        canActivate: [AuthGuard] 
      },
      {
        path: 'login/register',
        component: RegisterComponent,
        canActivate: [AuthGuard] 
      },
      {
        path: 'terms-and-conditions',
        component: TermsAndConditionsComponent
      },
      {
        path: 'activate-account',
        component: ActivateAccountComponent,
        canActivate: [AuthGuard] 
      },
      {
        path: 'c/:categoryCode',
        component: CategoryComponent
        
      }
    ]
  },
      {
        path: 'backoffice',
        component: BackofficeLayoutComponent,
        canActivate: [RoleGuard],
        data: { expectedRole: 'ADMIN' },
        children:[
          {
            path: '',
            component: AdminDashComponent,
          },
          {
            path: 'products',
            component: ProductsComponent,
          },
          {
            path: 'categories',
            component: CategoriesComponent,
          },
          {
            path: 'users',
            component: ManageUserComponent
          }
        ]
      }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
