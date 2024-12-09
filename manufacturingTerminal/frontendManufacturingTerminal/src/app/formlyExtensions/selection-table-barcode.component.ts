
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
import { TableColumn } from '@swimlane/ngx-datatable/lib/types/table-column.type'
import {CamundaMachineOccupation} from "../interfaces/camunda-machine-occupation";
import {SelectionModel} from "@angular/cdk/collections";
@Component({
  selector: 'formly-selectiontable-barcode',
  template: `
    <mat-form-field style="margin-top: 20px" appearance="outline" cdkFocusInitial>
      <mat-icon matPrefix>barcode_scanner</mat-icon>
      <mat-label >Positionsnummer</mat-label>
      <input id="input0" matInput appFocusOnShow (keyup.enter)="onEnterOccupation()" [(ngModel)]="machineOccupationInput">
    </mat-form-field>

    <table class="mat-table cdk-table" style="width: 100%">
      <tr class="mat-row cdk-row ng-star-inserted">

      </tr>

      <tr class="mat-header-row cdk-header-row ng-star-inserted">
        <th class="mat-header-cell" style="border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important"></th>
        <ng-container *ngFor="let headerCell of field.props?.columns">
          <th class="mat-header-cell"
              style="border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important">{{headerCell.name}}</th>
        </ng-container>
      </tr>


      <ng-container *ngFor="let row of field.fieldGroup; index as rowIndex">
        <tr class="mat-row cdk-row ng-star-inserted" [ngClass]="getRowClass(field, rowIndex)" style="height: 48px;"
            (click)="toggleRow(rowIndex)">
          <td class="mat-cell" style="border-bottom: 1px solid rgba(0, 0, 0, 0.12)">
            <mat-radio-button *ngIf="!isMultiSelect" (click)="$event.stopPropagation()"
                              (change)="$event ? toggleRow(rowIndex) : null"
                              [checked]="isRowSelected(rowIndex)">
            </mat-radio-button>
            <mat-checkbox *ngIf="isMultiSelect" (click)="$event.stopPropagation()"
                          (change)="$event ? toggleRow(rowIndex) : null"
                          [checked]="isRowSelected(rowIndex)">
            </mat-checkbox>
          </td>
          <ng-container *ngFor="let cell of field.props?.columns; index as columnIndex">
            <td class="mat-cell" style="border-bottom: 1px solid rgba(0, 0, 0, 0.12)">
              <formly-field [field]="getField(field, columnIndex, rowIndex)"></formly-field>
            </td>
          </ng-container>
        </tr>
      </ng-container>

    </table>


  `,
})
export class FormlySelectionTableBarcode  extends FieldArrayType implements AfterViewInit {
  @ViewChild('defaultColumn') public defaultColumn?: TemplateRef<any>;

  machineOccupationInput: string = ""
  scannedMachineOccupation: CamundaMachineOccupation | undefined;
  scannedMachine: any | undefined;

  private targetId = 'input0';
  @ViewChild("inputMachineOccupation") inputMachineOccupation!: ElementRef

  selection = new SelectionModel<any>(true, []);
  isMultiSelect = false;
  fullModel: any

  ngOnInit() {
    //console.log(this.model)
    this.isMultiSelect = this.field.props?.multiple
    this.selection = new SelectionModel<any>(this.isMultiSelect, []);
    this.selection.changed.subscribe(value =>
    {
      //console.log(value)
      let added = this.model.find((f: any) => f === value.added[0]);
      if(added != undefined) {
        added.selected = true;
      }
      let removed = this.model.find((f: any) => f === value.removed[0])
      if(removed != undefined) {
        removed.selected = false;
      }
    })
  }

  ngOnChanges(changes: SimpleChanges) {
    //console.log(changes)
  }

  ngAfterViewInit() {
      // console.log("this.field", this.field);
    //   console.log("this.model", this.model);
    //   console.log("this.form", this.form);
    //   console.log("this.key", this.key);
    //this.setFocus()
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

  getRowClass(field: FormlyFieldConfig, rowIndex: number) {
    var value = "Table_row_";
    value += this.getValue(field.props?.columns[0].id, rowIndex);
    value = value.replace(" ", "_")
    return value;
  }

  getRowModel(rowIndex: number) {
    return this.model[rowIndex];
  }

  toggleRow(rowIndex: number) {
    this.selection.toggle(this.getRowModel(rowIndex))
  }

  isRowSelected(rowIndex: number) {
    return this.selection.isSelected(this.getRowModel(rowIndex))
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
  setFocus() {
    const targetElem = document.getElementById(this.targetId);
    targetElem?.focus();
  }

  setTargetId(event: any) {
    this.targetId = `input${event.selectedIndex}`;
  }


  onEnterOccupation() {
    //console.log(this.model)
    let filteredByInput = this.model.filter((value: CamundaMachineOccupation) => value.machineOccupation.productionOrder.name == this.machineOccupationInput.substring(0, this.machineOccupationInput.length-3) && value.machineOccupation.name == this.machineOccupationInput.substring(this.machineOccupationInput.length-3, this.machineOccupationInput.length) )
    //filteredByInput.forEach((value: any) => value.selected = true)
    this.selection.toggle(filteredByInput[0])
    //console.log(this.model)
    //console.log(filteredByInput)

    /*this.scannedMachineOccupation = filteredByInput[0]
    if(this.scannedMachineOccupation?.machineOccupation?.machine !== null && this.scannedMachineOccupation?.machineOccupation?.machine !== undefined) {
      this.scannedMachine = this.scannedMachineOccupation.machineOccupation.machine
    }*/
    //stepper.next()
  }
}
