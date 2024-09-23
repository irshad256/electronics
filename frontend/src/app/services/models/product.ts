/* tslint:disable */
/* eslint-disable */
import { Category } from '../models/category';
import { ProductDto } from '../models/product-dto';
export interface Product {
  active?: boolean;
  categories?: Array<Category>;
  code?: string;
  createdDate?: string;
  description?: string;
  id?: number;
  imgUrl?: string;
  lastModifiedDate?: string;
  name?: string;
  price?: number;
  productDto?: ProductDto;
  stock?: number;
}
