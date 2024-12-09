import {AfterViewInit, Component, ElementRef, EventEmitter, Inject, OnInit, Output, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {UiBuilderService} from "../../../service/ui-builder.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ServerRequestService} from "../../../service/server-request.service";
import {MatStepper} from "@angular/material/stepper";
import {toString} from "lodash";
import {Machine} from "../../../interfaces/machine";
import {Router} from "@angular/router";

@Component({
  selector: 'app-barcode-input-modal',
  templateUrl: './barcode-input-modal.component.html',
  styleUrls: ['./barcode-input-modal.component.scss']
})
export class BarcodeInputModalComponent implements  OnInit {

  machineOccupationInput: string = ""
  machineOccupations: any
  machines: any
  machineInput: string = "";

  scannedMachineOccupation: CamundaMachineOccupation | undefined;
  scannedMachine: any | undefined;
  withMachineSelection = true
  machineId: number = 0

  firstFormGroup!: FormGroup;
  secondFormGroup!: FormGroup;
  private targetId = 'input0';
  @ViewChild("inputMachineOccupation") inputMachineOccupation!: ElementRef

  constructor(private router: Router, private dialogRef: MatDialogRef<BarcodeInputModalComponent>,@Inject(MAT_DIALOG_DATA) public data: any, private uiBuilder: UiBuilderService, private formBuilder: FormBuilder, private serverRequestService: ServerRequestService) { }

  ngOnInit() {
    //console.log(this.data)
    this.firstFormGroup = this.formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this.formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }

  setFocus() {
    const targetElem = document.getElementById(this.targetId);
    targetElem?.focus();
  }

  setTargetId(event: any) {
    console.log(event)
    if(event.selectedIndex < event.previouslySelectedIndex)
    {
      this.withMachineSelection = true
      this.scannedMachine = undefined
    }
    this.targetId = `input${event.selectedIndex}`;
  }


  onEnterOccupation(stepper: MatStepper) {
    //console.log(this.data["camundaMachinOccupations"])
    if(this.machineOccupationInput.includes("-"))
    {
      this.machineOccupationInput = this.machineOccupationInput.replace(/-/g, "")
    }

    this.machineOccupationInput = this.machineOccupationInput.replace(/\s+/g, "")
    let filteredByInput = this.data["camundaMachinOccupations"].filter((value: CamundaMachineOccupation) => value?.machineOccupation?.productionOrder?.name == this.machineOccupationInput.substring(0, this.machineOccupationInput.length-3) && value.machineOccupation.name == this.machineOccupationInput.substring(this.machineOccupationInput.length-3, this.machineOccupationInput.length) )
    //console.log(filteredByInput)
    this.scannedMachineOccupation = filteredByInput[0]
    if(this.scannedMachineOccupation?.machineOccupation?.machine !== null && this.scannedMachineOccupation?.machineOccupation?.machine !== undefined) {
      this.scannedMachine = this.scannedMachineOccupation.machineOccupation.machine
      this.withMachineSelection = false
    }
    if(this.scannedMachineOccupation)
    {
      //console.log(this.scannedMachineOccupation)
      if(this.scannedMachineOccupation.machineOccupation.status == "READY_TO_START")
      {
        stepper.next()
      }
      else if(this.scannedMachineOccupation.machineOccupation.status == "RUNNING" || this.scannedMachineOccupation.machineOccupation.status == "PAUSED")
      {
        var path = this.scannedMachineOccupation.camundaSubProductionSteps.slice(-1)[0]?.formKey

        this.router.navigate(['/formfields', {id: this.scannedMachineOccupation.id}]);

        this.dialogRef.close(false);
      }
    }
    else
    {
      this.firstFormGroup.setErrors(["NA"])
      //console.log(this.firstFormGroup)
      {

      }
    }
  }

  onEnterMachine(stepper: MatStepper) {
    //console.log(this.data["machines"])
    let filteredByInput = this.data["machines"].filter((value: Machine) => value.externalId == this.machineInput)
    //console.log(filteredByInput)
    this.scannedMachine = filteredByInput[0];
    if(this.scannedMachine)
    {
      stepper.next()
    }
    else
    {
      this.secondFormGroup.setErrors(["NA"])
      console.log(this.firstFormGroup)
      {

      }
    }
  }

  updateOccupation() {


  }

  submit() {
    //console.log(this.scannedMachineOccupation, this.machineId)
    if(this.scannedMachineOccupation !== undefined)
    {
      this.serverRequestService.startMachineOccupationById(this.scannedMachineOccupation.id, this.scannedMachine.id);
      this.dialogRef.close(true);
    }
  }
}
