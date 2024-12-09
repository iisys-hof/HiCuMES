import { Component, OnInit } from '@angular/core';
import {ServerRequestService} from "../../service/server-request.service";

@Component({
  selector: 'app-overhead-costs-container',
  templateUrl: './overhead-costs-container.component.html',
  styleUrls: ['./overhead-costs-container.component.scss']
})
export class OverheadCostsContainerComponent implements OnInit {

  overheadCostCenters$: any = undefined
  overheadCosts$: any = undefined
  topOverheadCostCenters$: any = undefined

  constructor(private serverRequestService: ServerRequestService) {
    this.serverRequestService.loadOverheadCostCenters()
    this.serverRequestService.loadOverheadCosts()
    this.serverRequestService.loadTopOverheadCostCenters()
    this.overheadCostCenters$ = this.serverRequestService.getAllOverheadCostCenters()
    this.overheadCosts$ = this.serverRequestService.getOverheadCosts()
    this.topOverheadCostCenters$ = this.serverRequestService.getTopOverheadCostCenters()
  }


  ngOnInit(): void {

  }

  protected readonly undefined = undefined;
}
