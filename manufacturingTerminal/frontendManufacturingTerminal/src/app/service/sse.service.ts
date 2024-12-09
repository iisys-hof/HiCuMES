import {Injectable, NgZone} from '@angular/core';
import {fromEvent, Observable, ReplaySubject, Subject, Subscription, throwError, timer} from "rxjs";
import {ServerRequestService} from "./server-request.service";
import {environment} from "../../environments/environment";
import {SelectionStateService} from "./selection-state.service";
import {Machine, MachineType} from "../interfaces/machine";
import {Router} from "@angular/router";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {concatMap, delay, retry, retryWhen, share, switchMap, take, takeUntil} from "rxjs/operators";
import {UserdataService} from "./userdata.service";

@Injectable({
  providedIn: 'root'
})
export class SseService {

  urlEndpoint = '/manufacturingTerminalBackend/data';

  urlSSESession = environment.baseUrl + this.urlEndpoint + '/camunda/startSSESession';
  //urlHeartbeat = environment.baseUrl + this.urlEndpoint + '/camunda/heartbeat';

  serverSentObserver$: ReplaySubject<any>
  eventChangedObserver$: ReplaySubject<any>

  selectedMachineTypes: MachineType[] | undefined = undefined;
  lastEvent: any = undefined
  lastMachineOccupation: any = undefined
  eventSource: EventSource | undefined | null;
  reconnectFrequencySec: number = 1;
  reconnectTimeout: number | undefined;
  SSE_RECONNECT_UPPER_LIMIT = 64;

  onlineEvent: Observable<Event>;
  offlineEvent: Observable<Event>;
  subscriptions: Subscription[] = [];

  timerRef: any = undefined;

  constructor(private ngZone: NgZone, private serverRequestService: ServerRequestService, private stateService: SelectionStateService, private userDataService: UserdataService, private router: Router, private http: HttpClient) {
    this.serverSentObserver$ = new ReplaySubject<any>();
    this.eventChangedObserver$ = new ReplaySubject<any>();
    this.stateService.getSelectedMachineTypes().subscribe(machineTypes => {
      this.selectedMachineTypes = machineTypes;
    })
    this.onlineEvent = fromEvent(window, 'online');
    this.offlineEvent = fromEvent(window, 'offline');

    this.subscriptions.push(this.onlineEvent.subscribe(e => {
      this.openSseChannel();
      console.log('Online...');
    }));

    this.subscriptions.push(this.offlineEvent.subscribe(e => {
      this.closeSseConnection();
      console.log('Offline...');
    }));
  }

 /* public reloadEverything() {
    this.serverRequestService.loadMachines();
    this.serverRequestService.loadMachineTypes();
    this.serverRequestService.loadProductionOrders();
    this.serverRequestService.loadAllMachineOccupations();
  }*/

  public getServerSentObserver ()
  {
    return this.serverSentObserver$.asObservable();
  }

  public getEventChangedObserver ()
  {
    return this.eventChangedObserver$.asObservable();
  }

  // Public function, initializes connection, returns true if successful
  openSseChannel(): boolean {
    this.createSseEventSource();
    return !!this.eventSource;
  }

// Creates SSE event source, handles SSE events
  protected createSseEventSource(): void {
    // Close event source if current instance of SSE service has some
    if (this.eventSource) {
      this.closeSseConnection();
      this.eventSource = null;
    }
    // Open new channel, create new EventSource
    this.eventSource = new EventSource(this.urlSSESession);

    // Process default event
    this.eventSource.onmessage = (event: MessageEvent) => {
      this.ngZone.run(() => this.processSseEvent(event));
    };

    // Add custom events
    /*Object.keys(SSE_EVENTS).forEach(key => {
      this.eventSource.addEventListener(SSE_EVENTS[key], event => {
        this.ngZone.run(() => this.processSseEvent(event));
      });
    });*/

    // Process connection opened
    this.eventSource.onopen = () => {
      this.reconnectFrequencySec = 1;
      //Load new data on sse connection start
      this.userDataService.updateUserdata()
      this.serverRequestService.requestNewData(undefined)
      /*this.timerRef = setInterval(() => {
        console.log("heartbeat")
        this.http.get(this.urlHeartbeat).subscribe(
          () => {},
          (error: HttpErrorResponse) => {
            console.error("Cannot reach HiCuMES Backend")
            //close current connection and reopen it
            this.closeSseConnection();
            this.openSseChannel();
          });
      }, 60000);*/
    };

    // Process error
    this.eventSource.onerror = (error: any) => {
      this.reconnectOnError();
    };
  }

// Processes custom event types
  private processSseEvent(sseEvent: MessageEvent): void {
    //console.log(sseEvent)
    this.serverSentObserver$.next(sseEvent);
    let json =  sseEvent.data ? JSON.parse(sseEvent.data) : {};
    let causedByEvent = json["causedByEvent"];
    //console.log(causedByEvent)
    if(!causedByEvent.startsWith("EventHeartBeat") && json["camundaMachineOccupation"] != undefined) {
      let machineOccupation = JSON.parse(json["camundaMachineOccupation"])
      if(machineOccupation.machineOccupation.status != 'PLANNED' && machineOccupation.machineOccupation.status != 'ARCHIVED') {
        this.serverRequestService.updateMachineOccupation(machineOccupation)
      }
      //this.serverRequestService.updateMachineOccupation(machineOccupation)

      if(machineOccupation.machineOccupation.status == 'SKIPPED' || machineOccupation.machineOccupation.status == 'PLANNED' || machineOccupation.machineOccupation.status == 'ARCHIVED' )
      {
        this.serverRequestService.removeMachineOccupation(machineOccupation)
      }
      //if(causedByEvent !== this.lastEvent || causedByEvent.startsWith("EventCamundaProcessEnd")) {
      if (this.selectedMachineTypes == undefined || this.selectedMachineTypes.find(selectedMachineType => machineOccupation?.activeProductionStep?.machineType?.externalId == selectedMachineType?.externalId) != undefined) {
        if (this.router.url.startsWith("/order-overview")) {
          //this.serverRequestService.requestNewData(undefined)
        } if (causedByEvent.startsWith("EventCreateSplitOrder")) {
          this.serverRequestService.removeMachineOccupation(machineOccupation)
        } else if (causedByEvent.startsWith("EventCamundaReleasePlannedProcess")) {
          //this.serverRequestService.requestNewData(machineOccupation?.id, true);
        } else {
          //this.serverRequestService.requestNewData(machineOccupation?.id);
        }
        //Reload all data, when EventCamundaProcessEnd is received but only specific data, for every other event

        this.eventChangedObserver$.next(causedByEvent);
      }
      //}
      this.lastEvent = causedByEvent;
    }
    if(causedByEvent.startsWith("EventProcessContinued") ||
      causedByEvent.startsWith("EventProcessPaused") ||
      causedByEvent.startsWith("EventResponseActiveTask") ||
      causedByEvent.startsWith("EventCamundaProcessEnd"))
    {
      //console.log("AAAAA - sse component")
      this.serverRequestService.loadOpenMachineOccupations()
    }
    if(causedByEvent.startsWith("EventHeartBeat"))
    {
      //console.log("heartbeat")
      clearInterval(this.timerRef)
      this.timerRef = setInterval(() => {
        console.log("No update since 20 s; reset connection")
        this.closeSseConnection();
        this.openSseChannel();
      }, 20000);
    }

    /*switch (sseEvent.type) {
      case SSE_EVENTS.STATUS: {
        this.store.dispatch(StatusActions.setStatus({ status: parsed }));
        // or
        // this.someReplaySubject.next(parsed);
        break;
      }
      // Add others if neccessary
      default: {
        console.error('Unknown event:', sseEvent.type);
        break;
      }
    }*/
  }

// Handles reconnect attempts when the connection fails for some reason.
  private reconnectOnError(): void {
    this.closeSseConnection();
    clearTimeout(this.reconnectTimeout);
    this.reconnectTimeout = setTimeout(() => {
      this.openSseChannel();
      this.reconnectFrequencySec *= 2;
      if (this.reconnectFrequencySec >= this.SSE_RECONNECT_UPPER_LIMIT) {
        this.reconnectFrequencySec = this.SSE_RECONNECT_UPPER_LIMIT;
      }
    }, this.reconnectFrequencySec * 1000);
  }

  private closeSseConnection(): void {
    if(!! this.eventSource)
    {
      this.eventSource?.close()
      //clearInterval(this.timerRef)
    }
  }

  ngOnDestroy(): void {
    this.closeSseConnection()
  }

}

