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

@Component({
  selector: 'formly-pdf-js-viewer',
  template: `
    <mat-tab-group *ngIf="src && isMultipleDocs()" style="height: 100%; width: 100%;">
      <mat-tab *ngFor="let doc of this.src; index as i " label="Dokument {{i+1}}">
        <ng2-pdfjs-viewer pdfSrc="{{doc}}" locale="de-DE"
                          [showSpinner]="true"></ng2-pdfjs-viewer>
      </mat-tab>
    </mat-tab-group>

    <div style="height: 100%; width: 100%;">
    <ng2-pdfjs-viewer *ngIf="src && !isMultipleDocs()" pdfSrc="{{this.src}}" locale="de-DE"
                      [showSpinner]="true"></ng2-pdfjs-viewer>
    </div>
  `,
})
export class FormlyPdfjsviewer extends FieldWrapper {
  // @ts-ignore
  formControl: FormControl;
  src: any;

  constructor() {
    super();
  }

  ngOnInit() {
    //console.log("this.field", this.field);
    //console.log("this.model", this.model);
    //console.log("this.form", this.form);
    //console.log("this.key", this.key);
    let t = "/assets/demoComp.pdf" //get(this.model, this.key)
    //let t = ["/assets/UITestdata/test_doc1.pdf"] //get(this.model, this.key)
    //let t = ["", "", "/assets/UITestdata/test_doc1.pdf", "/assets/UITestdata/test_doc2.pdf"] //get(this.model, this.key)
    //let t = '["", "", "/assets/UITestdata/test_doc1.pdf", "/assets/UITestdata/test_doc2.pdf"]' //get(this.model, this.key)
    //let t = get(this.model, this.key)
    //console.log(typeof t)
    //console.log(t)
    if(Array.isArray(t))
    {
      this.src = t;
    }
    else{
        try {
          //console.log(t, JSON.parse(t))
          this.src = JSON.parse(t)
        }
        catch
        {
          //console.log(t)
          this.src = t;
        }
    }
    //console.log(this.src)
  }

  isMultipleDocs(): boolean
  {
    return Array.isArray(this.src) && this.src.length > 1;
  }

}
