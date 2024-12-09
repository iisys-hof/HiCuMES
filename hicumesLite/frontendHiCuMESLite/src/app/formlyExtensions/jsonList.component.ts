import {AfterViewInit, Component} from '@angular/core';
import {FieldType} from '@ngx-formly/material/form-field';
import {FormControl} from "@angular/forms";
import {FieldWrapper} from "@ngx-formly/core";
import { get, set } from "lodash";
import * as moment from 'moment';
@Component({
  selector: 'formly-json-list',
  template: `
    <div *ngFor="let item of jsonToObject(getField()) | keyvalue" style="display:block;">
        <span style="display: inline-block; width: 130px;"><b>{{item.key}}</b>:</span><span> {{item.value}} </span>
    </div>
  `,
})
export class FormlyJsonList extends FieldWrapper{


  getField(){
    let t = get(this.model, this.key)
    return t;
  }

  jsonToObject(value: any) {
    if(value != undefined && value != null)
    {
      var returnValue = value
      returnValue = JSON.parse(returnValue);
      return returnValue;
    }
    else
    {
      return null
    }
  }

}
