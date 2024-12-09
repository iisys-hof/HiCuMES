import {
  AfterViewInit,
  Component,
  Input,
  OnChanges,
  OnInit, SimpleChanges,
  TemplateRef,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import {OverheadCostModal} from "../modals/overhead-cost-modal/overhead-cost-modal";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import * as $ from "jquery";
import {Subject} from "rxjs";
import {OverheadCostCenters} from "../../interfaces/overhead-cost-centers";
import {OverheadCosts} from "../../interfaces/overhead-costs";
import {User} from "../../interfaces/user";
import * as _ from "lodash";
import * as moment from "moment";
import {DataTableDirective} from "angular-datatables";

@Component({
  selector: 'app-overhead-costs',
  templateUrl: './overhead-costs.component.html',
  styleUrls: ['./overhead-costs.component.scss']
})
export class OverheadCostsComponent implements AfterViewInit, OnChanges {

  @Input()
  overViewName: string = 'Alle Aufträge'

  @Input()
  overHeadCostCenters: any
  @Input()
  topOverheadCostCenters: any
  @Input()
  overHeadCosts: any

  overHeadCostsList: any

  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject();
  @ViewChild(DataTableDirective, {static: false})
  dtElement: DataTableDirective | undefined;

  @ViewChild('actionButtons') actionButtons!: TemplateRef<{ data: any }>;
  @ViewChild('simpleModal') simpleModal!: TemplateRef<{ data: any }>;

  constructor(private viewRef: ViewContainerRef) {

  }

  formatDate(data: any) {
    if (data == undefined) {
      return "-"
    }
    const date = moment(data, 'YYYY-MM-DD HH:mm')
    return date.format('DD.MM.YY HH:mm');
  }

  rerender(): void {
    if (this.dtElement != undefined) {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
        console.log("rerender")
        console.log(dtInstance)
        // Destroy the table first
        dtInstance.clear();

        dtInstance.columns.adjust().draw();

        this.dtOptions.data = this.overHeadCostsList;
        // Call the dtTrigger to rerender again
        this.dtTrigger.next(this.dtOptions);
        dtInstance.order( [[5, 'desc'], [2, 'asc'], [0, 'asc']]).draw();
        dtInstance.rows.add(this.overHeadCostsList).draw(false);
      });
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log(changes)
    console.log(this.overHeadCosts)
    console.log(this.overHeadCostsList)

    this.overHeadCostsList = _.cloneDeep(this.overHeadCosts)
    this.overHeadCostsList.map((overheadCost: any) =>
    {
      if(overheadCost.timeRecord.endDateTime)
      {
        overheadCost["type"] = "2_finished"
      }
    })
    this.overHeadCostCenters.map((costCenter:OverheadCostCenters)  =>
    {
      //if(_.some(this.overHeadCosts, entry => !_.isEqual(entry.overheadCostCenter, costCenter)))
      //{
        this.overHeadCostsList.push(
          {
            overheadCostCenter: costCenter,
            type: "1_all"
          }
        )
      //}
    })
    this.topOverheadCostCenters.map((costCenter:OverheadCostCenters)  =>
    {
      //if(_.some(this.overHeadCosts, entry => !_.isEqual(entry.overheadCostCenter, costCenter)))
      //{
      this.overHeadCostsList.push(
          {
            overheadCostCenter: costCenter,
            type: "0_top"
          }
      )
      //}
    })
    this.rerender()
  }

  ngAfterViewInit(): void {
    console.log(this.overHeadCostsList)
    this.dtOptions = {
      data: this.overHeadCostsList,
      searching: true,
      searchDelay: 400,
      stateSave: true,
      pageLength: 100,
      columns: [
        {
          className: 'dt-control',
          data: 'type',
          orderable: false,
          defaultContent: '',
          render: function (data: any, type: any) {
            if (type === 'display') {
              return null
            }
            else {
              return data
            }
          }
        },
      {
        title: 'BA-Nr.',
        className: 'hc-ba-nr',
        data: 'overheadCostCenter.orderNumber'
      }, {
        title: 'AG',
        className: 'hc-arbeitsgang',
        data: 'overheadCostCenter.externalId'
      }, {
        title: 'MGR',
        className: 'hc-arbeitsgang',
        data: 'overheadCostCenter.mgr'
      }, {
        title: 'Bezeichnung',
        className: 'hc-arbeitsgang',
        data: 'overheadCostCenter.name'
      }, {
        title: 'Text',
        className: 'hc-arbeitsgang',
        data: 'overheadCostCenter.text',
        ngTemplateRef: {
          ref: this.simpleModal,

        }
      },{
        title: 'Startzeit',
        className: 'hc-start-termin',
        data: 'timeRecord.startDateTime',
        render: (data: any) => {
          return this.formatDate(data);
        }
      }, {
        title: 'Endzeit',
        className: 'hc-end-termin',
        data: 'timeRecord.endDateTime',
        render: (data: any) => {
          return this.formatDate(data);
        }
      }, {
        title: 'Dauer (h)',
        data: 'timeDuration',
        render: (data: any) => {
          if (data == undefined) {
            return "-"
          }
          else
          {
            return  (data/ 3600).toFixed(2);
          }
        }
      },{
        title: 'Bem.',
        data: 'note'
      },
        {
          title: 'Status',
          className: 'hc-status',
          data: 'timeRecord',
          render: (data: any, type: any) => {
            return this.renderStateIcons(data, type);
          }
        },
      {
        title: 'Aktionen',
        className: 'hc-aktionen',
        data: null,
        defaultContent: '',
        ngTemplateRef: {
          ref: this.actionButtons,
        }
      }],
      language: {
        url: './assets/datatables/datatables_de-DE.json'
      },
      dom: '<<"table-buttons"B><f>><tr><"table-footer-outer"<"table-footer-inner"l><"table-footer-inner"i><"table-footer-inner"p>>',
      select: {
        style: 'api'
      },
      buttons: {
        dom: {},
        buttons: [
        ]
      },
      columnDefs: [
        {
          targets: [0],
          className:"hide"
        },
        {
          targets: [1,4,9,10],
          className:"truncate"
        },
        {
          targets: [2,3,6,7,8],
          className:"truncate-small"
        },
        {
          targets: [11],
          className:"action-btns"
        }
      ],
      order: [[6, 'desc'], [3, 'asc'], [1, 'asc']],
      orderFixed: [[0, 'asc'], [6, 'desc'] , [1, 'asc']],
      rowGroup: {
        emptyDataGroup: "Laufende Buchungen",
        startRender: function (rows: { count: () => string; }, group: string) {

          if(group !== '')
          {
            if(group == '0_top')
            {
              group = "Top Gemeinkostenstellen"
            }
            if(group == '1_all')
            {
              group = "Alle Gemeinkostenstellen"
            }
            if(group == '2_finished')
            {
              group = "Abgeschlossene Buchungen"
            }
            return group + ' (' + rows.count() + ')';
          }
          else
          {

            return null;
          }
        },
        endRender: null,
        dataSrc: ['type']
      },
      autoWidth: false
    };

    this.dtTrigger.next(this.dtOptions);
  }

  private renderStateIcons(data: any, type: any) {
    let fieldName = ''
    let imageSrc = ''
    //console.log(data)
    if(data == undefined)
    {
      fieldName = 'Bereit'
      imageSrc = 'assets/img/ready_to_start.svg'
    }
    else if(data.endDateTime == undefined)
    {

      fieldName = 'Läuft'
      imageSrc = 'assets/img/running.svg'
    }
    else if(data.endDateTime != undefined)
    {
      fieldName = 'Abgeschlossen'
      imageSrc = 'assets/img/finished.svg'
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
}
