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
  selector: 'formly-pdf-viewer',
  template: `
    <iframe id="embedded" [src]="src | safe" style="height: 50vh; width: 100%;"></iframe>
  `,
})
export class FormlyPdfviewer extends FieldWrapper {
  // @ts-ignore
  formControl: FormControl;
  src: string = "/(root:extended-pdf-viewer)";

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
    this.src = "/(root:extended-pdf-viewer)" + "?src="+ t;
    console.log(this.src)
  }

}
