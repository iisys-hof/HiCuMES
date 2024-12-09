import {Component} from '@angular/core';
import {FormControl} from "@angular/forms";
import {FieldType} from "@ngx-formly/material" ;

@Component({
  selector: 'formly-datetime-picker',
  template: `
      <input [formControl]="formControl" [formlyAttributes]="field"  [owlDateTimeTrigger]="dt2" [owlDateTime]="dt2" matInput
             placeholder="TT.MM.JJJJ">
      <owl-date-time #dt2 [firstDayOfWeek]="1" backdropClass="mybackdrob"></owl-date-time>
  `,
})
export class FormlyDatetimePicker extends FieldType<any> {
  // @ts-ignore
  formControl: FormControl;
}
