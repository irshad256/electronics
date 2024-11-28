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
import { HTTP_INTERCEPTORS, HttpClient, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { CodeInputModule } from 'angular-code-input';
import { HttpTokenInterceptor } from './services/interceptor/http-token.interceptor';
import { BackofficeModule } from './backoffice/backoffice.module';
import { CategoryComponent } from './category/category.component';
import { ProductComponent } from './product/product.component';
import { OrderHistoryComponent } from './order-history/order-history.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { PersonalDetailsComponent } from './personal-details/personal-details.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';

@NgModule({ declarations: [
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
        CategoryComponent,
        ProductComponent,
        OrderHistoryComponent,
        WishlistComponent,
        PersonalDetailsComponent,
        UpdatePasswordComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    bootstrap: [AppComponent], imports: [BrowserModule,
        AppRoutingModule,
        RouterModule,
        FormsModule,
        CodeInputModule,
        BackofficeModule], providers: [
        HttpClient,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: HttpTokenInterceptor,
            multi: true
        },
        provideHttpClient(withInterceptorsFromDi())
    ] })
export class AppModule { }
