/* tslint:disable */
/* eslint-disable */
import { ProductDto } from '../models/product-dto';
export interface CategoryDto {
  code?: string;
  description?: string;
  name?: string;
  products?: Array<ProductDto>;
  subCategories?: Array<string>;
  superCategories?: Array<string>;
}
