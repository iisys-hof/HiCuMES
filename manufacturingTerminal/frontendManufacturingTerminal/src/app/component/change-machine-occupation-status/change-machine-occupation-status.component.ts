import {Component, Inject, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {UiBuilderService} from "../../service/ui-builder.service";
import {ServerRequestService} from "../../service/server-request.service";
import {SelectionStateService} from "../../service/selection-state.service";
import * as _ from "lodash";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {Router} from "@angular/router";
import {ProductionOrder} from "../../interfaces/production-order";

@Component({
  selector: 'app-change-machine-occupation-status',
  templateUrl: './change-machine-occupation-status.component.html',
  styleUrls: ['./change-machine-occupation-status.component.scss']
})
export class ChangeMachineOccupationStatusComponent implements OnInit, OnChanges{

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

  _alldata: CamundaMachineOccupation[] | null | undefined = [];
  columns: any[] | undefined;
  displayedColumns: string[] | undefined;
  groupByColumns: string[] = [];

  @Input()
  machineOccupationsFilterByMachineType: CamundaMachineOccupation[] | null | undefined;
  selected: any;
  allStates: any = [
    {value: 'PLANNED', viewValue: 'Geplant'},
    {value: 'READY_TO_START', viewValue: 'Offen'},
    {value: 'RUNNING', viewValue: 'Laufend'},
    {value: 'SUSPENDED', viewValue: 'Gestoppt'},
    {value: 'PAUSED', viewValue: 'Pausiert'},
    {value: 'FINISHED', viewValue: 'Abgeschlossen'},
    {value: 'ABORTED', viewValue: 'Abgebrochen'},
    {value: 'ARCHIVED', viewValue: 'Archiviert'},
    {value: 'SKIPPED', viewValue: 'Übersprungen'},
  ];

  availableStates: any = []
  availableStatesFINISHED: any = [
    {value: 'FINISHED', viewValue: 'Abgeschlossen'},
    {value: 'READY_TO_START', viewValue: 'Offen'}
  ]
  availableStatesOTHER: any = [
    {value: 'PLANNED', viewValue: 'Geplant'},
    {value: 'READY_TO_START', viewValue: 'Offen'},
    {value: 'SUSPENDED', viewValue: 'Gestoppt'},
    {value: 'FINISHED', viewValue: 'Abgeschlossen'},
    {value: 'ABORTED', viewValue: 'Abgebrochen'},
    {value: 'ARCHIVED', viewValue: 'Archiviert'},
    {value: 'SKIPPED', viewValue: 'Übersprungen'},
  ];

  availableStatesRUNNING: any = [
    {value: 'RUNNING', viewValue: 'Laufend'},
    {value: 'ABORTED', viewValue: 'Abgebrochen'},
  ];
  availableStatesPAUSED: any = [
    {value: 'PAUSED', viewValue: 'Pausiert'},
    {value: 'ABORTED', viewValue: 'Abgebrochen'},
  ];
  productionOrderNr: any;
  machineOccupations: CamundaMachineOccupation[] | undefined;
  waitForOccupations = false;

  constructor(private router: Router, private uiBuilder: UiBuilderService, private formBuilder: FormBuilder, private serverRequestService: ServerRequestService, private selectionStateService: SelectionStateService) {


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
    this.selectionStateService.getSelectedMachineTypes().subscribe(value => {
      //console.log(value)

      this.uiBuilder.getDynamicFormLayout("globalChangeStatusTable").subscribe((formLayout) => {
        this.tableLayout = formLayout;
      });
    })

    //console.log(this.filteredModel)
    this.secondStepperFormGroup.get("orderSplits")?.valueChanges.subscribe((value: any) => {
      let amount = 0;
      this.orderSplits().controls.forEach(control => {
        amount += control.value['split']
      })
      this.amountLeft = this.getSelected().machineOccupation.productionOrder.measurement.amount - amount
      //lastformControl.patchValue({amount: this.getSelected().machineOccupation.productionOrder.unit.amount - amount})
    })
  }

  orderSplits(): FormArray {
    //console.log(this.secondStepperFormGroup)
    return this.secondStepperFormGroup?.get("orderSplits") as FormArray;
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
    //console.log(changes)
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
    //console.log( this.filteredModel)
    return this.filteredModel?.camundaMachineOccupations?.filter((f: any) => f?.selected === true)
  }

  submit() {
    this.getSelected().map((mOcc: any) => {
      this.serverRequestService.changeStatus(mOcc, mOcc?.machineOccupation?.status);
    })
    this.router.navigate(["order-overview"])
  }

  nextStep(data?: any) {
    //console.log(data)
    data.map((mOcc: any) => {
      this.updateStatusValue(mOcc)
      if(mOcc?.machineOccupation.status == "FINISHED")
      {
        mOcc.availableStates = this.availableStatesFINISHED
      }
      else if(mOcc?.machineOccupation.status == "RUNNING")
      {
        mOcc.availableStates = this.availableStatesRUNNING
      }
      else if(mOcc?.machineOccupation.status == "PAUSED")
      {
        mOcc.availableStates = this.availableStatesPAUSED
      }
      else
      {
        mOcc.availableStates = this.availableStatesOTHER
      }
    })

    //console.log(data)
    //console.log(this.selected)
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

  onEnterProductionOrder() {
    this.waitForOccupations = true
    if(this.productionOrderNr != undefined)
    {
      /*if(this.productionOrderNr.length > 6)
      {
        this.productionOrderNr = this.productionOrderNr.substring(0, 6)
      }*/

      this.productionOrderNr = this.productionOrderNr.split('-')[0]
      this.serverRequestService.loadMachineOccupationsByProductionOrder(this.productionOrderNr)
      this.serverRequestService.getMachineOccupationsByProductionOrder().subscribe(mOcc => {
        if(mOcc != undefined) {

          this.machineOccupations = mOcc;
          this.model = {camundaMachineOccupations: mOcc};
          //console.log(this.model)
          //this.model.camundaMachineOccupations = this.model.camundaMachineOccupations.filter((machineOccupation: CamundaMachineOccupation) => machineOccupation.machineOccupation.status != "RUNNING" && machineOccupation.machineOccupation.status != "PAUSED")
          this.model.camundaMachineOccupations?.forEach((value: any) => {
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

          this.filteredModel = _.cloneDeep(this.model)
          this.waitForOccupations = false
        }
      })
    }
  }


  updateStatusValue(selectedMo: any) {
    if(selectedMo["oldStatus"] == undefined) {
      selectedMo["oldStatus"] = selectedMo?.machineOccupation.status
    }
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
