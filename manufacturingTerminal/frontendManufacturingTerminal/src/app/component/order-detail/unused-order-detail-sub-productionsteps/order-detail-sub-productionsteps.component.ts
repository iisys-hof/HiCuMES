import {Component, Input, OnInit} from '@angular/core';
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {SubProductionStep} from "../../../interfaces/sub-production-step";
import {CamundaSubProductionStep} from "../../../interfaces/camunda-sub-production-step";


const ELEMENT_DATA_SUB_PRODUCTION_STEPS: any [] = [
  {
    extMGRF: "100",
    extArbeitsgang: '3110',
    extStartTermin: "09.07.2020 \n 10m",
    extEndTermin: "09.07.2020 \n 10m",
    extLdfNr: '32636002',
    status: "RUNNING"
  },
  {
    extMGRF: "100",
    extArbeitsgang: '3100',
    extStartTermin: "09.07.2020 \n 10m",
    extEndTermin: "09.07.2020 \n 20m",
    extLdfNr: '32636003',
    status: "RUNNING"
  },
  {
    extMGRF: "100",
    extArbeitsgang: '3010',
    extStartTermin: "10.07.2020 \n 10m",
    extEndTermin: "10.07.2020 \n 0m",
    extLdfNr: '32636004',
    status: "RUNNING"
  },
];


@Component({
  selector: 'app-order-detail-sub-productionsteps',
  templateUrl: './order-detail-sub-productionsteps.component.html',
  styleUrls: ['./order-detail-sub-productionsteps.component.scss']
})
export class OrderDetailSubProductionstepsComponent implements OnInit {

  @Input() machineOccupation: CamundaMachineOccupation | null | undefined;

  displayedColumnsSubProductionSteps: string[] = ['extMGRF', 'extArbeitsgang', 'extStartTermin', 'extEndTermin', 'extLdfNr', 'status'];

  dataSourceSubProductionSteps: any[] = [];//ELEMENT_DATA_SUB_PRODUCTION_STEPS;

  constructor() {
  }

  ngOnInit(): void {
    if (this.machineOccupation != undefined) {
      this.dataSourceSubProductionSteps = [];
      this.machineOccupation.camundaSubProductionSteps.forEach((productionStep: CamundaSubProductionStep) => {
        this.dataSourceSubProductionSteps.push(
            {
              extMGRF: "100",
              extArbeitsgang: productionStep.name,
              extStartTermin: productionStep.subProductionStep.startDateTime,
              extEndTermin: productionStep.subProductionStep.endDateTime,
              extLdfNr: productionStep.id,
              status: "RUNNING"
            }
          )
        })
      }
  }
}

