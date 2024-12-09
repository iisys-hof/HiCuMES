import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Observable, ReplaySubject, Subject} from "rxjs";
import {FormsDatasource} from "../interfaces/forms-datasource";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {MappingAndDataSource} from "../interfaces/mapping-request";
import {TreeNode} from "../interfaces/mapping-response";
import {map} from "rxjs/operators";
import * as _ from "lodash";
import {MappingRequestService} from "./mapping-request.service";

@Injectable({
  providedIn: 'root'
})
export class FormsDatasourceService {

  private url = environment.baseUrl + '/mappingBackend/data/mappingEndpoint/camundaForms';

  private formsDataSource: FormsDatasource[] = [];
  private mappingsAndDataSource: MappingAndDataSource[] = [];

  private formsDatasource$: Subject<FormsDatasource[]>;
  private error$: Subject<string>;

  constructor(private http: HttpClient, private mappingRequestService: MappingRequestService) {
    this.formsDatasource$ = new ReplaySubject();
    this.error$ = new ReplaySubject();
    mappingRequestService.getAvailableMappings().subscribe(mappings => {
      this.mappingsAndDataSource = mappings;
      this.updateFormsDataSource();
    })
  }

  update() {
    this.error$.next(undefined);
    this.http.get<FormsDatasource[]>(this.url).subscribe(
      (data) => {
        this.formsDataSource = data;
        this.updateFormsDataSource();

      },(error: HttpErrorResponse) => {
        let errorMessage = "Fehler beim updaten der Forms Datenquellen: " + error.message + " wegen: " + error.error.message;
        console.error(errorMessage);
        console.log(error);

        this.error$.next(errorMessage);
      });
  }

  private updateFormsDataSource(){
    for (let form of this.formsDataSource) {
      let match = _.find(this.mappingsAndDataSource, {externalId: form.taskId});
      form.mappingAndDataSource = match;
    }
    this.formsDatasource$.next(this.formsDataSource);
  }

  getFormsDatasourceObservable(): Observable<FormsDatasource[]> {
    return this.formsDatasource$.asObservable();
  }

  getFormByTaskId(taskId: string) {
    return this.formsDatasource$.pipe(map(formsDataSource => {
      let selectedFormField = formsDataSource.filter(value => value.taskId === taskId)[0];
      return selectedFormField
    }));

  }

  getFormsDataSourceAsTree(): Observable<TreeNode>
  {
    return this.formsDatasource$.pipe(map(formsDatasource => {

      let camundaTree = this.createTreeNodeObject("Camunda");
      this.createTree(camundaTree, formsDatasource, ['collaborationId', 'processShowName', 'subProcessName', 'taskName'])

      return camundaTree;
      }
    ));
  }

  createTree(parentNode: TreeNode, formsDatasource: FormsDatasource[], fieldNames: string[]) {
    if(fieldNames.length <= 0)
    {
      return;
    }
    let groupedFieldName = _.groupBy(formsDatasource, fieldNames.shift());
    for (let fieldContent in groupedFieldName) {
      let childNode = this.createTreeNodeObject(fieldContent);
      if(fieldNames.length == 0) {
        childNode = this.createTreeLeaf(groupedFieldName[fieldContent][0]);
      }

      this.createTree(childNode, groupedFieldName[fieldContent], _.cloneDeep(fieldNames));
      parentNode.children.push(childNode);
    }
    return;
  }

  createTreeNodeObject(text: string): TreeNode {
    return {
      type: "OBJECT",
      children: [],
      exampleData:"",
      icon: "OBJECT",
      selector: "",
      text: text
    }
  }

  createTreeLeaf(formsDatasource: FormsDatasource): TreeNode {
    let icon = "CAMUNDA_NO_MAPPING"
    if (formsDatasource.mappingAndDataSource!== undefined) {
      icon = "CAMUNDA_MAPPING"
    }
    return {
      type: "INTEGER",
      children: [],
      exampleData:"",
      icon: icon,
      selector: formsDatasource.taskId,
      text: formsDatasource.taskName
    }
  }

  getErrorObservable(): Observable<string> {
    return this.error$.asObservable();
  }
}
