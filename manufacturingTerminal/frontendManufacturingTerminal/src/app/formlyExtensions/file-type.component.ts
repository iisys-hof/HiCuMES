import {Component} from '@angular/core';
import {FieldType} from '@ngx-formly/material';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'formly-field-file',
  template: `
    <input type="file" [formControl]="formControl" [formlyAttributes]="field">
  `,
})
export class FormlyFieldFile extends FieldType<any> {
  // @ts-ignore
  formControl: FormControl;
}
