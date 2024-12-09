import { Component, ViewChild, TemplateRef } from '@angular/core';
import { FieldWrapper } from '@ngx-formly/core';
import { MatDialog } from '@angular/material/dialog';
import { PdfViewerPopoutComponent } from '../component/modals/pdf-viewer-popout/pdf-viewer-popout.component';

@Component({
  selector: 'mat-panel-modal',
  template: `
    <div class="card" style="height: 100%">
      <div class="card-header">
        <h3 style="float: left">{{ props.label }}</h3>
        <button type="button" style="float: right" (click)="openModal()"
                mat-icon-button>
          <mat-icon>fullscreen</mat-icon>
        </button>
      </div>
      <div class="card-body" style="white-space: pre-wrap">
          <ng-container #fieldComponent></ng-container>
      </div>
    </div>
  `,
})
export class MatpanelModalWrapper extends FieldWrapper {
  @ViewChild('fieldComponent', { static: true })
  templateRef!: TemplateRef<any>;

  constructor(public dialog: MatDialog) {
    super();
  }

  openModal(): void {
    //console.log("test")
    //console.log(this.fieldComponent)
    //console.log(this.field)
    //console.log(this.to)
    this.dialog.open(PdfViewerPopoutComponent, {data: this.field});
  }
}
