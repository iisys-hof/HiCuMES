import {Injectable} from '@angular/core';
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductionNumbersService {
  public newProductionNumber: Subject<any> = new Subject();

  constructor() {
  }

  public addProductionNumberEntry(entry: any) {
    return this.newProductionNumber.next(entry);
  }
}
