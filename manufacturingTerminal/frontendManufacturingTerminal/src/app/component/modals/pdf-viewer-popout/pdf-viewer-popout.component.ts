import { Component, Inject, AfterViewInit, ViewChild, ViewContainerRef, TemplateRef } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import {FormGroup} from "@angular/forms";
import * as _ from "lodash";
import {FormlyFieldConfig} from "@ngx-formly/core";

@Component({
  selector: 'app-pdf-viewer-popout',
  template: `
    <div mat-dialog-content style="height: 100% !important; max-height: unset; overflow: hidden;">
      <div style="display: flex;
                    height: 100%;
                    width: 100%;
                    justify-content: center;">
        <as-split [direction]="splitDirection" [gutterSize]="15" style="height: 100%;" (dragStart)="hideWhileDrag()"
                  (dragEnd)="showAfterDrag()">
          <as-split-area *ngFor="let view of numberOfViews" [minSize]="10"
                         [maxSize]="80">
            <form *ngIf="afterView" [formGroup]="form" style="height: 100%">
              <formly-form [fields]="formLayout" [form]="form" [model]="model"></formly-form>
            </form>
          </as-split-area>

        </as-split>
      </div>
    </div>
    <div mat-dialog-actions>
      <button color="primary" mat-stroked-button mat-dialog-close="">Schließen</button>
      <span class="example-spacer"></span>
      <div class="buttons">
        <button [disabled]="numberOfViews.length >= 4" class="mat-icon-btn" color="primary" mat-stroked-button
                (click)="addView()">
          <mat-icon>add</mat-icon>
        </button>
        <button [disabled]="numberOfViews.length <= 1" class="mat-icon-btn" color="primary" mat-stroked-button
                (click)="removeView()">
          <mat-icon>remove</mat-icon>
        </button>
        <button [disabled]="numberOfViews.length <= 1" class="mat-icon-btn" color="primary" mat-stroked-button
                (click)="switchViewDirection()">
          <mat-icon>screen_rotation</mat-icon>
        </button>
      </div>
    </div>
  `,
})
export class PdfViewerPopoutComponent implements AfterViewInit {

  form = new FormGroup({});
  model: any = {};
  formLayout: any = [{
    fieldGroup: []
  }];
  afterView = false;
  private fieldConfig: any;
  numberOfViews: number[] = [1];
  splitDirection:  "horizontal" | "vertical" = "horizontal"

  constructor(@Inject(MAT_DIALOG_DATA) public data: { field: any }) {
    this.fieldConfig = _.cloneDeep(this.data)
  }

  ngAfterViewInit(): void {
    //console.log(this.fieldConfig)
    if (this.data) {
      this.fieldConfig.wrappers.shift()
      this.fieldConfig.className = ""
      this.fieldConfig.style = "height: 100%"
      this.formLayout[0].fieldGroup.push(this.fieldConfig)
      //console.log(this.formLayout)
      this.afterView = true
    } else {
    }
  }

  addView() {
    if(this.numberOfViews.length < 4)
    {
      this.numberOfViews.push(1)
    }
  }

  removeView() {
    if(this.numberOfViews.length > 1)
    {
      this.numberOfViews.pop()
    }
  }

  hideWhileDrag() {
    $("ng2-pdfjs-viewer").css('visibility', 'hidden').parent().css({"background-color": "#eeeeee", "padding": "10px"})
  }

  showAfterDrag() {
    $("ng2-pdfjs-viewer").css('visibility', '').parent().css({"background-color": "", "padding": ""})
  }

  switchViewDirection() {
    if(this.splitDirection == "horizontal"){
      this.splitDirection = "vertical"
    }
    else {
      this.splitDirection = "horizontal"
    }
  }
}
