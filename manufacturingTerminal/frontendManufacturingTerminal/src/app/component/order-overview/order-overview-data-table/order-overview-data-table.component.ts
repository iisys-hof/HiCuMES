import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ComponentFactoryResolver,
  ComponentRef,
  Input,
  OnChanges,
  OnInit,
  Renderer2, SimpleChange,
  SimpleChanges,
  TemplateRef,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import {Subject} from "rxjs";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {ServerRequestService} from "../../../service/server-request.service";
import {Router} from "@angular/router";
import {UtilitiesService} from "../../../service/utilities.service";
import {SelectionStateService} from "../../../service/selection-state.service";
import {DisableResponseButtonStateService} from "../../../service/disable-response-button-state.service";
import {FormlyFormOptions} from "@ngx-formly/core";
import {FormGroup} from "@angular/forms";
import {UiBuilderService} from "../../../service/ui-builder.service";
import * as moment from "moment";
import * as $ from 'jquery';
import 'datatables.net-select';
import {DataTableDirective} from "angular-datatables";
import {
  ComponentEventType
} from "../../../dataTablesTemplates/machine-occupation-action-buttons/machine-occupation-action-buttons.component";
import {OrderOverviewSubTableComponent} from "../order-overview-sub-table/order-overview-sub-table.component";
import {Machine} from "../../../interfaces/machine";
import {ComponentType} from "@angular/cdk/overlay";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {SplitOrderModalComponent} from "../../modals/split-order-modal/split-order-modal.component";
import {CollectionOrderModalComponent} from "../../modals/collection-order-modal/collection-order-modal.component";
import {BarcodeInputModalComponent} from "../../modals/barcode-input-modal/barcode-input-modal.component";
import {UnitTranslationPipe} from "../../../pipes/unit-translation.pipe";
import {StyleService} from "../../../service/style.service";
import {CamundaMachineOccupationDTO} from "../../../interfaces/camunda-machine-occupation-dto";
import {NgxSpinnerService} from "ngx-spinner";
import {OverheadCostModal} from "../../modals/overhead-cost-modal/overhead-cost-modal";

@Component({
  selector: 'app-order-overview-data-table',
  templateUrl: './order-overview-data-table.component.html',
  styleUrls: ['./order-overview-data-table.component.scss']
})
export class OrderOverviewDataTableComponent implements OnInit, AfterViewInit, OnChanges {

  dtOptions: any = {};
  model: any;
  isHideOpenMachineOccupations: boolean = false;
  isLoadAllStates: boolean;
  loadStateTextArr: string[] = ["Jeden Status laden", "Wichtigen Status laden"];
  loadStateText: string;
  public tableLoaded: boolean = false;
  expandedRow: {row: any, data: any} | undefined = undefined
  @Input()
  machineOccupationsFilterByMachineType: CamundaMachineOccupation[] | null | undefined;
  @Input()
  machineOccupationsDTO: CamundaMachineOccupationDTO[] | null | undefined;

  @Input()
  machines: Machine[] | undefined | null;

  @Input()
  overViewName: string = 'Alle Aufträge'
  @Input()
  overViewIcon: string = 'list'
  @Input()
  timeSelection: string = 'week';


  cMachineOccupations: CamundaMachineOccupation[] = [];
  cMachineOccupationsFilterByCollectionOrder: CamundaMachineOccupation[] = [];
  isCollectionOrderMode = false;

  @ViewChild(DataTableDirective, {static: false})
  dtElement: DataTableDirective | undefined;
  dtTrigger: Subject<any> = new Subject();
  @ViewChild('checkbox') checkbox!: TemplateRef<{ data: any }>;
  @ViewChild('actionButtons') actionButtons!: TemplateRef<{ data: any }>;
  @ViewChild('detailButton') detailButton!: TemplateRef<{ data: any }>;
  private childRow: ComponentRef<OrderOverviewSubTableComponent> | undefined;
  disableCollectionOrderButton: boolean = false;
  isLoadOnBarcode: boolean = false;

  constructor(private dialog: MatDialog, private viewRef: ViewContainerRef, private compFactory: ComponentFactoryResolver, private renderer: Renderer2, private serverRequestService: ServerRequestService, private router: Router, private cd: ChangeDetectorRef, private utilities: UtilitiesService, private selectionStateService: SelectionStateService, private disableResponseButtonStateService: DisableResponseButtonStateService,
              private unitTranslationPipe: UnitTranslationPipe, private spinner: NgxSpinnerService) {
    this.isLoadAllStates = false; //localStorage.getItem("loadAllStates") == 'true'
    if(this.isLoadAllStates)
    {
      this.loadStateText = this.loadStateTextArr[1]
    }
    else {
      this.loadStateText  = this.loadStateTextArr[0]
    }

    this.selectionStateService.getSelectedMachineTypes().subscribe(value => {
      if(value == undefined)
      {
        this.disableCollectionOrderButton = true
      }

    })

    jQuery.extend(jQuery.fn.dataTable.ext.oSort, {
      "status-pre": (a: any) => {
        switch (a) {
          case 'Läuft':
            return 0;
          case 'Pausiert':
            return 1;
          case 'Bereit':
            return 2;
          case 'Unterbrochen':
            return 3;
          case 'Geplant':
            return 4;
          case 'Abgebrochen':
            return 5;
          case 'Abgeschlossen':
            return 6;
          case 'Archiviert':
            return 7;
          default:
            return -1;
        }
      },
      "status-asc": function (a: any, b: any) {
        return a - b;
      },
      "status-desc": function (a: any, b: any) {
        return b - a;
      },
      "datetime-pre": (a: any) => {
        if(a === "" || a === "-")
        {
          return Number.NEGATIVE_INFINITY
        }
        return  moment(a, 'DD.MM.YYYY HH:mm').unix()
        }
      ,
      "datetime-asc": function (a: any, b: any) {
        return a.valueof() - b.valueOf();
      },
      "datetime-desc": function (a: any, b: any) {
        return b.valueof() - a.valueOf();
      },
      "name-pre": (a: any) => {
        return  a
      }
      ,
      "name-asc": function (a: any, b: any) {
        return a.valueof() - b.valueOf();
      },
      "name-desc": function (a: any, b: any) {
        return b.valueof() - a.valueOf();
      }
    });

    this.spinner.show();
  }

  rerender(): void {
    if (this.dtElement != undefined) {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {

        //console.log("rerender")
        //console.log(dtInstance)
        // Destroy the table first
        dtInstance.clear();

        //dtInstance.columns.adjust().draw();

        //this.dtOptions.data = this.model?.camundaMachineOccupations;
        // Call the dtTrigger to rerender again
        //this.dtTrigger.next(this.dtOptions);
        dtInstance.rows.add(this.model.camundaMachineOccupations)
        dtInstance.order([[9, 'asc'], [1, 'asc']])
        dtInstance.draw(false);
        /*dtInstance.page(page).draw('page')*/
      });
    }
  }

  startReaderModal(): void {
    this.openModal(BarcodeInputModalComponent, result => {
      console.log(result)
      this.isLoadOnBarcode = result;
    }, {
      data: {
        camundaMachinOccupations: this.cMachineOccupations,
        machines: this.machines
      }
    });

    if (this.dtElement != undefined) {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {

      });
    }
  }

  hideOpenMachineOccupations(): void{
    if(!this.isHideOpenMachineOccupations) {
      this.model.camundaMachineOccupations = this.model.camundaMachineOccupations.filter((value: CamundaMachineOccupation) => value.machineOccupation.status != "READY_TO_START");
      this.rerender();
      this.isHideOpenMachineOccupations = true;
      $(".hide-occupations").html('Offene Aufträge einblenden');
    }
    else
    {
      this.isHideOpenMachineOccupations = false;
      this.ngOnChanges();
      $(".hide-occupations").html('Offene Aufträge ausblenden');
    }
  }

  loadAllStates(): void{
    this.isLoadAllStates = !this.isLoadAllStates
    if(this.isLoadAllStates) {
      $(".load-all-states").html(this.loadStateTextArr[1]);
    }
    else
    {
      $(".load-all-states").html(this.loadStateTextArr[0]);
    }
    //localStorage.setItem('loadAllStates', String(this.isLoadAllStates));
    this.serverRequestService.requestNewData(undefined, undefined, true)
  }

  enableCollectionOrderModeModal(): void {
    this.serverRequestService.loadAllMachineOccupations()
      this.openModal(CollectionOrderModalComponent, result => {
        //console.log(result)
      }, {
        data: {
          parentMachineOccupation: undefined,
          machineOccupations: undefined
        }
      });

    if (this.dtElement != undefined) {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {

      });
    }
  }

  enableCollectionOrderMode(): void {
//TODO: Filter nur für Augträge, die ready to start sind
    if (this.dtElement != undefined) {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
        if (dtInstance.select.style() == 'multi') {
          this.isCollectionOrderMode = false;
          dtInstance.rows({selected: true}).each((value) => {
              let machineOccupations = dtInstance.rows(value).data().toArray()
              if (machineOccupations.length > 0) {
                this.serverRequestService.createCollectionOrder(machineOccupations)
              }
            }
          )
          dtInstance.rows().deselect()
          dtInstance.select.style('api')
          this.model.camundaMachineOccupations = this.cMachineOccupations
          this.rerender()
        } else if (dtInstance.select.style() == 'api') {
          dtInstance.select.style('multi')
          this.isCollectionOrderMode = true;
          this.model.camundaMachineOccupations = this.cMachineOccupationsFilterByCollectionOrder
          this.rerender()
        }
        console.log(this.model.camundaMachineOccupations)


      });
    }
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

  enableSplitOrderMode(): void {
    this.openModal(SplitOrderModalComponent, result => {
      console.log(result)
    }, {data: {parentMachineOccupation: undefined ,machineOccupations: this.cMachineOccupationsFilterByCollectionOrder}});

    if (this.dtElement != undefined) {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {

      });
    }
  }

  ngOnInit() {
  }

  ngOnChanges() {
    this.cMachineOccupations = [];
    this.machineOccupationsFilterByMachineType?.filter(occupation => !occupation.subMachineOccupation).forEach(occupation => {
      if (occupation.camundaSubProductionSteps && Array.isArray(occupation.camundaSubProductionSteps) && occupation.camundaSubProductionSteps.length) {
        //Remove entries of previous productionsteps from disableResponseButtonStateService
        occupation.camundaSubProductionSteps?.slice(0, -1).forEach(
          (subProductionStep) => {
            this.disableResponseButtonStateService.enableButton(subProductionStep.taskId);
          }
        )
        this.cMachineOccupations = [...this.cMachineOccupations, {
          ...occupation,
          camundaSubProductionSteps: occupation.camundaSubProductionSteps,
          currentSubProductionStep: occupation.camundaSubProductionSteps?.slice(-1)[0],
          lastSubProductionStep: occupation.camundaSubProductionSteps?.slice(-2)[0]
        }];
      } else {
        this.cMachineOccupations = [...this.cMachineOccupations, occupation];
      }
    });
    //console.log(this.cMachineOccupations)
    this.cMachineOccupationsFilterByCollectionOrder = this.cMachineOccupations.filter((value: CamundaMachineOccupation) => value.machineOccupation.subMachineOccupations == undefined && value.machineOccupation.status == "READY_TO_START" && !value.subMachineOccupation)
    this.model = {camundaMachineOccupations: this.cMachineOccupations};
    this.timeSelectionChange()
    this.rerender();
  }

  ngAfterViewInit() {
    this.dtOptions = {
      data: this.model.camundaMachineOccupations,
      searching: true,
      searchDelay: 400,
     /* search: {
        return: true
      },*/
      //deferRender: true,
      stateSave: true,
      columns: [
        {
          className: 'dt-control',
          orderable: false,
          data: '',
          defaultContent: '',
          ngTemplateRef: {
            ref: this.checkbox,
          }
        },
        {
          title: 'BA-Nr.',
          className: 'hc-ba-nr',
          defaultContent: '',
          data: 'machineOccupation',
          render: function (data: any, type: any) {
            if (type === 'display')
            {
              if (data.productionOrder !== null) {
                return data.productionOrder?.name + " - " + data.name;
              } else {
                return data.name
              }
            }
            if (type === 'filter')
            {
              if (data.productionOrder !== null) {
                //Allow Search to find entry when written with and without "-"
                return data.productionOrder?.name + data.name + data.productionOrder?.name + "-" + data.name;
              } else {
                let value = "";
                if(data.subMachineOccupations.length > 0)
                {
                  for(let subMO of data.subMachineOccupations)
                  {
                    value += subMO.externalId + subMO.productionOrder?.name + "-" + subMO.name + subMO.productionOrder.product.name
                  }
                }
                //console.log(data)
                return data.name + value
              }
            }
            if (type === 'sort')
            {
              if (data.productionOrder !== null) {
                return data.productionOrder?.name + "-" + data.name;
              } else {
                return data.name
              }
            }
          },
          ngTemplateRef: {
            ref: this.detailButton,
          }
        }, /*{
          title: 'Art',
          className: 'hc-art',
          data: 'machineOccupation.productionOrder.orderType'
        },
        {
          title: 'Verwendung',
          className: 'hc-verwendung',
          data: 'machineOccupation.productionOrder.customerOrder.name'
        },*/ {
          title: 'Arbeitsgang',
          className: 'hc-arbeitsgang',
          data: 'activeProductionStep.name'
        }, {
          title: 'Artikel',
          className: 'hc-artikel',
          data: 'machineOccupation.productionOrder.product',
          render: function (data: any) {
            if (data !== undefined && data?.elemname != undefined) {
              return "<span>" + data?.name + "</span>" + "<br>" + "<span class='sub-info' >" + data?.elemname + "</span>";
            }
            else if (data !== undefined)
            {
              return "<span>" + data?.name + "</span>";
            }
            else
            {
              return ""
            }
          }
        }, {
          title: 'Menge',
          className: 'hc-menge',
          data: 'machineOccupation',
          render: (data: any) => {
            if (data?.productionOrder?.measurement !== undefined) {
              return "<span>" + data?.productionOrder?.measurement?.amount + " " + this.unitTranslationPipe.transform(data?.productionOrder?.measurement?.unitString) + "</span>" + "<br>" + "<span class='sub-info' >" + "Offen: " + (data?.productionOrder?.measurement?.amount - data?.totalProductionNumbers?.acceptedMeasurement.amount) + "</span>";
            } else {
              return ""
            }
          }
        }, {
          title: 'Maschine',
          className: 'hc-maschine',
          data: 'machineOccupation.machine',
          render: function (data: any) {
            if (data !== undefined && data?.externalId !== undefined && data?.name !== undefined) {
              return "<span>" + data?.externalId + "</span>" + "<br>" + "<span class='sub-info'>" + data?.name + "</span>";
            }
            else
            {
              return ""
            }
          }
        }, {
          title: 'Start-Termin',
          className: 'hc-start-termin',
          data: 'machineOccupation.plannedStartDateTime',
          render: (data: any) => {
            return this.formatDate(data);
          }
        }, {
          title: 'End-Termin',
          className: 'hc-end-termin',
          data: 'machineOccupation.plannedEndDateTime',
          render: (data: any) => {
            return this.formatDate(data);
          }
        }, {
          title: 'Letzte Buchung',
          className: 'hc-letzte-buchung',
          data: 'lastSubProductionStep.subProductionStep.timeRecords',
          render: (data: any) => {
            //Get the last timerecord and if the user name is not null print it otherwise just print the last timerecord
            return this.formatLastTask(data);
          }
        }, {
          title: 'Status',
          className: 'hc-status',
          data: 'machineOccupation.status',
          render: (data: any, type: any) => {
            return this.renderStateIcons(data, type);
          }
        },{
          title: 'Letzte Notiz',
          className: 'hc-letzte-notiz',
          data: 'machineOccupation.productionOrder.notes',
          render:(data: any, type: any) => {
            let entry = data?.slice(-1)[0]
            if(entry != undefined)
            {
              return entry?.noteString
            }
            else
            {
              return undefined
            }
          }
        },
        {
          title: 'Aktionen',
          className: 'hc-aktionen',
          data: null,
          defaultContent: '',
          render: (data: any, type: any) => {
            return data.camundaSubProductionSteps?.slice(-1)[0]?.name
          },
          ngTemplateRef: {
            ref: this.actionButtons,
          }
        }
      ],
      language: {
        url: './assets/datatables/datatables_de-DE.json'
      },
      dom: '<<"#tableSearch"f>><tr><"table-footer-outer"<"table-footer-inner"l><"table-footer-inner"i><"table-footer-inner"p>>',
      select: {
        style: 'api'
      },
      buttons: {
        dom: {},
        buttons: [
          //'colvis',
          /*{
            extend: 'searchPanes',
            config: {
              cascadePanes: true,
              orderable: false
            }
          },*/
          /*{
            text: 'Sammelauftrag anmelden',
            action: (data: any) => {
              this.enableCollectionOrderMode()
            }
          },*/
          /*{
            text: 'Offene Aufträge ausblenden',
            className: 'hide-occupations',
            action: (data: any) => {
              this.hideOpenMachineOccupations()
            }
          },
          {
            text: this.loadStateText,
            className: 'load-all-states',
            action: (data: any) => {
              this.loadAllStates()
            }
          },
          {
            text: 'Sammelauftrag anmelden',
            className: 'create-collection-order',
            enabled: !this.disableCollectionOrderButton,
            action: (data: any) => {
              this.enableCollectionOrderModeModal()
            }
          },
          {
            text: 'Teilauftrag anmelden',
            enabled: !this.disableCollectionOrderButton,
            action: (data: any) => {
              this.enableSplitOrderMode()
            }
          },*/
          /*{
            text: 'Gemeinkosten',
            action: (data: any) => {
              this.openModal(OverheadCostModal, result => {
                console.log(result)
              }, {});
            }
          },*/
          /*{
            text: 'Barcode / RF-ID lesen',
            action: (data: any) => {
              this.startReaderModal()
            }
          }*/
        ]
      },
      columnDefs: [
        {
          searchPanes: {
            show: true
          },
          targets: [1, 2, 3, 4, 5,6,7,8,10,11]
        },
        {
          targets: [9],
          type: 'status',
          className:"status"
        },
        {
          targets: [6,7,8],
          type: 'datetime',
          searchPanes: {
            show: true
          }
        },
        {
          targets: [1],
          type: 'name',
          className:"name-field"
        },
        {
          targets: [2],
          className:"type"
        },
        {
          targets: [3],
          className:"truncate-small"
        },
        {
          targets: [2],
          className:"truncate"
        },
        {
          targets: [4],
          className:"amount"
        },
        {
          targets: [5],
          className:"truncate-small"
        },
        {
          targets: [10],
          className:"truncate-small"
        },
        {
          targets: [11],
          className:"action-btns"
        },
      ],
      order: [[9, 'asc'], [1, 'asc']],
      rowCallback: (row: Node, data: CamundaMachineOccupation, index: number) => {
        const self = this;
        // Unbind first in order to avoid any duplicate handler
        // (see https://github.com/l-lin/angular-datatables/issues/87)
        // Note: In newer jQuery v3 versions, `unbind` and `bind` are
        // deprecated in favor of `off` and `on`
        $('td', row).off('click');
        $('td', row).on('click', () => {
          //Only open rows, if selection is disabled
          //console.log(data)
          if(data.machineOccupation.status != "READY_TO_START")
          {
            if (!this.isCollectionOrderMode) {
              this.expandRow(row, data);
            }
          }
        });
        return row;
      },
      rowGroup: {
        emptyDataGroup: null,
        startRender: function (rows: { count: () => string; }, group: string) {

          let string = '';
          switch (group) {
            case 'PLANNED':
              string = 'Geplante Aufträge'
              break;
            case 'READY_TO_START':
              string = 'Offene Aufträge'
              break;
            case 'RUNNING':
              string = 'Laufende Aufträge'
              break;
            case 'SUSPENDED':
              string = 'Unterbrochene Aufträge'
              break;
            case 'PAUSED':
              string = 'Pausierte Aufträge'
              break;
            case 'FINISHED':
              string = 'Abgeschlossene Aufträge'
              break;
            case 'ABORTED':
              string = 'Abgebrochene Aufträge'
              break;
            case 'ARCHIVED':
              string = 'Archivierte Aufträge'
              break;
            case 'SKIPPED':
              string = 'Übersprungene Aufträge'
              break;
          }
          if(string !== '')
          {
            return string + ' (' + rows.count() + ')';
          }
          else
          {
            return null;
          }
        },
        endRender: null,
        dataSrc: 'machineOccupation.status'
      },
      autoWidth: false
    };

    this.dtTrigger.next(this.dtOptions);

    if (this.dtElement != undefined) {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
        dtInstance
          .on('select', function (e, dt, type, indexes) {
            var rowData = dtInstance.rows(indexes).data().toArray();
            rowData.forEach((data: any) => {
              data['selected'] = true
            })
          })
        dtInstance
          .on('deselect', function (e, dt, type, indexes) {
            var rowData = dtInstance.rows(indexes).data().toArray();
            rowData.forEach((data: any) => {
              data['selected'] = false
            })
          })
        dtInstance
          .on('init.dt', (e, dt, type, indexes) => {
              this.tableLoaded = true;
              console.log('Table initialisation complete: ' + new Date().getTime());
            })
      })
    }
  }

  expandRow(trRef: any, rowData: any) {
    console.log(trRef, rowData);
    if (this.dtElement != undefined) {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
        let row = dtInstance.row(trRef);
        if (row.child.isShown()) {
          row.child.hide();
          this.renderer.removeClass(trRef, 'shown');
          this.expandedRow = undefined
        } else {
          let factory = this.compFactory.resolveComponentFactory(OrderOverviewSubTableComponent);
          this.childRow = this.viewRef.createComponent(factory);
          this.childRow.instance.data = [rowData];
          // this.childRow
          row.child(this.childRow.location.nativeElement).show();
          this.renderer.addClass(trRef, 'shown');
          console.log(trRef.nextElementSibling.firstElementChild)
          //Find the sub table of the element and add a class for overwriting padding
          this.renderer.addClass(trRef.nextElementSibling.firstElementChild, "no-padding")
          this.expandedRow = {row: trRef, data: rowData}
        }
      });
    }
  }

  formatLastTask(data: any) {
    //data.slice(-1)[0]?.user != null ? this.formatDateTask(data?.slice(-1)[0]?.endDateTime) + " - " + data?.slice(-1)[0]?.user : this.formatDateTask(data?.slice(-1)[0]?.endDateTime)
    let user = data?.slice(-1)[0]?.responseUser.userName
    if(user == null)
    {
      user = "";
    }
    else {
      user = "<br><span class='sub-info'>" + user + "</span>"  ;
    }
    if (data?.slice(-1)[0] == undefined) {
      return "Unbearbeitet"
    }
    else if(data?.slice(-1)[0] != undefined && data?.slice(-1)[0].endDateTime == undefined)
    {
      return this.formatDateTime(data?.slice(-1)[0].startDateTime) + user
    }
    else {
      return this.formatDateTime(data?.slice(-1)[0].endDateTime) + user
    }
  }

  formatDateTime(data: any)
  {
    if (data == undefined) {
      return "-"
    }
    const date = moment(data, 'YYYY-MM-DD HH:mm')
    return date.format('DD.MM.YY HH:mm');
  }

  formatDate(data: any) {
    if (data == undefined) {
      return "-"
    }
    const date = moment(data, 'YYYY-MM-DD HH:mm')
    return date.format('DD.MM.YY');
  }

  private renderStateIcons(data: any, type: any) {
    let fieldName = ''
    let imageSrc = ''

    switch (data) {
      case 'PLANNED':
        fieldName = 'Geplant'
        imageSrc = 'assets/img/planned.svg'
        break;
      case 'READY_TO_START':
        fieldName = 'Bereit'
        imageSrc = 'assets/img/ready_to_start.svg'
        break;
      case 'RUNNING':
        fieldName = 'Läuft'
        imageSrc = 'assets/img/running.svg'
        break;
      case 'SUSPENDED':
        fieldName = 'Unterbrochen'
        imageSrc = 'assets/img/suspended.svg'
        break;
      case 'PAUSED':
        fieldName = 'Pausiert'
        imageSrc = 'assets/img/pause.svg'
        break;
      case 'FINISHED':
        fieldName = 'Abgeschlossen'
        imageSrc = 'assets/img/finished.svg'
        break;
      case 'ABORTED':
        fieldName = 'Abgebrochen'
        imageSrc = ''
        break;
      case 'ARCHIVED':
        fieldName = 'Archiviert'
        imageSrc = 'assets/img/archived.svg'
        break;
      case 'SKIPPED':
        fieldName = 'Übersprungen'
        imageSrc = ''
        break;
    }

    if (type === 'display') {
      if(imageSrc !== '')
      {
        return `<img alt="Dashboard" class="datatable-status-img"  src="${imageSrc}"  style="display: block;"/> ${fieldName}`
        //return `<img alt="Dashboard" height="32" src="${imageSrc}" width="32" style="margin-right: 10px; margin-left: 2px"/>`
      }
      else
      {
        return fieldName
      }
    } else {
      return fieldName
    }
  }

  private addDetails(data: any, type: any) {
    if (type === 'display') {
      return '<div style="display: flex;"><p style="display: flex;">' + data + '</p><button _ngcontent-cmd-c90="" routerlink="order-details" style="display: flex;" color="accent" mat-stroked-button="" class="mat-focus-indicator action-icon-btn mat-stroked-button mat-button-base mat-accent" ng-reflect-color="accent" tabindex="0" ng-reflect-router-link="order-details"><span class="mat-button-wrapper"><mat-icon _ngcontent-cmd-c90="" role="img" class="mat-icon notranslate material-icons mat-icon-no-color" aria-hidden="true" data-mat-icon-type="font">format_list_numbered</mat-icon></span><span matripple="" class="mat-ripple mat-button-ripple" ng-reflect-disabled="false" ng-reflect-centered="false" ng-reflect-trigger="[object HTMLButtonElement]"></span><span class="mat-button-focus-overlay"></span></button></div>'
    }
    return data
  }

  timeSelectionChange() {
    let time = 7
    switch (this.timeSelection) {
      case "week": {
        time = 7
        break;
      }
      case "month": {
        time = 31
        break;
      }
      case "all": {
        time = -1
        break;
      }
    }
    //console.log(this.timeSelection)
    //console.log(this.cMachineOccupations)

    if(time < 0)
    {
      this.model = {camundaMachineOccupations: this.cMachineOccupations};
      //this.rerender();
      return
    }

    let filteredByTimeSelection = this.cMachineOccupations.filter((value: any) =>
    {
      //Always show all that are ready to start
      if(value.machineOccupation.status == "READY_TO_START")
      {
        return true;
        //return (moment(value.machineOccupation.plannedStartDateTime).diff(moment(), "days"))*-1 < time*2
      }
      //Always show all that are ready to start
      else if(value.machineOccupation.status == "PAUSED" || value.machineOccupation.status == "RUNNING" || value.machineOccupation.status == "FINISHED")
      {
        return (moment(value?.lastSubProductionStep?.subProductionStep?.timeRecords.slice(-1)[0]?.startDateTime).diff(moment(), "days"))*-1 < time ||
          (value?.lastSubProductionStep?.subProductionStep?.timeRecords.slice(-1)[0]?.endDateTime != undefined && (moment(value?.lastSubProductionStep?.subProductionStep?.timeRecords.slice(-1)[0]?.endDateTime).diff(moment(), "days"))*-1 < time)
      }
      else
      {
        return false;
      }
      /*else if(value.machineOccupation.actualStartDateTime == undefined && value.camundaSubProductionSteps.length > 0)
      {
        return (moment(value?.lastSubProductionStep?.subProductionStep?.timeRecords.slice(-1)[0]?.startDateTime).diff(moment(), "days"))*-1 < time
      }
      else
      {
        return (moment(value?.lastSubProductionStep?.subProductionStep?.timeRecords.slice(-1)[0]?.startDateTime).diff(moment(), "days"))*-1 < time
            || (moment(value.machineOccupation.actualStartDateTime).diff(moment(), "days"))*-1 < time
      }*/
    })

    this.model = {camundaMachineOccupations: filteredByTimeSelection};
    this.rerender();
  }
}



