import {Component, OnChanges, OnInit} from '@angular/core';
import {ServerRequestService} from "../../service/server-request.service";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {SseService} from "../../service/sse.service";
import {Observable} from "rxjs";
import {Machine} from "../../interfaces/machine";
import {SelectionStateService} from "../../service/selection-state.service";
import { NgxSpinnerService } from "ngx-spinner";
import {CamundaMachineOccupationDTO} from "../../interfaces/camunda-machine-occupation-dto";

@Component({
  selector: 'app-order-overview',
  templateUrl: './order-overview.component.html',
  styleUrls: ['./order-overview.component.scss']
})
export class OrderOverviewComponent implements OnInit {


  public machineOccupationsFilterByMachineType$: Observable<CamundaMachineOccupation[]> | undefined;
  public machineOccupationsDTO$: Observable<CamundaMachineOccupation[]> | undefined;
  public machines$: Observable<Machine[]> | undefined;

  constructor(private spinner: NgxSpinnerService, private serverRequestService: ServerRequestService, sseService: SseService, private stateService: SelectionStateService) {

  }

  ngOnInit(): void {
    this.machineOccupationsFilterByMachineType$ = this.serverRequestService.getMachineOccupationsFilterByMachineType();
    this.machineOccupationsFilterByMachineType$.subscribe();
    this.machineOccupationsDTO$ = this.serverRequestService.getMachineOccupationsDTO();
    this.machineOccupationsDTO$.subscribe()
    this.machines$ = this.serverRequestService.getAllMachines();
    this.machines$.subscribe();
    this.stateService.removeCurrentMachineOccupation();
    this.spinner.show();
  }

  startMachineOccupation(machineOccupation: CamundaMachineOccupation) {
    this.serverRequestService.startMachineOccupation(machineOccupation);
    /*setTimeout(() => {
      this.reloadEverything()
    }, 2000);*/
  }
}
