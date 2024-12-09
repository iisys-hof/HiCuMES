import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  OnInit,
  Output,
  Pipe,
  PipeTransform,
  QueryList,
  ViewChild,
  ViewChildren
} from '@angular/core';
import {Observable} from "rxjs";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {ServerRequestService} from "../../../service/server-request.service";
import {Router} from "@angular/router";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {CamundaSubProductionStep} from "../../../interfaces/camunda-sub-production-step";
import {UtilitiesService} from "../../../service/utilities.service";
import {SelectionStateService} from "../../../service/selection-state.service";
import {SseService} from "../../../service/sse.service";
import {DisableResponseButtonStateService} from "../../../service/disable-response-button-state.service";
import {forEach} from "lodash";

@Component({
  selector: 'app-order-overview-table',
  templateUrl: './order-overview-table.component.html',
  styleUrls: ['./order-overview-table.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class OrderOverviewTableComponent implements OnInit {
  @Output()
  startMachineOccupation = new EventEmitter<CamundaMachineOccupation>();

  machineOccupationsFilterByMachine$: Observable<CamundaMachineOccupation[]>;
  // @ts-ignore
  @ViewChild('outerSort', {static: true}) sort: MatSort;
  @ViewChildren('innerSort') innerSort: QueryList<MatSort> | undefined;
  @ViewChildren('innerTables') innerTables: QueryList<MatTable<CamundaSubProductionStep>> | undefined;
// @ts-ignore
  dataSource: MatTableDataSource<CamundaMachineOccupation>;
  cMachineOccupations: CamundaMachineOccupation[] = [];
  columnsToDisplay = ['baNr', 'article', 'startDate', 'endTime', 'lastFeedback', 'progress', 'startButton', 'toInputMask'];
  innerDisplayedColumnsAutomatic = ['formKey', 'name', 'taskId', 'filledFormField'];
  innerDisplayedColumnsManual = []
  innerDisplayedColumns = [...this.innerDisplayedColumnsAutomatic, ...this.innerDisplayedColumnsManual];
  tableExpanded: boolean = false;
  progressBar: number = 10;
  progressBarRadius: number = 0;
  innerColumnNames =
    {
      "formKey": "Art der Eingabemaske",
      "name": "Name",
      "taskId": "Task Id.Nr.",
      "filledFormField": "Eingaben"
    };
  expandedElement: CamundaMachineOccupation | null | undefined;

  public disableResponseButtonsSet: Set<string> | undefined;

  constructor(private serverRequestService: ServerRequestService, private router: Router, private cd: ChangeDetectorRef, private utilities: UtilitiesService, private selectionStateService: SelectionStateService,  private disableResponseButtonStateService: DisableResponseButtonStateService) {
    this.machineOccupationsFilterByMachine$ = this.serverRequestService.getMachineOccupationsFilterByMachineType();
    this.disableResponseButtonStateService.getDisabledButtons().subscribe(disabledButtons => {
      this.disableResponseButtonsSet = disabledButtons;
      console.log(this.disableResponseButtonsSet)
    })
  }

  ngOnInit() {

    this.machineOccupationsFilterByMachine$.subscribe((camundaMachineOccupations) => {
      console.log("updated observable")
      console.log(camundaMachineOccupations)
      this.cMachineOccupations = [];
      camundaMachineOccupations.forEach(occupation => {
        if (occupation.camundaSubProductionSteps && Array.isArray(occupation.camundaSubProductionSteps) && occupation.camundaSubProductionSteps.length) {
          //Remove entries of previous productionsteps from disableResponseButtonStateService
          occupation.camundaSubProductionSteps?.slice(0,-1).forEach(
            (subProductionStep) =>
            {
              this.disableResponseButtonStateService.enableButton(subProductionStep.taskId);
            }
          )
          this.cMachineOccupations = [...this.cMachineOccupations, {
            ...occupation,
            camundaSubProductionSteps: new MatTableDataSource(occupation.camundaSubProductionSteps),
            currentSubProductionStep: occupation.camundaSubProductionSteps?.slice(-1)[0],
          }];
        } else {
          this.cMachineOccupations = [...this.cMachineOccupations, occupation];
        }
      });
      this.dataSource = new MatTableDataSource(this.cMachineOccupations);
      // @ts-ignore
      this.dataSource.sort = this.sort;
      console.log(this.dataSource)
    });

    this.setProgress(80)
  }

  toggleRow(element: CamundaMachineOccupation) {
    console.log("element", element)
    if (element.camundaSubProductionSteps.length === 0) {
      return;
    }
    element.camundaSubProductionSteps && (element.camundaSubProductionSteps as MatTableDataSource<CamundaSubProductionStep>).data.length ? (this.expandedElement = this.expandedElement === element ? null : element) : null;
    this.tableExpanded = this.expandedElement !== null;
    this.cd.detectChanges();
    // @ts-ignore
    this.innerTables.forEach((table, index) => (table.dataSource as MatTableDataSource<CamundaSubProductionStep>).sort = this.innerSort.toArray()[index]);
  }

  startMachineOccupationEvent(machineOccupation: CamundaMachineOccupation) {
    this.startMachineOccupation.emit(machineOccupation);
  }

  toInputMask(machineOccupation: CamundaMachineOccupation) {
    var cspsM: MatTableDataSource<CamundaSubProductionStep> = machineOccupation.camundaSubProductionSteps;
    var path = cspsM.data.slice(-1)[0].formKey
    if (this.router.config.filter(e => e.path === path).length > 0) {
      this.router.navigate(['/' + path, {id: machineOccupation.id}]);
    } else {
      this.router.navigate(['/formfields', {id: machineOccupation.id}]);
    }
  }

  toDetailMask(machineOccupation: CamundaMachineOccupation) {
    this.router.navigate(['/order-detail' , {id: machineOccupation.id}]);
  }

  getFormKeyType(element: string) {
    return this.utilities.getSubProdcuctionStepSettings(element);
  }

  getInnercolumnName(element: string) {
    // @ts-ignore
    return this.innerColumnNames[element];
  }

  setProgress(prog: number) {
    this.progressBar = prog;

    if (prog > 94) {
      this.progressBarRadius = 8
    }
    if (prog > 97) {
      this.progressBarRadius = 12;
    }
    if (prog > 99) {
      this.progressBarRadius = 24;
    }
  }

  jsonToObject(value: any) {
    var returnValue = value
    returnValue = JSON.parse(returnValue);
    console.log(returnValue)
    return returnValue;
  }
}



