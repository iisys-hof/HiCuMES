
import {Component, ViewChild, TemplateRef, OnInit, ElementRef, AfterViewInit, OnChanges} from '@angular/core';
import { FormlyFieldConfig, FieldArrayType } from '@ngx-formly/core';
import { TableColumn } from '@swimlane/ngx-datatable/lib/types/table-column.type'
import * as _ from "lodash";
import {CamundaMachineOccupation} from "../interfaces/camunda-machine-occupation";
import {ToolSetting} from "../interfaces/tool";
import {get} from "lodash";

const testData: any [] = [
  {
    "id": 549,
    "externalId": "352738_cbn_wzb_130_ytform",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 66,
      "externalId": "cbn_wzb_130_ytform",
      "unitType": null,
      "name": "Form"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": ""
  },
  {
    "id": 550,
    "externalId": "352738_cbn_wzb_130_ytdupress",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 67,
      "externalId": "cbn_wzb_130_ytdupress",
      "unitType": null,
      "name": "Durchm./Länge"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "8,63"
  },
  {
    "id": 551,
    "externalId": "352738_cbn_wzb_130_ytbrpress",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 68,
      "externalId": "cbn_wzb_130_ytbrpress",
      "unitType": null,
      "name": "Breite"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "20,20"
  },
  {
    "id": 552,
    "externalId": "352738_cbn_wzb_130_ytbopress",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 69,
      "externalId": "cbn_wzb_130_ytbopress",
      "unitType": null,
      "name": "Bohrung/Höhe"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "5,65"
  },
  {
    "id": 553,
    "externalId": "352738_cbn_wzb_130_ytsltpress",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 70,
      "externalId": "cbn_wzb_130_ytsltpress",
      "unitType": null,
      "name": "Sackloch Tiefe"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "0,00"
  },
  {
    "id": 554,
    "externalId": "352738_cbn_wzb_130_ytaupress",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 71,
      "externalId": "cbn_wzb_130_ytaupress",
      "unitType": null,
      "name": "Info Pressen"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": ""
  },
  {
    "id": 555,
    "externalId": "352738_cbn_wzb_130_ytausdpress1",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 72,
      "externalId": "cbn_wzb_130_ytausdpress1",
      "unitType": null,
      "name": "Aussparung 1"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "0,00"
  },
  {
    "id": 556,
    "externalId": "352738_cbn_wzb_130_ytausdpress2",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 73,
      "externalId": "cbn_wzb_130_ytausdpress2",
      "unitType": null,
      "name": "Aussparung 2"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "0,00"
  },
  {
    "id": 557,
    "externalId": "352738_cbn_wzb_130_ytpressart",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 74,
      "externalId": "cbn_wzb_130_ytpressart",
      "unitType": null,
      "name": "Pressart"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "GLVER"
  },
  {
    "id": 558,
    "externalId": "352738_cbn_wzb_130_yteprv",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 75,
      "externalId": "cbn_wzb_130_yteprv",
      "unitType": null,
      "name": "Pressvolumen"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "0,6758"
  },
  {
    "id": 559,
    "externalId": "352738_cbn_wzb_130_yteprg",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 76,
      "externalId": "cbn_wzb_130_yteprg",
      "unitType": null,
      "name": "Pressgewicht"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "1,5273"
  },
  {
    "id": 549,
    "externalId": "352738_cbn_wzb_130_ytform",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 66,
      "externalId": "cbn_wzb_130_ytform",
      "unitType": null,
      "name": "Form"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": ""
  },
  {
    "id": 550,
    "externalId": "352738_cbn_wzb_130_ytdupress",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 67,
      "externalId": "cbn_wzb_130_ytdupress",
      "unitType": null,
      "name": "Durchm./Länge"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "8,63"
  },
  {
    "id": 551,
    "externalId": "352738_cbn_wzb_130_ytbrpress",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 68,
      "externalId": "cbn_wzb_130_ytbrpress",
      "unitType": null,
      "name": "Breite"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "20,20"
  },
  {
    "id": 552,
    "externalId": "352738_cbn_wzb_130_ytbopress",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 69,
      "externalId": "cbn_wzb_130_ytbopress",
      "unitType": null,
      "name": "Bohrung/Höhe"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "5,65"
  },
  {
    "id": 553,
    "externalId": "352738_cbn_wzb_130_ytsltpress",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 70,
      "externalId": "cbn_wzb_130_ytsltpress",
      "unitType": null,
      "name": "Sackloch Tiefe"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "0,00"
  },
  {
    "id": 554,
    "externalId": "352738_cbn_wzb_130_ytaupress",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 71,
      "externalId": "cbn_wzb_130_ytaupress",
      "unitType": null,
      "name": "Info Pressen"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": ""
  },
  {
    "id": 555,
    "externalId": "352738_cbn_wzb_130_ytausdpress1",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 72,
      "externalId": "cbn_wzb_130_ytausdpress1",
      "unitType": null,
      "name": "Aussparung 1"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "0,00"
  },
  {
    "id": 556,
    "externalId": "352738_cbn_wzb_130_ytausdpress2",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 73,
      "externalId": "cbn_wzb_130_ytausdpress2",
      "unitType": null,
      "name": "Aussparung 2"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "0,00"
  },
  {
    "id": 557,
    "externalId": "352738_cbn_wzb_130_ytpressart",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 74,
      "externalId": "cbn_wzb_130_ytpressart",
      "unitType": null,
      "name": "Pressart"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "GLVER"
  },
  {
    "id": 558,
    "externalId": "352738_cbn_wzb_130_yteprv",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 75,
      "externalId": "cbn_wzb_130_yteprv",
      "unitType": null,
      "name": "Pressvolumen"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "0,6758"
  },
  {
    "id": 559,
    "externalId": "352738_cbn_wzb_130_yteprg",
    "tool": null,
    "machine": null,
    "toolSettingParameter": {
      "id": 76,
      "externalId": "cbn_wzb_130_yteprg",
      "unitType": null,
      "name": "Pressgewicht"
    },
    "measurement": {
      "value": 0,
      "unitString": "stk"
    },
    "settingsValue": "1,5273"
  }
]

@Component({
  selector: 'formly-toolsettingTable',
  template: `
    <table style="width: 100%" *ngIf="toolSettingsGroupedByToolSettingParams">

      <tr>
        <ng-container *ngFor="let headerCell of toolSettingsGroupedByToolSettingParams[0]">
          <th class="mat-header-cell" style="border: 1px solid rgba(0, 0, 0, 0.12) !important; text-align: center">{{headerCell.toolSettingParameter.name}}</th>
        </ng-container>
      </tr>

      <ng-container *ngFor="let row of toolSettingsGroupedByToolSettingParams; index as rowIndex">
        <tr >
          <ng-container *ngFor="let cell of row; index as columnIndex">
            <td class="mat-cell" style="border: 1px solid rgba(0, 0, 0, 0.12); text-align: center">
              {{cell?.settingsValue}}

            </td>
          </ng-container>
        </tr>
      </ng-container>

    </table>
  `,
})
export class ToolSettingTable  extends FieldArrayType implements AfterViewInit {
  @ViewChild('defaultColumn') public defaultColumn?: TemplateRef<any>;

  data: {} | undefined
  toolSettingsGroupedByToolSettingParams: any[] | undefined

  constructor() {
    super();

    //console.log(this.data)
  }

  ngAfterViewInit() {
      //console.log("this.field", this.field);
      //console.log("this.model", this.model);
      //console.log("this.form", this.form);
      //console.log("this.key", this.key);
      let t = null;
      try
      {
        t = JSON.parse(this.model)
      }
      catch (e)
      {
        t = this.model
      }
      this.toolSettingsGroupedByToolSettingParams = _(t).groupBy('toolSettingParameter.externalId').toArray().unzip().value()
    //console.log(this.toolSettingsGroupedByToolSettingParams)
  }
}
