/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { CategoryDto } from '../models/category-dto';
import { getAllCategories } from '../fn/category/get-all-categories';
import { GetAllCategories$Params } from '../fn/category/get-all-categories';
import { getCategory } from '../fn/category/get-category';
import { GetCategory$Params } from '../fn/category/get-category';

@Injectable({ providedIn: 'root' })
export class CategoryService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getCategory()` */
  static readonly GetCategoryPath = '/c/{code}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getCategory()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCategory$Response(params: GetCategory$Params, context?: HttpContext): Observable<StrictHttpResponse<CategoryDto>> {
    return getCategory(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getCategory$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCategory(params: GetCategory$Params, context?: HttpContext): Observable<CategoryDto> {
    return this.getCategory$Response(params, context).pipe(
      map((r: StrictHttpResponse<CategoryDto>): CategoryDto => r.body)
    );
  }

  /** Path part for operation `getAllCategories()` */
  static readonly GetAllCategoriesPath = '/c/';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllCategories()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllCategories$Response(params?: GetAllCategories$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<CategoryDto>>> {
    return getAllCategories(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllCategories$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllCategories(params?: GetAllCategories$Params, context?: HttpContext): Observable<Array<CategoryDto>> {
    return this.getAllCategories$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<CategoryDto>>): Array<CategoryDto> => r.body)
    );
  }

}
