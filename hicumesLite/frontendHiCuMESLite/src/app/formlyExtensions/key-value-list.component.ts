
import {Component, ViewChild, TemplateRef, OnInit, ElementRef, AfterViewInit, OnChanges} from '@angular/core';
import {FormlyFieldConfig, FieldArrayType, FieldArrayTypeConfig} from '@ngx-formly/core';
import * as _ from 'lodash';
@Component({
  selector: 'formly-key-value-list',
  template: `

    <mat-list style="margin-bottom: 10px">
      <mat-list-item class="key-value-list" *ngFor="let entry of field.props?.columns; index as index" style="height: auto; margin-bottom: 5px;">
        <div style="display: flex;
    width: 100%;
    justify-content: space-between;
    flex-direction: row;" class="mat-cell"><span style="height: auto;">{{entry.name}}: </span><span><formly-field [field]="getField(field, index, 0)"></formly-field></span></div>
      </mat-list-item>
    </mat-list>

  `,
})
export class KeyValueListComponent extends FieldArrayType<FieldArrayTypeConfig> implements AfterViewInit, OnInit {
  @ViewChild('defaultColumn') public defaultColumn?: TemplateRef<any>;

  ngOnInit() {
  }

  ngAfterViewInit() {
  //   console.log("this.field", this.field);
  //   console.log("this.model", this.model);
  //   console.log("this.form", this.form);
  //   console.log("this.key", this.key);
  }

  getField(field: FormlyFieldConfig, column: number, rowIndex: number ): FormlyFieldConfig {
    // @ts-ignore
    return field.fieldGroup[rowIndex].fieldGroup.find(f => f.id === field.templateOptions?.columns[column].id);
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
