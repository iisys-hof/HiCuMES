
import {
  Component,
  ViewChild,
  TemplateRef,
  OnInit,
  ElementRef,
  AfterViewInit,
  OnChanges,
  ApplicationRef, OnDestroy
} from '@angular/core';
import {FormlyFieldConfig, FieldArrayType, FieldArrayTypeConfig} from '@ngx-formly/core';
import { TableColumn } from '@swimlane/ngx-datatable/lib/types/table-column.type'
import * as _ from "lodash";
import {CamundaMachineOccupation} from "../interfaces/camunda-machine-occupation";
import {ToolSetting} from "../interfaces/tool";
import {get} from "lodash";
import {ProductRelationship} from "../interfaces/product-relationship";
import {ServerRequestService} from "../service/server-request.service";
import {FormGroup} from "@angular/forms";
import {UiBuilderService} from "../service/ui-builder.service";
import {UtilitiesService} from "../service/utilities.service";
import {NgxSpinnerService} from "ngx-spinner";
import {Observable, Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'formly-toolsettingTable',
  template: `
    <form *ngIf="hasData; else progressSpinner" [formGroup]="tableForm"
          class="order-detail-header-form order-detail-header-table-1">
      <formly-form [fields]="tableLayoutProductRelationships" [form]="tableForm" [model]="model"></formly-form>
    </form>
    <ng-template #progressSpinner>
      <ngx-spinner class="hc-spinner" type="square-jelly-box" [fullScreen]="false"> Lädt ...</ngx-spinner>
    </ng-template>
  `,
})
export class ProductRelationshipsTable  extends FieldArrayType implements AfterViewInit, OnDestroy {
  @ViewChild('defaultColumn') public defaultColumn?: TemplateRef<any>;
  private destroy$ = new Subject<void>();

  data: {} | undefined

  tableLayoutProductRelationships: any;
  tableForm = new FormGroup({});
  hasData: boolean = false;
  innerModel: any = {}

  constructor(private serverRequestService: ServerRequestService, private uiBuilder: UiBuilderService,  private spinner: NgxSpinnerService) {
    super();
     this.spinner.show();

    this.tableForm.statusChanges.subscribe(value => {
      //this.model["productRelationships"] =  this.innerModel["productRelationships"]
      this.saveToSessionStorage()
      //console.log("this.model", this.model);
    })


  }

  ngOnDestroy() {
    //console.log("DESTROY")
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngAfterViewInit() {

    //console.log("this.field", this.field);
    //console.log("this.model", this.model);
    //console.log("this.form", this.form);
    //console.log("this.key", this.key);
   //console.log(this.toolSettingsGroupedByToolSettingParams)

    //If the model has no productRelationships, it means, that it wasn't saved in sessionStorage before. Therefore we need to first load it from backend and save it
    if(this.model.productRelationships?.length <= 0)
    {
      //console.log("load values")
      this.serverRequestService.loadProductRelationshipsByProductId(this.model.productId, this.model.productionOrderExtId)
      this.serverRequestService.getProductRelationships().pipe(
        takeUntil(this.destroy$)
      ).subscribe(value => {
          //console.log("LOADED")
          //this.innerModel = this.model
          value?.forEach((relationship: any) => {
            //precalculate values to not have to do it in formly
            relationship["amountPercent"] = relationship.measurement.amount * 100
            relationship["amount"] = relationship.measurement.amount * this.model.amount
            relationship["unitString"] = relationship.measurement.unitString
          })
          this.hasData = true
          this.model["productRelationships"] = value
          //this.formControl.patchValue(this.innerModel)
          //make sure to show the values in UI
          this.formControl.updateValueAndValidity()
          //this.formControl.clearValidators()
          //this.formControl.patchValue(this.model)
        }
      )
    }

    let layout = "productRelationshipsFormField"
    if(this.field.props["layout"])
    {
      layout = this.field.props["layout"]
    }

    //console.log(layout)
    this.uiBuilder.getDynamicFormLayout(layout).subscribe((tableLayout) => {
      this.tableLayoutProductRelationships = tableLayout;
      //console.log("layout loaded")

      //We already have some data saved in the sessionStorage, so we use that
      if(this.model.productRelationships?.length > 0)
      {
        //console.log("already has data")
        //console.log(this.model)
        this.hasData = true
        //this.innerModel = this.model
        //make sure to show the values in UI
        this.formControl.updateValueAndValidity()
        //this.formControl.clearValidators()
      }
    });
  }

  saveToSessionStorage() {
    let id = this.model.subProdStepExtId
    if(id)
    {
      sessionStorage.setItem('FormfieldRelationship_' + id, JSON.stringify(this.model))
    }
  }
}
