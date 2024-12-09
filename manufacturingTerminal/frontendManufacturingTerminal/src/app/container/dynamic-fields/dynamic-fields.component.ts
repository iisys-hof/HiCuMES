import {ApplicationRef, Component, OnInit} from '@angular/core';
import {UiBuilderService} from "../../service/ui-builder.service";
import {Observable} from "rxjs";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {DynamicLayout} from "../../interfaces/dynamic-layout";
import { get, set } from "lodash";
import {FormGroup} from "@angular/forms";
import {FormlyFieldConfig, FormlyFormOptions} from "@ngx-formly/core";
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-dynamic-fields',
  templateUrl: './dynamic-fields.component.html',
  styleUrls: ['./dynamic-fields.component.scss']
})
export class DynamicFieldsComponent implements OnInit {

  formForm = new FormGroup({});
  tableForm = new FormGroup({});
  model: any;
  options: FormlyFormOptions = {};

  formLayouts:any = []

  constructor(private uiBuilder: UiBuilderService, private appRef: ApplicationRef, private activatedRoute: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(routeParams => {
      this.formLayouts = [];
      this.model = undefined;
      var formParams = routeParams.form?.split(',');
      this.buildUi(formParams);
    });
  }

  buildUi(formParams: string[])
  {
    if(formParams != undefined) {
      formParams.forEach(formLayout => {
        this.uiBuilder.getTestDataSource(formLayout).subscribe(x => {
          this.model = x;
        });
        this.uiBuilder.getDynamicFormLayout(formLayout).subscribe((formLayout) => {
          this.formLayouts.push(formLayout);
          console.log("Dynamic Form Layout", this.formLayouts);
        });

      });
    }
    /*else {
      this.uiBuilder.getDynamicFormLayout("productionNumberForms").subscribe((formLayout) => {
        this.formLayouts[0] = formLayout;
        console.log("Dynamic Form Layout", this.formLayouts);
      });

      this.uiBuilder.getDynamicFormLayout("productionNumberTable").subscribe((tableLayout) => {
        this.formLayouts[1] = tableLayout;
        console.log("Dynamic Form Layout", this.formLayouts);
      });
    }*/
  }

  onSubmit(model: any) {
    console.log(model);
  }

  submit() {
    alert(JSON.stringify(this.model));
  }

}
