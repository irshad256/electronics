/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { Category } from '../../models/category';
import { CategoryDto } from '../../models/category-dto';

export interface CreateCategory$Params {
      body: CategoryDto
}

export function createCategory(http: HttpClient, rootUrl: string, params: CreateCategory$Params, context?: HttpContext): Observable<StrictHttpResponse<Category>> {
  const rb = new RequestBuilder(rootUrl, createCategory.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Category>;
    })
  );
}

createCategory.PATH = '/backoffice/category/add';
