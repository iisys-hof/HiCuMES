
import {
  Component,
  ViewChild,
  TemplateRef,
  OnInit,
  ElementRef,
  AfterViewInit,
  OnChanges,
  AfterViewChecked, ChangeDetectorRef, Pipe, PipeTransform, ViewChildren, QueryList
} from '@angular/core';
import {FormlyFieldConfig, FieldArrayType, FieldWrapper} from '@ngx-formly/core';
import { TableColumn } from '@swimlane/ngx-datatable/lib/types/table-column.type'
import { CamundaMachineOccupation } from '../interfaces/camunda-machine-occupation';
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {CamundaSubProductionStep} from "../interfaces/camunda-sub-production-step";
import {UtilitiesService} from "../service/utilities.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {FormGroup} from "@angular/forms";
@Component({
  selector: 'formly-expansiontable',
  template: `
    <table mat-table class="mat-table cdk-table" style="width: 100%" [dataSource]="this.model" multiTemplateDataRows>
      <ng-container *ngFor="let headerCell of field.props?.columns; index as columnIndex"
                    matColumnDef="{{headerCell.name}}">
        <th mat-header-cell *matHeaderCellDef>{{headerCell.name}}</th>
        <td mat-cell *matCellDef="let element; dataIndex as rowIndex"
            [class.blur-cell]="element !== expandedElement && tableExpanded">
          <formly-field [field]="getField(field, columnIndex, rowIndex)"></formly-field>
        </td>
      </ng-container>

      <ng-container matColumnDef="expandedDetail">
        <td *matCellDef="let element" [attr.colspan]="displayedColumns.length" mat-cell>
          <div *ngIf="element?.camundaSubProductionSteps?.length"
               [@detailExpand]="element == expandedElement ? 'expanded' : 'collapsed'"
               class="expansion-table-element-detail">
            <div *ngIf="expandedElement" class="inner-table mat-elevation-z8">

              <form [formGroup]="expansionForm" class="expansion-table-subtable">
                <formly-form [fields]="expansionTableLayout" [form]="expansionForm"
                             [model]="modelExpansionTable"></formly-form>
              </form>

            </div>
          </div>
        </td>
      </ng-container>


      <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
      <tr (click)="toggleRow(element)" mat-row *matRowDef="let element; columns: displayedColumns;"
          [class.expansion-table-data-row]="element.camundaSubProductionSteps?.length" class="expansion-table-row"></tr>
      <tr *matRowDef="let row; columns: ['expandedDetail']" class="expansion-table-detail-row" mat-row></tr>

    </table>
  `,
  styles: [`

    .blur-cell {
      transition: 100ms filter linear;
      filter: blur(2px);
    }

    tr.expansion-table-detail-row {
      height: 0;
    }

    .expansion-table-detail-row {
      background: #F1F1F1 !important;
    }

    .expansion-table-data-row {
      background: #F8F8F8 !important;

      td:first-child::before {
        font-family: "Material Icons";
        content: "\\e5cc";
        position: absolute;
        margin-left: -34px;
        font-size: 32px;
      }

      td:first-child span.exl::before {
        font-family: "Material Icons";
        content: "\\e645";
        position: absolute;
        margin-left: -48px;
        font-size: 32px;
        color: crimson;
      }
    }

    .expansion-table-element-detail {
      overflow: hidden;
      display: flex;
    }

    .cdk-column-expandedDetail, .mat-column-expandedDetail {
      padding: 0px !important;
    }

    .inner-table {
      width: 100%;
    }
  `],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})

export class FormlyExpansiontable  extends FieldArrayType {
  @ViewChild('defaultColumn') public defaultColumn?: TemplateRef<any>;
  @ViewChildren('innerTables') innerTables: QueryList<CamundaSubProductionStep> | undefined;

  displayedColumns: string[] = [];
  tableExpanded: boolean = false;
  expandedElement: CamundaMachineOccupation | null | undefined;

  expansionForm = new FormGroup({});
  expansionTableLayout: any;
  modelExpansionTable: any;

  constructor(private cd: ChangeDetectorRef) {
    super();
  }

  ngAfterViewInit() {
    this.field.props?.columns.forEach((column: any) => {
      this.displayedColumns.push(column.name)
    })
    this.expansionTableLayout = this.field.props?.subTable
    this.cd.detectChanges()
  }

  toggleRow(element: CamundaMachineOccupation) {
    if (element.camundaSubProductionSteps.length === 0) {
      return;
    }
    element.camundaSubProductionSteps && element.camundaSubProductionSteps.length ? (this.expandedElement = this.expandedElement === element ? null : element) : null;
    this.tableExpanded = this.expandedElement !== null;
    this.modelExpansionTable = this.expandedElement;
    this.cd.detectChanges();
    // @ts-ignore
    this.innerTables.forEach((table, index) => table.dataSource.sort = this.innerSort.toArray()[index]);
  }

  getField(field: FormlyFieldConfig, column: number, rowIndex: number ): FormlyFieldConfig {
    // @ts-ignore
    return this.field.fieldGroup[rowIndex].fieldGroup.find(f => f.id === field.props?.columns[column].id);
  }

}

