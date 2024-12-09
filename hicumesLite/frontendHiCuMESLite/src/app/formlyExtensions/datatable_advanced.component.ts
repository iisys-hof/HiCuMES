
import {
  Component,
  ViewChild,
  TemplateRef,
  OnInit,
  ElementRef,
  AfterViewInit,
  OnChanges,
  SimpleChanges
} from '@angular/core';
import {FormlyFieldConfig, FieldArrayType, FieldArrayTypeConfig} from '@ngx-formly/core';
import {SelectionModel} from "@angular/cdk/collections";
import * as _ from "lodash";

@Component({
  selector: 'formly-datatable-advanced',
  template: `
    <table class="mat-table cdk-table" style="width: 100%" id="datatable">

      <tr class="mat-header-row cdk-header-row ng-star-inserted custom-header-row">
        <th class="mat-header-cell" style="border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important"></th>
        <ng-container *ngFor="let headerCell of field.props?.columns">
          <th class="mat-header-cell custom-header-cell"
              [ngClass]="getHeaderClass(field, headerCell)"
              style="border-bottom: 1px solid rgba(0, 0, 0, 0.12); padding: 5px 0 5px 5px; width: calc({{getColumnWidthNumber(headerCell, field.props?.columns.length)}})">{{ headerCell.name }}
          </th>
        </ng-container>
      </tr>


      <ng-container *ngFor="let row of field.fieldGroup; index as rowIndex">
        <tr class="mat-row cdk-row ng-star-inserted" [ngClass]="getRowClass(field, row, rowIndex)" style="height: 48px;"
            (click)="toggleRow(field, rowIndex)">
          <td class="mat-cell" style="border-bottom: 1px solid rgba(0, 0, 0, 0.12); padding-left: 5px">
            <mat-radio-button *ngIf="!isMultiSelect" (click)="$event.stopPropagation()"
                              (change)="$event? toggleField(field,rowIndex) : null"
                              [checked]="isRowSelected(rowIndex)">
            </mat-radio-button>
            <mat-checkbox *ngIf="isMultiSelect" (click)="$event.stopPropagation()"
                          (change)="$event? toggleField(field,rowIndex) : null"
                          [checked]="isRowSelected(rowIndex)">
            </mat-checkbox>
          </td>
          <ng-container *ngFor="let cell of field.props?.columns; index as columnIndex">
            <td class="mat-cell" style="border-bottom: 1px solid rgba(0, 0, 0, 0.12); padding-right: 5px">
              <formly-field [field]="getField(field, columnIndex, rowIndex)"></formly-field>
            </td>
          </ng-container>
        </tr>
      </ng-container>

    </table>
  `,
})
export class FormlyDatatableAdvanced  extends FieldArrayType implements AfterViewInit, OnInit, OnChanges {
  @ViewChild('defaultColumn') public defaultColumn?: TemplateRef<any>;

  selection = new SelectionModel<any>(true, []);
  isMultiSelect = false;
  fullModel: any



  ngOnInit() {
    this.isMultiSelect = this.field.props?.multiple
    this.selection = new SelectionModel<any>(this.isMultiSelect, []);
    //console.log(this.model)
    this.model.forEach((row : any) =>
    {
      if(row.datatable_advanced_selected)
      {
        //console.log(row)
        this.selection.select(row)
      }
      else
      {
        this.selection.deselect(row)
      }
    })
    this.selection.changed.subscribe(value =>
    {
      //console.log(value)
      let added = this.model.find((f: any) => f === value.added[0]);
      if(added != undefined) {
        added.selected = true;
        added['datatable_advanced_selected'] = true;
      }
      let removed = this.model.find((f: any) => f === value.removed[0])
      if(removed != undefined) {
        removed.selected = false;
        removed['datatable_advanced_selected'] = false;
      }
      //console.log(this.selection)
      this.formControl.patchValue(this.model)
      //this.formControl.updateValueAndValidity()
      //this.formControl.clearValidators()
    })
  }

  ngOnChanges(changes: SimpleChanges) {
    //console.log(changes)
  }

  ngAfterViewInit() {
    //   console.log("this.field", this.field);
    //   console.log("this.model", this.model);
    //   console.log("this.form", this.form);
    //   console.log("this.key", this.key);

  }

  getField(field: FormlyFieldConfig, column: number, rowIndex: number): FormlyFieldConfig {
    // @ts-ignore
    return field.fieldGroup[rowIndex].fieldGroup.find(f => f.id === field.props?.columns[column].id);
  }

  getValue(prop: any, row: any) {
    var element = this.model[row];
    var props = prop.split(".");
    var value = this.reflectionGet(element, props);
    return value
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

  getRowClass(field: FormlyFieldConfig, row: any,  rowIndex: number) {
    var value = "Table_row_";
    value += this.getValue(field.props?.columns[0].id, rowIndex);
    value = value.replace(" ", "_")
    if(row.className)
    {
      value += " " + row.className
    }

    return value;
  }

  getRowModel(rowIndex: number) {
    return this.model[rowIndex];
  }

  toggleRow(field: FieldArrayTypeConfig, rowIndex: number) {
    if(field.props?.disableRowClick && (field.props?.disableRowClick == true || field.props?.disableRowClick == "true"))
    {
      return
    }
    else {
      this.toggleField(field, rowIndex)
    }
  }

  toggleField(field: FieldArrayTypeConfig, rowIndex: number) {
    this.selection.toggle(this.getRowModel(rowIndex))
  }

  isRowSelected(rowIndex: number) {
    return this.selection.isSelected(this.getRowModel(rowIndex))
  }

  getHeaderClass(field: FormlyFieldConfig, headerCell: any) {
    var value = "Table_header_" + headerCell.id;
    if (headerCell.className) {
      value += " " + headerCell.className
    }
    return value;
  }

  private reflectionGet(element: any, props: []): any {
    var prop = props.shift()
    //console.log("Get ", props, prop, element)
    if (prop != undefined && element != undefined) {
      var obj = Reflect.get(element, prop)
      if (obj != undefined) {
        return this.reflectionGet(obj, props)
      }
    } else {
      return element
    }
  }
}
