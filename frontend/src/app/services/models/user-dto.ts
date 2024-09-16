/* tslint:disable */
/* eslint-disable */
import { RoleDto } from '../models/role-dto';
export interface UserDto {
  accountLocked?: boolean;
  createdDate?: string;
  dateOfBirth?: string;
  email?: string;
  enabled?: boolean;
  firstName?: string;
  id?: number;
  lastModifiedDate?: string;
  lastName?: string;
  password?: string;
  roles?: Array<RoleDto>;
  title?: string;
}
