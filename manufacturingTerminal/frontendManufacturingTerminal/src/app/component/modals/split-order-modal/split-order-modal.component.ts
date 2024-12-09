import {Component, Inject, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {UiBuilderService} from "../../../service/ui-builder.service";
import {FormlyFormOptions} from "@ngx-formly/core";
import {ServerRequestService} from "../../../service/server-request.service";
import {SelectionStateService} from "../../../service/selection-state.service";
import * as _ from "lodash";

@Component({
  selector: 'app-split-order-modal',
  templateUrl: './split-order-modal.component.html',
  styleUrls: ['./split-order-modal.component.scss']
})
export class SplitOrderModalComponent implements OnInit, OnChanges{

  firstStepperFormGroup: FormGroup;
  secondStepperFormGroup: FormGroup;

  parentMachineOccupation: any
  model: any;
  filteredModel: any;
  selectedModel: any;
  options: FormlyFormOptions = {};
  tableLayout: any;
  detailLayout: any
  split: any;
  splitNumber: number = 0;
  splits: number[] = [];

  tableForm = new FormGroup({selectOrder: new FormControl()});
  detailForm = new FormGroup({selectOrder: new FormControl()});
  amountLeft = 0

  _alldata: any[] = [];
  columns: any[];
  displayedColumns: string[];
  groupByColumns: string[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private uiBuilder: UiBuilderService, private formBuilder: FormBuilder, private serverRequestService: ServerRequestService, private selectionStateService: SelectionStateService) {


    this.model =  {camundaMachineOccupations: data.machineOccupations};
    this.parentMachineOccupation = data.parentMachineOccupation
    this.model.camundaMachineOccupations.forEach((value: any) =>
    {
      let toolSettingsGroupedByToolSettingParams = _(value.machineOccupation.activeToolSettings).groupBy('toolSettingParameter.externalId').value()
      //console.log(toolSettingsGroupedByToolSettingParams)
      value.machineOccupation["toolSettingsGrouped"] = toolSettingsGroupedByToolSettingParams;
      return value
    })
    //console.log(this.model)
    /*this.uiBuilder.getDynamicFormLayout("collectionOrderTable").subscribe((tableLayout) => {
      this.tableLayout = tableLayout;
      this.detailLayout = tableLayout
    });*/

    this.selectionStateService.getSelectedMachineTypes().subscribe(value => {
      //console.log(value)
      /*this.uiBuilder.getDynamicFormLayout(value?.externalId + "-splitOrderTable").subscribe((formLayout) => {
        if (formLayout == undefined) {*/
          this.uiBuilder.getDynamicFormLayout("globalSplitOrderTable").subscribe((formLayout) => {
            this.tableLayout = formLayout;
          });
        /*}
        this.tableLayout = formLayout;
      });*/
    })


    this.firstStepperFormGroup = this.formBuilder.group({
      camundaMachineOccupations: this.getSelected(),
    });
    this.secondStepperFormGroup = this.formBuilder.group({
      orderSplits: this.formBuilder.array([
        this.formBuilder.group({
          split: ''
        }),
        this.formBuilder.group({
          split: ''
        })
      ])
    });



    this.filteredModel = _.cloneDeep(this.model)
    //console.log(this.filteredModel)
    this.secondStepperFormGroup.get("orderSplits")?.valueChanges.subscribe((value: any) => {
      let amount = 0;
      this.orderSplits().controls.forEach(control => {
        amount += control.value['split']
      })
      this.amountLeft = this.getSelected().machineOccupation.productionOrder.measurement.amount - amount
      //lastformControl.patchValue({amount: this.getSelected().machineOccupation.productionOrder.unit.amount - amount})
    })

    this.columns = [{
      field: 'id'
    }, {
      field: 'externalId'
    }, {
      field: 'machineOccupation.name'
    }, {
      field: 'businessKey'
    }, {
      field: 'color'
    }];
    this.displayedColumns = this.columns.map(column => column.field);
    this.groupByColumns = ['brand'];

  }


  ngOnInit() {
    this._alldata = this.data;
    //console.log(this._alldata)

  }

  orderSplits(): FormArray {
    //console.log(this.secondStepperFormGroup)
    return this.secondStepperFormGroup.get("orderSplits") as FormArray;
  }

  newSplit(): FormGroup {
    return this.formBuilder.group({
      split: ''
    })
  }

  addSplit() {
    this.orderSplits().push(this.newSplit())
  }

  removeSplit(index: number){
    this.orderSplits().removeAt(index)
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes)
  }

  getSelected() {
    let selected = this.filteredModel?.camundaMachineOccupations?.filter((f: any) => f?.selected === true)
    selected?.map((value: any) => {
      //console.log(value)
      let index = this.model.camundaMachineOccupations.findIndex((x: any) => x.id == value.id)
      this.model.camundaMachineOccupations[index] = value;
    })
    //console.log(selected)
    //console.log(this.filteredModel?.camundaMachineOccupations?.filter((f: any) => f?.selected === true))
    return this.filteredModel?.camundaMachineOccupations?.filter((f: any) => f?.selected === true)[0]
  }

  submit() {
    let splits: number[] = []
    this.secondStepperFormGroup.value.orderSplits.forEach((value : any) => splits.push(value.split))
    //console.log(splits)
    this.serverRequestService.createSplitOrder(this.getSelected(), splits);
  }

  nextStep(data?: any) {
    //console.log(data)
    return true;
  }

  getField(row: any, field: any) {
    let obj = row;
    for (var i=0, path=field.split('.'), len=path.length; i<len; i++){
      obj = obj[path[i]];
    };
    return obj
  }

  getModelClone(selected: any) {

    return _.cloneDeep({'camundaMachineOccupations': [selected]})
  }

  toggleWithLess(selected: any) {
    if(selected['toggleWithLess'] == null)
    {
      selected['toggleWithLess'] = true;
    }
    else {
      selected['toggleWithLess'] = !selected['toggleWithLess']
    }
    //console.log(selected)
  }

  allPlanned() {
    return this.amountLeft != 0;
  }

  updateValue($event: any, selected: any, index: any) {
    if(selected['subAmount'] == undefined)
    {
      selected['subAmount'] = [];
    }
    //console.log($event)
    selected['amountLeft'] = selected.machineOccupation.productionOrder.measurement.amount
    selected['subAmount'][index] = $event?.target?.value;
    //console.log(selected)
    selected['subAmount'].map((value: any) => {
      //console.log(value)
      selected['amountLeft'] -= Number(value)
    })

  }
}
export class Group {
  level = 0;
  parent: Group | undefined;
  expanded = true;
  totalCounts = 0;
  get visible(): boolean {
    return !this.parent || (this.parent.visible && this.parent.expanded);
  }
}



  /*

  firstStepperFormGroup: FormGroup;
  secondStepperFormGroup: FormGroup;

  model: any;
  selectedModel: any;
  options: FormlyFormOptions = {};
  tableLayout: any;
  split: any;
  splitNumber: number = 0;
  splits: number[] = [];

  tableForm = new FormGroup({selectOrder: new FormControl()});
  amountLeft = 0

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private uiBuilder: UiBuilderService, private formBuilder: FormBuilder, private serverRequestService: ServerRequestService) {
    this.model =  {camundaMachineOccupations: data};
    this.uiBuilder.getDynamicFormLayout("splitOrderTable").subscribe((tableLayout) => {
      this.tableLayout = tableLayout;
    });

    this.firstStepperFormGroup = this.formBuilder.group({
      camundaMachineOccupations: this.getSelected(),
    });
    this.secondStepperFormGroup = this.formBuilder.group({
      orderSplits: this.formBuilder.array([
        this.formBuilder.group({
          split: ''
        }),
        this.formBuilder.group({
          split: ''
        })
      ])
    });

    this.secondStepperFormGroup.get("orderSplits")?.valueChanges.subscribe((value: any) => {
      let amount = 0;
      this.orderSplits().controls.forEach(control => {
        amount += control.value['split']
      })
      this.amountLeft = this.getSelected().machineOccupation.productionOrder.measurement.amount - amount
      //lastformControl.patchValue({amount: this.getSelected().machineOccupation.productionOrder.unit.amount - amount})
    })
  }


  ngOnInit() {
  }

  orderSplits(): FormArray {
    return this.secondStepperFormGroup.get("orderSplits") as FormArray;
  }

  newSplit(): FormGroup {
    return this.formBuilder.group({
      split: ''
    })
  }

  addSplit() {
    this.orderSplits().push(this.newSplit())
  }

  removeSplit(index: number){
    this.orderSplits().removeAt(index)
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  getSelected() {
    return this.model?.camundaMachineOccupations?.find((f: any) => f?.selected === true)
  }

  submit() {
    let splits: number[] = []
    this.secondStepperFormGroup.value.orderSplits.forEach((value : any) => splits.push(value.split))
    //console.log(splits)
    this.serverRequestService.createSplitOrder(this.getSelected(), splits);
  }

  allPlanned() {
    return this.amountLeft != 0;
  }

  updateAmountLeft() {
    this.amountLeft = this.getSelected()?.machineOccupation.productionOrder.unit.amount
  }
}
*/
