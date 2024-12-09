import {Component, OnChanges, OnInit} from '@angular/core';
import {ServerRequestService} from "../../service/server-request.service";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {SseService} from "../../service/sse.service";
import {merge, Observable} from "rxjs";
import {Machine} from "../../interfaces/machine";
import {SelectionStateService} from "../../service/selection-state.service";
import {AuthGuard} from "../../service/authguard.service";
import {NgxSpinnerService} from "ngx-spinner";
import {BookingEntry} from "../../interfaces/bookingEntry";

@Component({
  selector: 'app-user-order-overview',
  templateUrl: './user-booking-overview.component.html',
  styleUrls: ['./user-booking-overview.component.scss']
})
export class UserBookingOverviewComponent implements OnInit {


  public bookingEntriesOverheadCosts$: Observable<BookingEntry[]> | undefined;
  public bookingEntriesOrders$: Observable<BookingEntry[]> | undefined;
  public bookingEntriesAll$: Observable<BookingEntry[]> | undefined;

  constructor(private spinner: NgxSpinnerService, private authGuard: AuthGuard, private serverRequestService: ServerRequestService, sseService: SseService, private stateService: SelectionStateService) {

  }

  ngOnInit(): void {
    this.authGuard.isLoggedIn().then(isLoggedIn => {
      if(isLoggedIn) {
        this.serverRequestService.loadAllBookingEntriesUser()
        this.serverRequestService.loadAllBookingEntriesOverheadCostsUser()
        this.bookingEntriesOverheadCosts$ = this.serverRequestService.getAllBookingEntriesOverheadCostsUser()
        this.bookingEntriesOrders$ = this.serverRequestService.getAllBookingEntriesUser()
        this.bookingEntriesAll$ = this.serverRequestService.getAllBookingAndOverheadCostsUser()
        this.spinner.show();
      }
    })
  }

  startMachineOccupation(machineOccupation: CamundaMachineOccupation) {
    this.serverRequestService.startMachineOccupation(machineOccupation);
    /*setTimeout(() => {
      this.reloadEverything()
    }, 2000);*/
  }
}
