import {Component, OnChanges, OnInit} from '@angular/core';
import {ServerRequestService} from "../../service/server-request.service";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {SseService} from "../../service/sse.service";
import {Observable} from "rxjs";
import {Machine} from "../../interfaces/machine";
import {SelectionStateService} from "../../service/selection-state.service";
import {AuthGuard} from "../../service/authguard.service";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  selector: 'app-user-order-overview',
  templateUrl: './user-order-overview.component.html',
  styleUrls: ['./user-order-overview.component.scss']
})
export class UserOrderOverviewComponent implements OnInit {


  public machineOccupationsFilterByMachineType$: Observable<CamundaMachineOccupation[]> | undefined;
  public machineOccupationsDTO$: Observable<CamundaMachineOccupation[]> | undefined;
  public machines$: Observable<Machine[]> | undefined;

  constructor(private spinner: NgxSpinnerService, private authGuard: AuthGuard, private serverRequestService: ServerRequestService, sseService: SseService, private stateService: SelectionStateService) {

  }

  ngOnInit(): void {
    this.authGuard.isLoggedIn().then(isLoggedIn => {
      if(isLoggedIn) {
        this.machineOccupationsDTO$ = this.serverRequestService.getALlMachineOccupations();
        this.machineOccupationsDTO$.subscribe()
        this.machineOccupationsFilterByMachineType$ = this.serverRequestService.getMachineOccupationsDTOFilterByMachineTypeAndUser(this.authGuard.getUsername());
        this.machineOccupationsFilterByMachineType$.subscribe();

        this.machines$ = this.serverRequestService.getAllMachines();
        this.machines$.subscribe();
        this.stateService.removeCurrentMachineOccupation();
        this.spinner.show();
      }
    })
  }

  startMachineOccupation(machineOccupation: CamundaMachineOccupation) {
    this.serverRequestService.startMachineOccupation(machineOccupation);
    /*setTimeout(() => {
      this.reloadEverything()
    }, 2000);*/
  }
}
