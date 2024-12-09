import {Product} from "./product";

export interface ProductRelationship {
  id: number
  externalId: string
  measurement: {
    amount: number
    unitString: string
  }
  product: Product
  part: Product
}
