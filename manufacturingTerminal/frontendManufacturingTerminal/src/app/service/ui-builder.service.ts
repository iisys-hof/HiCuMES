import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {data} from "jquery";
import {Area, DynamicLayout} from "../interfaces/dynamic-layout";
import {Observable, of, ReplaySubject, Subject} from "rxjs";
import {Machine} from "../interfaces/machine";
import {catchError, map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UiBuilderService {


  dynamicLayout:DynamicLayout | undefined;
  private dynamicLayout$: Subject<DynamicLayout>;

  dynamicFormLayout: any | undefined;
  private dynamicFormLayout$: Subject<any>;


  constructor(private http: HttpClient) {
    this.dynamicLayout$ = new ReplaySubject(1);
    this.dynamicFormLayout$ = new ReplaySubject(1);
  }

  generateHTML() {
    return "";
  }

  getDynamicLayout() {
     this.http.get<DynamicLayout>("./assets/testUI.json").subscribe((x) => {
       this.dynamicLayout$.next(x);
       console.log(this.dynamicLayout$)
    })
    return this.dynamicLayout$;
  }
  // getDynamicFormLayout(name?: string) {
  //   if(name === undefined || name === null) {
  //     this.http.get<any>("../assets/formlyUI.json").subscribe((x) => {
  //       this.dynamicFormLayout$.next(x);
  //     })
  //   }
  //   else {
  //     this.http.get<any>("../assets/" + name + ".json").subscribe((x) => {
  //       this.dynamicFormLayout$.next(x);
  //     })
  //   }
  //
  //   return this.dynamicFormLayout$;
  // }

  getDynamicFormLayout(name?: string) {
    if (name === undefined || name === null) {
      return this.http.get<any>("./assets/UIDefinitions/formlyUI.json")
    } else {
      return this.http.get<any>("./assets/UIDefinitions/" + name + ".json").pipe(map(response => {return response}), catchError(() => of(undefined)));
    }
  }

  getTestDataSource(name?: string) {
    if(name === undefined || name === null || !this.fileExists("../assets/UITestdata/" + name + "-DataSource.json")) {
      return this.http.get("./assets/UITestdata/testDataSource.json");
    }
    else
    {
        return this.http.get("./assets/UITestdata/" + name + "-DataSource.json")
    }
  }

  public fileExists(url: string): Observable<boolean> {
    return this.http.get(url).pipe(map(() => true), catchError(() => of(false)));
  }

}
