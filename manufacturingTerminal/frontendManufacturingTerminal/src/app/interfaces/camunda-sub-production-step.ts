import {SubProductionStep} from "./sub-production-step";

export interface CamundaSubProductionStep {
  id: number
  externalId: string
  taskId: string
  name: string
  formKey: string
  taskDefinitionKey: string
  subProductionStep: SubProductionStep
  formField: [{
    type: string;
    required: string
    value: any
    key: string
    controlType: string
    label: string
    order: number
    options: [any]
  }]
}
