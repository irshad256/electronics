import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { RouterModule } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { RegisterComponent } from './register/register.component';
import { TermsAndConditionsComponent } from './terms-and-conditions/terms-and-conditions.component';
import { FormsModule } from '@angular/forms';
import { ActivateAccountComponent } from './activate-account/activate-account.component';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from '@angular/common/http';
import { CodeInputModule } from 'angular-code-input';
import { HttpTokenInterceptor } from './services/interceptor/http-token.interceptor';
import { BackofficeModule } from './backoffice/backoffice.module';
import { CategoryComponent } from './category/category.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LayoutComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    ResetPasswordComponent,
    RegisterComponent,
    TermsAndConditionsComponent,
    ActivateAccountComponent,
    CategoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    CodeInputModule,
    BackofficeModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    HttpClient,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpTokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
