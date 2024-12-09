import {Component} from '@angular/core';
import {FieldArrayType} from '@ngx-formly/core';

@Component({
  selector: 'formly-repeat-section',
  template: `
    <div *ngFor="let internalField of field.fieldGroup; let i = index;" class="row">
      <formly-field class="col" [field]="internalField"></formly-field>
      <div  class="col-sm-1 d-flex align-items-center">
        <button *ngIf="field.templateOptions?.disabled==false" mat-stroked-button color="warn" type="button" (click)="remove(i)"><mat-icon>delete_outline</mat-icon></button>

      </div>
    </div>
    <div *ngIf="field.templateOptions?.disabled==false" style="margin:30px 0;">
      <button mat-fab type="button" (click)="add()"><mat-icon>add</mat-icon></button>
    </div>
  `,
})
export class RepeatTypeComponent extends FieldArrayType {}


/**  Copyright 2018 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license */
