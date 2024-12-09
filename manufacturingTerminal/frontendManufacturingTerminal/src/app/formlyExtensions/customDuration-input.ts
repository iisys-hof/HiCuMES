import {Component} from '@angular/core';
import {FieldType} from '@ngx-formly/material/form-field';
import {FormControl} from "@angular/forms";
import {FieldWrapper} from "@ngx-formly/core";
import {toNumber} from "lodash";

@Component({
  selector: 'formly-custom-duration-input',
  template: `
    <div class="card" style="margin-top: 3px; margin-bottom: 5px;">
      <div class="card-body" style="white-space: pre-wrap">
        <div class="duration-toggle">
          <mat-icon matPrefix>edit</mat-icon>
          <mat-slide-toggle [(ngModel)]="useCustomTime" (change)="toggleChange()">Mit Zeitverteilung</mat-slide-toggle>
        </div>
        <div class="duration-input" *ngIf="useCustomTime">
        <mat-form-field appearance="outline" class="input">
          <mat-icon matPrefix>edit</mat-icon>
          <mat-label>Meldezeit</mat-label>
          <input id="input0" matInput type="number" min="0" [(ngModel)]="customDuration" (ngModelChange)="inputChange()"/>
        </mat-form-field>
        <mat-form-field appearance="outline" class="unit-selection">
          <mat-label>Zeiteinheit</mat-label>
          <mat-select value="60" [(ngModel)]="customDurationUnit" (ngModelChange)="inputChange()">
            <mat-option value="60">
              Minuten
            </mat-option>
            <mat-option value="3600">
              Stunden
            </mat-option>
          </mat-select>
        </mat-form-field>
        </div>
      </div>
    </div>
  `,
})
export class CustomDurationInput extends FieldWrapper {
  // @ts-ignore
  formControl: FormControl;
  useCustomTime: boolean = false;
  customDuration: any = 0;
  customDurationSecondes: number = 0;
  customDurationUnit: String = '60';

  ngAfterViewInit() {
    //console.log("this.field", this.field);
    //console.log("this.model", this.model);
    //console.log("this.form", this.form);
    //console.log("this.key", this.key);
    this.formControl.setValue(0);
  }

  toggleChange() {
    if(this.useCustomTime)
    {
      this.formControl.setValue(this.customDurationSecondes == null? 0 : this.customDurationSecondes)
    }
    else
    {
      this.formControl.setValue(0)
    }
  }

  inputChange() {
    //console.log(this.customDuration, this.customDurationUnit)
    this.customDurationSecondes = Math.abs(this.customDuration) * toNumber(this.customDurationUnit)
    //console.log(this.customDurationSecondes)
    this.formControl.setValue(this.customDurationSecondes)
  }

}
