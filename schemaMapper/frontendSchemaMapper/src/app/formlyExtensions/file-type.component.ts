import {Component} from '@angular/core';
import {FieldType} from '@ngx-formly/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'formly-field-file',
  template: `
    <input type="file" [formControl]="formControl" [formlyAttributes]="field">
  `,
})
export class FormlyFieldFile extends FieldType {
  // @ts-ignore
  formControl: FormControl;
}
