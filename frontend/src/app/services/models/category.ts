/* tslint:disable */
/* eslint-disable */
import { CategoryDto } from '../models/category-dto';
import { Product } from '../models/product';
export interface Category {
  allCategoryDto?: CategoryDto;
  code?: string;
  description?: string;
  id?: number;
  name?: string;
  products?: Array<Product>;
}
