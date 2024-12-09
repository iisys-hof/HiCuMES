import {Department} from "./department";

export interface MachineType {
  id: number
  externalId: string
  name: string
  departments: Department[]
}

export interface Machine {
  id: number
  externalId: string
  name: string
  machineType: MachineType
}
