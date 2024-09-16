/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { getAllProduct } from '../fn/backoffice/get-all-product';
import { GetAllProduct$Params } from '../fn/backoffice/get-all-product';
import { getAllUsers } from '../fn/backoffice/get-all-users';
import { GetAllUsers$Params } from '../fn/backoffice/get-all-users';
import { Product } from '../models/product';
import { UserDto } from '../models/user-dto';

@Injectable({ providedIn: 'root' })
export class BackofficeService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
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

  /** Path part for operation `getAllProduct()` */
  static readonly GetAllProductPath = '/backoffice/products';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllProduct()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllProduct$Response(params?: GetAllProduct$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<Product>>> {
    return getAllProduct(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllProduct$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllProduct(params?: GetAllProduct$Params, context?: HttpContext): Observable<Array<Product>> {
    return this.getAllProduct$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<Product>>): Array<Product> => r.body)
    );
  }

}
