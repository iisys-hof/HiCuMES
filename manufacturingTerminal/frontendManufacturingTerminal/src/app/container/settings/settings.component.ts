import {Component, OnInit, ViewChild} from '@angular/core';
import {SelectionStateService} from "../../service/selection-state.service";
import {Observable} from "rxjs";
import {MachineType} from "../../interfaces/machine";
import {ServerRequestService} from "../../service/server-request.service";
import {Userdata, UserdataService} from "../../service/userdata.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Department} from "../../interfaces/department";
import {MatSelect} from "@angular/material/select";
import {MatOption} from "@angular/material/core";
import {NgxSpinnerService} from "ngx-spinner";
import {KeycloakService} from "keycloak-angular";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

  allMachineTypes$: Observable<MachineType[]>;
  allMachineTypes: MachineType[] = [];
  allDepartments$: Observable<Department[]>;
  allDepartments: Department[] = [];
  selectedMachineTypes: MachineType[] | undefined;
  selectedDepartments: Department[] | undefined;

  allSelectedDepartment: boolean= false;
  @ViewChild('selectDepartmentSetting') selectDepartment: MatSelect | undefined;
  allSelectedMachineType: boolean= false;
  @ViewChild('selectMachineTypeSetting') selectMachineType: MatSelect | undefined;

  formGroup = this.formBuilder.group({
    jumpToMainPageSetting: true,
    jumpToMaskPageSetting: true
  });

  userData: Userdata | undefined = undefined
  private userDepartmentList: string[] = [];
  private userMachineTypeList: string[] = [];

  constructor(private http: HttpClient, private readonly keycloak: KeycloakService, private serverRequestService: ServerRequestService, private stateService: SelectionStateService, private userDataService: UserdataService, private formBuilder: FormBuilder, private spinner: NgxSpinnerService) {


    this.allMachineTypes$ = this.serverRequestService.getAllMachineTypes();
    this.allDepartments$ = this.serverRequestService.getAllDepartments();
    //this.allOrders$ = this.serverRequestService.getAllProductionOrders();
    //this.user$ = this.stateService.getSelectedUser();
    //this.selectedProductionOrder$ = this.stateService.getSelectedProductionOrder();



    this.userDataService.updateUserdata()
    this.userDataService.getUserdata().subscribe(value => {
      console.log(value)
      this.userData = value
      this.formGroup.value.jumpToMainPageSetting = this.userData?.attributes?.JumpToMain;
      this.formGroup.value.jumpToMaskPageSetting = this.userData?.attributes?.JumpToMask;

      this.userDepartmentList = this.userData?.attributes?.DefaultDepartment.split(', ')
      this.userMachineTypeList = this.userData?.attributes?.DefaultMachineType.split(', ')
      this.updateUserSelectionDepartments()
      this.updateUserSelectionMachineTypes()

    })

    this.allDepartments$.subscribe(departments => {
      this.allDepartments = departments
      this.updateUserSelectionDepartments()
    })
    this.allMachineTypes$.subscribe(machineTypes => {
      this.allMachineTypes = machineTypes
      this.updateUserSelectionMachineTypes()
    })
    this.spinner.show();
  }

  updateUserSelectionDepartments()
  {
    this.selectedDepartments = this.allDepartments.filter(dept => this.userDepartmentList?.find(userDept => userDept == dept.externalId) != undefined)
      this.changeDepartmentSelection()
  }
   updateUserSelectionMachineTypes()
  {
    this.selectedMachineTypes = this.allMachineTypes.filter(maType => this.userMachineTypeList?.find(userMaType => userMaType == maType.externalId) != undefined)
      this.changeMachineTypeSelection()
  }

  ngOnInit(): void {

  }


  changeMachineTypeSelection() {
    if(this.selectedMachineTypes != undefined && this.selectedMachineTypes.length > 0) {
      this.allSelectedMachineType = this.allMachineTypes.length == this.selectedMachineTypes.length;
    }
  }

  changeDepartmentSelection() {
    if(this.selectedDepartments != undefined && this.selectedDepartments.length > 0) {
      this.allSelectedDepartment = this.allDepartments.length == this.selectedDepartments.length;
    }
  }

  compareSelection(object1: any, object2: any) {
    return object1 && object2 && object1.externalId == object2.externalId;
  }

  sort(data: any) {
    return data.sort((a: { externalId: string; }, b: { externalId: string; }) => parseInt(a.externalId) - parseInt(b.externalId))
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
      this.selectedMachineTypes = this.allMachineTypes
      this.changeMachineTypeSelection()
    } else {
      this.selectedMachineTypes = []
      this.changeMachineTypeSelection()
    }
  }

  changeUserData(formGroup: FormGroup)
  {
    console.log(formGroup)
    let departmentList = this.selectedDepartments?.map(value => value.externalId)
    let machineTypeList = this.selectedMachineTypes?.map(value => value.externalId)
    let departmentListString = (departmentList != undefined) ? departmentList.join(', ') : "";
    let machineTypeListString = (machineTypeList != undefined) ? machineTypeList.join(', ') : "";
    const userData = {
      attributes: {
        JumpToMain: formGroup.value.jumpToMainPageSetting,
        JumpToMask: formGroup.value.jumpToMaskPageSetting,
        DefaultDepartment: departmentListString,
        DefaultMachineType: machineTypeListString
      }
    };
    console.log(userData)
    this.userDataService.changeUserdata(userData);
  }

  save()
  {
    //localStorage.setItem('machineTypeSelection', JSON.stringify(this.selectedMachineType));
  }

  isDev() {
      return this.keycloak.getUserRoles().includes("dev")
  }


  urlEndpoint = '/manufacturingTerminalBackend/data';
  urlDevRunMapping = environment.baseUrl + this.urlEndpoint + '/dev/postHazel';

  updateFP() {

    const body =
      [{
        "eventType" : "RUN_MAPPING",
        "eventTopic": "RunMappingTopic",
        "eventContent" : {
          "mappingName": "Odoo_to_FP",
          "useSavedData": "false",
          "isSimulate": "false"
        }
      }]

    this.http.post(this.urlDevRunMapping, body).subscribe(
      (data) => {}, (error: HttpErrorResponse) => {
      });
  }

  updateHC() {

    const body =
      [{
        "eventType" : "RUN_MAPPING",
        "eventTopic": "RunMappingTopic",
        "eventContent" : {
          "mappingName": "FP_to_HiCuMES",
          "useSavedData": "false",
          "isSimulate": "false"
        }
      }]

    this.http.post(this.urlDevRunMapping, body).subscribe(
      (data) => {}, (error: HttpErrorResponse) => {
      });
  }
}
