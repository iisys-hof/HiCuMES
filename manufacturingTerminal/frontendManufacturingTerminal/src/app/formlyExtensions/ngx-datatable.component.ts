import { Component, ViewChild, TemplateRef, OnInit } from '@angular/core';
import { FormlyFieldConfig, FieldArrayType } from '@ngx-formly/core';
import {TableColumn} from "@swimlane/ngx-datatable/lib/types/table-column.type";

@Component({
  selector: 'formly-ngx-datatable',
  template: `
    <ngx-datatable
      #table
      class="bootstrap"
      [rows]="model"
      [columns]="to.columns"
      [columnMode]="to.columnMode"
      [rowHeight]="to.rowHeight"
      [headerHeight]="to.headerHeight"
      [footerHeight]="to.footerHeight"
      [limit]="to.limit"
      [scrollbarH]="to.scrollbarH"
      [reorderable]="to.reorderable"
      [externalSorting]="true"
      >
      <ng-template #defaultColumn ngx-datatable-cell-template let-rowIndex="rowIndex" let-value="value" let-row="row" let-column="column">
          {{column}}
        <formly-field [field]="getField(field, column, rowIndex)"></formly-field>
      </ng-template>
    </ngx-datatable>
`,
})
export class NgxDatatableComponent extends FieldArrayType implements OnInit {
  @ViewChild('defaultColumn') public defaultColumn!: TemplateRef<any>;

  ngOnInit() {
    console.log(this)
    this.to.columns.forEach((column: { cellTemplate: TemplateRef<any> | undefined; }) => column.cellTemplate = this.defaultColumn);
  }

  getField(field: FormlyFieldConfig, column: TableColumn, rowIndex: number ): FormlyFieldConfig {
    console.log(field)
    // @ts-ignore
    return field.fieldGroup[rowIndex].fieldGroup.find(f => f.id === field.props?.columns[column].id);
  }
}
