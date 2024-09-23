/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addProduct } from '../fn/backoffice/add-product';
import { AddProduct$Params } from '../fn/backoffice/add-product';
import { Category } from '../models/category';
import { createCategory } from '../fn/backoffice/create-category';
import { CreateCategory$Params } from '../fn/backoffice/create-category';
import { getAllUsers } from '../fn/backoffice/get-all-users';
import { GetAllUsers$Params } from '../fn/backoffice/get-all-users';
import { Product } from '../models/product';
import { UserDto } from '../models/user-dto';

@Injectable({ providedIn: 'root' })
export class BackofficeService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `addProduct()` */
  static readonly AddProductPath = '/backoffice/product/add';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addProduct()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  addProduct$Response(params?: AddProduct$Params, context?: HttpContext): Observable<StrictHttpResponse<Product>> {
    return addProduct(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addProduct$Response()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  addProduct(params?: AddProduct$Params, context?: HttpContext): Observable<Product> {
    return this.addProduct$Response(params, context).pipe(
      map((r: StrictHttpResponse<Product>): Product => r.body)
    );
  }

  /** Path part for operation `createCategory()` */
  static readonly CreateCategoryPath = '/backoffice/category/add';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createCategory()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createCategory$Response(params: CreateCategory$Params, context?: HttpContext): Observable<StrictHttpResponse<Category>> {
    return createCategory(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createCategory$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createCategory(params: CreateCategory$Params, context?: HttpContext): Observable<Category> {
    return this.createCategory$Response(params, context).pipe(
      map((r: StrictHttpResponse<Category>): Category => r.body)
    );
  }

  /** Path part for operation `getAllUsers()` */
  static readonly GetAllUsersPath = '/backoffice/users';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllUsers()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllUsers$Response(params?: GetAllUsers$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<UserDto>>> {
    return getAllUsers(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllUsers$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllUsers(params?: GetAllUsers$Params, context?: HttpContext): Observable<Array<UserDto>> {
    return this.getAllUsers$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<UserDto>>): Array<UserDto> => r.body)
    );
  }

}
