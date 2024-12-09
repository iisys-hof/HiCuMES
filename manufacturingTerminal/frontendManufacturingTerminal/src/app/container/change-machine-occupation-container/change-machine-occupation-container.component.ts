import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {Machine} from "../../interfaces/machine";
import {ServerRequestService} from "../../service/server-request.service";
import {SseService} from "../../service/sse.service";
import {SelectionStateService} from "../../service/selection-state.service";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  selector: 'app-change-machine-occupation-container',
  templateUrl: './change-machine-occupation-container.component.html',
  styleUrls: ['./change-machine-occupation-container.component.scss']
})
export class ChangeMachineOccupationContainerComponent implements OnInit {

  public machineOccupationsFilterByMachineType$: Observable<CamundaMachineOccupation[]> | undefined;

  constructor(private serverRequestService: ServerRequestService, sseService: SseService, private stateService: SelectionStateService, private spinner: NgxSpinnerService) {

    this.spinner.show();
  }

  ngOnInit(): void {
    //console.log("INIT")
    //this.serverRequestService.requestNewData(undefined, undefined, true)
    //this.machineOccupationsFilterByMachineType$ = this.serverRequestService.getMachineOccupationsFilterByMachineType();
    //this.machineOccupationsFilterByMachineType$.subscribe();
    //this.stateService.removeCurrentMachineOccupation();
  }

}
