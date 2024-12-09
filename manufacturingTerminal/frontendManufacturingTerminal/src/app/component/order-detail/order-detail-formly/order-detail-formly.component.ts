import {ApplicationRef, Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {UiBuilderService} from "../../../service/ui-builder.service";
import {UtilitiesService} from "../../../service/utilities.service";
import {Observable} from "rxjs";
import {ProductRelationship} from "../../../interfaces/product-relationship";
import {ServerRequestService} from "../../../service/server-request.service";
import {SelectionStateService} from "../../../service/selection-state.service";
import {DisableResponseButtonStateService} from "../../../service/disable-response-button-state.service";
import {Router} from "@angular/router";
import {Machine} from "../../../interfaces/machine";
import * as _ from "lodash";
import {ComponentType} from "@angular/cdk/overlay";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {OverheadCostNoteModal} from "../../modals/overhead-cost-note-modal/overhead-cost-note-modal";

/*
const ELEMENT_DATA: any [] = [
  {
    baNumber: 326363,
    printDate: '07.07.2020',
    amount: "11 Stk.",
    article: 76000,
    dateStart: '07.07.2020',
    dateEnd: '07.08.2020',
    status: 'RUNNING'
    ,
    orderType: "P",
    orderName: "2063799_2\nvon: 2",
    bAmount: "10 Stk.",
    distribution: "We/Ke",
    costumer: 54146,
    notes: "K&KI Bearing"
  }];

const ELEMENT_DATA_QUALITY: any [] = [
  {
    extSchleifm: "85A", extKoernung: '120/5', extHaerte: "K", extGefuege: 9, extBindung: '-', extPoren: '-', extNB: '-'
    , extSpez: "-", extYHand: 0.00, extYAuto: "2,100", extKonz: "0", extForm: "ISO Form 1", extRandRadius: "A"
  }];

const ELEMENT_DATA_MEASUREMENTS: any [] = [
  {
    extDurchmesserToleranz: "36,00 | -",
    extBreiteToleranz: '34,00 | -',
    extBohrungHoeheToleranz: "16,00 | -",
    extAussparung1: "0,00/0,00",
    extAussparung2: '0,00/0,00',
    extDrehzahl1: '-',
    extDrehzahl2: '-',
    extDrehzahl3: "-",
    extBemerkung: "-",
    extVms: "63"
  }];*/


@Component({
  selector: 'app-order-detail-formly',
  templateUrl: './order-detail-formly.component.html',
  styleUrls: ['./order-detail-formly.component.scss']
})
export class OrderDetailFormlyComponent implements OnInit, OnChanges {
  @Input() machineOccupation: CamundaMachineOccupation | null | undefined;
  @Input() productRelationships: ProductRelationship[] | null | undefined;

  @Input()
  machines: Machine[] | undefined | null;

  machineOccupationsByProductionOrder: CamundaMachineOccupation[] | undefined
    /*

      displayedColumnsHeader1: string[] = ['baNumber', 'printDate', 'amount', 'article', 'dateStart', 'dateEnd', 'status'];
      displayedColumnsHeader2: string[] = ['orderType', 'orderName', 'bAmount', 'distribution', 'costumer', 'notes'];
      displayedColumnsHeader2Names = {
        orderType: "Auftragsart",
        orderName: "Auftrag",
        bAmount: "B-Menge",
        distribution: "Verkauf",
        costumer: "Kunde",
        notes: "Beschreibung"
      };

      displayedColumnsQuality: string[] = ['extSchleifm', 'extKoernung', 'extHaerte', 'extGefuege', 'extBindung', 'extPoren', 'extNB', 'extSpez', 'extYHand', 'extYAuto', 'extKonz', 'extForm', 'extRandRadius'];
      displayedColumnsMeasurements1: string[] = ['extDurchmesserToleranz', 'extBreiteToleranz', 'extBohrungHoeheToleranz', 'extAussparung1', 'extAussparung2'];
      displayedColumnsMeasurements2: string[] = ['extDrehzahl1', 'extDrehzahl2', 'extDrehzahl3', 'extBemerkung', 'extVms'];


      dataSourceHeader = ELEMENT_DATA;
      dataSourceQuality = ELEMENT_DATA_QUALITY;
      dataSourceMeasurements = ELEMENT_DATA_MEASUREMENTS;

    */

/*

  displayedColumnsHeader1: string[] = ['baNumber', 'printDate', 'amount', 'article', 'dateStart', 'dateEnd', 'status'];
  displayedColumnsHeader2: string[] = ['orderType', 'orderName', 'bAmount', 'distribution', 'costumer', 'notes'];
  displayedColumnsHeader2Names = {
    orderType: "Auftragsart",
    orderName: "Auftrag",
    bAmount: "B-Menge",
    distribution: "Verkauf",
    costumer: "Kunde",
    notes: "Beschreibung"
  };

  displayedColumnsQuality: string[] = ['extSchleifm', 'extKoernung', 'extHaerte', 'extGefuege', 'extBindung', 'extPoren', 'extNB', 'extSpez', 'extYHand', 'extYAuto', 'extKonz', 'extForm', 'extRandRadius'];
  displayedColumnsMeasurements1: string[] = ['extDurchmesserToleranz', 'extBreiteToleranz', 'extBohrungHoeheToleranz', 'extAussparung1', 'extAussparung2'];
  displayedColumnsMeasurements2: string[] = ['extDrehzahl1', 'extDrehzahl2', 'extDrehzahl3', 'extBemerkung', 'extVms'];


  dataSourceHeader = ELEMENT_DATA;
  dataSourceQuality = ELEMENT_DATA_QUALITY;
  dataSourceMeasurements = ELEMENT_DATA_MEASUREMENTS;

*/



  tableForm = new FormGroup({});
  model: any;
  modelProductionOrder: any;
  options: FormlyFormOptions = {};
  tableLayoutHeader: any;
  tableLayoutQuality: any;
  tableLayoutProductRelationships: any;
  tableLayoutSubProductionSteps: any;
  tableLayoutToolSettings: any;
  tableLayoutProductionSteps: any;
  tableLayoutProductQCFeatures: any;
  tableLayoutDimensions: any;
  tableLayoutNotes: any;
  camundaMachineOccupation: any;

  public disableResponseButtonsSet: Set<string> | undefined;

  constructor(private disableResponseButtonStateService: DisableResponseButtonStateService, private router: Router, private uiBuilder: UiBuilderService, private appRef: ApplicationRef, private utilitiesService: UtilitiesService, private serverRequestService: ServerRequestService, private stateService: SelectionStateService, private dialog: MatDialog) {
    this.uiBuilder.getDynamicFormLayout("orderDetailHeader").subscribe((tableLayout) => {
      this.tableLayoutHeader = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("orderDetailQuality").subscribe((tableLayout) => {
      this.tableLayoutQuality = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("orderDetailProductRelationships").subscribe((tableLayout) => {
      this.tableLayoutProductRelationships = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("orderDetailProductQCFeatures").subscribe((tableLayout) => {
      this.tableLayoutProductQCFeatures = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("orderDetailNotes").subscribe((tableLayout) => {
      this.tableLayoutNotes = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("orderDetailProductionSteps").subscribe((tableLayout) => {
      this.tableLayoutProductionSteps = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("orderDetailSubProductionSteps").subscribe((tableLayout) => {
      this.tableLayoutSubProductionSteps = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("orderDetailToolSettings").subscribe((tableLayout) => {
      this.tableLayoutToolSettings = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("orderDetailDimensions").subscribe((tableLayout) => {
      this.tableLayoutDimensions = tableLayout;
    });

    this.disableResponseButtonStateService.getDisabledButtons().subscribe(disabledButtons => {
      this.disableResponseButtonsSet = disabledButtons;
    })
  }

ngOnChanges(changes:SimpleChanges) {
    console.log(changes)
    this.ngOnInit()
}

  ngOnInit(): void {

    this.model = {
      "machineOccupation": [this.utilitiesService.restructureCamundaMachineOccupation(this.machineOccupation)],
      "camundaSubProductionSteps": this.machineOccupation?.camundaSubProductionSteps,
      "product": [this.machineOccupation?.machineOccupation.productionOrder.product],
      "qualityControlFeatures": this.machineOccupation?.machineOccupation.productionOrder.product?.qualityControlFeatures,
      "productRelationships": [],
      "productionSteps": this.machineOccupation?.machineOccupation?.productionSteps
    }

    console.log(this.model)
    console.log(this.productRelationships)
    this.productRelationships?.forEach(relationship => {
      console.log(relationship)
      if(this.machineOccupation != undefined)
      {
        this.model["productRelationships"].push(
          {
            "product": relationship.product,
            "part": relationship.part,
            "amountPercent": relationship.measurement.amount * 100,
            "amount": relationship.measurement.amount * this.machineOccupation?.machineOccupation.productionOrder.measurement.amount,
            "unitString": relationship.measurement.unitString
          }
        )
      }
    });

    if(this.machineOccupation !== null && this.machineOccupation !== undefined) {
      this.stateService.updateCurrentMachineOccupation(this.machineOccupation)
      console.log(this.machineOccupation)
      this.camundaMachineOccupation = this.machineOccupation
      this.serverRequestService.loadMachineOccupationsByProductionOrder(this.machineOccupation.machineOccupation.productionOrder.externalId)

      this.serverRequestService.getMachineOccupationsByProductionOrder().subscribe(value =>
      {

        this.machineOccupationsByProductionOrder = _.cloneDeep(value)
        if(this.machineOccupationsByProductionOrder) {
          if(this.machineOccupationsByProductionOrder[0]?.machineOccupation.productionOrder.id == this.machineOccupation?.machineOccupation?.productionOrder?.id)
          {

            this.machineOccupationsByProductionOrder?.map(occupation => this.utilitiesService.restructureCamundaMachineOccupation(occupation))

            let restructured = this.machineOccupationsByProductionOrder?.map(occupation => this.utilitiesService.restructureCamundaMachineOccupation(occupation))
            let result: any = [];
            _.each(restructured, value => {
              value.productionSteps.map((step : any) =>
              {
                //Iterate through every camundaMachineOccupation and just add one productionStep to machineOccupation; clone camundaMachineOccupation, if there are more productionSteps
                let copy = _.cloneDeep(value);
                copy.productionSteps = [step]
                result.push(copy)
              })

            })
            this.modelProductionOrder = {"camundaMachineOccupations": _.flatten(result)}
          }
        }
      })
    }
  }

  openModal(modal: ComponentType<any>, callback: (result: any) => void, config: MatDialogConfig = {}) {
    if (this.dialog.openDialogs.length > 0) {
      return;
    }
    const dialogRef = this.dialog.open(modal, config);

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        callback(result);
      }
    });
  }

  openEditModal() {
    this.openModal(OverheadCostNoteModal, result => {
      console.log(result)

      //this.serverRequestService.editOverHeadCostNote(this.data.id, result)
      if(this.machineOccupation?.machineOccupation) {
        this.serverRequestService.addNoteToMachineOccupation(this.machineOccupation?.machineOccupation, result)
      }
    }, {data: {note: null}});
  }

  getNameOfColumn(dto: any, property: string): any {
    return dto[property];
  }

  toInputMask(machineOccupation: CamundaMachineOccupation) {
    console.log(machineOccupation)
    var path = machineOccupation.camundaSubProductionSteps.slice(-1)[0].formKey
    if (this.router.config.filter(e => e.path === path).length > 0) {
      this.router.navigate(['/' + path, {id: machineOccupation.id}]);
    } else {
      this.router.navigate(['/formfields', {id: machineOccupation.id}]);
    }
  }
}
