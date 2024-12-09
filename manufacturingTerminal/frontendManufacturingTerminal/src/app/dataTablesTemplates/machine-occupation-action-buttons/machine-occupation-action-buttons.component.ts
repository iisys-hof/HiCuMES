import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  Input,
  OnChanges,
  OnInit,
  Output,
  Renderer2,
  ViewChild
} from '@angular/core';
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
import { MatIcon } from '@angular/material/icon';
import {SseService} from "../../service/sse.service";
import {ComponentType} from "@angular/cdk/overlay";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ListOpenModalComponent} from "../../component/modals/list-open-modal/list-open-modal.component";

@Component({
  selector: 'app-machine-occupation-action-buttons',
  templateUrl: './machine-occupation-action-buttons.component.html',
  styleUrls: ['./machine-occupation-action-buttons.component.scss']
})
export class MachineOccupationActionButtonsComponent implements OnInit {

  @Output()
  emitter = new Subject<ComponentEventType>();

  @Input()
  data: any;

  @Input()
  machines: Machine[] | undefined | null


  availableMachines: Machine[] | undefined;

  public disableResponseButtonsSet: Set<string> | undefined;
  disabledStart = false;


  interval: any;
  progressValue = 0;
  holdInterval = 800;


  private openData: any= undefined
  private isOpen: boolean = false


  constructor(private dialog: MatDialog, private sseService: SseService, private serverRequestService: ServerRequestService, private router: Router, private cd: ChangeDetectorRef, private utilities: UtilitiesService, private selectionStateService: SelectionStateService, private disableResponseButtonStateService: DisableResponseButtonStateService) {
    this.disableResponseButtonStateService.getDisabledButtons().subscribe(disabledButtons => {
      this.disableResponseButtonsSet = disabledButtons;
    })
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

  openListOpenModal(data: any, machineOccupation: any, machine?: any) {
    this.openModal(ListOpenModalComponent, result => {
      console.log(result)
      if(result?.forceLogout)
      {
        this.startMachineOccupation(machineOccupation, machine)
      }

    }, {data: {title: "Achtung! Sie haben noch laufende Buchungen!", continueText: "Dennoch starten"}});
  }


  ngOnInit()  : void {
    //console.log(this.machines)
    //console.log(this.data)
    //console.log(this.data?.camundaSubProductionSteps?.slice(-1)[0]?.formKey)
    this.availableMachines = this.machines?.filter(machine => machine.machineType.externalId === this.data?.activeProductionStep.machineType?.externalId);
    this.serverRequestService.getAllOpen().subscribe(value =>
      {
        //console.log(value)
        this.openData = value;
        if((this.openData.overheadCosts && this.openData.overheadCosts?.length > 0) || (this.openData.machineOccupations && this.openData.machineOccupations?.length > 0))
        {
          this.isOpen = true
        }
        else if ((this.openData.overheadCosts && this.openData.overheadCosts?.length <= 0) && (this.openData.machineOccupations && this.openData.machineOccupations?.length <= 0))
        {
          this.isOpen = false
        }
      }
    )
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

  startMachineOccupationEvent(machineOccupation: CamundaMachineOccupation, machine?: any) {
    if(this.isOpen)
    {
      this.openListOpenModal(this.openData, machineOccupation, machine)
    }
    else
    {
      this.startMachineOccupation(machineOccupation, machine)
    }
  }

  startMachineOccupation(machineOccupation: CamundaMachineOccupation, machine?: any)
  {
    if(machine)
    {
      machineOccupation.machineOccupation.machine = machine;
    }
    this.serverRequestService.startMachineOccupation(machineOccupation);
    this.disabledStart = true;
  }

  /*pauseMachineOccupationEvent(machineOccupation: CamundaMachineOccupation) {
    this.serverRequestService.pauseMachineOccupation(machineOccupation);
  }*/

  toInputMask(machineOccupation: CamundaMachineOccupation) {
    var path = machineOccupation.camundaSubProductionSteps.slice(-1)[0]?.formKey
    if (this.router.config.filter(e => e.path === path).length > 0) {
      this.router.navigate(['/' + path, {id: machineOccupation.id}]);
    } else {
      this.router.navigate(['/formfields', {id: machineOccupation.id}]);
    }
  }

  startMachineOccupationWithMachineSelectedEvent(machineOccupation: CamundaMachineOccupation, machine: Machine) {
    this.startMachineOccupationEvent(machineOccupation, machine)
  }

  getIconForTaskFormKey(formKey: string):string{

    var subProdStepJSON = this.utilities.getSubProdcuctionStepSettings(formKey);

    return subProdStepJSON.icon;
  }

  getColorForTaskFormKey(formKey: string):string{

    var subProdStepJSON = this.utilities.getSubProdcuctionStepSettings(formKey);

    return subProdStepJSON.color;
  }


  handleMouseDown(event: MouseEvent) {
    if (event.button === 0) {
      this.startTimer();
    }
  }

  handleTouchStart(event: TouchEvent) {
    if (event.touches.length === 1) {
      this.startTimer();
    }
  }

  startTimer() {
    let tickTimeout = 10;
    this.interval = setInterval(() => {
      this.progressValue += (tickTimeout/this.holdInterval) * 100
      //console.log(this.progressValue)
      if(this.progressValue > 110)
      {
        this.startMachineOccupationEvent(this.data)
        this.stopTimer()
      }
    }, tickTimeout);
  }

  stopTimer() {
    this.progressValue = 0
    clearInterval(this.interval);
  }


/*  test(machineOccupation: CamundaMachineOccupation, toolSetting: ToolSetting) {
    this.serverRequestService.setActiveToolSetting(machineOccupation, toolSetting)
  }*/
}

export interface ComponentEventType {
  cmd: string;
  data: any;
}
