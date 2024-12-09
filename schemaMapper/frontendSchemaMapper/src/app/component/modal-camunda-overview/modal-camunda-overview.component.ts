import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {FormField, FormsDatasource} from "../../interfaces/forms-datasource";
import {TreeNode} from "../../interfaces/mapping-response";
import {MatDialogRef} from "@angular/material/dialog";
import {FormsDatasourceService} from "../../service/forms-datasource.service";
import {SelectedTreeNode} from "../../interfaces/selected-tree-node";
import {MappingAndDataSource} from "../../interfaces/mapping-request";

@Component({
  selector: 'app-modal-camunda-overview',
  templateUrl: './modal-camunda-overview.component.html',
  styleUrls: ['./modal-camunda-overview.component.scss']
})
export class ModalCamundaOverviewComponent implements OnInit {

  formsDataSource$: Observable<FormsDatasource[]>;
  formsDataSourceTree$: Observable<TreeNode>;
  selectedTaskId: string = "";
  selectedTask?: FormsDatasource;

  constructor(public matDialogRef: MatDialogRef<ModalCamundaOverviewComponent>, private formsDatasourceService:FormsDatasourceService) {
    this.formsDataSource$ = formsDatasourceService.getFormsDatasourceObservable();
    this.formsDataSourceTree$ = formsDatasourceService.getFormsDataSourceAsTree();

  }
  ngOnInit(): void {
  }

  changedInputSelection($event: SelectedTreeNode) {
    this.selectedTaskId = $event.treeNode.selector;
    this.formsDatasourceService.getFormByTaskId(this.selectedTaskId).subscribe((selectedTask) => {
      this.selectedTask = selectedTask;
    }).unsubscribe();
  }

  openMapping() {
    if(this.selectedTask === undefined) {
      return;
    }
    let name = this.selectedTask.collaborationId + " > " + this.selectedTask.processShowName + " > " + this.selectedTask.subProcessName + " > " + this.selectedTask.taskName;
    let form = this.selectedTask.form;
    let processVariables = this.selectedTask.processVariables;
    //console.log("form", form);
    //console.log("processVariables", processVariables);
    //console.log("task", this.selectedTask);
    let readerResult = this.generateExampleForm(form, processVariables);
    //console.log("readerResult", readerResult);
    let mappingAndDatasource: MappingAndDataSource = {
      readerResult: {
        result: JSON.stringify(readerResult)
      },
      id: Math.floor(Math.random()*1000000),
      name: name ,
      externalId: this.selectedTaskId,
      dataReader:{
        readerID:"local-Camunda",
        parserID:"parserPlugin-JSON"
      },
      dataWriter: {
        writerID: "outputPlugin-DatabaseWriter",
        writerKeyValueConfigs: [
          {
            configKey: "DATABASE_NAME",
            configValue: "hicumes"
          },
          {
            configKey: "ENTITY_FILTER",
            configValue: "de.iisys.sysint.hicumes.core.entities"
          }
        ]
      }
    }

    if(this.selectedTask.mappingAndDataSource) {
      this.selectedTask.mappingAndDataSource.name = name;
      //return this.selectedTask.mappingAndDataSource;
      mappingAndDatasource.mappingConfiguration = this.selectedTask.mappingAndDataSource.mappingConfiguration
      mappingAndDatasource.id = this.selectedTask.mappingAndDataSource.id
      mappingAndDatasource.name = this.selectedTask.mappingAndDataSource.name
    }
    else {
      mappingAndDatasource.mappingConfiguration =
                {
                mappings:[],
                  loops: [],
                  xsltRules:
                "<xsl:stylesheet version=\"2.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <output>\n" +
                "        <subProductionStep>\n" +
                "          <productionNumbers>\n" +
                "            <acceptedMeasurement>\n" +
                "              <amount>\n" +
                "                  <xsl:value-of select=\"ObjectNode/ff_PartsOK\" />\n" +
                "              </amount>\n" +
                "            </acceptedMeasurement>\n" +
                "            <rejectedMeasurement>\n" +
                "              <amount>\n" +
                "                  <xsl:value-of select=\"ObjectNode/ff_PartsNOK\" />\n" +
                "              </amount>\n" +
                "            </rejectedMeasurement>\n" +
                "          </productionNumbers>\n" +
                "        </subProductionStep>\n" +
                "    </output>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>"
              }
    }

  return mappingAndDatasource;
  }

  private generateExampleForm(form: FormField[], processVariables?: any):any {
    let example:any = {};
    example["processVariables"] = {}
    for(let formField of form) {
        example[formField.id] = this.generateFormValue(formField);
    }
    if(processVariables) {
      for (let parameter of processVariables) {
        example["processVariables"][parameter.name] = this.generateParameterValue(parameter);
      }
    }
    return example;
  }


  //IF custom formfieldtypes are added, they must be included here!!!!
  private generateFormValue(formField: FormField):any {
    switch (formField.type) {
      case "string":
        return formField.defaultValue ?? "Beispiel Eingabetext";
      case "long":
        let number = parseInt(formField.defaultValue);
        if(isNaN(number)) {
          return 1234;
        }
        return number;
      case "double":
        let double = parseFloat(formField.defaultValue);
        if(isNaN(double)) {
          return 12.34;
        }
        return double;
      case "boolean":
        if(formField.defaultValue === "true") {
          return true;
        }
        return  false;
      case "date":
        return formField.defaultValue ?? "2021-11-30T13:53:12.136Z";
      case "enum":
        return formField.defaultValue ?? formField?.properties?.property[0]?.value ?? "Enum Eingabetext";
    }

  }

  private generateParameterValue(paramter: any):any {
        return paramter['""'] ?? "Beispiel Eingabetext";
  }

}
