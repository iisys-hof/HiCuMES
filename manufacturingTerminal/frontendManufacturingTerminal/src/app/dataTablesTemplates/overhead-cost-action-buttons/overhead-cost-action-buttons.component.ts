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
import {OverheadCostCenters} from "../../interfaces/overhead-cost-centers";
import {get} from "lodash";
import {OverheadCosts} from "../../interfaces/overhead-costs";
import {ComponentType} from "@angular/cdk/overlay";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {SplitOrderModalComponent} from "../../component/modals/split-order-modal/split-order-modal.component";
import {OverheadCostNoteModal} from "../../component/modals/overhead-cost-note-modal/overhead-cost-note-modal";
import {ListOpenModalComponent} from "../../component/modals/list-open-modal/list-open-modal.component";

@Component({
  selector: 'app-overhead-cost-action-buttons',
  templateUrl: './overhead-cost-action-buttons.component.html',
  styleUrls: ['./overhead-cost-action-buttons.component.scss']
})
export class OverheadCostActionButtonsComponent implements OnInit {

  @Output()
  emitter = new Subject<ComponentEventType>();

  @Input()
  data: any;

  disabledStart = false;

  interval: any;
  progressValue = 0;
  holdInterval = 800;

  private openData: any= undefined
  private isOpen: boolean = false

  constructor(private serverRequestService: ServerRequestService, private dialog: MatDialog) {

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

  openListOpenModal(data: any, costCenter: any) {
    this.openModal(ListOpenModalComponent, result => {
      console.log(result)
      if(result?.forceLogout)
      {
        this.serverRequestService.startOverheadCost(costCenter.externalId)
        this.disabledStart = true;
      }

    }, {data: {title: "Achtung! Sie haben noch laufende Buchungen!", continueText: "Dennoch starten"}});
  }

  ngOnInit()  : void {
    //console.log(this.data)

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

  startOverheadCost(costCenter: OverheadCostCenters) {
    if(this.isOpen)
    {
      this.openListOpenModal(this.openData, costCenter)
    }
    else
    {
      this.serverRequestService.startOverheadCost(costCenter.externalId)
      this.disabledStart = true;
    }
  }

  stopOverheadCost(overheadCost: OverheadCosts) {

    this.serverRequestService.stopOverheadCost(String(overheadCost.id))
    this.disabledStart = true;
  }

  /*pauseMachineOccupationEvent(machineOccupation: CamundaMachineOccupation) {
    this.serverRequestService.pauseMachineOccupation(machineOccupation);
  }*/

  handleMouseDown(event: MouseEvent) {
    //console.log(event)
    if (event.button === 0) {
      this.startTimer();
    }
  }

  handleTouchStart(event: TouchEvent) {
    //console.log(event)
    if (event.touches.length === 1) {
      this.startTimer();
    }
  }

  handleMouseDown2(event: MouseEvent) {
    //console.log(event)
    if (event.button === 0) {
      this.startTimer2();
    }
  }

  handleTouchStart2(event: TouchEvent) {
    //console.log(event)
    if (event.touches.length === 1) {
      this.startTimer2();
    }
  }

  startTimer() {
    let tickTimeout = 10;
    this.interval = setInterval(() => {
      this.progressValue += (tickTimeout/this.holdInterval) * 100
      //console.log(this.progressValue)
      if(this.progressValue > 110)
      {
        this.startOverheadCost(this.data.overheadCostCenter)
        this.stopTimer()
      }
    }, tickTimeout);
  }

  startTimer2() {
    let tickTimeout = 10;
    this.interval = setInterval(() => {
      this.progressValue += (tickTimeout/this.holdInterval) * 100
      //console.log(this.progressValue)
      if(this.progressValue > 110)
      {
        this.stopOverheadCost(this.data)
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
  openEditModal() {
    this.openModal(OverheadCostNoteModal, result => {
        console.log(result)
      this.serverRequestService.editOverHeadCostNote(this.data.id, result)
    }, {data: {note: this.data.note}});
  }
}

export interface ComponentEventType {
  cmd: string;
  data: any;
}
