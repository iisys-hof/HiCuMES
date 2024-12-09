
import {
  Component,
  ViewChild,
  TemplateRef,
  OnInit,
  ElementRef,
  AfterViewInit,
  OnChanges,
  SimpleChanges, AfterViewChecked
} from '@angular/core';
import {FormlyFieldConfig, FieldArrayType, FieldArrayTypeConfig} from '@ngx-formly/core';
import {Sort} from "@angular/material/sort";
import {get} from "lodash";
import * as _ from "lodash";
@Component({
  selector: 'formly-datatable',
  template: `
    <table class="mat-table cdk-table" style="width: 100%" id="datatable" matSort (matSortChange)="sortData($event)" [matSortActive]="getDefaultSort()" [matSortDirection]="getDefaultDirection()">

      <tr class="mat-header-row cdk-header-row ng-star-inserted custom-header-row">
        <ng-container *ngFor="let headerCell of field.props?.columns">
          <th *ngIf="headerCell?.sortable == true" class="mat-header-cell custom-header-cell"
              [ngClass]="getHeaderClass(field, headerCell)"
              mat-sort-header="{{headerCell.id}}"
              style="border-bottom: 1px solid rgba(0, 0, 0, 0.12); padding: 5px 0 5px 5px; width: calc({{getColumnWidthNumber(headerCell, field.props?.columns.length)}})">{{headerCell.name}}</th>
          <th *ngIf="!headerCell?.sortable" class="mat-header-cell custom-header-cell"
              [ngClass]="getHeaderClass(field, headerCell)"
              style="border-bottom: 1px solid rgba(0, 0, 0, 0.12); padding: 5px 0 5px 5px; width: calc({{getColumnWidthNumber(headerCell, field.props?.columns.length)}})">{{headerCell.name}}</th>
        </ng-container>
      </tr>


      <ng-container *ngFor="let row of field.fieldGroup; index as rowIndex">
        <tr class="mat-row cdk-row ng-star-inserted" [ngClass]="getRowClass(field, row, rowIndex)"
            (click)="onRowClick(field, rowIndex)" style="margin:5px 0 5px 0">
          <ng-container *ngFor="let cell of field.props?.columns; index as columnIndex">
            <td class="mat-cell"
                style="border-bottom: 1px solid rgba(0, 0, 0, 0.12); padding: 5px 0 5px 5px; width: calc({{getColumnWidthNumber(cell, field.props?.columns.length)}})">
              <formly-field [field]="getField(field, columnIndex, rowIndex)"></formly-field>
            </td>
          </ng-container>
        </tr>
      </ng-container>
    </table>
    <ng-container *ngIf="hasError()">
      <span class="mat-row cdk-row ng-star-inserted" style="margin: 10px; float: right; color: #dc3545;">
          {{this.field.props.validationMessage}}
      </span>
    </ng-container>
  `,
})
export class FormlyDatatable  extends FieldArrayType implements AfterViewInit, OnInit {
  @ViewChild('defaultColumn') public defaultColumn?: TemplateRef<any>;

  originalData: any
  sortedData: any


  ngOnInit() {
    this.formControl.valueChanges.subscribe(value => {
      //console.log(value)
    })

    this.originalData = this.formControl.value
    //this.sortData({active: this.getDefaultSort(), direction: this.getDefaultDirection()})
    //console.log(this.formControl.value)

  }

  ngAfterViewInit() {
  //   console.log("this.field", this.field);
  //   console.log("this.model", this.model);
  //   console.log("this.form", this.form);
  //   console.log("this.key", this.key);
    this.originalData = this.formControl.value
    this.sortData({active: this.getDefaultSort(), direction: this.getDefaultDirection()})
    //console.log(this.formControl.value)
  }



  getField(field: FormlyFieldConfig, column: number, rowIndex: number ): FormlyFieldConfig {
    // @ts-ignore
    return field.fieldGroup[rowIndex].fieldGroup.find(f => f.id === field.props?.columns[column].id);
  }

  getValue(prop: any, row: any) {
    var element = this.model[row];
    if(typeof prop === 'string' || typeof prop === 'object')
    {
      var props = prop.split(".");
      var value = this.reflectionGet(element, props);
      return value
    }
    else
    {
      return prop
    }
  }

  private reflectionGet(element: any, props: []): any
  {
    const prop = props.shift();
    //console.log("Get ", props, prop, element)
    if(prop != undefined)
    {
      const obj = Reflect.get(element, prop);
      if(obj != undefined) {
        return this.reflectionGet(obj, props)
      }
    }
    else {
      return element
    }
  }

  getRowClass(field: FormlyFieldConfig, row: any,  rowIndex: number) {
    var value = "Table_row_";
    value += this.getValue(field.props?.columns[0].id, rowIndex);
    value = value.replace(" ", "_")
    if(row.className)
    {
      value += " " + row.className
    }

    if(this.field?.props?.markRows)
    {
      let result: boolean = false
      this.field?.props?.markRows?.forEach((expressions: any) =>
      {
        if(result)
        {
          return;
        }
        let value1 = this.getValue(expressions?.exp1, rowIndex)
        let value2 = this.getValue(expressions?.exp2, rowIndex)
        if(value1 == undefined)
        {
          value1 = expressions?.exp1
        }
        if(value2 == undefined)
        {
          value2 = expressions?.exp2
        }
        switch (expressions?.op)
        {
          case ">":
            result = value1 > value2
            break;
          case "<":
            result =  value1 < value2
            break;
          case "==":
            result =  value1 == value2
            break;
          case ">=":
            result =  value1 >= value2
            break;
          case "<=":
            result =  value1 <= value2
            break;
        }
      })
      if(result)
      {
        value += " marked"
      }
    }
    return value;
  }

  onRowClick(field: any, rowIndex: number) {
    if(this.field?.props?.onRowClick) {
      this.field?.props?.onRowClick(field.fieldGroup[rowIndex].model);
    }
  }

  hasError() {
    let hasError = false
    this.field.fieldGroup?.forEach((row:any) => {
      if(row.className)
        {
          hasError = true
        }
      }
    )
    return hasError;
  }


  getColumnWidthNumber(headerCell: any, length: any) {
    //console.log(headerCell)
    if(headerCell.colWidth)
    {
      return headerCell.colWidth + "%"
    }
    else
    {
      return 100/length + "%"
    }
  }

  sortData(sort: Sort) {
    if(this.formControl?.value) {
      const data = this.formControl?.value

      //console.log(sort)
      //console.log(data)
      if (!sort.active || sort.direction === '') {
        this.sortedData = data;
        return;
      }
      // @ts-ignore
      let prop = this.field?.fieldArray?.fieldGroup?.find(f => f.id === sort.active)?.key;
      this.sortedData = data.sort((a: any, b: any) => {
        let aValue = get(a, prop)
        let bValue = get(b, prop)
        if (String(prop).includes("[last()]")) {

          aValue = this.reflectionGet(a, prop.split("."))
          bValue = this.reflectionGet(b, prop.split("."))
        }

        const isAsc = sort.direction === 'asc';
        return this.compare(aValue, bValue, isAsc)
      });
      this.formControl.setValue(this.sortedData)
      //console.log(this.formControl.value)
    }
  }

  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  getDefaultSort() {
    return this.field?.props?.columns?.find((f: any) => f?.defaultSort)?.id;
  }

  getDefaultDirection() {
    return this.field?.props?.columns?.find((f: any) => f?.defaultSort)?.defaultSort;
  }

  getHeaderClass(field: FormlyFieldConfig, headerCell: any) {
    var value = "Table_header_" + headerCell.id;
    if(headerCell.className) {
      value += " " + headerCell.className
    }

    return value;
  }
}
