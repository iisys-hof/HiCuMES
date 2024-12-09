import {MappingRule} from "./mapping-request";

export interface MappingRuleWithLoops {
  mappingRule: MappingRule,
  inputLoops: string[],
  outputLoops: string[]
}
