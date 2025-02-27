import {AfterViewInit, Component, OnInit} from '@angular/core';
import { FieldType } from '@ngx-formly/material';
import {get} from "lodash";
import * as moment from "moment";
import {DecimalPipe} from "@angular/common";
import {UnitTranslationPipe} from "../pipes/unit-translation.pipe";

@Component({
  selector: 'formly-switch',
  template: `
    <ng-container *ngFor="let f of field.fieldGroup">
      <ng-container *ngIf="f.type" [ngSwitch]="f.type">
        <formly-field *ngSwitchCase="'input'" [field]="f"></formly-field>
        <formly-field *ngSwitchCase="'simple'" [field]="f"></formly-field>
      </ng-container>
    </ng-container>
  `,
})
export class FormlySwitch extends FieldType<any>{

}
