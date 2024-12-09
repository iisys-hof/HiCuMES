import {AfterViewInit, Component} from '@angular/core';
import {FieldType} from '@ngx-formly/material/form-field';
import {FormControl} from "@angular/forms";
import {FieldWrapper} from "@ngx-formly/core";
import { get, set } from "lodash";
import {UtilitiesService} from "../service/utilities.service";
@Component({
  selector: 'subproductionstep-formkey',
  template: `
    <mat-icon>{{getSubprodStepSettings(this.model?.formKey)?.icon}}</mat-icon>
    <!--<span>{{getSubprodStepSettings(this.model?.formKey).name}}</span>-->
  `,
  styles: [`
  mat-icon {
    vertical-align: middle;
    font-size: 24px;
  }

  span {
    vertical-align: middle;
    margin-left: 10px;
    font-size: 14px;
  }
  `]
})
export class SubproductionstepFormkey extends FieldWrapper{

    constructor(private utilities: UtilitiesService) {
      super();
    }

  getField(){
    let t = get(this.model, this.key)
    if (t === "" || t === undefined || t === null) {
      t = "---"
    }
    return t;
  }

  getSubprodStepSettings(formKey: string) {
    // @ts-ignore
    return this.utilities.getSubProdcuctionStepSettings(formKey);
  }
}
