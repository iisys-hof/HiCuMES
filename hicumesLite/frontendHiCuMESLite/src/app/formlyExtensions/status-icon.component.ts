import {AfterViewInit, Component} from '@angular/core';
import {FieldType} from '@ngx-formly/material/form-field';
import {FormControl} from "@angular/forms";
import {FieldWrapper} from "@ngx-formly/core";
import { get, set } from "lodash";
@Component({
  selector: 'status-icon',
  template: `
    <ng-container *ngIf="getField()==='READY_TO_START'">
      <img alt="Dashboard" height="32" src="assets/img/ready_to_start.svg" width="32"/>
    </ng-container>
    <ng-container *ngIf="getField()==='RUNNING'">
      <img alt="Dashboard" height="28" src="assets/img/running.svg" style="margin-right: 2px; margin-left: 2px"
           width="28"/>
    </ng-container>
    <ng-container *ngIf="getField()==='FINISHED'">
      <img alt="Dashboard" height="32" src="assets/img/finished.svg" width="32"/>
    </ng-container>
    <ng-container *ngIf="getField()==='ARCHIVED'">
      <img alt="Dashboard" height="32" src="assets/img/archived.svg" width="32"/>
    </ng-container>
    <ng-container *ngIf="getField()==='PLANNED'">
      <img alt="Dashboard" height="32" src="assets/img/planned.svg" width="32"/>
    </ng-container>
    <ng-container *ngIf="getField()==='SUSPENDED'">
      <img alt="Dashboard" height="32" src="assets/img/suspended.svg" width="32"/>
    </ng-container>
    <ng-container *ngIf="getField()==='PAUSED'">
      <img alt="Dashboard" height="32" src="assets/img/pause.svg" width="32"/>
    </ng-container>
    <p style="visibility: collapse; height: 0; margin: 0">{{getField()}}</p>
  `,
})
export class StatusIcon extends FieldWrapper{


  getField(){
    let t = get(this.model, this.key)
    if (t === "" || t === undefined || t === null) {
      t = "---"
    }
    return t;
  }

}
