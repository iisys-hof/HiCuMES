import {MappingAndDataSource} from "./mapping-request";

export interface FormsDatasource {
  processVariables: any;
  mappingAndDataSource?: MappingAndDataSource;
  collaborationId:string
  processId:string
  processName:string
  processGeneralId:string
  processShowName:string
  subProcessId:string
  subProcessName:string
  taskId:string
  taskName:string
  formKey:string
  form: FormField[]
}

export interface FormField {
  id: string,
  label: string,
  type: string,
  defaultValue: string
  properties?:any
}
