import {
  Component,
  ComponentFactoryResolver,
  ComponentRef,
  ElementRef,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import {FieldType} from '@ngx-formly/material/form-field';
import {FormControl} from "@angular/forms";
import {FieldWrapper} from "@ngx-formly/core";
import {get} from "lodash";
import {PdfViewerComponent} from "../iframe/pdf-viewer/pdf-viewer.component";

@Component({
  selector: 'formly-video-player',
  template: `
    <video *ngIf="src" [src]="src" controls style="display: flex; align-content: center; align-items: center; padding: 15px; width:100%">
  `,
})
export class FormlyVideoPlayer extends FieldWrapper {
  // @ts-ignore
  formControl: FormControl;
  src: string = "";

  constructor() {
    super();
  }

  ngOnInit() {
    console.log("this.field", this.field);
    console.log("this.model", this.model);
    console.log("this.form", this.form);
    console.log("this.key", this.key);
    let t = get(this.model, this.key)
    console.log(t)
    this.src = t
    console.log(this.src)
  }

}
