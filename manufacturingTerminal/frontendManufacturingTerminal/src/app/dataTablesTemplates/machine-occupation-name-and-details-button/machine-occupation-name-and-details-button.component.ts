import {ChangeDetectorRef, Component, Input, OnInit, Output} from '@angular/core';
import {Subject} from "rxjs";
import {ServerRequestService} from "../../service/server-request.service";
import {Router} from "@angular/router";
import {UtilitiesService} from "../../service/utilities.service";
import {SelectionStateService} from "../../service/selection-state.service";
import {DisableResponseButtonStateService} from "../../service/disable-response-button-state.service";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {isNaN} from "lodash";

@Component({
  selector: 'app-machine-occupation-name-and-details-button',
  templateUrl: './machine-occupation-name-and-details-button.component.html',
  styleUrls: ['./machine-occupation-name-and-details-button.component.scss']
})
export class MachineOccupationNameAndDetailsButtonComponent implements OnInit {

  @Output()
  emitter = new Subject<ComponentEventType>();

  @Input()
  data: any = {};
  public disableResponseButtonsSet: Set<string> | undefined;

  constructor(private serverRequestService: ServerRequestService, private router: Router, private cd: ChangeDetectorRef, private utilities: UtilitiesService, private selectionStateService: SelectionStateService, private disableResponseButtonStateService: DisableResponseButtonStateService) {
    this.disableResponseButtonStateService.getDisabledButtons().subscribe(disabledButtons => {
      this.disableResponseButtonsSet = disabledButtons;
    })
  }

  ngOnInit()  : void {
  }

  onAction1() {
    this.emitter.next({
      cmd: "action1",
      data: this.data,
    });
  }

  ngOnDestroy() {
    this.emitter.unsubscribe();
  }

  toDetailMask(machineOccupation: CamundaMachineOccupation) {
    this.router.navigate(['/order-detail' , {id: machineOccupation.id}]);
  }

  isNaN(value: any) {
    return isNaN(Number.parseInt(value))
  }
}

export interface ComponentEventType {
  cmd: string;
  data: any;
}
