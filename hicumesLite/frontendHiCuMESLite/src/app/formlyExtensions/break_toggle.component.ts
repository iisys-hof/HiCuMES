import {Component} from '@angular/core';
import {FieldType} from '@ngx-formly/material/form-field';
import {FormControl} from "@angular/forms";
import {FieldWrapper} from "@ngx-formly/core";
import {toNumber} from "lodash";

@Component({
  selector: 'formly-break-toggle-input',
  template: `
    <div class="card" style="height: 100%;">
      <div class="card-body" style="white-space: pre-wrap; padding: 5px">
        <div class="duration-toggle">
          <mat-icon matPrefix>coffee</mat-icon>
          <mat-slide-toggle [(ngModel)]="addBreakDuration" (change)="toggleChange()" style="margin-left: 5px">Pausenzeit abziehen
          </mat-slide-toggle>
        </div>
        <div class="duration-input" *ngIf="addBreakDuration">
          <mat-form-field appearance="outline" class="unit-selection">
            <mat-label>Pausenzeit</mat-label>
            <mat-select value="15" [(ngModel)]="breakDuration" (ngModelChange)="inputChange()">
              <mat-option value="15">
                15 Minuten
              </mat-option>
              <mat-option value="30">
                30 Minuten
              </mat-option>
            </mat-select>
          </mat-form-field>
        </div>
      </div>
    </div>
  `,
})
export class BreakToggleComponent extends FieldWrapper {
  // @ts-ignore
  formControl: FormControl;
  addBreakDuration: boolean = false;
  breakDurationSecondes: number = 0;
  breakDuration: String = '15';

  ngAfterViewInit() {
    console.log("this.field", this.field);
    //console.log("this.model", this.model);
    console.log("this.form", this.form);
    //console.log("this.key", this.key);
    this.formControl.setValue(null);
  }

  toggleChange() {
    if(this.addBreakDuration)
    {
      this.formControl.setValue(900)
    }
    else
    {
      this.formControl.setValue(null)
    }
  }

  inputChange() {
    //console.log(this.customDuration, this.customDurationUnit)
    this.breakDurationSecondes = toNumber(this.breakDuration)*60
    //console.log(this.customDurationSecondes)
    this.formControl.setValue(this.breakDurationSecondes)
  }

}
