import {ReaderResult} from "./mapping-request";

export interface MappingResponse {
  inputTree?: TreeNode;
  outputTree?: TreeNode;
  mappingInput?: MappingInput;
  mappingOutput?:MappingOutput;
  mappingLogging?: MappingLogging;
  readerResult?: ReaderResult;

}

export interface MappingOutput {
  result: any;
}
export interface MappingInput {
  jsonNode: any;
}

export interface TreeNode {
  selector: string;
  text: string;
  icon: string;
  type: string;
  exampleData: string;
  children: TreeNode[];
}

export interface MappingLogging {
  log: string[];
  error: string[];
}
