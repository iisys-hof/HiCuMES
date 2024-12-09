import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ServerRequestService} from "../../../service/server-request.service";
import {combineLatest, Observable} from "rxjs";
import {Machine, MachineType} from "../../../interfaces/machine";
import {SelectionStateService} from "../../../service/selection-state.service";
import {User} from "../../../interfaces/user";
import {ProductionOrder} from "../../../interfaces/production-order";
import {SideNavService} from "../../../service/side-nav.service";
import {filter, map} from "rxjs/operators";
import {NavigationEnd, Router} from "@angular/router";
import {UtilitiesService} from "../../../service/utilities.service";
import {SseService} from "../../../service/sse.service";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {Department} from "../../../interfaces/department";
import {MatOption} from "@angular/material/core";
import {MatSelect} from "@angular/material/select";
import {UserdataService} from "../../../service/userdata.service";

@Component({
  selector: 'app-top-navigation',
  templateUrl: './top-navigation.component.html',
  styleUrls: ['./top-navigation.component.scss']

})
export class TopNavigationComponent implements OnInit {

  allMachineTypes$: Observable<MachineType[]>;
  allMachineTypes: MachineType[] = [];
  filteredMachineTypes: MachineType[] = [];
  allDepartments$: Observable<Department[]>;
  allDepartments: Department[] = [];
  selectedMachineTypes: MachineType[] | undefined;
  selectedDepartments: Department[] | undefined;
  selectedMachineTypes$: Observable<MachineType[]>;
  selectedDepartments$: Observable<Department[]>;
  currentMachineOccupation$: Observable<CamundaMachineOccupation>;
  currentMachineOccupation: CamundaMachineOccupation | undefined

  navBarOpen: boolean = false;

  task: string = "";
  title: string = ""

  allSelectedDepartment: boolean= false;
  @ViewChild('selectDepartment') selectDepartment: MatSelect | undefined;
  allSelectedMachineType: boolean= false;
  @ViewChild('selectMachineType') selectMachineType: MatSelect | undefined;

  constructor(private serverRequestService: ServerRequestService, private stateService: SelectionStateService, private sideNavService: SideNavService, public router: Router, private utilities: UtilitiesService, private sseService: SseService, private userDataService: UserdataService) {

    router.events.pipe(
      // @ts-ignore
      filter(event => event instanceof NavigationEnd)).subscribe((event: NavigationEnd) => {

      this.task = event.url.substr(1);
      if (this.task.includes(';')) {
        this.task = this.task.substr(0, this.task.indexOf(';'))
      }
      if (this.task.length > 0) {
        //console.log(this.task)
        var urlSettings = this.utilities.getSubProdcuctionStepSettings(this.task);
        if (urlSettings != undefined) {
          this.title = this.utilities.getSubProdcuctionStepSettings(this.task).name;
        } else {
          this.title = "";
        }
      }
    });

    this.allMachineTypes$ = this.serverRequestService.getAllMachineTypes();
    this.allDepartments$ = this.serverRequestService.getAllDepartments();
    //this.allOrders$ = this.serverRequestService.getAllProductionOrders();
    //this.user$ = this.stateService.getSelectedUser();
    //this.selectedProductionOrder$ = this.stateService.getSelectedProductionOrder();
    this.selectedMachineTypes$ = this.stateService.getSelectedMachineTypes();
    this.selectedDepartments$ = this.stateService.getSelectedDepartment();
    //Used for displaying the BA. NR. in the top, when in details or camunda form view
    this.currentMachineOccupation$ = this.stateService.getCurrentMachineOccupation();
    //this.selectedOrder = undefined;
    this.currentMachineOccupation = undefined;
    this.selectedMachineTypes$.subscribe(value => this.selectedMachineTypes = value)
    this.selectedDepartments$.subscribe(value => this.selectedDepartments = value)
    this.allDepartments$.subscribe(departments => {
      this.allDepartments = departments
    })
    this.allMachineTypes$.subscribe(machineTypes => {
      this.allMachineTypes = machineTypes
      this.filterMachineTypes()
    })

    combineLatest([this.allMachineTypes$, this.allDepartments$, this.userDataService.getUserdata()])
      .subscribe(value => {
        //console.log(value)
        let departmentString = localStorage.getItem('defaultDepartment')
        //console.log(departmentString)
        if (departmentString !== undefined && departmentString !== null && departmentString !== "undefined") {
          this.updateUserSelectionDepartments(departmentString.split(', '))
        }
        let machineTypeString = localStorage.getItem('defaultMachineType')
        //console.log(machineTypeString)
        if (machineTypeString !== undefined && machineTypeString !== null && machineTypeString !== "undefined") {
          this.updateUserSelectionMachineTypes(machineTypeString.split(', '))
        }

        //this.serverRequestService.loadAllMachineOccupationsDTO()
      });
  }

  updateUserSelectionDepartments(userDepartmentList: string[])
  {
    this.selectedDepartments = this.allDepartments.filter(dept => userDepartmentList?.find(userDept => userDept == dept.externalId) != undefined)
    //console.log(this.selectedDepartments)
      this.changeDepartmentSelection()
  }
  updateUserSelectionMachineTypes(userMachineTypeList: string[])
  {
    this.selectedMachineTypes = this.allMachineTypes.filter(maType => userMachineTypeList?.find(userMaType => userMaType == maType.externalId) != undefined)
    //console.log(this.selectedMachineTypes)
      this.changeMachineTypeSelection()
  }


  ngOnInit(): void {
    this.sseService.openSseChannel();
    this.sideNavService.sideNavClosedSubject.subscribe((x) => {
      if (x !== "start")
        this.navBarOpen = false;
    });
    this.sideNavService.sideNavOpenedSubject.subscribe((x) => {
      if (x !== "start")
        this.navBarOpen = true;
    });
    this.sideNavService.sideNavShouldCloseSubject.subscribe((x) => {
      if (x !== "start")
        this.navBarOpen = false;
    });
  }

  toggleRightSidenav() {
    this.navBarOpen = !this.navBarOpen;
    this.sideNavService.toggle();
  }


  changeMachineTypeSelection() {
    if(this.selectedMachineTypes != undefined && this.selectedMachineTypes.length > 0) {
      this.allSelectedMachineType = this.filteredMachineTypes.length == this.selectedMachineTypes.length;
    }
    //console.log(this.filteredMachineTypes.length, this.selectedMachineTypes?.length)
    /*if (this.selectedMachineType === undefined) {
      this.stateService.removeMachineTypeSelection();
      return;
    }*/
    this.stateService.selectMachineTypes(this.selectedMachineTypes);
    //let machineTypeList = this.selectedMachineTypes?.map(value => value.externalId)
    //let machineTypeListString = (machineTypeList != undefined) ? machineTypeList.join(', ') : "";
    //localStorage.setItem('defaultMachineType', machineTypeListString);
    //this.router.getCurrentNavigation()
    //this.router.navigate(["/"])
    /*if(!(this.router.url === "/order-overview" || this.router.url === "/user-order-overview"))
    {
      this.router.navigate(["/"])
    }*/
    //We don't need to reload here, because the server request service is already subscribed on the machineTypeSelection
    /*
    if(this.selectedMachineType == null) {
      this.serverRequestService.loadAllMachineOccupations();
    }
    else {
      console.log("Request Data from top nav")
      this.serverRequestService.loadMachineOccupationsByMachineType(this.selectedMachineType?.externalId);
    }*/
  }

  changeDepartmentSelection() {
    if(this.selectedDepartments != undefined && this.selectedDepartments.length > 0) {
      this.allSelectedDepartment = this.allDepartments.length == this.selectedDepartments.length;
    }
    //console.log(this.selectedDepartments)
    /*if (this.selectedMachineType === undefined) {
      this.stateService.removeMachineTypeSelection();
      return;
    }*/
    this.stateService.selectDepartments(this.selectedDepartments);
    this.filterMachineTypes()
    //let departmentList = this.selectedDepartments?.map(value => value.externalId)
    //let departmentListString = (departmentList != undefined) ? departmentList.join(', ') : "";
    //localStorage.setItem('defaultDepartment', departmentListString);
    //this.router.getCurrentNavigation()
    //this.router.navigate(["/"])
    /*if(!(this.router.url === "/order-overview" || this.router.url === "/user-order-overview"))
    {
      this.router.navigate(["/"])
    }*/
    //We don't need to reload here, because the server request service is already subscribed on the machineTypeSelection
    /*
    if(this.selectedMachineType == null) {
      this.serverRequestService.loadAllMachineOccupations();
    }
    else {
      console.log("Request Data from top nav")
      this.serverRequestService.loadMachineOccupationsByMachineType(this.selectedMachineType?.externalId);
    }*/
  }

  /*changeOrderSelection(target: any) {
    console.log(this.selectedOrder)
    if (this.selectedOrder === undefined) {
      this.stateService.removeOrderSelection();
      return;
    }
    this.stateService.selectProductionOrder(this.selectedOrder);
  }*/
  compareSelection(object1: any, object2: any) {
    return object1 && object2 && object1.externalId == object2.externalId;
  }

  sort(data: any) {
    return data.sort((a: { externalId: string; }, b: { externalId: string; }) => parseInt(a.externalId) - parseInt(b.externalId))
  }


  getLabel(machineOccupation: CamundaMachineOccupation) {
    if(machineOccupation?.machineOccupation?.productionOrder?.name)
    {
      return machineOccupation?.machineOccupation?.productionOrder?.name + "-" + machineOccupation?.machineOccupation?.name
    }
    else {
      return machineOccupation?.machineOccupation?.name
    }
  }

  getDepartment(machineOccupation: CamundaMachineOccupation) {
    if(machineOccupation?.machineOccupation?.department?.name)
    {
      return machineOccupation?.machineOccupation?.department?.name
    }
    else {
      return undefined
    }
  }

  getMachineType(machineOccupation: CamundaMachineOccupation) {
    if( machineOccupation?.machineOccupation?.machine?.machineType?.name)
    {
      return machineOccupation?.machineOccupation?.machine?.machineType?.name
    }
    else {
      return undefined
    }
  }

  getMachine(machineOccupation: CamundaMachineOccupation) {
    if( machineOccupation?.machineOccupation?.machine?.name)
    {
      return machineOccupation?.machineOccupation?.machine?.name
    }
    else {
      return undefined
    }
  }

  reload() {
    if(this.router.url === "/booking-overview")
    {
      this.serverRequestService.loadAllBookingEntries(this.serverRequestService.getBookingStart(), this.serverRequestService.getBookingEnd())
      this.serverRequestService.loadAllBookingEntriesOverheadCosts(this.serverRequestService.getBookingStart(), this.serverRequestService.getBookingEnd())
    }
    else if(this.router.url === "/overhead-costs")
    {
      this.serverRequestService.loadOverheadCostCenters()
      this.serverRequestService.loadOverheadCosts()
      this.serverRequestService.loadTopOverheadCostCenters()
    }
    else if(this.router.url === "/user-booking-overview")
    {
      this.serverRequestService.loadAllBookingEntriesUser(this.serverRequestService.getBookingUserStart(), this.serverRequestService.getBookingUserEnd())
      this.serverRequestService.loadAllBookingEntriesOverheadCostsUser(this.serverRequestService.getBookingUserStart(), this.serverRequestService.getBookingUserEnd())
    }
    else
    {
      let selection = this.stateService.getCurrentCombinedSelection()
      this.serverRequestService.loadAllMachineOccupationsDTO(undefined, undefined, selection)
    }
  }

  private filterMachineTypes() {
    if(this.selectedDepartments && this.selectedDepartments.length > 0) {
      //console.log(this.selectedDepartments)
      this.filteredMachineTypes = this.allMachineTypes.filter(machineType => {
          return machineType?.departments?.find(department => {
            return this.selectedDepartments?.find(selectedDepartment =>
            {
              return department.externalId == selectedDepartment?.externalId
            }) != undefined
          }) != undefined
      })
    }
    else
    {
      this.filteredMachineTypes = this.allMachineTypes
    }
    this.changeMachineTypeSelection()
    //console.log(this.filteredMachineTypes)
  }

  toggleAllDepartments() {
    if (this.allSelectedDepartment) {
      this.selectedDepartments = this.allDepartments
      this.changeDepartmentSelection()
    } else {
      this.selectedDepartments = []
      this.changeDepartmentSelection()
    }
  }

  toggleAllMachineTypes() {
      if (this.allSelectedMachineType) {
        this.selectedMachineTypes = this.filteredMachineTypes
        this.changeMachineTypeSelection()
      } else {
        this.selectedMachineTypes = []
        this.changeMachineTypeSelection()
      }
  }
}
