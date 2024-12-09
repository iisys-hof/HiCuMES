import {AfterViewInit, Component} from '@angular/core';
import {FieldType} from '@ngx-formly/material/form-field';
import {FormControl, FormGroup} from "@angular/forms";
import {FieldWrapper} from "@ngx-formly/core";
import { get, set } from "lodash";
import * as moment from 'moment';
import * as _ from "lodash";
@Component({
  selector: 'formly-json-list',
  template: `
    <div class="eingaben" >
      <ng-container *ngFor="let field of this.model.formField" [ngSwitch]="checkField(field)">
            <span [class.col-span-2]="checkField(field) === 'hicumes_ToolSettings'" class="title"  ><b>{{field.label}}:</b></span>

            <span class="value" *ngSwitchCase="'date'">{{this.parseDate(json[field.key])}}</span>
            <span class="value"*ngSwitchCase="'boolean'">{{getBoolean(json[field.key])}}</span>
            <span class="value"*ngSwitchCase="'pdf'">{{getValue(json[field.key])}}</span>
            <span class="value" style="grid-column: 1 / span 2;" *ngSwitchCase="'hicumes_ToolSettings'">
              <table style="margin: 3px" *ngIf="initToolSettings(json[field.key]) && toolSettingsGroupedByToolSettingParams">
              <tr>
                <ng-container *ngFor="let headerCell of toolSettingsGroupedByToolSettingParams[0]">
                  <th class="mat-header-cell" style="border: 1px solid rgba(0, 0, 0, 0.12) !important; text-align: center">{{headerCell.toolSettingParameter.name}}</th>
                </ng-container>
              </tr>
              <ng-container *ngFor="let row of toolSettingsGroupedByToolSettingParams; index as rowIndex">
                <tr >
                  <ng-container *ngFor="let cell of row; index as columnIndex">
                    <td class="mat-cell" style="border: 1px solid rgba(0, 0, 0, 0.12); text-align: center;">
                      {{cell?.settingsValue}}
                    </td>
                  </ng-container>
                </tr>
              </ng-container>

            </table>
            </span>
            <span class="value" *ngSwitchDefault>{{getValue(json[field.key])}}</span>
      </ng-container>
    </div>
  `,
})
export class FormlyCamundaFormfieldOverview extends FieldWrapper implements AfterViewInit{

  json = [];
  toolSettingsGroupedByToolSettingParams: any = undefined;

  ngAfterViewInit() {
    console.log("this.field", this.field);
    console.log("this.model", this.model);
    console.log("this.form", this.form);
    console.log("this.key", this.key);

    this.jsonToObject(this.model.filledFormField)
  }

  getField(){
    let t = get(this.model, this.key)
    return t;
  }

  parseDate(value: string)
  {
    const date = moment(value)
    return date.format('DD.MM.YYYY HH:mm');
  }

  jsonToObject(value: any) {
    if(value != undefined && value != null)
    {
      var returnValue = value
      returnValue = JSON.parse(returnValue);
      this.json = returnValue;
      console.log(returnValue)
      return returnValue;
    }
    else
    {
      return null
    }
  }

  getValue(value: any)
  {
    if(value == null || value == undefined)
    {
      return "--"
    }
    else
    {
      return value
    }
  }

  checkField(field: any) {
    let properties;
    if (field.properties) {
      properties = JSON.parse(field.properties)
    }
    if(typeof properties === "object" && properties["type"] !== null)
    {
      return properties['type']
    }
    if(field.type == 'date')
    {
      return 'date'
    }
    if(field.type == 'boolean')
    {
      return 'boolean'
    }
    return null
  }

  initToolSettings(toolSettings: any) {
    let t = null;
    try
    {
      t = JSON.parse(toolSettings)
    }
    catch (e)
    {
      t = toolSettings
    }
    this.toolSettingsGroupedByToolSettingParams = _(t).groupBy('toolSettingParameter.externalId').toArray().unzip().value()
    return true
  }

  getBoolean(bool: any) {
    return bool? "Ja": "Nein"
  }
}
