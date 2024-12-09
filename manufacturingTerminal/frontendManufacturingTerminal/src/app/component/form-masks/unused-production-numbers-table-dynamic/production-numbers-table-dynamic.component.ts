import {ApplicationRef, Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {UiBuilderService} from "../../../service/ui-builder.service";

@Component({
  selector: 'app-production-numbers-table-dynamic',
  templateUrl: './production-numbers-table-dynamic.component.html',
  styleUrls: ['./production-numbers-table-dynamic.component.scss']
})
export class ProductionNumbersTableDynamicComponent implements OnInit {

  tableForm = new FormGroup({});
  model: any;
  options: FormlyFormOptions = {};
  tableLayout: any;

  constructor(private uiBuilder: UiBuilderService, private appRef: ApplicationRef) {
    uiBuilder.getTestDataSource()?.subscribe(x => {
      this.model = x;
    });

    this.uiBuilder.getDynamicFormLayout("productionNumberTable").subscribe((tableLayout) => {
      this.tableLayout = tableLayout;
    });

  }

  onSubmit(model: any) {
    console.log(model);
  }

  submit() {
    alert(JSON.stringify(this.model));
  }


  ngOnInit(): void {
  }

}
