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

@Component({
  selector: 'formfield-head-table-action',
  template: `
    <ng-container [formControl]="formControl">
    <button *ngIf="this.isEdit()" (click)="sendFinishWithoutCamunda(this.model)" class="state_button" color="primary" mat-stroked-button [disabled]="this.showLoading">
      <mat-icon>save</mat-icon>
      <mat-progress-bar mode="indeterminate" *ngIf="this.showLoading"></mat-progress-bar>
    </button>
    <button *ngIf="!this.isEdit()" (click)="editForms(this.model)"  class="state_button" color="primary" mat-stroked-button>
      <mat-icon>edit</mat-icon>
    </button>
    </ng-container>
  `,
})
export class FomrfieldHeadTableAction extends FieldWrapper {

  public showLoading = false
  // @ts-ignore
  formControl: FormControl;
  public disableResponseButtonsSet: Set<string> | undefined;

  constructor(private serverRequestService: ServerRequestService, private router: Router, private cd: ChangeDetectorRef, private utilities: UtilitiesService, private selectionStateService: SelectionStateService, private disableResponseButtonStateService: DisableResponseButtonStateService) {
    super();

    this.disableResponseButtonStateService.getDisabledButtons().subscribe(disabledButtons => {
      this.disableResponseButtonsSet = disabledButtons;
      //console.log(this.disableResponseButtonsSet)
    })
  }

  ngAfterViewInit() {
    //console.log("this.field", this);
    //console.log("this.model", this.model);
    //console.log("this.form", this.form);
    //console.log("this.key", this.key);
  }

  toInputMask(machineOccupation: CamundaMachineOccupation) {
    var path = machineOccupation.camundaSubProductionSteps.slice(-1)[0].formKey
    if (this.router.config.filter(e => e.path === path).length > 0) {
      this.router.navigate(['/' + path, {id: machineOccupation.id}]);
    } else {
      this.router.navigate(['/formfields', {id: machineOccupation.id}]);
    }
  }

  startMachineOccupationEvent(machineOccupation: CamundaMachineOccupation) {
    this.serverRequestService.startMachineOccupation(machineOccupation);
  }

  toDetailMask(machineOccupation: CamundaMachineOccupation) {
    //console.log(machineOccupation)
    this.router.navigate(['/order-detail' , {id: machineOccupation.id}]);
  }

  sendFinishWithoutCamunda(model: any) {
    this.showLoading = true
    this.field?.props?.sendFinishWithoutCamunda(model);
  }

  editForms(model: any) {
    this.field?.props?.editForm(this.field);
  }

  isEdit() {
    //If subproductionStep has externalId, it was persisted before
    let isPersisted = this.model.subProductionSteps.slice(-1)[0]?.externalId
    //Set if field was persisted before and should be edited. Otherwise undefined
    let isEdit = this.field?.parent?.props?.editEntries
    if(!isPersisted || (isPersisted && isEdit))
    {
      this.formControl.parent?.setErrors(["edit"])
      //console.log(isPersisted, isEdit, true)
      return true;
    }
    else
    {

      this.formControl.setErrors(null)
      //console.log(isPersisted, isEdit, false)
      return false
    }
  }
}
