import {Machine} from "./machine";
import {ProductionOrder} from "./production-order";
import {SubProductionStep} from "./sub-production-step";
import {Tool, ToolSetting} from "./tool";
import {Department} from "./department";

export interface MachineOccupation {
  totalProductionNumbers: any;
  name: string;
  id: number
  externalId: string

  camundaProcessName: string
  productionSteps: any
  machine: Machine
  tool: Tool
  productionOrder: ProductionOrder
  subProductionSteps: SubProductionStep[]
  toolSettings: ToolSetting[]
  parentMachineOccupation?: MachineOccupation
  subMachineOccupations?: MachineOccupation[]
  activeToolSettings?: ToolSetting[]
  department?: Department

  plannedStartDateTime: any
  plannedEndDateTime: any
  actualStartDateTime: any
  actualEndDateTime: any
  status: string
  progress: number;
}
