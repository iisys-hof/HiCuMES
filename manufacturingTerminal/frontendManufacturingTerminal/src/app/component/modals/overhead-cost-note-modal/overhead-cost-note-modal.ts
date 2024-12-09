import {Component, Inject, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UiBuilderService} from "../../../service/ui-builder.service";
import {ServerRequestService} from "../../../service/server-request.service";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {NgxSpinnerService} from "ngx-spinner";
import * as _ from "lodash";

@Component({
  selector: 'app-overhead-cost-note-modal',
  templateUrl: './overhead-cost-note-modal.html',
  styleUrls: ['./overhead-cost-note-modal.scss']
})
export class OverheadCostNoteModal implements OnInit, OnChanges{

  noteText: string | undefined = undefined

  constructor(private dialogRef: MatDialogRef<OverheadCostNoteModal>, @Inject(MAT_DIALOG_DATA) public data: any, private serverRequestService: ServerRequestService, private uiBuilder: UiBuilderService, private spinner: NgxSpinnerService) {

    this.noteText = this.data.note
    this.spinner.show();
  }


  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  onSubmit()
  {
    if(this.noteText == "" || this.noteText == undefined)
    {
      this.noteText = " "
    }
    this.dialogRef.close(this.noteText)
  }

  onClose() {
    this.dialogRef.close(undefined)
  }

  onEnterCostCenter() {

  }

  protected readonly undefined = undefined;

}
