import {Component, Inject, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {UiBuilderService} from "../../service/ui-builder.service";
import {ServerRequestService} from "../../service/server-request.service";
import {SelectionStateService} from "../../service/selection-state.service";
import * as _ from "lodash";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import * as moment from 'moment';
import {Router} from "@angular/router";
import {ProductionOrder} from "../../interfaces/production-order";
import {Observable} from "rxjs";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {map, shareReplay} from 'rxjs/operators';
import {BookingEntry} from "../../interfaces/bookingEntry";
import * as XLSX from 'xlsx';
import {now} from "moment";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  selector: 'app-booking-overview',
  templateUrl: './booking-overview.component.html',
  styleUrls: ['./booking-overview.component.scss']
})
export class BookingOverviewComponent implements OnInit, OnChanges {


  model: any;
  modelOverheadCosts: any;
  modelUnfiltered: any;
  tableLayout: any;
  tableLayoutOverheadCosts: any;
  options: FormlyFormOptions = {};
  tableForm = new FormGroup({});
  tableFormOverheadCosts = new FormGroup({});
  selected = 'year';
  range: FormGroup;

  isTouch$: Observable<boolean> = this.breakpointObserver.observe([Breakpoints.Tablet, Breakpoints.Handset])
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  start: string = "";
  end: string = "";
  filterError: boolean = false;

  constructor( private breakpointObserver: BreakpointObserver,private serverRequestService: ServerRequestService, private uiBuilder: UiBuilderService, private spinner: NgxSpinnerService) {
    this.uiBuilder.getDynamicFormLayout("bookingOverview").subscribe((tableLayout) => {
      this.tableLayout = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("bookingOverviewOverheadCosts").subscribe((tableLayout) => {
      this.tableLayoutOverheadCosts = tableLayout;
    });
    this.range = new FormGroup(
      {
        start: new FormControl(),
        end: new FormControl()
      })
    this.model = undefined
    this.modelOverheadCosts = undefined
    this.spinner.show();
  }


  ngOnInit() {
    //console.log("INIT BOOKING")
    this.serverRequestService.loadAllBookingEntries()
    this.serverRequestService.loadAllBookingEntriesOverheadCosts()
    this.serverRequestService.getAllBookingEntries().subscribe((data: any) => {
      this.filterError = false
      //console.log(data)
      let newData: BookingEntry[] = [];
      let dataCopy = _.cloneDeep(data)
      dataCopy.forEach((bookingEntry: any) => {
        //console.log(bookingEntry)
        const newEntry = _.cloneDeep(bookingEntry); // Create a deep copy of the original item
        newEntry.machineOccupation["subProductionStep"] = bookingEntry.subProductionStep; // Replace subProductionSteps with a single entry
        //Automatically set the time dependet of the kind of booking Mapping
        if(bookingEntry.stepTime)
        {
          newEntry.machineOccupation["subProductionStep"]["duration"] =  moment.duration(newEntry.machineOccupation.subProductionStep.timeDurations.work, "seconds").locale("de").format("h [h] m [min]", {
            usePlural: false
          })
        }
        else
        {
          newEntry.machineOccupation["subProductionStep"]["duration"] = moment.duration(newEntry.machineOccupation.timeDurations.work, "seconds").locale("de").format("h [h] m [min]", {
            usePlural: false
          })
        }
        newData.push(newEntry);
        //console.log(newEntry)
      });
      console.log(newData)

      newData = newData.sort((a :any,b: any) => {
        return a.subProductionStep.timeRecords.slice(-1)[0].endDateTime.localeCompare(b.machineOccupation.subProductionStep.timeRecords.slice(-1)[0].endDateTime)
      })

      this.model = {bookingEntries: newData}
      this.modelUnfiltered = _.cloneDeep({bookingEntries: newData})
      //console.log(this.model);
    })
    this.serverRequestService.getAllBookingEntriesOverheadCosts().subscribe((data: any) => {
      //console.log(data)
      let newData: BookingEntry[] = [];
      let dataCopy = _.cloneDeep(data)
      dataCopy.forEach((bookingEntry: any) => {
        //console.log(bookingEntry)
        const newEntry = _.cloneDeep(bookingEntry); // Create a deep copy of the original item
        newEntry.overheadCost["duration"] = moment.duration(newEntry.overheadCost?.timeDuration, "seconds").locale("de").format("h [h] m [min]", {
          usePlural: false
        })
        newData.push(newEntry);
        //console.log(newEntry)
      });

      console.log(newData)
      newData = newData.sort((a :any,b: any) => {
        return a.overheadCost.timeRecord?.endDateTime.localeCompare(b.overheadCost.timeRecord?.endDateTime)
      })
      this.modelOverheadCosts = {bookingEntries: newData}
      //console.log(this.model);
    })

  }

  ngOnChanges(changes: SimpleChanges): void {
    //console.log(changes)
  }

  onClose() {
    if(this.range.value.start.valueOf() && this.range.value.end.valueOf()) {
      this.model = undefined
      this.modelOverheadCosts = undefined
      //console.log(moment(this.range.value.start.valueOf()).format("yyyy-MM-DD"), moment(this.range.value.end.valueOf()).format("yyyy-MM-DD"))
      this.start = moment(this.range.value.start.valueOf()).format("yyyy-MM-DD")
      this.end = moment(this.range.value.end.valueOf()).format("yyyy-MM-DD")
      this.serverRequestService.setBookingStart(this.start)
      this.serverRequestService.setBookingEnd(this.end)
      this.serverRequestService.loadAllBookingEntries(this.start, this.end)
      this.serverRequestService.loadAllBookingEntriesOverheadCosts(this.start, this.end)
    }
  }

  exportexcel(): void
  {
    /* table id is passed over here */
    let element = document.getElementById('datatable');
    const ws: XLSX.WorkSheet =XLSX.utils.table_to_sheet(element, {raw: true});

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    /* save to file */
    XLSX.writeFile(wb, "Buchungsübersicht_" + moment(now()).format("yyyy-MM-DD_HH-mm") + ".xlsx");
  }

  toggleError() {
    this.model = undefined
    let bookingEntries = undefined;
    if(!this.filterError)
    {
      bookingEntries = _.cloneDeep(this.modelUnfiltered)?.bookingEntries?.filter((value: any) => value?.bookingState == "ERROR")
    }
    else
    {
      bookingEntries = _.cloneDeep(this.modelUnfiltered)?.bookingEntries
    }
    this.model = {bookingEntries: bookingEntries}
  }
}
