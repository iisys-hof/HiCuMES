import {Injectable} from '@angular/core';
import * as _ from 'lodash';
import {CamundaMachineOccupation} from "../interfaces/camunda-machine-occupation";

@Injectable({
  providedIn: 'root'
})
export class UtilitiesService {
  private subProductionStepSettings =
    {
      "CheckQualityTask": {"name": "Qualität prüfen", "color": "#ecb341", "icon": "rule"},
      "SetParameterTask": {"name": "Parameter einstellen", "color": "#e3e34f", "icon": "tune"},
      "SelectOrderTask": {"name": "Auftrag auswählen", "color": "#5e97e3", "icon": "list"},
      "ConfirmProductionTask": {
        "name": "Produktion rückmelden",
        "color": "#40a040",
        "icon": "assignment_return"
      },
      "ReworkTask": {"name": "Nacharbeit", "color": "#d05656", "icon": "bug_report"},
      "AddSuppliesTask": {"name": "Hilfsstoffe auffüllen", "color": "#886bc1", "icon": "add_shopping_cart"},
      "UnsuportedTask": {"name": "Sonstige Aufgabe", "color": "#bf7dba", "icon": "add_shopping_cart"},
      "order-overview": {"name": "Auftragsübersicht", "color": "#d23ec6", "icon": "rule"},
      "user-order-overview": {"name": "Meine Auftragsübersicht", "color": "#d23ec6", "icon": "rule"},
    }

  deepReplaceRecursive(search: string, replace: string, obj: any): any {

    _.each(obj, (val: any, key: any) => {
      if (val === search) {
        // replace val if it's equal to the old value
        obj[key] = replace;
      } else if (typeof (val) === 'object') {
        // if val has nested values, make recursive call
        this.deepReplaceRecursive(search, replace, val);
      }
    });

    return obj;
  }

  deepReplaceRecursiveWithKey(search: string, replace: string, obj: any, keySelector: string): any {

    _.each(obj, (val: any, key: any) => {
      if (val === search) {
        // replace val if it's equal to the old value
        if (keySelector == key) {
          obj[key] = replace;

        }
      } else if (typeof (val) === 'object') {
        // if val has nested values, make recursive call
        this.deepReplaceRecursiveWithKey(search, replace, val, keySelector);
      }
    });

    return obj;
  }

  getSubProdcuctionStepSettings(formKey: string) {

    // @ts-ignore
    return this.subProductionStepSettings[formKey];

  }

  restructureCamundaMachineOccupation(camundaMachineOccupation: CamundaMachineOccupation | null | undefined)
  {
    var toolSettingsGroupedByToolSettingParams = _(camundaMachineOccupation?.machineOccupation.activeToolSettings).groupBy('toolSettingParameter.externalId').toArray().unzip().value()

    var restructured =
      {
        "machineOccupation": camundaMachineOccupation?.machineOccupation,
        "productionOrder": camundaMachineOccupation?.machineOccupation.productionOrder,
        "customerOrder": camundaMachineOccupation?.machineOccupation.productionOrder?.customerOrder,
        "product": camundaMachineOccupation?.machineOccupation.productionOrder?.product,
        "tool": camundaMachineOccupation?.machineOccupation.tool,
        "toolSettings": camundaMachineOccupation?.machineOccupation.activeToolSettings,
        "toolSettingsGroupedByParameters": toolSettingsGroupedByToolSettingParams,
        "machine": camundaMachineOccupation?.machineOccupation.machine,
        "productionSteps": camundaMachineOccupation?.machineOccupation.productionSteps,
      };
    return restructured;
  }

}
