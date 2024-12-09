import {Product} from "./product";
import {CustomerOrder} from "./customer-order";

export interface ProductionOrder {
  id: number
  externalId: string
  name: string
  measurement: {
    amount: number
    unitString: string
  }
  priority: number
  deadline: any
  status: string
  product: Product
  customerOrder: CustomerOrder
}
