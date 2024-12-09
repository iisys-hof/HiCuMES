import {Component, TemplateRef, ViewChild} from '@angular/core';
import {get} from "lodash";
import {DecimalPipe, JsonPipe} from "@angular/common";
import {FieldType} from "@ngx-formly/material";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'formly-simple-modal',
  template: `

    <ng-template #openModalView>
      <h2 matDialogTitle>Details</h2>
      <div>
        <pre>{{getField()}}</pre>
      </div>
      <mat-dialog-actions align="end">
        <button mat-stroked-button mat-dialog-close>Schließen</button>
      </mat-dialog-actions>
    </ng-template>

    <button mat-button
            matTooltip="{{getField()}}" matTooltipPosition="left" (click)="openModal()" style="text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    display: block;
    width: 120px;
}">{{getFieldPlain()}}</button>
  `,
})
export class FormlySimpleModal extends FieldType<any> {

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

  getFieldPlain(): any {
    return get(this.model, this.key)
  }

}
