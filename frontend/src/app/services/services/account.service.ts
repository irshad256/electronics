/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { updatePassword } from '../fn/account/update-password';
import { UpdatePassword$Params } from '../fn/account/update-password';
import { UserDto } from '../models/user-dto';

@Injectable({ providedIn: 'root' })
export class AccountService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `updatePassword()` */
  static readonly UpdatePasswordPath = '/account/update-password';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updatePassword()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updatePassword$Response(params: UpdatePassword$Params, context?: HttpContext): Observable<StrictHttpResponse<UserDto>> {
    return updatePassword(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updatePassword$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updatePassword(params: UpdatePassword$Params, context?: HttpContext): Observable<UserDto> {
    return this.updatePassword$Response(params, context).pipe(
      map((r: StrictHttpResponse<UserDto>): UserDto => r.body)
    );
  }

}
