import {Component, Inject, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UiBuilderService} from "../../../service/ui-builder.service";
import {ServerRequestService} from "../../../service/server-request.service";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";

@Component({
  selector: 'app-pause-modal',
  templateUrl: './pause-modal.component.html',
  styleUrls: ['./pause-modal.component.scss']
})
export class PauseModalComponent implements OnInit, OnChanges{

  //TODO AUS DB LADEN
  suspensionTypes: any =[
    {value: 'BREAK_DEFAULT', viewValue: 'Pause'},
    {value: 'BREAK_GeneralExpense', viewValue: 'Gemeinkosten'},
    {value: 'BREAK_MissingMaterial', viewValue: 'Unterartikel fehlt'},
    {value: 'BREAK_OrderChange', viewValue: 'Auftragswechsel'},
    {value: 'BREAK_ShiftChange', viewValue: 'Schichtwechsel / Arbeitsende'},
  ];
  selected: string = this.suspensionTypes[0].value;

  machineOccupation: CamundaMachineOccupation;

  constructor(private dialogRef: MatDialogRef<PauseModalComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private serverRequestService: ServerRequestService) {
    this.machineOccupation = data
  }


  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  onSubmit()
  {
    //console.log(this.machineOccupation)
    if(this.machineOccupation != undefined)
    {
      this.dialogRef.close(this.selected)
    }
  }

  onClose() {
    this.dialogRef.close(undefined)
  }
}
