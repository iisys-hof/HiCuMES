import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ComponentFactoryResolver,
  ComponentRef,
  Input,
  OnChanges,
  OnInit,
  Renderer2,
  SimpleChanges,
  TemplateRef,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import {Subject} from "rxjs";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {ServerRequestService} from "../../../service/server-request.service";
import {Router} from "@angular/router";
import {UtilitiesService} from "../../../service/utilities.service";
import {SelectionStateService} from "../../../service/selection-state.service";
import {DisableResponseButtonStateService} from "../../../service/disable-response-button-state.service";
import {FormlyFormOptions} from "@ngx-formly/core";
import {FormGroup} from "@angular/forms";
import {UiBuilderService} from "../../../service/ui-builder.service";
import * as moment from "moment";
import * as $ from 'jquery';
import {DataTableDirective} from "angular-datatables";
import {ComponentEventType} from "../../../dataTablesTemplates/machine-occupation-action-buttons/machine-occupation-action-buttons.component";
import {OrderOverviewSubTableComponent} from "../order-overview-sub-table/order-overview-sub-table.component";
import {Machine} from "../../../interfaces/machine";
import { MachineOccupation } from 'src/app/interfaces/machine-occupation';

@Component({
  selector: 'app-order-overview-table-dynamic',
  templateUrl: './order-overview-table-dynamic.component.html',
  styleUrls: ['./order-overview-table-dynamic.component.scss'],
})
export class OrderOverviewTableDynamicComponent implements OnChanges {

  /*tableForm = new FormGroup({});
  model: any;
  options: FormlyFormOptions = {};
  tableLayout: any;*/

  @Input()
  machineOccupationsFilterByMachineType: CamundaMachineOccupation[] | undefined | null;

  @Input()
  machines: Machine[] | undefined | null;

  /*machineOccupationsFilterByReady: CamundaMachineOccupation[] | undefined = [];
  machineOccupationsFilterByRunning: CamundaMachineOccupation[] | undefined = [];
  machineOccupationsFilterByFinished: CamundaMachineOccupation[] | undefined = [];*/

  //cMachineOccupations: CamundaMachineOccupation[] = [];

  constructor() {

    /*this.uiBuilder.getDynamicFormLayout("orderOverviewTable").subscribe((tableLayout) => {
      this.tableLayout = tableLayout;
    });*/
  }

  ngOnChanges(changes: SimpleChanges) {
    this.machineOccupationsFilterByMachineType = this.machineOccupationsFilterByMachineType?.filter((value: CamundaMachineOccupation) => value.machineOccupation.parentMachineOccupation == null)
    /*this.machineOccupationsFilterByReady = this.machineOccupationsFilterByMachineType?.filter((value: CamundaMachineOccupation) => value.machineOccupation.status == 'READY_TO_START');
    this.machineOccupationsFilterByRunning = this.machineOccupationsFilterByMachineType?.filter((value: CamundaMachineOccupation) => value.machineOccupation.status == 'RUNNING');
    this.machineOccupationsFilterByFinished = this.machineOccupationsFilterByMachineType?.filter((value: CamundaMachineOccupation) => value.machineOccupation.status == 'FINISHED');*/
/*
    this.cMachineOccupations = [];
    this.machineOccupationsFilterByMachineType?.forEach(occupation => {
      if (occupation.camundaSubProductionSteps && Array.isArray(occupation.camundaSubProductionSteps) && occupation.camundaSubProductionSteps.length) {
        //Remove entries of previous productionsteps from disableResponseButtonStateService
        occupation.camundaSubProductionSteps?.slice(0, -1).forEach(
          (subProductionStep) => {
            this.disableResponseButtonStateService.enableButton(subProductionStep.taskId);
          }
        )
        this.cMachineOccupations = [...this.cMachineOccupations, {
          ...occupation,
          camundaSubProductionSteps: occupation.camundaSubProductionSteps,
          currentSubProductionStep: occupation.camundaSubProductionSteps?.slice(-1)[0],
          lastSubProductionStep: occupation.camundaSubProductionSteps?.slice(-2)[0]
        }];
      } else {
        this.cMachineOccupations = [...this.cMachineOccupations, occupation];
      }
    });
    this.model = {camundaMachineOccupations: this.cMachineOccupations};
*/
  }

}



