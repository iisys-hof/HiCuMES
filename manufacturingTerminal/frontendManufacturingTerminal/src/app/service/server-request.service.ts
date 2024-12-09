import {Injectable} from '@angular/core';
import {combineLatest, merge, ReplaySubject, Subject} from "rxjs";
import {Machine, MachineType} from "../interfaces/machine";
import {ProductionOrder} from "../interfaces/production-order";
import {environment} from "../../environments/environment";
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {map} from 'rxjs/operators'
import {SelectionStateService} from "./selection-state.service";
import {CamundaMachineOccupation} from "../interfaces/camunda-machine-occupation";
import {Product} from "../interfaces/product";
import {ProductRelationship} from "../interfaces/product-relationship";
import {ToolSetting} from "../interfaces/tool";
import {MachineOccupation} from "../interfaces/machine-occupation";
import {AuthGuard} from "./authguard.service";
import {CamundaSubProductionStep} from "../interfaces/camunda-sub-production-step";
import * as _ from 'lodash';
import {Router} from "@angular/router";
import {Userdata, UserdataService} from "./userdata.service";
import {KeycloakService} from "keycloak-angular";
import {BookingEntry} from "../interfaces/bookingEntry";
import {CamundaMachineOccupationDTO} from "../interfaces/camunda-machine-occupation-dto";
import {OverheadCostCenters} from "../interfaces/overhead-cost-centers";
import {OverheadCosts} from "../interfaces/overhead-costs";
import {Department} from "../interfaces/department";
import {UserOccupation} from "../interfaces/user-occupation";

@Injectable({
  providedIn: 'root'
})
export class ServerRequestService {

  urlEndpoint = '/manufacturingTerminalBackend/data';

  urlMachines = environment.baseUrl + this.urlEndpoint + '/production/machine';
  urlMachineTypes = environment.baseUrl + this.urlEndpoint + '/production/machineType';
  urlDepartments = environment.baseUrl + this.urlEndpoint + '/production/departments';
  urlProductionOrders = environment.baseUrl + this.urlEndpoint + '/production/productionOrder';
  urlSetActiveToolSetting = environment.baseUrl + this.urlEndpoint + '/production/setActiveToolSetting';
  urlCreateCollectionOrder = environment.baseUrl + this.urlEndpoint + '/production/createCollectionOrder';
  urlCreateSplitOrder = environment.baseUrl + this.urlEndpoint + '/production/splitOrder';
  urlAddNote = environment.baseUrl + this.urlEndpoint + '/production/addNote';
  urlMachineOccupations = environment.baseUrl + this.urlEndpoint + '/camunda/machineOccupation';
  urlUserOccupation = environment.baseUrl + this.urlEndpoint + '/production/userOccupation/cmo';
  urlStartMachineOccupation = environment.baseUrl + this.urlEndpoint + '/camunda/startMachineOccupation';
  urlPauseMachineOccupation = environment.baseUrl + this.urlEndpoint + '/camunda/pauseMachineOccupation';
  urlContinueMachineOccupation = environment.baseUrl + this.urlEndpoint + '/camunda/continueMachineOccupation';
  urlFinishFormfield = environment.baseUrl + this.urlEndpoint + '/camunda/finishFormField';
  urlPersistFormfield = environment.baseUrl + this.urlEndpoint + '/camunda/persistFormField';
  urlResendBookingEntry = environment.baseUrl + this.urlEndpoint + '/camunda/resendBooking';
  urlProductRelationships = environment.baseUrl + this.urlEndpoint + '/coreData/productRelationship';
  urlProducts = environment.baseUrl + this.urlEndpoint + '/coreData/product';
  urlChangeMachineOccupationStatus = environment.baseUrl + this.urlEndpoint + '/camunda/changeStatus';
  urlBookingOverview = environment.baseUrl + this.urlEndpoint + '/camunda/bookingOverview';
  urlBookingEntries = environment.baseUrl + this.urlEndpoint + '/camunda/bookingOverview2';
  urlBookingEntriesOverheadCosts = environment.baseUrl + this.urlEndpoint + '/camunda/bookingOverview/overheadCosts';
  urlCMODTO = environment.baseUrl + this.urlEndpoint + '/camunda/machineOccupationDTO';
  urlOverHeadCosts = environment.baseUrl + this.urlEndpoint + '/production/overheadCosts';
  urlOverHeadCostCenters = environment.baseUrl + this.urlEndpoint + '/production/overheadCostCenters';
  urlOverHeadCostCentersTop = environment.baseUrl + this.urlEndpoint + '/production/overheadCostCenters/top';
  urlOverHeadCostsOpen = environment.baseUrl + this.urlEndpoint + '/production/overheadCosts/open';
  urlMachineOccupationsOpen = environment.baseUrl + this.urlEndpoint + '/camunda/machineOccupation/open';
  userData: Userdata | undefined = undefined;
  private allMachines$: Subject<Machine[]>;
  private allMachineTypes$: Subject<MachineType[]>;
  private allDepartments$: Subject<Department[]>;
  private allProductionOrders$: Subject<ProductionOrder[]>;
  private allMachineOccupation$: ReplaySubject<CamundaMachineOccupation[]>;
  private allMachineOccupationsDTO$: ReplaySubject<CamundaMachineOccupation[]>;
  private allMachineOccupationBookingOverview$: ReplaySubject<CamundaMachineOccupation[]>;
  private allBookingEntries$: ReplaySubject<BookingEntry[]>;
  private allBookingEntriesUser$: ReplaySubject<BookingEntry[]>;
  private allBookingEntriesOverheadCosts$: ReplaySubject<BookingEntry[]>;
  private allBookingEntriesOverheadCostsUser$: ReplaySubject<BookingEntry[]>;
  private allCostCenters$: ReplaySubject<OverheadCostCenters[]>;
  private topCostCenters$: ReplaySubject<OverheadCostCenters[]>;
  private userOverheadCosts$: ReplaySubject<OverheadCosts[]>;
  private machineOccupationsByProductionOrder$: ReplaySubject<CamundaMachineOccupation[]>;
  private singleMachineOccupation: CamundaMachineOccupation | undefined = undefined;
  private singleMachineOccupation$: ReplaySubject<CamundaMachineOccupation>;
  private userOccupation: UserOccupation | undefined = undefined;
  private userOccupation$: ReplaySubject<UserOccupation>;
  private filteredMachineOccupation$: ReplaySubject<CamundaMachineOccupation[]>;
  private allProducts$: ReplaySubject<Product[]>;
  private productRelationships$: ReplaySubject<ProductRelationship[]>;
  private allMachineOccupations: CamundaMachineOccupation[] = [];


  private openMachineOccupations$: ReplaySubject<CamundaMachineOccupation[]>;
  private openOverheadCosts$: ReplaySubject<OverheadCosts[]>;

  private errors$: Subject<string>;

  private selectedMachineTypes: MachineType[] | undefined = undefined;
  private selectedDepartments: Department[] | undefined = undefined;

  private lastMachineOccupationId = "";

  startBooking: string = "";
  endBooking: string = "";
  startUserBooking: string = "";
  endUserBooking: string = "";


  constructor(private readonly keycloak: KeycloakService, private http: HttpClient, private selectionStateService: SelectionStateService,  private authGuard: AuthGuard, private router: Router, private userDataService: UserdataService) {
    this.allMachines$ = new ReplaySubject(1);
    this.allMachineTypes$ = new ReplaySubject(1);
    this.allDepartments$ = new ReplaySubject(1);
    this.allProductionOrders$ = new ReplaySubject(1);
    this.allMachineOccupation$ = new ReplaySubject(1);
    this.allMachineOccupationsDTO$ = new ReplaySubject(1);
    this.allMachineOccupationBookingOverview$ = new ReplaySubject(1);
    this.allBookingEntries$ = new ReplaySubject(1);
    this.allBookingEntriesUser$ = new ReplaySubject(1);
    this.allBookingEntriesOverheadCosts$ = new ReplaySubject(1);
    this.allBookingEntriesOverheadCostsUser$ = new ReplaySubject(1);
    this.allCostCenters$ = new ReplaySubject(1);
    this.topCostCenters$ = new ReplaySubject(1);
    this.userOverheadCosts$ = new ReplaySubject(1);
    this.machineOccupationsByProductionOrder$ = new ReplaySubject(1);
    this.singleMachineOccupation$ = new ReplaySubject(1);
    this.userOccupation$ = new ReplaySubject(1);
    this.filteredMachineOccupation$ = new ReplaySubject(1);
    this.allProducts$ = new ReplaySubject(1);
    this.productRelationships$ = new ReplaySubject(1);
    this.openMachineOccupations$ = new ReplaySubject(1);
    this.openOverheadCosts$ = new ReplaySubject(1);

    this.errors$ = new ReplaySubject(1);
    console.log("init server request service")
    //this.requestNewData(undefined)
    this.userDataService.getUserdata().subscribe(
      data => {
        this.userData = data;
      }
    )
  }

  public updateMachineOccupation(machineOccupation: CamundaMachineOccupation)
  {
    this.allMachineOccupations = this.allMachineOccupations.map((occupation: CamundaMachineOccupation) => {
      if(occupation.id === machineOccupation.id)
      {
        //console.log(machineOccupation, occupation)
        return machineOccupation
      }
      else
      {
        return occupation
      }
    });
    if(this.allMachineOccupations.find(value => value.id == machineOccupation.id) == undefined)
    {
      //Add new MachineOccupation if no MachineType was selected at all or if it was selected
      //console.log(this.selectedMachineTypes)
      if(this.selectedMachineTypes == undefined ||
        this.selectedMachineTypes.length == 0 ||
        this.selectedMachineTypes.find(selectedMachineType => machineOccupation?.activeProductionStep?.machineType?.externalId == selectedMachineType?.externalId) != undefined)
      {
        this.allMachineOccupations.push(machineOccupation)
      }

    }
    //console.log(this.allMachineOccupations)
    this.allMachineOccupation$.next(this.allMachineOccupations)
    this.allMachineOccupationsDTO$.next(this.allMachineOccupations)

    //Update singleMachineOccupation
    if(this.singleMachineOccupation?.id == machineOccupation.id)
    {
      this.singleMachineOccupation = machineOccupation
      this.singleMachineOccupation$.next(this.singleMachineOccupation)
      this.loadUserOccupationByCMOId(this.singleMachineOccupation.id.toString())
    }
  }

  public removeMachineOccupation(machineOccupation: CamundaMachineOccupation)
  {
    if(this.allMachineOccupations.find(value => value.id == machineOccupation.id) != undefined)
    {
      let filteredMachineOccupations = this.allMachineOccupations.filter(value => value.id != machineOccupation.id)
      this.allMachineOccupations = filteredMachineOccupations;
    }
    //console.log(this.allMachineOccupations)
    this.allMachineOccupation$.next(this.allMachineOccupations)
    if(this.singleMachineOccupation?.id == machineOccupation.id)
    {
      this.singleMachineOccupation$.next(undefined)
    }
  }

  public initalRequest()
  {
    this.loadMachines();
    this.loadMachineTypes();
    this.loadDepartments();

  }

  public requestNewData(machineOccupationId: string | undefined, updateSingle?: boolean, forceAllStates?: boolean) {
    console.log("Request new Data from SErver request", machineOccupationId)
    this.loadMachines();
    this.loadMachineTypes();
    this.loadDepartments();
    //this.loadAllMachineOccupationsDTO();
    //this.serverRequestService.loadProductionOrders();
    //this.serverRequestService.loadAllMachineOccupations();
    if(machineOccupationId !== undefined && this.lastMachineOccupationId == machineOccupationId) {
      this.loadMachineOccupationById(this.lastMachineOccupationId);
      this.loadUserOccupationByCMOId(this.lastMachineOccupationId);
    }

    this.selectionStateService.getCombinedSelectionDepartmentAndMachineType().subscribe(selection =>
    {
      this.selectedMachineTypes = selection.machineType
      this.selectedDepartments = selection.department
      if(selection.machineType != undefined && selection.department != undefined)
      {
        if(machineOccupationId == undefined)
        {
          //console.log(selection)
          this.loadAllMachineOccupationsDTO(undefined, forceAllStates, selection)
          if(this.singleMachineOccupation != undefined)
          {
            this.loadMachineOccupationById(this.singleMachineOccupation.id.toString());
          }
        }
        else if(updateSingle && machineOccupationId !== undefined)
        {
          this.loadAllMachineOccupations(machineOccupationId)
        }
      }
    })
  }

  loadMachines() {
    this.http.get<Machine[]>(this.urlMachines).subscribe(
      (data) => {
        this.allMachines$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Maschienen$", error);
        this.handleUnauthorized(error);
      });
  }

  loadMachineTypes() {
    this.http.get<MachineType[]>(this.urlMachineTypes).subscribe(
      (data) => {
        this.allMachineTypes$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Maschienen$", error);
        this.handleUnauthorized(error);
      });
  }

  loadDepartments() {
    this.http.get<Department[]>(this.urlDepartments).subscribe(
      (data) => {
        this.allDepartments$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Abteilungen", error);
        this.handleUnauthorized(error);
      });
  }

  loadProductionOrders() {
    this.http.get<ProductionOrder[]>(this.urlProductionOrders).subscribe(
      (data) => {
        this.allProductionOrders$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der ProductionOrder$", error);
        this.handleUnauthorized(error);
      });
  }

  loadAllMachineOccupations(updateSingleId?: string, forceLoadAll?: boolean) {
    console.log("load all mos")
    this.allMachineOccupation$.next(undefined)
    let request;
    let params = new HttpParams();
    let isLoadAll = "false" //localStorage.getItem("loadAllStates")
    if(isLoadAll == "true" || forceLoadAll)
    {
      params = params.append("loadAllStates", true)
    }
    else
    {
      params = params.append("loadAllStates", false)
    }

    if(this.selectedMachineTypes && this.selectedMachineTypes.length > 0)
    {
        this.selectedMachineTypes.forEach(value =>
            params = params.append("machineType", value?.externalId));
    }

    request = this.http.get<CamundaMachineOccupation[]>(this.urlMachineOccupations, {params: params})
    request.subscribe(
      (data) => {
        this.allMachineOccupations = data
        if(updateSingleId)
        {
          this.singleMachineOccupation = this.allMachineOccupations.find(value => value.id.toString() == updateSingleId)
          this.singleMachineOccupation$.next(this.singleMachineOccupation);
        }
        this.allMachineOccupation$.next(this.allMachineOccupations);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  waitForLoadAll = false;
  loadAllMachineOccupationsDTO(updateSingleId?: string, forceLoadAll?: boolean, selection?: {department: Department[]; machineType: MachineType[]}) {
    if(!this.waitForLoadAll)
    {
      this.waitForLoadAll = true;
    //console.log("load all mos dto")
    //console.trace()
    //console.log(this.selectedDepartments, this.selectedMachineTypes)
    //console.trace()
    let url = this.urlCMODTO
    this.allMachineOccupationsDTO$.next(undefined)
    let request;
    let params = new HttpParams();
    let isLoadAll = "false" //localStorage.getItem("loadAllStates")
    if(isLoadAll == "true" || forceLoadAll)
    {
      params = params.append("loadAllStates", true)
    }
    else
    {
      params = params.append("loadAllStates", false)
    }

    if(selection?.machineType && selection?.machineType.length > 0)
    {
      selection?.machineType.forEach(value =>
        params = params.append("machineTypes", value?.externalId));
    }

    if(selection?.department && selection?.department.length > 0)
    {
      url += "/departments"
      selection?.department.forEach(value =>
        params = params.append("departments", value?.externalId));
    }

    request = this.http.get<CamundaMachineOccupation[]>(url, {params: params})
    request.subscribe(
      (data) => {
        this.allMachineOccupations = data
        //console.log(this.allMachineOccupations)
        this.allMachineOccupations.map((cMachineOccupation: any) => {
          //console.log(cMachineOccupation)
          const subProductionSteps = _.get(cMachineOccupation, 'camundaSubProductionSteps', []);
          //console.log(subProductionSteps)
// Sort the timeRecords based on the startDateTime for each camundaSubProductionStep
          subProductionSteps.forEach((step: { subProductionStep: { timeRecords: any[]; }; }) => {
            step.subProductionStep.timeRecords = _.sortBy(step.subProductionStep.timeRecords, 'startDateTime');
          });

// Sort the camundaSubProductionSteps based on the earliest startDateTime within their timeRecords
          subProductionSteps.sort((a: any, b: any) => {
            const startDateTimeA = _.get(a, 'subProductionStep.timeRecords[0].startDateTime', '');
            const startDateTimeB = _.get(b, 'subProductionStep.timeRecords[0].startDateTime', '');

            // @ts-ignore
            return new Date(startDateTimeA) - new Date(startDateTimeB);
          });
        })


        if(updateSingleId)
        {
          this.singleMachineOccupation = this.allMachineOccupations.find(value => value.id.toString() == updateSingleId)
          this.singleMachineOccupation$.next(this.singleMachineOccupation);
        }
        this.allMachineOccupationsDTO$.next(this.allMachineOccupations)
        //console.log(this.allMachineOccupations)
        this.waitForLoadAll = false
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
    }
  }

  loadMachineOccupationsByProductionOrder(productionOrderExtId: string) {
    this.machineOccupationsByProductionOrder$.next(undefined)
      let params = new HttpParams().set("productionOrder", productionOrderExtId);
      params = params.append("loadAllStates", true);
      let request = this.http.get<CamundaMachineOccupation[]>(this.urlMachineOccupations, {params: params})

    request.subscribe(
      (data) => {
        this.machineOccupationsByProductionOrder$.next(data);
        data.map(value =>
        {
          this.allMachineOccupations = this.allMachineOccupations.map((occupation: CamundaMachineOccupation) => {
            if(occupation.id === value.id)
            {
              //console.log(data, occupation)
              return value
            }
            else
            {
              return occupation
            }
          });
          //console.log(this.allMachineOccupations)
        })
        this.allMachineOccupation$.next(undefined)
        this.allMachineOccupation$.next(this.allMachineOccupations)
        this.allMachineOccupationsDTO$.next(undefined)
        this.allMachineOccupationsDTO$.next(this.allMachineOccupations)
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  loadMachineOccupationsByIdWithProductRelationship(id:string)
  {
    let params = new HttpParams().set("id", id);
    this.lastMachineOccupationId = id;
    this.singleMachineOccupation$.next(undefined)
    this.http.get<CamundaMachineOccupation>(this.urlMachineOccupations, {params: params}).subscribe(
      (data) => {
        if(data?.machineOccupation?.productionOrder?.product) {
          this.loadProductRelationshipsByProductId(data?.machineOccupation.productionOrder.product.id, data?.machineOccupation.productionOrder.externalId)
        }
        this.singleMachineOccupation = data;
        this.singleMachineOccupation$.next(this.singleMachineOccupation);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  loadUserOccupationByCMOId(id:string)
  {
    this.http.get<UserOccupation>(this.urlUserOccupation + "/" + id).subscribe(
      (data) => {
        this.userOccupation = data;
        this.userOccupation$.next(this.userOccupation);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der UserOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  waitForLoadSingle = false;
  loadMachineOccupationById(id: string) {
    if(!this.waitForLoadSingle) {
      this.waitForLoadSingle = true;
      let params = new HttpParams().set("id", id);
      this.lastMachineOccupationId = id;
      this.singleMachineOccupation$.next(undefined)
      this.http.get<CamundaMachineOccupation>(this.urlMachineOccupations, {params: params}).subscribe(
        (data) => {
          this.singleMachineOccupation = data
          this.singleMachineOccupation$.next(this.singleMachineOccupation);
          this.allMachineOccupations = this.allMachineOccupations.map((occupation: CamundaMachineOccupation) => {
            if (occupation.id === data.id) {
              //console.log(data, occupation)
              return data
            } else {
              return occupation
            }
          });
          //console.log(this.allMachineOccupations)
          this.allMachineOccupation$.next(undefined)
          this.allMachineOccupation$.next(this.allMachineOccupations)
          this.allMachineOccupationsDTO$.next(undefined)
          this.allMachineOccupationsDTO$.next(this.allMachineOccupations)
          this.waitForLoadSingle = false;
        }, (error: HttpErrorResponse) => {
          this.error("Fehler beim laden der MachineOccupation$", error);
          this.handleUnauthorized(error);
        });
    }
  }

  loadProductRelationshipsByProductId(id?: number, extIdProductionOrder?: string) {
    if(id == undefined)
    {
      id = 0;
    }
    let params = new HttpParams().set("id",id);
    if(extIdProductionOrder)
    {
      params = params.append("extIdProductionOrder", extIdProductionOrder)
    }
    this.productRelationships$.next(undefined)
    this.http.get<ProductRelationship[]>(this.urlProductRelationships, {params: params}).subscribe(
      (data) => {
        this.productRelationships$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Products$", error);
        this.handleUnauthorized(error);
      });
  }

  loadAllMachineOccupationBookingOverview(startDate?: string, endDate?: string) {
    let params = new HttpParams();
    if(startDate)
    {
      params = params.append("start", startDate)
    }
    if(endDate)
    {
      params = params.append("end", endDate)
    }
    this.http.get<CamundaMachineOccupation[]>(this.urlBookingOverview, {params: params}).subscribe(
      (data) => {
        this.allMachineOccupationBookingOverview$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Buchungen", error);
        this.handleUnauthorized(error);
      });
  }
  loadAllBookingEntries(startDate?: string, endDate?: string) {
    let params = new HttpParams();
    if(startDate)
    {
      params = params.append("start", startDate)
    }
    if(endDate)
    {
      params = params.append("end", endDate)
    }
    this.http.get<BookingEntry[]>(this.urlBookingEntries, {params: params}).subscribe(
      (data) => {
        this.allBookingEntries$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Buchungen", error);
        this.handleUnauthorized(error);
      });
  }

  loadAllBookingEntriesOverheadCosts(startDate?: string, endDate?: string) {
    let params = new HttpParams();
    if(startDate)
    {
      params = params.append("start", startDate)
    }
    if(endDate)
    {
      params = params.append("end", endDate)
    }
    this.http.get<BookingEntry[]>(this.urlBookingEntriesOverheadCosts, {params: params}).subscribe(
      (data) => {
        this.allBookingEntriesOverheadCosts$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Buchungen", error);
        this.handleUnauthorized(error);
      });
  }

  loadAllBookingEntriesUser(startDate?: string, endDate?: string) {
    let userName = this.authGuard.getUsername()
    let params = new HttpParams();
    if(startDate)
    {
      params = params.append("start", startDate)
    }
    if(endDate)
    {
      params = params.append("end", endDate)
    }
    this.http.get<BookingEntry[]>(this.urlBookingEntries + "/" + userName, {params: params}).subscribe(
      (data) => {
        this.allBookingEntriesUser$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Buchungen", error);
        this.handleUnauthorized(error);
      });
  }

  loadAllBookingEntriesOverheadCostsUser(startDate?: string, endDate?: string) {
    let userName = this.authGuard.getUsername()
    let params = new HttpParams();
    if(startDate)
    {
      params = params.append("start", startDate)
    }
    if(endDate)
    {
      params = params.append("end", endDate)
    }
    this.http.get<BookingEntry[]>(this.urlBookingEntriesOverheadCosts + "/" + userName, {params: params}).subscribe(
      (data) => {
        this.allBookingEntriesOverheadCostsUser$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Buchungen", error);
        this.handleUnauthorized(error);
      });
  }

  loadOverheadCostCenters() {
    this.http.get<OverheadCostCenters[]>(this.urlOverHeadCostCenters).subscribe(
      (data) => {
        this.allCostCenters$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Gemeinkosten$", error);
        this.handleUnauthorized(error);
      });
  }

  loadAllProducts() {
    this.http.get<Product[]>(this.urlProducts).subscribe(
      (data) => {
        this.allProducts$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Products$", error);
        this.handleUnauthorized(error);
      });
  }

  getAllMachines() {
    return this.allMachines$.asObservable();
  }

  getAllMachineTypes() {
    return this.allMachineTypes$.asObservable();
  }

  getAllDepartments() {
    return this.allDepartments$.asObservable();
  }

  getAllProducts() {
    return this.allProducts$.asObservable();
  }

  getAllProductionOrders() {
    return this.allProductionOrders$.asObservable();
  }

  getProductRelationships()
  {
    return this.productRelationships$.asObservable()
  }

  getAllMachineOccupationBookingOverview()
  {
    return this.allMachineOccupationBookingOverview$.asObservable()
  }

  getAllBookingEntries()
  {
    return this.allBookingEntries$.asObservable()
  }

  getAllBookingEntriesUser()
  {
    return this.allBookingEntriesUser$.asObservable()
  }

  getAllBookingEntriesOverheadCosts()
  {
    return this.allBookingEntriesOverheadCosts$.asObservable()
  }

  getAllBookingEntriesOverheadCostsUser()
  {
    return this.allBookingEntriesOverheadCostsUser$.asObservable()
  }

  getAllBookingAndOverheadCostsUser()
  {
    return merge(this.allBookingEntriesUser$, this.allBookingEntriesOverheadCostsUser$);
  }

  getAllOverheadCostCenters()
  {
    return this.allCostCenters$.asObservable()
  }

  getTopOverheadCostCenters()
  {
    return this.topCostCenters$.asObservable()
  }

  getOverheadCosts()
  {
    return this.userOverheadCosts$
  }

  removeProductRelationships() {
    this.productRelationships$.next(undefined);
  }



  /*getMachineOccupationsFilterByMachineAndProductionOrder() {
    return combineLatest([this.allMachineOccupation$.asObservable(), this.selectionStateService.getSelectedMachine(), this.selectionStateService.getSelectedProductionOrder()])
      .pipe(map(([machineOccupations, machine, order]) => {
        console.log(machine)
        console.log(order)
        if (machine === undefined && order === undefined) {
          return machineOccupations;
        }
        else if(machine === undefined)
        {
          return machineOccupations.filter(occupation => occupation.machineOccupation.productionOrder.id === order.id);
        }
        else if(order === undefined)
        {
          return machineOccupations.filter(occupation => occupation.machineOccupation.machine.id === machine.id);
        }
        else {
          return machineOccupations.filter(occupation => occupation.machineOccupation.machine.id === machine?.id && occupation.machineOccupation.productionOrder.id === order?.id);
        }
      }));
  }*/

  /*getMachineOccupationsFilterByMachineTypeAndProductionOrder() {
    return combineLatest([this.allMachineOccupation$.asObservable(), this.selectionStateService.getSelectedMachineType(), this.selectionStateService.getSelectedProductionOrder()])
      .pipe(map(([machineOccupations, machineType, order]) => {
        console.log(machineType)
        console.log(order)
        if (machineType === undefined && order === undefined) {
          return machineOccupations;
        }
        else if(machineType === undefined)
        {
          return machineOccupations.filter(occupation => occupation.machineOccupation.productionOrder.id === order.id);
        }
        else if(order === undefined)
        {
          return machineOccupations.filter(occupation => occupation.machineOccupation.productionSteps[0]?.machineType?.id === machineType.id);
        }
        else {
          return machineOccupations.filter(occupation => occupation.machineOccupation.productionSteps[0].machineType.id === machineType?.id && occupation.machineOccupation.productionOrder.id === order?.id);
        }
      }));
  }*/

  getMachineOccupationsFilterByMachineType() {
    return combineLatest([this.allMachineOccupation$.asObservable()])
      .pipe(map(([machineOccupations]) => {
          return machineOccupations;
      }));
  }
  getMachineOccupationsDTO() {
    return this.allMachineOccupationsDTO$.asObservable()
  }

  getMachineOccupationsFilterByMachineTypeAndUser(userName: string) {
    return combineLatest([this.allMachineOccupation$.asObservable()])
      .pipe(map(([machineOccupations]) => {
        //Just send the machineOccupations, where the user worked on
        return machineOccupations?.filter(occupation => {
          //Find every camundaSubProductionStep where the user worked on
          let camundaSubProductionStepsByUser = _.filter(occupation.camundaSubProductionSteps, { subProductionStep: {timeRecords: [{responseUser: {userName: userName}}]}})
          if(camundaSubProductionStepsByUser.length > 0) {
            //console.log(camundaSubProductionStepsByUser);
            return true
          }
          else {
            return false
          }
        });
      }));
  }

  getMachineOccupationsDTOFilterByMachineTypeAndUser(userName: string) {
    return combineLatest([this.allMachineOccupationsDTO$.asObservable()])
      .pipe(map(([machineOccupations]) => {
        //Just send the machineOccupations, where the user worked on
        return machineOccupations?.filter(occupation => {
          //Find every camundaSubProductionStep where the user worked on
          //console.log(occupation)
          let camundaSubProductionStepsByUser = _.filter(occupation.camundaSubProductionSteps, { subProductionStep: {timeRecords: [{responseUser: {userName: userName}}]}})
          if(camundaSubProductionStepsByUser.length > 0) {
            //console.log(camundaSubProductionStepsByUser);
            return true
          }
          else {
            return false
          }
        });
      }));
  }

  getMachineOccupationsByProductionOrder()
  {
    return this.machineOccupationsByProductionOrder$.asObservable()
  }

  /*getMachineOccupationsFilterByMachine() {
    return combineLatest([this.allMachineOccupation$.asObservable(), this.selectionStateService.getSelectedMachineType()])
      .pipe(map(([machineOccupations, machineType]) => {
        if (machineType === undefined) {
          return machineOccupations;
        }
        return machineOccupations.filter(occupation => occupation.machineOccupation.machine.id === machineType.id);
      }));
  }*/

  /*getMachineOccupationsFilterByProductionOrder() {
    return combineLatest([this.allMachineOccupation$.asObservable(), this.selectionStateService.getSelectedProductionOrder()])
      .pipe(map(([machineOccupations, order]) => {
        if (order === undefined) {
          return machineOccupations;
        }
        return machineOccupations.filter(occupation => occupation.machineOccupation.productionOrder.id === order.id);
      }));
  }*/

  startMachineOccupation(machineOccupation: CamundaMachineOccupation) {
    this.lastMachineOccupationId = machineOccupation.id.toString();
    let userName = this.authGuard.getUsername()
    this.http.post(this.urlStartMachineOccupation, {machineOccupationId: machineOccupation.id, machineId: machineOccupation.machineOccupation.machine.id, userName: userName}).subscribe(
      (data) => {
        if(this.userData?.attributes.JumpToMask)
        {
          this.router.navigate(['/formfields', {id: machineOccupation.id}]);
        }
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim starten der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  pauseMachineOccupation(machineOccupation: CamundaMachineOccupation, suspensionType: string) {
    let userName = this.authGuard.getUsername()
    this.http.post(this.urlPauseMachineOccupation, {formKey: machineOccupation.camundaSubProductionSteps.slice(-1)[0].formKey, businessKey: machineOccupation.businessKey, camundaProcessInstanceId: machineOccupation.camundaProcessInstanceId, userName: userName, suspensionType: suspensionType}).subscribe(
      (data) => {
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim pausieren der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  startMachineOccupationById(machineOccupationId: number, machineId: number) {
    this.lastMachineOccupationId = machineOccupationId.toString();
    let userName = this.authGuard.getUsername()
    this.http.post(this.urlStartMachineOccupation, {machineOccupationId: machineOccupationId, machineId: machineId, userName: userName}).subscribe(
      (data) => {
        if(this.userData?.attributes.JumpToMask)
        {
          this.router.navigate(['/formfields', {id: machineOccupationId}]);
        }
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim starten der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  setActiveToolSetting(machineOccupation: CamundaMachineOccupation, toolSettings: ToolSetting[]) {
    let toolSettingsIds: number[] = [];
    toolSettings.forEach((setting : ToolSetting) => {
      toolSettingsIds.push(setting.id);
    });

    this.http.post(this.urlSetActiveToolSetting, {machineOccupationId: machineOccupation.id, toolSettingIds: toolSettingsIds}).subscribe(
      (data) => {
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim setzen der Werkzeugeinstellung$", error);
        this.handleUnauthorized(error);
      });
  }

  createCollectionOrder(machineOccupations: CamundaMachineOccupation[], parentMachineOccupation?: CamundaMachineOccupation) {
    let machineOccupationIds: { id: number, amount: number }[] = [];
    machineOccupations.forEach((machineOccupation : any) => {
      machineOccupationIds.push({'id': machineOccupation.id, 'amount': machineOccupation?.toggleFull ? 0 : Number(machineOccupation?.subAmount) });
    });
    console.log(machineOccupationIds)
    console.log(parentMachineOccupation)
    this.http.post(this.urlCreateCollectionOrder, {collectionOrderId: parentMachineOccupation?.id, machineOccupations: machineOccupationIds}).subscribe(
      (data) => {
        //this.loadAllMachineOccupations()
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim erzeugen des Sammelauftrags", error);
        this.handleUnauthorized(error);
      });
  }

  getMachineOccupationsFilterById() {
    return this.singleMachineOccupation$
  }

  getALlMachineOccupations() {
    return this.allMachineOccupation$.asObservable();
  }

  finishSubProductionStep(filledFormfield: any) {
    filledFormfield["userName"] = this.authGuard.getUsername();
    console.log(filledFormfield)
    this.http.post(this.urlFinishFormfield, filledFormfield).subscribe(
      (data) => {
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim finischem des Formfields", error);
        this.handleUnauthorized(error);
      });
  }

  persistSubProductionStep(formfield: any) {
    formfield["userName"] = this.authGuard.getUsername();
    console.log(formfield)
    this.http.post(this.urlPersistFormfield, formfield).subscribe(
      (data) => {
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim finischem des Formfields", error);
        this.handleUnauthorized(error);
      });
  }

  private error(message: string, error: HttpErrorResponse) {
    let errorMessage = ": " + error.message + " wegen: " + error.error.message;
    console.error(errorMessage);
    console.log(error);
    this.errors$.next(errorMessage);
  }

  getMachinesFilterByMachineType() {
    return combineLatest([this.allMachines$.asObservable(), this.selectionStateService.getSelectedMachineTypes()])
      .pipe(map(([machines, machineType]) => {
        if (machineType === undefined) {
          return machines;
        }
        return machines.filter(machine => machineType.find(value => machine.machineType.id === value.id) != undefined);
      }));
  }

  createSplitOrder(machineOccupation: MachineOccupation, splits: number[]) {

    this.http.post(this.urlCreateSplitOrder, {machineOccupation: machineOccupation.id, splits: splits}).subscribe(
      (data) => {
        //this.loadAllMachineOccupations()
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim erzeugen des Sammelauftrags", error);
        this.handleUnauthorized(error);
      });
  }

  changeStatus(cMachineOccupation: CamundaMachineOccupation, status: string) {
    let userName = this.authGuard.getUsername()
    this.http.post(this.urlChangeMachineOccupationStatus, {machineOccupationId: cMachineOccupation.id, status: status, userName: userName}).subscribe(
      (data) => {
        //this.loadAllMachineOccupations()
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim erzeugen des Sammelauftrags", error);
        this.handleUnauthorized(error);
      });
  }


  addNoteToMachineOccupation(machineOccupation: MachineOccupation, noteString: string) {
    let userName = this.authGuard.getUsername()
    this.http.post(this.urlAddNote, {machineOccupation: machineOccupation.id, note: noteString, userName: userName}).subscribe(
      (data) => {
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim hinzufügen einer Notiz", error);
        this.handleUnauthorized(error);
      });
  }

  continueMachineOccupation(machineOccupation: CamundaMachineOccupation) {
    let userName = this.authGuard.getUsername()
    this.http.post(this.urlContinueMachineOccupation, {formKey: machineOccupation.camundaSubProductionSteps.slice(-1)[0].formKey, businessKey: machineOccupation.businessKey, camundaProcessInstanceId: machineOccupation.camundaProcessInstanceId, userName: userName}).subscribe(
      (data) => {
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim fortsetzen der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  handleUnauthorized(error: HttpErrorResponse)
  {
    if(error.status == 403)
    {
      this.keycloak.login(
        {
          redirectUri:
            window.location.origin + environment.baseref + this.router.url,
        }
      ).then();
    }
  }

  resendBookingEntry(bookingEntryId: number) {
    this.http.post(this.urlResendBookingEntry, {id:  bookingEntryId}).subscribe(
      (data) => {
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim erneuten Senden der Buchung", error);
        this.handleUnauthorized(error);
      });
  }

  setBookingStart(start: string) {
    this.startBooking = start
  }

  setBookingEnd(end: string) {
    this.endBooking = end
  }

  getBookingStart() {
    return this.startBooking
  }

  getBookingEnd() {
    return this.endBooking
  }

  setBookingUserStart(start: string) {
    this.startUserBooking = start
  }

  setBookingUserEnd(end: string) {
    this.endUserBooking = end
  }

  getBookingUserStart() {
    return this.startUserBooking
  }

  getBookingUserEnd() {
    return this.endUserBooking
  }

  getOpenMachineOccupations() {
    return this.openMachineOccupations$.asObservable()
  }  getOpenOverheadCosts() {
    return this.openOverheadCosts$.asObservable()
  }

  getAllOpen() {
    return combineLatest([this.openMachineOccupations$.asObservable(), this.openOverheadCosts$.asObservable()])
      .pipe(map(([machineOccupation$, overheadCost$]) => ({
        machineOccupations: machineOccupation$,
        overheadCosts: overheadCost$
      })));
  }

  loadOverheadCosts() {
    let userName = this.authGuard.getUsername()
    let params = new HttpParams();
    //this.userOverheadCosts$.next(undefined)
    params = params.append("userName", userName)
    this.http.get<OverheadCosts[]>(this.urlOverHeadCosts, {params: params}).subscribe(
      (data) => {
        this.userOverheadCosts$.next(data)
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim fortsetzen der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  loadTopOverheadCostCenters() {
    let userName = this.authGuard.getUsername()
    let params = new HttpParams();
    //this.userOverheadCosts$.next(undefined)
    params = params.append("userName", userName)
    this.http.get<OverheadCostCenters[]>(this.urlOverHeadCostCentersTop, {params: params}).subscribe(
      (data) => {
        this.topCostCenters$.next(data)
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim fortsetzen der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  startOverheadCost(costCenterExtId: string) {
    let userName = this.authGuard.getUsername()
    this.http.post(this.urlOverHeadCosts + "/-1", {userName: userName, costCenter: costCenterExtId, note: ""}).subscribe(
      (data) => {
        this.loadOverheadCosts()
        this.loadOpenOverheadCosts()
        this.loadTopOverheadCostCenters()
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim fortsetzen der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  stopOverheadCost(costId: string) {
    let userName = this.authGuard.getUsername()
    this.http.post(this.urlOverHeadCosts + "/" + costId, {userName: userName, costCenter: "", note: ""}).subscribe(
      (data) => {
        this.loadOverheadCosts()
        this.loadOpenOverheadCosts()
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim fortsetzen der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  editOverHeadCostNote(costId: string, result: any) {
    let userName = this.authGuard.getUsername()
    this.http.post(this.urlOverHeadCosts + "/" + costId + "/editNote", {userName: userName, costCenter: "", note: result}).subscribe(
      (data) => {
        this.loadOverheadCosts()
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim fortsetzen der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  loadOpenOverheadCosts() {
    this.openOverheadCosts$.next(undefined)
    let userName = this.authGuard.getUsername()
    let params = new HttpParams();
    //this.userOverheadCosts$.next(undefined)
    params = params.append("userName", userName)
    this.http.get<OverheadCosts[]>(this.urlOverHeadCostsOpen, {params: params}).subscribe(
      (data) => {
        this.openOverheadCosts$.next(data)
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim fortsetzen der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  loadOpenMachineOccupations() {
    this.openMachineOccupations$.next(undefined)
    let userName = this.authGuard.getUsername()
    let params = new HttpParams();
    //this.userOverheadCosts$.next(undefined)
    params = params.append("userName", userName)
    this.http.get<CamundaMachineOccupation[]>(this.urlMachineOccupationsOpen, {params: params}).subscribe(
      (data) => {
        data.map((cMachineOccupation: any) => {
          //console.log(cMachineOccupation)
          const subProductionSteps = _.get(cMachineOccupation, 'camundaSubProductionSteps', []);
          //console.log(subProductionSteps)
// Sort the timeRecords based on the startDateTime for each camundaSubProductionStep
          subProductionSteps.forEach((step: { subProductionStep: { timeRecords: any[]; }; }) => {
            step.subProductionStep.timeRecords = _.sortBy(step.subProductionStep.timeRecords, 'startDateTime');
          });

// Sort the camundaSubProductionSteps based on the earliest startDateTime within their timeRecords
          subProductionSteps.sort((a: any, b: any) => {
            const startDateTimeA = _.get(a, 'subProductionStep.timeRecords[0].startDateTime', '');
            const startDateTimeB = _.get(b, 'subProductionStep.timeRecords[0].startDateTime', '');

            // @ts-ignore
            return new Date(startDateTimeA) - new Date(startDateTimeB);
          });
        })
        this.openMachineOccupations$.next(data)

      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim fortsetzen der MachineOccupation$", error);
        this.handleUnauthorized(error);
      });
  }

  getUserOccupation() {
    return this.userOccupation$.asObservable();
  }

  joinMachineOccupation(machineOccupation: CamundaMachineOccupation) {
    let userName = this.authGuard.getUsername()
    this.http.post(this.urlUserOccupation + "/" + machineOccupation.id + "/add", {userName: userName}).subscribe(
      (data) => {
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim hinzufügen der UserOccupation$", error);
        this.handleUnauthorized(error);
      });
  }
}
