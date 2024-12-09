import {Machine} from "./machine";
import {ProductionOrder} from "./production-order";
import {SubProductionStep} from "./sub-production-step";
import {Tool, ToolSetting} from "./tool";
import {Department} from "./department";
import {MachineOccupation} from "./machine-occupation";
import {User} from "./user";

export interface UserOccupation {
  id: number
  externalId: string
  machineOccupation: MachineOccupation
  activeUsers?: User[]
  inactiveUsers?: User[]
  userTimeRecords?: any
}
