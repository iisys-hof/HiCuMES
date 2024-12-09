export interface MappingRequest {
  mappingAndDataSource: MappingAndDataSource;
  simulate: boolean;
  useSavedData: boolean;
}


export interface MappingAndDataSource {
  id?: number;
  externalId?: string
  name?: string;
  dataReader?: DataReader;
  dataWriter?: DataWriter;
  mappingConfiguration?: MappingConfiguration;
  readerResult?: ReaderResult;
}

export interface DataReader {
  readerKeyValueConfigs?: KeyValueConfig[];
  parserKeyValueConfigs?: KeyValueConfig[];
  parserID: string;
  readerID: string;
}

export interface KeyValueConfig {
  configKey: string;
  configValue: string;
}

export interface DataWriter {
  writerKeyValueConfigs?: KeyValueConfig[];
  parserKeyValueConfigs?: KeyValueConfig[];
  writerID: string
  parserID?: string
}

export interface MappingConfiguration {
  mappings: MappingRule[];
  loops: MappingConfiguration[];
  inputSelector?: string;
  outputSelector?: string;
  xsltRules?: string;
}

export interface ReaderResult {
  result: string;
}

export interface MappingRule {
  uiId: number;
  inputSelector: string;
  outputSelector: string;
  rule: string;
}
