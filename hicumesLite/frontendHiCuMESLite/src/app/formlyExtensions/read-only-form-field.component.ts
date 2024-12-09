import { Component, OnInit } from '@angular/core';
import {FieldWrapper} from "@ngx-formly/core";
import {get} from "lodash";
import * as moment from "moment";
import {DecimalPipe} from "@angular/common";

@Component({
  selector: 'formly-readOnly-formfield',
  template: `
    <div style="display: flex; align-content: center; align-items: center; padding: 5px; width:100%">{{getField()}}</div>

  `,
})
export class ReadOnlyFormFieldComponent extends FieldWrapper{

  constructor(private decimalPipe: DecimalPipe) {
    super()
  }

  getField(){
    let t = get(this.model, this.key)

    if (t === "" || t === undefined || t === null) {
      t = "Nicht vorhanden"
    }
    else if(this.field.props?.type === "date")
    {
      const date = moment(t, 'YYYY-MM-DD HH:mm')
      //console.log("DATE", date);
      return date.format('DD.MM.YYYY HH:mm');
    }
    else if (typeof t === 'number')
    {
      return this.decimalPipe.transform(t);
    }
    return t;
  }

}
