/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { getAllProduct } from '../fn/product/get-all-product';
import { GetAllProduct$Params } from '../fn/product/get-all-product';
import { ProductDto } from '../models/product-dto';

@Injectable({ providedIn: 'root' })
export class ProductService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllProduct()` */
  static readonly GetAllProductPath = '/p/';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllProduct()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllProduct$Response(params?: GetAllProduct$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ProductDto>>> {
    return getAllProduct(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllProduct$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllProduct(params?: GetAllProduct$Params, context?: HttpContext): Observable<Array<ProductDto>> {
    return this.getAllProduct$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<ProductDto>>): Array<ProductDto> => r.body)
    );
  }

}
