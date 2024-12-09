import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SideNavService {
  public sideNavToggleSubject: BehaviorSubject<any> = new BehaviorSubject("start");
  public sideNavClosedSubject: BehaviorSubject<any> = new BehaviorSubject("start");
  public sideNavOpenedSubject: BehaviorSubject<any> = new BehaviorSubject("start");
  public sideNavShouldCloseSubject: BehaviorSubject<any> = new BehaviorSubject("start");


  public toggle() {
    return this.sideNavToggleSubject.next(null);
  }
  public close() {
    return this.sideNavClosedSubject.next(null);
  }
  public open() {
    return this.sideNavOpenedSubject.next(null);
  }
  public shouldClose() {
    return this.sideNavShouldCloseSubject.next(null);
  }
}
