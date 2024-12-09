import { Component } from '@angular/core';
import { FieldArrayType } from '@ngx-formly/core';

@Component({
  selector: 'formly-key-value-type',
  template: `
    <div>
      <div *ngFor="let field of field.fieldGroup; let i = index;" class="row">
        <formly-field class="col-sm-10" [field]="field"></formly-field>
        <div class="flex-1 col-sm-2 d-flex align-items-center" style="padding:0px;">
          <button class="btn" style="color: #01579b" type="button" (click)="remove(i)"><mat-icon>delete_outline</mat-icon></button>
        </div>
      </div>
      <div>
        <button class="btn" style="background-color: #01579b; border-color: #01579b; color: white; margin-left: 15px;" type="button" (click)="add()">{{ to.label }}</button>
      </div>
    </div>
  `
})
export class KeyValueTypeComponent extends FieldArrayType {}
