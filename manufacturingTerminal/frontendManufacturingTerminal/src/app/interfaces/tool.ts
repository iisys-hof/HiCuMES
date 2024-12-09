import {Machine} from "./machine";

export interface ToolType {
  id: number,
  externalId: string,
  number: number
  name: string
}

export interface Tool {
  id: number,
  externalId: string,
  name: string,
  toolType: ToolType,
  toolSettings?: ToolSetting[]
}

export interface ToolSetting {
  id: number,
  externalId: string,
  tool: Tool,
  machine: Machine,
  toolSettingParameter: any,
  productionStep: any
}
