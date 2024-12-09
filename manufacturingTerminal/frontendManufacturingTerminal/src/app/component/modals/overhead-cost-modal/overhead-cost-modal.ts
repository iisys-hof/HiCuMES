import {Component, Inject, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from "@angular/material/dialog";
import {UiBuilderService} from "../../../service/ui-builder.service";
import {ServerRequestService} from "../../../service/server-request.service";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {NgxSpinnerService} from "ngx-spinner";
import * as _ from "lodash";
import {ComponentType} from "@angular/cdk/overlay";
import {OverheadCostNoteModal} from "../overhead-cost-note-modal/overhead-cost-note-modal";

@Component({
  selector: 'app-overhead-cost-modal',
  templateUrl: './overhead-cost-modal.html',
  styleUrls: ['./overhead-cost-modal.scss']
})
export class OverheadCostModal implements OnInit, OnChanges{

  overheadCostCenters: any = undefined
  selected: any = undefined;

  tableLayout: any;
  tableLayoutHistory: any;
  tableLayoutNew: any;
  options: FormlyFormOptions = {};
  tableForm = new FormGroup({});
  tableFormHistory = new FormGroup({});
  overheadCosts: any
  overheadCostsHistory: any
  overheadCostCentersModel: any

  constructor(private dialogRef: MatDialogRef<OverheadCostModal>, @Inject(MAT_DIALOG_DATA) public data: any, private serverRequestService: ServerRequestService, private uiBuilder: UiBuilderService, private spinner: NgxSpinnerService,) {
    this.serverRequestService.loadOverheadCostCenters()
    this.serverRequestService.loadOverheadCosts()

    this.uiBuilder.getDynamicFormLayout("overheadCosts").subscribe((tableLayout) => {
      this.tableLayout = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("overheadCostsHistory").subscribe((tableLayout) => {
      this.tableLayoutHistory = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("overheadCostsNew").subscribe((tableLayout) => {
      this.tableLayoutNew = tableLayout;
    });
    this.spinner.show();
  }


  ngOnInit() {
    this.serverRequestService.getAllOverheadCostCenters().subscribe(costCenters =>
    {
      console.log(costCenters)
      this.overheadCostCenters = [{}]
      costCenters.map(value => {
        this.overheadCostCenters.push({value: value.externalId, viewValue: value.externalId + " - " + value.name})
      })
      this.overheadCostCentersModel = {overheadCostCenters: costCenters}
    })
    this.serverRequestService.getOverheadCosts().subscribe(costs =>
    {
      this.overheadCosts = {overheadCosts: _.cloneDeep(costs).filter(value => value?.timeRecord?.endDateTime == undefined)}
      this.overheadCostsHistory = {overheadCosts: _.cloneDeep(costs).filter(value => value?.timeRecord?.endDateTime != undefined)}
      console.log(this.overheadCosts)
      console.log(this.overheadCostsHistory)
    })
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  onSubmit()
  {
      console.log(this.selected)
      this.serverRequestService.startOverheadCost(this.selected)
  }

  onClose() {
    this.dialogRef.close(undefined)
  }

  onEnterCostCenter() {

  }


  protected readonly undefined = undefined;
}
