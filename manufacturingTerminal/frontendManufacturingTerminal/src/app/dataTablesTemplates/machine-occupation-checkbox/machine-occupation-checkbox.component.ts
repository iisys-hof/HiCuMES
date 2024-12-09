import {ChangeDetectorRef, Component, Input, OnInit, Output} from '@angular/core';
import {Subject} from "rxjs";
import {MachineOccupation} from "../../interfaces/machine-occupation";
import {ServerRequestService} from "../../service/server-request.service";
import {Router} from "@angular/router";
import {UtilitiesService} from "../../service/utilities.service";
import {SelectionStateService} from "../../service/selection-state.service";
import {DisableResponseButtonStateService} from "../../service/disable-response-button-state.service";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {Machine} from "../../interfaces/machine";
import {ToolSetting} from "../../interfaces/tool";

@Component({
  selector: 'app-machine-occupation-checkbox',
  templateUrl: './machine-occupation-checkbox.component.html',
  styleUrls: ['./machine-occupation-checkbox.component.scss']
})
export class MachineOccupationCheckboxComponent implements OnInit {

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
}

export interface ComponentEventType {
  cmd: string;
  data: any;
}
