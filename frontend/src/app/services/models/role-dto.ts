/* tslint:disable */
/* eslint-disable */
import { UserDto } from '../models/user-dto';
export interface RoleDto {
  createdDate?: string;
  id?: number;
  lastModifiedDate?: string;
  name?: string;
  users?: Array<UserDto>;
}
