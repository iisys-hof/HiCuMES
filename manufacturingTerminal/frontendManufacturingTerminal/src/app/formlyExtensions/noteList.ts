import {Component, ViewChild, TemplateRef, OnInit, ElementRef, AfterViewInit, OnChanges} from '@angular/core';
import { FormlyFieldConfig, FieldArrayType } from '@ngx-formly/core';
import { TableColumn } from '@swimlane/ngx-datatable/lib/types/table-column.type'
import * as moment from "moment";
@Component({
  selector: 'formly-notelist',
  template: `
    <span *ngIf="field.props?.headerTitle" class="note-header">{{field.props?.headerTitle}}</span>
    <mat-list role="list">
      <mat-list-item class="note-item" *ngFor="let note of model">{{note.noteString}} - {{note.userName}} {{getTimeStamp(note?.creationDateTime)}}</mat-list-item>
    </mat-list>
  `,
  styles: [`
    .note-header{
      -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
      font: 400 14px / 20px Roboto, "Helvetica Neue", sans-serif;
      letter-spacing: normal;
      caption-side: bottom;
      border-collapse: collapse;
      font-family: Roboto, "Helvetica Neue", sans-serif;
      box-sizing: border-box;
      text-align: -webkit-match-parent;
      border-color: inherit;
      border-style: solid;
      border-width: 0;
      font-weight: 500;
      color: rgba(0, 0, 0, 0.54);
      padding: 0 0 0 5px !important;
      border-bottom: 0 !important;
      font-size: 13px;
      margin-bottom: 0px;
      width: calc(100%);
    }

    .note-item{
      text-align: var(--bs-body-text-align);
      -webkit-text-size-adjust: 100%;
      -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
      font: 400 14px / 20px Roboto, "Helvetica Neue", sans-serif;
      letter-spacing: normal;
      caption-side: bottom;
      border-collapse: collapse;
      font-family: Roboto, "Helvetica Neue", sans-serif;
      box-sizing: border-box;
      border-color: inherit;
      border-style: solid;
      border-width: 0;
      color: rgba(0, 0, 0, 0.87);
      padding: 0 0 0 5px !important;
      border-bottom: 0 !important;
      font-size: 13px;
      margin-bottom: 0px;
      width: calc(100%);
      ::ng-deep .mat-list-item-content{
        padding: 0 !important;
      }
    }

    .mat-list-item{
      height: auto;
    }

  `]
})
export class FormlyNoteList extends FieldArrayType implements AfterViewInit {
  @ViewChild('defaultColumn') public defaultColumn?: TemplateRef<any>;

  ngAfterViewInit() {
  //   console.log("this.field", this.field);
     console.log("this.model", this.model);
  //   console.log("this.form", this.form);
  //   console.log("this.key", this.key);
  }

  getTimeStamp(t: any)
  {
    if(t != undefined) {
      const date = moment(t, 'YYYY-MM-DD HH:mm')
      //console.log("DATE", date);
      if(this.field.props?.dateFormat)
      {
          return date.format(this.field.props?.dateFormat);
      }
      else
      {
         return date.format('DD.MM.YYYY HH:mm');
      }
    }
    else{
      return ""
    }
  }

  getField(field: FormlyFieldConfig, column: number, rowIndex: number ): FormlyFieldConfig {
    // @ts-ignore
    return field.fieldGroup[rowIndex].fieldGroup.find(f => f.id === field.props?.columns[column].id);
  }

  getValue(prop: any, row: any) {
    var element = this.model[row];
    var props = prop.split(".");
    var value = this.reflectionGet(element, props);
    return value
  }

  private reflectionGet(element: any, props: []): any
  {
    var prop = props.shift()
    // console.log("Get ", props, prop, element)
    if(prop != undefined)
    {
      var obj = Reflect.get(element, prop)
      if(obj != undefined) {
        return this.reflectionGet(obj, props)
      }
    }
    else {
      return element
    }
  }

  getRowClass(field: FormlyFieldConfig, rowIndex: number) {
    var value = "Table_row_";
    value += this.getValue(field.props?.columns[0].prop, rowIndex);
    value = value.replace(" ", "_")
    return value;
  }
}

