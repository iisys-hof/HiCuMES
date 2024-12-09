import {AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges, TemplateRef, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {UiBuilderService} from "../../service/ui-builder.service";
import {ServerRequestService} from "../../service/server-request.service";
import * as _ from "lodash";
import {get} from "lodash";
import {Observable} from "rxjs";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {map, shareReplay} from 'rxjs/operators';
import {BookingEntry} from "../../interfaces/bookingEntry";
import * as XLSX from 'xlsx';
import * as moment from 'moment';
import {now} from 'moment';
import 'moment-duration-format';
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  selector: 'app-user-booking-overview-table',
  templateUrl: './user-booking-overview-table.component.html',
  styleUrls: ['./user-booking-overview-table.component.scss']
})
export class UserBookingOverviewTableComponent implements OnInit, AfterViewInit, OnChanges {

  model: any;
  modelOverheadCosts: any;
  tableLayout: any;
  tableLayoutOverheadCosts: any;
  options: FormlyFormOptions = {};
  tableFormBooking = new FormGroup({});
  tableFormOverheadCosts = new FormGroup({});
  selected = 'year';
  range: FormGroup;

  isTouch$: Observable<boolean> = this.breakpointObserver.observe([Breakpoints.Tablet, Breakpoints.Handset])
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  sumBookings = 0;
  sumBookingsString: String = ""
  sumOverheadCosts = 0;
  sumOverheadCostsString = ""

  start: string = "";
  end: string = "";
  filterError: boolean = false;

  @Input()
  overViewName: string = 'Meine Buchungen'
  @Input()
  bookingEntries: any


  @ViewChild('simpleModal') simpleModal!: TemplateRef<{ data: any }>;

  constructor( private breakpointObserver: BreakpointObserver,private serverRequestService: ServerRequestService, private uiBuilder: UiBuilderService, private spinner: NgxSpinnerService) {
    this.uiBuilder.getDynamicFormLayout("bookingOverviewUser").subscribe((tableLayout) => {
      this.tableLayout = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("bookingOverviewOverheadCostsUser").subscribe((tableLayout) => {
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
    this.serverRequestService.loadAllBookingEntriesUser()
    this.serverRequestService.loadAllBookingEntriesOverheadCostsUser()
    this.serverRequestService.getAllBookingEntriesUser().subscribe((data: any) => {
      //console.log(data)
      let newData: BookingEntry[] = [];
      let dataCopy = _.cloneDeep(data)
      this.sumBookings = 0
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
              this.sumBookings += newEntry.machineOccupation.subProductionStep.timeDurations.work
            }
            else
            {
              newEntry.machineOccupation["subProductionStep"]["duration"] = moment.duration(newEntry.machineOccupation.timeDurations.work, "seconds").locale("de").format("h [h] m [min]", {
                usePlural: false
              })
              this.sumBookings += newEntry.machineOccupation.timeDurations.work
            }
            newData.push(newEntry);
            //console.log(newEntry)
      });
      this.sumBookingsString = moment.duration(this.sumBookings, "seconds").locale("de").format("h [h] m [min]", {
        usePlural: false
      })
      //console.log(newData)

      newData = newData.sort((a :any,b: any) => {
        return a.subProductionStep.timeRecords.slice(-1)[0].endDateTime.localeCompare(b.machineOccupation.subProductionStep.timeRecords.slice(-1)[0].endDateTime)
      })

      this.model = {bookingEntries: newData}
      console.log(this.model)
      //console.log(this.model);
    })
    this.serverRequestService.getAllBookingEntriesOverheadCostsUser().subscribe((data: any) => {
      //console.log(data)
      let newData: BookingEntry[] = [];
      let dataCopy = _.cloneDeep(data)
      this.sumOverheadCosts = 0;
      dataCopy.forEach((bookingEntry: any) => {
        //console.log(bookingEntry)
          const newEntry = _.cloneDeep(bookingEntry); // Create a deep copy of the original item
          newEntry.overheadCost["duration"] = moment.duration(newEntry.overheadCost?.timeDuration, "seconds").locale("de").format("h [h] m [min]", {
            usePlural: false
          })
          newData.push(newEntry);
          this.sumOverheadCosts += newEntry.overheadCost?.timeDuration
          //console.log(newEntry)
      });

      this.sumOverheadCostsString = moment.duration(this.sumOverheadCosts, "seconds").locale("de").format("h [h] m [min]", {
        usePlural: false
      })
      newData = newData.sort((a :any,b: any) => {
        return a.overheadCost.timeRecord?.endDateTime.localeCompare(b.overheadCost.timeRecord?.endDateTime)
      })

      this.modelOverheadCosts = {bookingEntries: newData}
      //console.log(this.model);
    })


  }

  onClose() {
    if(this.range.value.start.valueOf() && this.range.value.end.valueOf()) {
      this.model = undefined
      this.modelOverheadCosts = undefined
      //console.log(moment(this.range.value.start.valueOf()).format("yyyy-MM-DD"), moment(this.range.value.end.valueOf()).format("yyyy-MM-DD"))
      this.start = moment(this.range.value.start.valueOf()).format("yyyy-MM-DD")
      this.end = moment(this.range.value.end.valueOf()).format("yyyy-MM-DD")
      this.serverRequestService.setBookingUserStart(this.start)
      this.serverRequestService.setBookingUserEnd(this.end)
      this.serverRequestService.loadAllBookingEntriesUser(this.start, this.end)
      this.serverRequestService.loadAllBookingEntriesOverheadCostsUser(this.start, this.end)
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    //console.log(changes)
  }


  ngAfterViewInit(): void {
  }

}
