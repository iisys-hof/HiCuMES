import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {
  public choosenSubStep: BehaviorSubject<any> = new BehaviorSubject("SSCV");

  constructor() {
  }

  public changeURL(text: string) {
    return this.choosenSubStep.next(text);
  }
}

