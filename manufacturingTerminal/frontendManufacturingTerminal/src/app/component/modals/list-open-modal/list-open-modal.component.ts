import {Component, Inject, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from "@angular/material/dialog";
import {UiBuilderService} from "../../../service/ui-builder.service";
import {ServerRequestService} from "../../../service/server-request.service";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {NgxSpinnerService} from "ngx-spinner";
import * as _ from "lodash";
import {ComponentType} from "@angular/cdk/overlay";
import {OverheadCostNoteModal} from "../overhead-cost-note-modal/overhead-cost-note-modal";
import {OverheadCosts} from "../../../interfaces/overhead-costs";

@Component({
  selector: 'app-list-open-modal',
  templateUrl: './list-open-modal.component.html',
  styleUrls: ['./list-open-modal.component.scss']
})
export class ListOpenModalComponent implements OnInit, OnChanges{

  tableForm = new FormGroup({});
  tableLayoutOverheadCosts: any;
  tableLayoutMachineOccupations: any;
  title: any = "Achtung! Nicht alle Buchungen sind beendet oder unterbrochen!";
  continueText: any = "Dennoch abmelden";
  constructor(private dialogRef: MatDialogRef<ListOpenModalComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private spinner: NgxSpinnerService, private uiBuilder: UiBuilderService, private serverRequestService: ServerRequestService) {
//console.log(data)

    //console.log("AAAAA - logout component")
    if(data?.title)
    {
      this.title = data.title
    }
    if(data?.continueText)
    {
      this.continueText = data.continueText
    }
    this.serverRequestService.loadOpenOverheadCosts()
    this.serverRequestService.loadOpenMachineOccupations()
    this.serverRequestService.getAllOpen().subscribe(value =>
      {
        //console.log(value)
        this.data = value;
      }
    )
    this.uiBuilder.getDynamicFormLayout("overheadCostsOpen").subscribe((tableLayout) => {
      this.tableLayoutOverheadCosts = tableLayout;
    });
    this.uiBuilder.getDynamicFormLayout("machineOccupationsOpen").subscribe((tableLayout) => {
      this.tableLayoutMachineOccupations = tableLayout;
    });

    this.spinner.show();
  }


  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  onSubmit()
  {
    this.dialogRef.close(undefined)
  }

  onClose() {
    this.dialogRef.close(undefined)
  }

  onLogout() {
    this.dialogRef.close({forceLogout: true})
  }

  onEnterCostCenter() {

  }

}
