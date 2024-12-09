export interface PluginInformation {
  pluginDisplayName: string
  pluginID: string
  pluginType: string
  uiFormfields?: Formfields[]
}

export interface Formfields {
  key: string
  label: string
  required: boolean
  type: string
  defaultValue: string
  selectOptionsList?: SelectOption[]
}

export interface SelectOption {
  label: string
  value: string
}
