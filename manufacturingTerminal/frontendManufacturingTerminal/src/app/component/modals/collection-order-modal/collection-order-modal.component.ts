import {Component, ElementRef, Inject, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {UiBuilderService} from "../../../service/ui-builder.service";
import {ServerRequestService} from "../../../service/server-request.service";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {MatTableDataSource} from "@angular/material/table";
import * as _ from "lodash";
import {Observable} from "rxjs";
import {SelectionStateService} from "../../../service/selection-state.service";
import {MatStepper} from "@angular/material/stepper";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  selector: 'app-collection-order-modal',
  templateUrl: './collection-order-modal.component.html',
  styleUrls: ['./collection-order-modal.component.scss']
})



export class CollectionOrderModalComponent  implements OnInit, OnChanges{

  firstStepperFormGroup!: FormGroup;
  secondStepperFormGroup!: FormGroup;

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
  columns!: any[];
  displayedColumns!: string[];
  groupByColumns: string[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private uiBuilder: UiBuilderService, private formBuilder: FormBuilder, private serverRequestService: ServerRequestService, private selectionStateService: SelectionStateService, private spinner: NgxSpinnerService) {



    this.spinner.show();

  }


  ngOnInit() {
      this._alldata = this.data;
      //console.log(this._alldata)
    this.serverRequestService.getALlMachineOccupations().subscribe(cmos =>
    {
      if(cmos != undefined) {
        const cmosFiltered = cmos.filter((value: CamundaMachineOccupation) => value.machineOccupation.subMachineOccupations == undefined && value.machineOccupation.status == "READY_TO_START" && !value.subMachineOccupation)
        this.model = {camundaMachineOccupations: cmosFiltered};
        this.parentMachineOccupation = this.data.parentMachineOccupation
        this.model.camundaMachineOccupations.forEach((value: any) => {
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
          /*this.uiBuilder.getDynamicFormLayout(value.externalId + "-collectionOrderTable").subscribe((formLayout) => {
            if (formLayout == undefined) {*/
              this.uiBuilder.getDynamicFormLayout("globalCollectionOrderTable").subscribe((formLayout) => {
                this.tableLayout = formLayout;
              });
            /*}
            this.tableLayout = formLayout;
          });*/
        })


        this.firstStepperFormGroup = this.formBuilder.group({
          camundaMachineOccupations: this.getSelected(),
        });
        this.secondStepperFormGroup = this.formBuilder.group({});
        this.filteredModel = _.cloneDeep(this.model)
        //console.log(this.filteredModel)
        this.secondStepperFormGroup.get("orderSplits")?.valueChanges.subscribe((value: any) => {
          let amount = 0;
          this.orderSplits().controls.forEach(control => {
            amount += control.value['split']
          })
          this.amountLeft = this.getSelected().machineOccupation.productionOrder.unit.amount - amount
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
    })

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
    return this.filteredModel?.camundaMachineOccupations?.filter((f: any) => f?.selected === true)
  }

  submit() {
    let machineOccupations = this.getSelected()
    //console.log(machineOccupations)
    if(machineOccupations.length > 0) {
      this.serverRequestService.createCollectionOrder(machineOccupations, this.parentMachineOccupation)
    }
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

  toggleFull(selected: any) {
    if(selected['toggleFull'] == null)
    {
      selected['toggleFull'] = true;
    }
    else {
      selected['toggleFull'] = !selected['toggleFull']
    }
    //console.log(selected)
  }

  updateValue($event: any, selected: any) {
    console.log($event)
    selected['subAmount'] = $event?.target?.value;
    selected['amountLeft'] = selected.machineOccupation.productionOrder.measurement.amount - Number(selected['subAmount'])
  }

  allFilled() {
   for(var selected of this.getSelected())
    {
      if((!selected['subAmount'] || selected['subAmount'] <= 0) && !selected['toggleFull'])
      {
        return false
      }
    }
   return true
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
