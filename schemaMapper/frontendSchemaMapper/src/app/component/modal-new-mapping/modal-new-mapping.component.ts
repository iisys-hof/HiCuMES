import {Component, Inject, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {KeyValueConfig, MappingAndDataSource} from "../../interfaces/mapping-request";
import {FormlyFieldConfig, FormlyFormOptions} from "@ngx-formly/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {PluginInformation} from "../../interfaces/plugin-information";

@Component({
  selector: 'app-modal-new-mapping',
  templateUrl: './modal-new-mapping.component.html',
  styleUrls: ['./modal-new-mapping.component.scss']
})
export class ModalNewMappingComponent implements OnInit {

  pluginInformations: PluginInformation[] = [];
  form = new FormGroup({});
  model?: any = {
    id: Math.floor(Math.random() * 1000000),
    name: '',
    mappingConfiguration: {
      mappings: [],
      loops: [],
      inputSelector: undefined,
      outputSelector: undefined,
      xsltRules: undefined
    },
    dataReader:
    {
      readerKeyValueConfigs: [],
      parserKeyValueConfigs: [],
      readerID: '',
      parserID: '',
    },
    dataWriter:
    {
      writerKeyValueConfigs: [],
      parserKeyValueConfigs: [],
      writerID: '',
      parserID: '',
    }
  };

  options: FormlyFormOptions = {};

  fields: FormlyFieldConfig[] = []

  createMapping(): void {

      var reader = new FileReader();
      const isReadFromFile = this.model.isReadFromFile
      console.log(this.model)
      let model = this.cleanUpModel()
      if(this.data.loadModel == undefined) {
        if(this.model.mappingEngine === "xslt")
        {
          this.model.mappingConfiguration.xsltRules =
            "<xsl:stylesheet version=\"2.0\"\n" +
            "    xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" +
            "    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
            "  <xsl:decimal-format name=\"d\" decimal-separator=\",\" grouping-separator=\".\"/>\n" +
            "  <xsl:template match=\"/\">\n" +
            "    <output>\n" +
            "    </output>\n" +
            "  </xsl:template>\n" +
            "</xsl:stylesheet>";
        }
        if (this.model.mappingConfiguration.xsltRules) {

          /*reader.onload = (evt) => {
            let fileContent = evt?.target?.result;
            // @ts-ignore
            this.model?.mappingConfiguration.xsltRules = fileContent;
            console.log(model)
            //this.matDialogRef.close(model);
          };
          // @ts-ignore
          reader.readAsText(this.model?.mappingConfiguration.xsltRules[0]);*/
        }

        var readerFile = new FileReader();
        if (isReadFromFile) {
          readerFile.onloadend = (evt) => {
            let fileContent = evt?.target?.result;
            this.model.readerResult.result = fileContent;

            let additional = this.model?.dataReader.readerKeyValueConfigs.filter((value: any) => value.configKey === 'ADDITIONAL')
            if(additional.length > 0 && additional[0].configValue != null && additional[0].configValue != '' && additional[0].configValue != 'null')
            {
              let readerResult: any =
                `{
                  \"result\": ${this.model?.readerResult.result},
                  \"additional\": ${additional[0].configValue}
                 }
                `
                console.log(readerResult)
              // @ts-ignore
              model.readerResult = readerResult

              console.log(readerResult)
              console.log(model)
            }
            else
            {
              model.readerResult = this.model.readerResult.result
            }
          }
          // @ts-ignore
          readerFile.readAsText(this.model?.readerResult?.result[0]);



        }
    }
    else
    {
      if(this.model.mappingEngine === "xslt" && this.model.mappingConfiguration.xsltRules == null)
      {
        this.model.mappingConfiguration.xsltRules =
          "<xsl:stylesheet version=\"2.0\"\n" +
          "    xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" +
          "    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
          "  <xsl:decimal-format name=\"d\" decimal-separator=\",\" grouping-separator=\".\"/>\n" +
          "  <xsl:template match=\"/\">\n" +
          "    <output>\n" +
          "    </output>\n" +
          "  </xsl:template>\n" +
          "</xsl:stylesheet>";
      }
      model.readerResult = undefined;

      }
    console.log(model)
    this.matDialogRef.close(model);
  }

  constructor(public matDialogRef: MatDialogRef<ModalNewMappingComponent>, @Inject(MAT_DIALOG_DATA) public data: { pluginInfos: PluginInformation[], loadModel: any }) {
    this.pluginInformations = data.pluginInfos;
    console.log(this.pluginInformations)
    console.log(data)
    if(data.loadModel != undefined)
    {
      this.model = data.loadModel.mappingAndDataSource

      this.extendModel()
    }
  }

  ngOnInit(): void {
    this.buildUI();
  }

  getPluginAsSelectOption(typeFilter: string): any {
    const pluginInfoOptions: any[] = [];
    const plugins = this.getPluginsByType(typeFilter);
    plugins.forEach(value => {
      var option = {label: value?.pluginDisplayName, value: value?.pluginID}
      pluginInfoOptions.push(option);
    })
    return pluginInfoOptions;
  }

  getPluginInformation(pluginId: string): PluginInformation {
    let pluginInfo = {pluginDisplayName: '', pluginID: '', pluginType: ''};
    this.pluginInformations.forEach(value => {
      if (value?.pluginID == pluginId) {
        pluginInfo = value;
      }
    })
    return pluginInfo;
  }

  buildPluginSpecificFormFields(pluginId: string): FormlyFieldConfig[] {
    const fieldGroup: any[] = [];

    const pluginInfo = this.getPluginInformation(pluginId);
    var pluginFieldKey = this.getPluginFieldKey(pluginInfo)
    pluginInfo?.uiFormfields?.forEach(formFieldInfo => {
      const option: FormlyFieldConfig =
          {
            className: 'col-12',
            type: formFieldInfo?.type.toLowerCase(),
            key: `plugin.${pluginInfo.pluginID}.${formFieldInfo.key}`,
            defaultValue: formFieldInfo?.defaultValue,
            templateOptions: {
              label: formFieldInfo?.type.toLowerCase() != 'file' ? formFieldInfo?.label : '',
              required: formFieldInfo?.required,
              options: formFieldInfo?.type.toLowerCase() == 'select' ? formFieldInfo.selectOptionsList : []
            },
            fieldArray:
              formFieldInfo?.type.toLowerCase() != 'keyvalue' ?
                {} : {fieldGroupClassName: 'display-flex',
                      fieldGroup: [
                        {
                          className: 'flex-1 col-sm-4',
                          type: 'input',
                          key: 'configKey',
                          templateOptions: {
                            label: 'Parameter:',
                            required: true,
                          },
                        },
                        {
                          className: 'flex-1 col-sm-8',
                          type: 'input',
                          key: 'configValue',
                          templateOptions: {
                            label: 'Wert:',
                            required: true,
                          },
                        },
                      ],
                      }
          };
      fieldGroup.push(option);
    })
    return fieldGroup;
  }

  getPluginsByType(typeFilter: string): PluginInformation[] {
    const plugins: any[] = [];
    this.pluginInformations.forEach(value => {
      if (value?.pluginType == typeFilter) {
        plugins.push(value);
      }
    });
    return plugins;
  }

  private getPluginFieldKey(value: PluginInformation): { type: string, pluginIdField: string, pluginConfig: string } {
    let type = '';
    let pluginIdField = '';
    let pluginConfig = '';
    switch (value?.pluginType) {
      case 'INPUT_READER': {
        type = 'dataReader';
        pluginIdField = 'readerID';
        pluginConfig = 'readerKeyValueConfigs'
        break;
      }
      case 'INPUT_PARSER': {
        type = 'dataReader';
        pluginIdField = 'parserID';
        pluginConfig = 'parserKeyValueConfigs'
        break;
      }
      case 'OUTPUT_WRITER': {
        type = 'dataWriter';
        pluginIdField = 'writerID';
        pluginConfig = 'writerKeyValueConfigs'
        break;
      }
      case 'OUTPUT_PARSER': {
        type = 'dataWriter';
        pluginIdField = 'parserID';
        pluginConfig = 'parserKeyValueConfigs'
        break;
      }
    }
    return {type: type, pluginIdField: pluginIdField, pluginConfig: pluginConfig}
  }

  private getPluginSelectDisplayText(value: PluginInformation): string {
    let text = ''
    switch (value?.pluginType) {
      case 'INPUT_READER': {
        text = 'Auswahl des Eingangsschema-Plugins'
        break;
      }
      case 'INPUT_PARSER': {
        text = 'Auswahl des Eingangsschema-Parsers'
        break;
      }
      case 'OUTPUT_WRITER': {
        text = 'Auswahl des Ausgangsschema-Plugins'
        break;
      }
      case 'OUTPUT_PARSER': {
        text = 'Auswahl des Ausgangsschema-Parsers'
        break;
      }
    }
    return text;
  }

  private getSpecificFieldHideExpression(value: PluginInformation) {
    const fieldKey = this.getPluginFieldKey(value)
    return `model?.${fieldKey.type}?.${fieldKey.pluginIdField} !== "${value.pluginID}"`
  }

  buildPluginFieldGroup(typeFilter: string): FormlyFieldConfig[] {
    const pluginList = this.getPluginsByType(typeFilter);
    const fieldKey = this.getPluginFieldKey(pluginList[0])
    const fieldGroup: FormlyFieldConfig[] = [
      {
        type: 'select',
        key: `${fieldKey.type}.${fieldKey.pluginIdField}`,
        className: 'col-12',
        templateOptions: {
          label: this.getPluginSelectDisplayText(pluginList[0]),
          options: this.getPluginAsSelectOption(typeFilter),
          required: true,
        }
      }];

    pluginList?.forEach(value => {
      const pluginSpecificFields =
        {
          hideExpression: this.getSpecificFieldHideExpression(value),
          fieldGroup: this.buildPluginSpecificFormFields(value.pluginID),
        }
      fieldGroup.push(pluginSpecificFields);
    })
    return fieldGroup
  }

  buildUI() {
    this.fields = [
      {
        key: 'name',
        type: 'input',
        className: '',
        templateOptions: {
          placeholder: 'Name des neuen Mappings',
          required: true,
          minLength: 5
        }
      },
      {
        className: 'section-label',
        hideExpression: this.getPluginsByType('INPUT_READER').length <= 0,
        template: '<hr /><div><strong>Eingangsquelle:</strong></div>',
      },
      {
        fieldGroupClassName: 'row',
        hideExpression: this.getPluginsByType('INPUT_READER').length <= 0,
        fieldGroup: this.buildPluginFieldGroup('INPUT_READER')
      },
      {
        className: 'section-label',
        hideExpression: this.getPluginsByType('INPUT_PARSER').length <= 0,
        template: '<hr /><div><strong>Eingangsparser:</strong></div>',
      },
      {
        fieldGroupClassName: 'row',
        hideExpression: this.getPluginsByType('INPUT_PARSER').length <= 0,
        fieldGroup: this.buildPluginFieldGroup('INPUT_PARSER')
      },
      {
        className: 'section-label',
        hideExpression: this.getPluginsByType('OUTPUT_WRITER').length <= 0,
        template: '<hr /><div><strong>Ziel:</strong></div>',
      },
      {
        fieldGroupClassName: 'row',
        hideExpression: this.getPluginsByType('OUTPUT_WRITER').length <= 0,
        fieldGroup: this.buildPluginFieldGroup('OUTPUT_WRITER')
      },
      {
        className: 'section-label',
        hideExpression: this.getPluginsByType('OUTPUT_PARSER').length <= 0,
        template: '<hr /><div><strong>Zielparser:</strong></div>',
      },
      {
        fieldGroupClassName: 'row',
        hideExpression: this.getPluginsByType('OUTPUT_PARSER').length <= 0,
        fieldGroup: this.buildPluginFieldGroup('OUTPUT_PARSER')
      },
      {
        className: 'section-label',
        //hideExpression: this.getPluginsByType('OUTPUT_PARSER').length <= 0,
        template: '<hr /><div><strong>Mapping Engine:</strong></div>',
      },
      {
        fieldGroupClassName: 'row',
        //hideExpression: this.getPluginsByType('OUTPUT_PARSER').length <= 0,
        fieldGroup:
          [
            {
              type: "select",
              key: "mappingEngine",
              className: "col-12",
              templateOptions: {
                label: "Auswahl des Eingangsschema-Plugins",
                options: [
                  {
                    label: "XSLT Mapper",
                    value: "xslt"
                  },
                  {
                    label: "JS Mapper",
                    value: "js"
                  }
                ],
                required: true
              }
            },
            // {
            //   className: 'col-12',
            //   key: 'mappingConfiguration.xsltRules',
            //   type: 'file',
            //   hideExpression: 'model?.mappingEngine !== "xslt"'
            // },
          ],
      },
      {
        className: 'section-label',
        //hideExpression: this.getPluginsByType('OUTPUT_PARSER').length <= 0,
        template: '<hr /><div><strong>Beispieldaten:</strong></div>',
      },
      {
        fieldGroupClassName: 'row',
        //hideExpression: this.getPluginsByType('OUTPUT_PARSER').length <= 0,
        fieldGroup:
          [
            {
              className: 'col-12',
              key: 'isReadFromFile',
              type: 'toggle',
              templateOptions: {
                label: 'Daten aus Datei direkt einlesen?'
              },
            },
            {
              className: 'col-12',
              key: 'readerResult.result',
              type: 'file',
              hideExpression: '!model?.isReadFromFile'
            },
          ],
      }
    ];
    console.log(this.fields)
  }

  getConfigAsKeyValueConfig(pluginInformation: PluginInformation): KeyValueConfig[]
  {
    let keyValueConfigs: KeyValueConfig[] = [];
    let configs = this.model['plugin'][pluginInformation.pluginID];
    console.log(pluginInformation.pluginID, this.model['plugin'][pluginInformation.pluginID], configs)

    if(configs != undefined || configs != null) {
      let keys = Object.keys(configs);
      keys.forEach(key => {
        let value = '';
        if (typeof configs[key] === 'string' || configs[key] instanceof String)
        {
          value = configs[key]
        }
        else
        {
          value =  JSON.stringify(configs[key])
        }
        keyValueConfigs.push(
          {
            configKey: key,
            configValue: value
          })
        console.log(keyValueConfigs)
      })
    }
    return keyValueConfigs;
  }

  getPluginInformationFromModel(pluginId: string, pluginConfig: any)
  {
    let pluginInfo = this.getPluginInformation(pluginId)
    let config: any = {}
    pluginConfig?.forEach((value: any) => {
      let fieldConfig = pluginInfo.uiFormfields?.filter((formField: any) => formField.key == value.configKey)[0]
      try {
        switch (fieldConfig?.type)
        {
          case "KEYVALUE":
          {
            config[value.configKey] = JSON.parse(value.configValue);
            break;
          }
          default:
          {
            config[value.configKey] = value.configValue;
          }
        }
      }
      catch (e) {
      }
    })
    console.log(config)
    return config
  }

  cleanUpModel() {
    //Remove all objects, that were set from formly and that don't belong to the mappingAndDatasource
    this.model.dataReader.readerKeyValueConfigs = this.getConfigAsKeyValueConfig({pluginDisplayName: "", pluginID: this.model.dataReader.readerID, pluginType: 'INPUT_READER'});
    this.model.dataReader.parserKeyValueConfigs = this.getConfigAsKeyValueConfig({pluginDisplayName: "", pluginID: this.model.dataReader.parserID, pluginType: 'INPUT_PARSER'});
    this.model.dataWriter.writerKeyValueConfigs = this.getConfigAsKeyValueConfig({pluginDisplayName: "", pluginID: this.model.dataWriter.writerID, pluginType: 'OUTPUT_WRITER'});
    this.model.dataWriter.parserKeyValueConfigs = this.getConfigAsKeyValueConfig({pluginDisplayName: "", pluginID: this.model.dataWriter.parserID, pluginType: 'OUTPUT_PARSER'});

    const mappingAndDataSource: MappingAndDataSource =
      {
        id: this.model.id,
        name: this.model.name,
        externalId: this.model.name,
        mappingConfiguration: this.model.mappingConfiguration,
        dataReader: this.model.dataReader,
        dataWriter: this.model.dataWriter,
        readerResult: this.model.readerResult
      };

      return  mappingAndDataSource;
  }

  private extendModel() {
    console.log(this.model)
    this.model['plugin'] = {}
    this.model['plugin'][this.model.dataReader.readerID] = this.getPluginInformationFromModel(this.model.dataReader.readerID, this.model.dataReader.readerKeyValueConfigs)
    this.model['plugin'][this.model.dataReader.parserID] = this.getPluginInformationFromModel(this.model.dataReader.parserID, this.model.dataReader.parserKeyValueConfigs)
    this.model['plugin'][this.model.dataWriter.writerID] = this.getPluginInformationFromModel(this.model.dataWriter.writerID, this.model.dataWriter.writerKeyValueConfigs)
    this.model['plugin'][this.model.dataWriter.parserID] = this.getPluginInformationFromModel(this.model.dataWriter.parserID, this.model.dataWriter.parserKeyValueConfigs)
    console.log(this.model)
    if(this.model.mappingConfiguration.xsltRules != null)
    {
      this.model["mappingEngine"] = "xslt"
    }
    else {
      this.model["mappingEngine"] = "js"
    }
  }
}
