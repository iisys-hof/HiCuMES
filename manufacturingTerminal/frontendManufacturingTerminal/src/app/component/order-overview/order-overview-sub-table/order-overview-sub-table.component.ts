import {AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import * as $ from "jquery";
import {DataTableDirective} from "angular-datatables";
import {Subject} from "rxjs";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {FormGroup} from "@angular/forms";
import {UiBuilderService} from "../../../service/ui-builder.service";

@Component({
  selector: 'app-order-overview-sub-table',
  templateUrl: './order-overview-sub-table.component.html',
  styleUrls: ['./order-overview-sub-table.component.scss']
})
export class OrderOverviewSubTableComponent implements OnInit {

  @ViewChild(DataTableDirective, {static: false})
  dtElement: DataTableDirective | undefined;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};

  @Input()
  data: any = {};

  expandedElement: CamundaMachineOccupation | null | undefined;
  subMachineOccupations: any
  expansionForm = new FormGroup({});
  expansionTableTasksLayout: any = []
  expansionTableOrdersLayout: any = []
  modelExpansionTable: any;

  constructor(private uiBuilder: UiBuilderService) {
    this.uiBuilder.getDynamicFormLayout("orderOverviewSubTable").subscribe((tableLayout) => {
      this.expansionTableTasksLayout = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("orderOverviewSubTableOrders").subscribe((tableLayout) => {
      this.expansionTableOrdersLayout = tableLayout;
    });
  }

  ngOnInit() {
    this.expandedElement = this.data[0];
    this.subMachineOccupations = this.expandedElement?.machineOccupation
    console.log(this.expandedElement)
    console.log(this.subMachineOccupations)
  //   this.dtOptions = {
  //     data: this.data[0].camundaSubProductionSteps,
  //     columns: [
  //       {
  //         title: 'Art der Eingabemaske',
  //         data: 'formKey'
  //       }, {
  //         title: 'Name',
  //         data: 'name'
  //       }, {
  //         title: 'Task Id.Nr.',
  //         data: 'taskId'
  //       }, {
  //         title: 'Eingaben',
  //         data: 'filledFormField'
  //       }
  //     ],
  //     language: {
  //       url: './assets/datatables/datatables_de-DE.json'
  //     },
  //     dom: '<t><"table-footer">',
  //     buttons: {
  //       dom: {
  //         button: {
  //           className: 'mat-focus-indicator state_button mat-stroked-button mat-button-base mat-primary ng-star-inserted' //Primary class for all buttons
  //         }
  //       },
  //       buttons: [
  //         'colvis',
  //         'copy',
  //         'print',
  //         'excel'
  //       ]},
  //     order: [[0, 'asc']],
  //   };
  }


}
