import {Component, OnChanges, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ServerRequestService} from "../../service/server-request.service";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {Observable} from "rxjs";
import {DisableResponseButtonStateService} from "../../service/disable-response-button-state.service";
import {Userdata, UserdataService} from "../../service/userdata.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserOccupation} from "../../interfaces/user-occupation";

@Component({
  selector: 'app-subproductionstep-formfield',
  templateUrl: './subproductionstep-formfield.component.html',
  styleUrls: ['./subproductionstep-formfield.component.scss']
})
export class SubproductionstepFormfieldComponent implements OnInit {
  machineOccupation$: Observable<CamundaMachineOccupation | undefined> | undefined;
  userOccupation$: Observable<UserOccupation | undefined> | undefined;
  machineOccupation: CamundaMachineOccupation | undefined;
  machineOccupationId:any = null;

  userData: Userdata | undefined = undefined;
  constructor(private route: ActivatedRoute,
              private router: Router, private serverRequestService: ServerRequestService,
              private disableResponseButtonStateService: DisableResponseButtonStateService,
              private userDataService: UserdataService,
              private snackBar: MatSnackBar) {
  }


  openSnackBar() {
    this.snackBar.open('Buchung abgeschickt!', "", {
      panelClass: "info-snackbar",
      duration: 3000
    });
  }

  ngOnInit(): void {
    this.reloadEverything();

    this.userDataService.getUserdata().subscribe(
      data => {
        this.userData = data;

        //console.log(this.userData)
      }
    )

    //this.router.events.subscribe((event) => {
      //console.log("EVENT", event);

    //});
    //console.log("e", this.route.snapshot.url.toString().includes("ConfirmProductionTask"));


    this.machineOccupationId = this.route.snapshot.paramMap.get('id');
    if(this.machineOccupationId != null) {
      this.serverRequestService.loadMachineOccupationById(this.machineOccupationId);
      this.serverRequestService.loadUserOccupationByCMOId(this.machineOccupationId);
      this.machineOccupation$ = this.serverRequestService.getMachineOccupationsFilterById();
      this.userOccupation$ = this.serverRequestService.getUserOccupation()
      this.machineOccupation$.subscribe((machineOccupation) => {
        this.machineOccupation = machineOccupation;
      })
    }
    else {
      //TODO hinweis anzeigen
    }
  }


  finishFormfield(filledFormfield: any, suspensionType?: string, sendToCamund?: boolean) {
    let taskId = this.machineOccupation?.camundaSubProductionSteps.slice(-1)[0].taskId

    let formfield: any =
    {
      taskId: taskId,
      id: this.machineOccupation?.id,
      formField: filledFormfield
    }
    if(suspensionType)
    {
      formfield["suspensionType"] = suspensionType
    }
    if(sendToCamund)
    {
      formfield["sendToCamunda"] = sendToCamund
    }

    this.serverRequestService.finishSubProductionStep(formfield);
    this.disableResponseButtonStateService.disableButton(taskId);
    if(this.userData?.attributes.JumpToMain)
    {
      this.router.navigate(['/']);
    }

    this.openSnackBar()
    //TODO: Add routing, if user enabled it in setting
    //
    /*.then(() => {
      setTimeout(() => {
      this.reloadEverything();
    }, 2000);
    });*/
  }

  finishFormfieldWithPause(contents: any) {
    this.finishFormfield(contents.filledFormfield, contents.suspensionType)
  }

  private reloadEverything() {
    this.serverRequestService.loadMachines();
    //this.serverRequestService.loadProductionOrders();

    if(this.machineOccupationId != null) {
      this.serverRequestService.loadMachineOccupationById(this.machineOccupationId);
      this.serverRequestService.loadUserOccupationByCMOId(this.machineOccupationId);
    }
    //this.serverRequestService.loadAllMachineOccupations();
  }

  continueMachineOccupation() {
    if(this.machineOccupation != undefined)
    {
      this.serverRequestService.continueMachineOccupation(this.machineOccupation);
    }
  }

  pauseMachineOccupation($event: any) {

    if(this.machineOccupation != undefined && $event != undefined) {
      this.serverRequestService.pauseMachineOccupation(this.machineOccupation, $event);

      if($event == 'BREAK_GeneralExpense')
      {
        this.router.navigate(["overhead-costs"])
      }
    }

  }

  joinMachineOccupation() {
    if(this.machineOccupation != undefined) {
      this.serverRequestService.joinMachineOccupation(this.machineOccupation);
    }
  }
}
