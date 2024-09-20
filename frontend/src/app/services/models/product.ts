/* tslint:disable */
/* eslint-disable */
import { ProductDto } from '../models/product-dto';
export interface Product {
  active?: boolean;
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
