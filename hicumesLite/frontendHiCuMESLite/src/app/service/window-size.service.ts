import {Injectable} from '@angular/core';
import {fromEvent, Observable, ReplaySubject, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WindowSizeService {

  public windowHigh$: Subject<number>;
  public windowWidth$: Subject<number>;
  public resizeObservable$: Observable<any>;

  constructor() {
    this.windowHigh$ = new ReplaySubject<number>();
    this.windowWidth$ = new ReplaySubject<number>();
    this.windowHigh$.next(window.innerHeight);
    this.windowWidth$.next(window.innerWidth);

    this.resizeObservable$ = fromEvent(window, 'resize');
    this.resizeObservable$.pipe().subscribe(evt => {
      this.windowHigh$.next(window.innerHeight);
      this.windowWidth$.next(window.innerWidth);
    });
  }

  getHeightObservable(): Observable<number> {
    return this.windowHigh$.asObservable();
  }

  getWidthObservable(): Observable<number> {
    return this.windowWidth$.asObservable();
  }

}
