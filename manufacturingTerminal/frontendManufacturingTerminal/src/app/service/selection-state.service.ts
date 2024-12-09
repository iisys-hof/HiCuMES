import {EventEmitter, Injectable} from '@angular/core';
import {combineLatest, ReplaySubject, Subject} from "rxjs";
import {Machine, MachineType} from "../interfaces/machine";
import {ProductionOrder} from "../interfaces/production-order";
import {User} from "../interfaces/user";
import {CamundaMachineOccupation} from "../interfaces/camunda-machine-occupation";
import {ServerRequestService} from "./server-request.service";
import * as _ from "lodash";
import {Department} from "../interfaces/department";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class SelectionStateService {

  private selectedMachine$: Subject<Machine>;
  private selectedMachineTypes$: Subject<MachineType[]>;
  private selectedMachineTypes: MachineType[] | undefined = [];
  private selectedMachineTypesExtId$: Subject<string[]>;
  private selectedDepartments$: Subject<Department[]>;
  private selectedDepartments: Department[] | undefined = [];
  private selectedDepartmentsExtId$: Subject<string[]>;
  private currentMachineOccupation: Subject<CamundaMachineOccupation>;
  //private selectedProductionOrder$: Subject<ProductionOrder>;
  //private selectedUser$: Subject<User>;

  constructor() {
    this.selectedMachine$ = new ReplaySubject<Machine>(1);
    this.selectedMachineTypes$ = new ReplaySubject<MachineType[]>(1);
    this.selectedMachineTypesExtId$ = new ReplaySubject<string[]>(1);
    this.selectedDepartments$ = new ReplaySubject<Department[]>(1);
    this.selectedDepartmentsExtId$ = new ReplaySubject<string[]>(1);
    this.currentMachineOccupation = new ReplaySubject<CamundaMachineOccupation>(1);
    this.selectedMachineTypes$.next(undefined)
    this.selectedDepartments$.next(undefined)
    //this.selectedProductionOrder$ = new ReplaySubject<ProductionOrder>();
    //this.selectedUser$ = new ReplaySubject<User>();

    //this.selectedUser$.next({name: "Karl Klammer"});
    //this.selectedProductionOrder$.next(undefined);
  }

  selectMachine(machine: Machine) {
    this.selectedMachine$.next(machine);
  }

  selectMachineTypes(machineTypes: MachineType[] | undefined) {
    this.selectedMachineTypes$.next(machineTypes);
    this.selectedMachineTypes = machineTypes;
  }

  selectDepartments(departments: Department[] | undefined) {
    console.log(departments)
    this.selectedDepartments$.next(departments);
    this.selectedDepartments = departments;
  }

  updateCurrentMachineOccupation(machineOccupation: CamundaMachineOccupation) {
    this.currentMachineOccupation.next(machineOccupation);
  }

  removeMachineSelection() {
    this.selectedMachine$.next(undefined);
  }

  removeMachineTypeSelection() {
    this.selectedMachineTypes$.next(undefined);
  }

  removeDepartmentSelection() {
    this.selectedDepartments$.next(undefined);
  }


  removeCurrentMachineOccupation() {
    this.currentMachineOccupation.next(undefined);
  }


  getSelectedMachine() {
    return this.selectedMachine$.asObservable();
  }

  getSelectedMachineTypes() {
    return this.selectedMachineTypes$.asObservable();
  }

  getSelectedDepartment() {
    return this.selectedDepartments$.asObservable();
  }

  getCombinedSelectionDepartmentAndMachineType () {
    return combineLatest([this.getSelectedMachineTypes(), this.getSelectedDepartment()])
      .pipe(map(results => ({machineType: results[0], department: results[1]})));
  }

  getCurrentCombinedSelection()
  {
    return { department: this.selectedDepartments != undefined? this.selectedDepartments : [], machineType: this.selectedMachineTypes != undefined? this.selectedMachineTypes : [] }
  }

  getCurrentMachineOccupation() {
    return this.currentMachineOccupation.asObservable();
  }

  /*selectUser(user: User) {
    this.selectedUser$.next(user);
  }*/

  /*selectProductionOrder(productionOrder: ProductionOrder) {
    this.selectedProductionOrder$.next(productionOrder);
  }*/

  /*removeOrderSelection() {
     this.selectedProductionOrder$.next(undefined);
   }*/

  /*getSelectedUser() {
  return this.selectedUser$.asObservable();
  }*/

  /*getSelectedProductionOrder() {
    return this.selectedProductionOrder$.asObservable();
  }*/

}
