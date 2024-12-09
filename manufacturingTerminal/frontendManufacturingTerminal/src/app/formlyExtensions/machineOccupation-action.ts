import {ChangeDetectorRef, Component, EventEmitter, Output} from '@angular/core';
import {FieldType} from '@ngx-formly/material/form-field';
import {FormControl} from "@angular/forms";
import {FieldWrapper} from "@ngx-formly/core";
import {UiBuilderService} from "../service/ui-builder.service";
import {ServerRequestService} from "../service/server-request.service";
import {Router} from "@angular/router";
import {UtilitiesService} from "../service/utilities.service";
import {SelectionStateService} from "../service/selection-state.service";
import {DisableResponseButtonStateService} from "../service/disable-response-button-state.service";
import {CamundaMachineOccupation} from "../interfaces/camunda-machine-occupation";
import {MatTableDataSource} from "@angular/material/table";
import {CamundaSubProductionStep} from "../interfaces/camunda-sub-production-step";
import {MatDialogRef} from "@angular/material/dialog";
import { ListOpenModalComponent } from '../component/modals/list-open-modal/list-open-modal.component';

@Component({
  selector: 'machineoccupationaction',
  template: `
    <!--button routerLink="order-details" style="margin-left: 10px" (click)="toDetailMask(this.model)" class="action-icon-btn" color="accent"
            mat-stroked-button>
      <mat-icon>data_info_alert</mat-icon>
      DETAILS
    </button-->

    <button (click)="toInputMask(this.model)" *ngIf="this.model?.machineOccupation?.status==='RUNNING' || this.model?.machineOccupation?.status==='PAUSED'" class="action-icon-btn" color="accent" mat-stroked-button>

      <!--<mat-icon>open_in_new</mat-icon>-->

      <mat-icon *ngIf="this.model?.camundaSubProductionSteps?.slice(-1)[0]?.formKey" class="task-icon" [style.color]="getColorForTaskFormKey(this.model?.camundaSubProductionSteps?.slice(-1)[0]?.formKey)">{{getIconForTaskFormKey(this.model?.camundaSubProductionSteps?.slice(-1)[0]?.formKey)}}</mat-icon>
      <mat-icon *ngIf="!this.model?.camundaSubProductionSteps?.slice(-1)[0]?.formKey" class="task-icon">open_in_new</mat-icon>
      <span>Zur Maske</span>

      <!--<span *ngIf="!this.data?.camundaSubProductionSteps?.slice(-1)[0]?.name">RÜCKMELDUNG</span>
      <span>{{this.data?.camundaSubProductionSteps?.slice(-1)[0]?.name}}</span>-->

      <mat-progress-bar mode="indeterminate" *ngIf="disableResponseButtonsSet?.has(this.model?.currentSubProductionStep?.taskId)"></mat-progress-bar>
    </button>

    <!--button *ngIf="this.model?.machineOccupation?.status==='READY_TO_START'" (click)="startMachineOccupationEvent(this.model)" class="state_button" color="primary" mat-stroked-button>
      START
    </button>
    <button *ngIf="this.model?.machineOccupation?.status==='RUNNING'" class="state_button" color="primary" disabled mat-stroked-button>
      LÄUFT
    </button>
    <button *ngIf="this.model?.machineOccupation?.status==='FINISHED'" class="state_button" color="primary" disabled mat-stroked-button>
      BEENDET
    </button>

    <button style="margin-left: 10px" (click)="toInputMask(this.model)" *ngIf="this.model?.machineOccupation?.status==='RUNNING'" class="action-icon-btn" color="accent" [disabled]="disableResponseButtonsSet?.has(this.model?.currentSubProductionStep?.taskId)"
            mat-stroked-button>
      <mat-icon>open_in_new</mat-icon>
      RÜCKMELDUNG
    </button-->
  `,
  styles: [`
    .action-btns {
      display: flex;
      flex-direction: row;
      align-content: center;
      justify-content: center;
    }

    .start_button{
      margin-left: 0px;
    }

    .pause_button{
      margin-left: 10px;
    }

    .action-icon-btn{
      margin-left: 0px;
    }

    .task-icon{

      margin-right: 5px;
    }

  `]
})
export class MachineOccupationAction extends FieldWrapper {
  // @ts-ignore
  formControl: FormControl;
  public disableResponseButtonsSet: Set<string> | undefined;

  constructor(private dialogRef: MatDialogRef<ListOpenModalComponent>, private serverRequestService: ServerRequestService, private router: Router, private cd: ChangeDetectorRef, private utilities: UtilitiesService, private selectionStateService: SelectionStateService, private disableResponseButtonStateService: DisableResponseButtonStateService) {
    super();

    this.disableResponseButtonStateService.getDisabledButtons().subscribe(disabledButtons => {
      this.disableResponseButtonsSet = disabledButtons;
      console.log(this.disableResponseButtonsSet)
    })
  }

  ngAfterViewInit() {
    console.log("this.field", this.field);
    console.log("this.model", this.model);
    console.log("this.form", this.form);
    console.log("this.key", this.key);
  }

  toInputMask(machineOccupation: CamundaMachineOccupation) {
    var path = machineOccupation.camundaSubProductionSteps.slice(-1)[0]?.formKey
    if (this.router.config.filter(e => e.path === path).length > 0) {
      //If you are in a formfield mask already, the default navigation would not reload the page. This fixes it
      this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>this.router.navigate(['/' + path, {id: machineOccupation.id}]));
      this.dialogRef.close(undefined)
    } else {
      //If you are in a formfield mask already, the default navigation would not reload the page. This fixes it
      this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>this.router.navigate(['/formfields', {id: machineOccupation.id}]));

      this.dialogRef.close(undefined)
    }
  }

  getIconForTaskFormKey(formKey: string):string{

    var subProdStepJSON = this.utilities.getSubProdcuctionStepSettings(formKey);

    return subProdStepJSON.icon;
  }

  getColorForTaskFormKey(formKey: string):string{

    var subProdStepJSON = this.utilities.getSubProdcuctionStepSettings(formKey);

    return subProdStepJSON.color;
  }
  startMachineOccupationEvent(machineOccupation: CamundaMachineOccupation) {
    this.serverRequestService.startMachineOccupation(machineOccupation);
  }

  toDetailMask(machineOccupation: CamundaMachineOccupation) {
    console.log(machineOccupation)
    this.router.navigate(['/order-detail' , {id: machineOccupation.id}]);
  }
}
