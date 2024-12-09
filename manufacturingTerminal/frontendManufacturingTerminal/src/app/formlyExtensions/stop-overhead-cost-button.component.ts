import {Component} from '@angular/core';
import {FieldType} from '@ngx-formly/material/form-field';
import {FormControl} from "@angular/forms";
import {FieldWrapper} from "@ngx-formly/core";
import {get} from "lodash";
import {ServerRequestService} from "../service/server-request.service";

@Component({
  selector: 'formly-stop-overhead-cost-button',
  template: `
    <button class="state_button" color="primary" mat-stroked-button (click)="onClick($event)">
        {{getField()}}
    </button>
  `,
})
export class StopOverheadCostButton extends FieldWrapper {
  // @ts-ignore
  formControl: FormControl;
  constructor(private serverRequestService: ServerRequestService) {
    super();
  }

  getField() {
    let t = get(this.model, this.key)

    if (this.field.props?.label) {
      t = this.field.props?.label;
    }

    return t;
  }

  onClick($event: Event) {
    //console.log(typeof get(this.model, this.key))
    this.serverRequestService.stopOverheadCost(get(this.model, this.key))

  }

  ngAfterViewInit() {
    //console.log("this.field", this.field);
    //console.log("this.model", this.model);
    //console.log("this.form", this.form);
    //console.log("this.key", this.key);
  }
}
