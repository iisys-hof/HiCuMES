import {MachineOccupation} from "./machine-occupation";
import {CamundaSubProductionStep} from "./camunda-sub-production-step";

export interface CamundaMachineOccupation {
  subMachineOccupation: Boolean;
  id: number
  externalId: string

  machineOccupation: MachineOccupation
  activeProductionStep: any
  camundaSubProductionSteps: any
  camundaProcessInstanceId: string
  businessKey: string
  currentSubProductionStep: CamundaSubProductionStep
  lastSubProductionStep?: CamundaSubProductionStep
  selected?: boolean
}
