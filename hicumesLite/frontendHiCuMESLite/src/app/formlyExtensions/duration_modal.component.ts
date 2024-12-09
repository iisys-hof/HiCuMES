import {Component, TemplateRef, ViewChild} from '@angular/core';
import {get} from "lodash";
import {DecimalPipe, JsonPipe} from "@angular/common";
import {FieldType} from "@ngx-formly/material";
import {MatDialog} from "@angular/material/dialog";
import * as moment from "moment/moment";

@Component({
  selector: 'formly-duration-modal',
  template: `

    <ng-template #openModalView>
      <h2 matDialogTitle class="zeiten">Details</h2>
      <div >
        <div *ngFor="let item of getTimeDetails()"class="zeiten">
          <span class="title"><b>{{item.key}}</b>:</span>
          <span class="value"> {{item.value}} </span>
          <span class="value"> {{'(' + item.value2 + ')'}} </span>
        </div>
      </div>
      <mat-dialog-actions align="end">
        <button mat-stroked-button mat-dialog-close>Schließen</button>
      </mat-dialog-actions>
    </ng-template>

    <button mat-button
            matTooltip="{{getTimeAutoHours()}}" matTooltipPosition="left" (click)="openModal()" style="text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    display: block;
    width: 120px;
}">{{getTimeAutoPretty()}}</button>
  `
})
export class FormlyDurationModal extends FieldType<any> {

  @ViewChild('openModalView') openModalView!: TemplateRef<any>
  constructor(private dialog: MatDialog, private jsonPipe: JsonPipe) {
    super()
  }

  openModal() {
    let dialogRef = this.dialog.open(this.openModalView);
    dialogRef.afterClosed().subscribe(result => {})
  }
  getFieldJSON(): any {
    let t = null;
    try
    {
      t = JSON.parse(get(this.model, this.key))
    }
    catch (e)
    {
      t = get(this.model, this.key)
    }
    //console.log(t)
    return this.jsonPipe.transform(t);
  }

  getField(): any {
    if(this.field.props?.type)
    {
      if(this.field.props?.type == "json")
      {
        return this.getFieldJSON()
      }
    }
    return get(this.model, this.key)
  }

  getTimeAutoPretty()
  {
    return this.formatTimePretty(this.getTimeAuto())
  }

  getTimeAutoHours()
  {
    return this.formatTimeHours(this.getTimeAuto())
  }

  getTimeDetails() {
    let bookingEntry = this.model
    let durationStepWork = bookingEntry?.machineOccupation.subProductionStep?.timeDurations?.work
    let durationStepBreak = bookingEntry?.machineOccupation.subProductionStep?.timeDurations?.break
    let durationMOWork = bookingEntry?.machineOccupation?.timeDurations?.work
    let durationMOBreak = bookingEntry?.machineOccupation?.timeDurations?.break
    let string =    [
        {key: "Arbeitszeit Schritt", value: this.formatTimePretty(durationStepWork), value2: this.formatTimeHours(durationStepWork)},
        {key: "Unterbrechungsszeit Schritt", value: this.formatTimePretty(durationStepBreak), value2: this.formatTimeHours(durationStepBreak)},
        {key: "Arbeitszeit AG", value: this.formatTimePretty(durationMOWork), value2: this.formatTimeHours(durationMOWork)},
        {key: "Unterbrechungsszeit AG", value: this.formatTimePretty(durationMOBreak), value2: this.formatTimeHours(durationMOBreak)},
        {key: "MeldeZeit", value: this.formatTimePretty(this.getTimeAuto()), value2: this.formatTimeHours(this.getTimeAuto())},
    ]

    return string
  }

  getTimeAuto() {
    let bookingEntry = this.model
    let durationAuto = 0;
    if(bookingEntry?.stepTime)
    {
      durationAuto = bookingEntry?.machineOccupation?.subProductionStep?.timeDurations?.work
    }
    else
    {
      durationAuto = bookingEntry?.machineOccupation?.timeDurations?.work
    }
    return durationAuto;
  }

  private formatTimeHours(time: number)
  {
    return moment.duration(time, "seconds").asHours().toFixed(2) + " h"
  }

  private formatTimePretty(time: number)
  {
    return moment.duration(time, "seconds").locale("de")
  }
}
