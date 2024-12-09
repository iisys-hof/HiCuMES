import { Injectable } from '@angular/core';
import {ReplaySubject, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DisableResponseButtonStateService {

  private disabledButtons$: Subject<Set<string>>;
  private disableButtonSet: Set<string>;


  constructor() {
    this.disabledButtons$ = new ReplaySubject<Set<string>>();
    this.disableButtonSet = new Set<string>()
    this.disabledButtons$.next(this.disableButtonSet);
  }

  getDisabledButtons() {
    return this.disabledButtons$.asObservable();
  }

  public disableButton(taskId: string)
  {
    this.disableButtonSet.add(taskId)
    this.disabledButtons$.next(this.disableButtonSet);
  }

  public enableButton(taskId: string)
  {
    if(this.disableButtonSet.delete(taskId))
    {
      this.disabledButtons$.next(this.disableButtonSet);
    }
  }
}
