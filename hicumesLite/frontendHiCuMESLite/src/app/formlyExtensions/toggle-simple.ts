import {Component} from '@angular/core';
import {FieldType} from '@ngx-formly/material/form-field';
import {FormControl} from "@angular/forms";
import {FieldWrapper} from "@ngx-formly/core";

@Component({
  selector: 'formly-pdf-viewer',
  template: `
    <mat-slide-toggle [formControl]="formControl"></mat-slide-toggle>
  `,
})
export class ToggleSimple extends FieldWrapper {
  // @ts-ignore
  formControl: FormControl;

  ngAfterViewInit() {
    console.log("this.field", this.field);
    console.log("this.model", this.model);
    console.log("this.form", this.form);
    console.log("this.key", this.key);
  }
}
