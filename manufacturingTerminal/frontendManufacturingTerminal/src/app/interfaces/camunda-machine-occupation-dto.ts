import {MachineOccupation} from "./machine-occupation";
import {CamundaSubProductionStep} from "./camunda-sub-production-step";

export interface CamundaMachineOccupationDTO {
  cmoId: number
  productionOrdnerName: string
  machineOccupationName: string
  orderType: string
  usage: string
  prodStepName: string
  productName: string
  productDetail: string
  amount: number
  unit: string
  machineId: string
  machineName: string
  startDateTime: any
  endDateTime: any
  status: string
  notes: any
}
