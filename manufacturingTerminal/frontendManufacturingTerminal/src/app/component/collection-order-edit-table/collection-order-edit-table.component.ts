import {
  AfterViewInit,
  Component, EventEmitter,
  Inject, Input,
  OnChanges,
  OnInit, Output,
  SimpleChanges,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {FieldArrayType, FormlyFieldConfig} from "@ngx-formly/core";
import {SelectionModel} from "@angular/cdk/collections";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {UiBuilderService} from "../../service/ui-builder.service";
import {ServerRequestService} from "../../service/server-request.service";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {StyleService} from "../../service/style.service";
import {ValidationRule} from "../../interfaces/enum-validation-rules";
import {ComponentType} from "@angular/cdk/overlay";
import {CollectionOrderModalComponent} from "../modals/collection-order-modal/collection-order-modal.component";
import { Observable } from 'rxjs';
import * as _ from "lodash";

@Component({
  selector: 'app-collection-order-edit-table',
  templateUrl: './collection-order-edit-table.component.html',
  styleUrls: ['./collection-order-edit-table.component.scss']
})
export class CollectionOrderEditTableComponent implements AfterViewInit, OnInit, OnChanges {
  @ViewChild('defaultColumn') public defaultColumn?: TemplateRef<any>;

  @Input()
  tableForm = new FormGroup({selectOrder: new FormControl()});
  @Output() tableFormChange = new EventEmitter();
  headLayout: any;
  tableLayout: any;
  selection = new SelectionModel<any>(true, []);
  isMultiSelect = false;
  gridColumn: number = 1;
  gridRow: number = 1;
  fullModel: any
  @Input() machineOccupation: CamundaMachineOccupation | null | undefined;
  @Input() model: any | null | undefined;
  @Output() modelChange = new EventEmitter();

  @Output()
  onRowClick: EventEmitter<any> = new EventEmitter();
  public machineOccupationsFilterByMachineType$: Observable<CamundaMachineOccupation[]> | undefined;
  public cMachineOccupationsFilterByCollectionOrder: CamundaMachineOccupation[] | undefined;

  constructor(private dialog: MatDialog, private uiBuilder: UiBuilderService, private formBuilder: FormBuilder, private serverRequestService: ServerRequestService, private styleService: StyleService) {

    this.uiBuilder.getDynamicFormLayout("collectionOrderEditTable").subscribe((tableLayout) => {
      //this.tableLayout = tableLayout;
      this.machineOccupationsFilterByMachineType$ = this.serverRequestService.getMachineOccupationsFilterByMachineType();

      this.machineOccupationsFilterByMachineType$.subscribe((value => {
        this.cMachineOccupationsFilterByCollectionOrder = value?.filter((value: CamundaMachineOccupation) => value.machineOccupation.subMachineOccupations == undefined && value.machineOccupation.status == "READY_TO_START" && !value.subMachineOccupation)
      }));
    })
  }

    ngOnInit() {
  }

  ngOnChanges() {
    //console.log(this.machineOccupation)
    this.model = this.machineOccupation

    this.model.machineOccupation.subMachineOccupations.map((subMachineOccupation: any) => {
      if(subMachineOccupation?.subProductionSteps?.slice(-1)[0]?.keyValueMap)
      {
        //console.log(subMachineOccupation.subProductionSteps.slice(-1)[0].keyValueMap)
        _.merge(subMachineOccupation.subProductionSteps.slice(-1)[0], subMachineOccupation.subProductionSteps.slice(-1)[0].keyValueMap)
        //console.log(subMachineOccupation)
      }
    })

    const camundaFields = this.machineOccupation?.camundaSubProductionSteps.slice(-1)[0].formField;
    const camundaFormKey = this.machineOccupation?.camundaSubProductionSteps.slice(-1)[0].formKey;
    this.headLayout = [this.generateFormLayout(camundaFormKey, camundaFields, true)]
    this.tableLayout = [this.generateFormLayout(camundaFormKey, camundaFields, false)]
    this.setValidationRules(this.headLayout, camundaFields)
    this.setValidationRules(this.tableLayout, camundaFields)
    this.setDefaultValues(this.headLayout, camundaFields)
    this.setDefaultValues(this.tableLayout, camundaFields)

    //console.log(this.tableLayout)
  }
  openModal(modal: ComponentType<any>, callback: (result: any) => void, config: MatDialogConfig = {}) {
    if (this.dialog.openDialogs.length > 0) {
      return;
    }
    const dialogRef = this.dialog.open(modal, config);

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        callback(result);
      }
    });
  }

  ngAfterViewInit() {
    //   console.log("this.field", this.field);
    //   console.log("this.model", this.model);
    //   console.log("this.form", this.form);
    //   console.log("this.key", this.key);
  }

  generateFormLayout(formKey: any, formFields: any, isHead: boolean) {
    this.gridColumn = 1;
    this.gridRow = 1;
    //console.log("FormKey", formKey)
    //console.log("FormFields", formFields)

    let layout: FormlyFieldConfig =
      {
        fieldGroupClassName: "grid-collection",
        fieldGroup: []
      }

    //console.log("Formfields: ", formFields)
    let filteredFields = formFields
    if(isHead)
    {
      filteredFields = formFields.filter((formField: any) =>
      {
        let properties
        if (formField.properties) {
          properties = JSON.parse(formField.properties)
        }
        //console.log(properties)
        if(properties == undefined || properties['collection-order-table-position'] === undefined || properties['collection-order-table-position'] === 'head')
        {
          return true
        }
        else
        {
          return false
        }
      }).forEach((formField: any) => {
        const field = this.getFormlyFieldFromCamundaFormField(formField);
        if(field != undefined)
        {
          layout.fieldGroup?.push(field);
        }
      })
    }
    else
    {

      let layout: any =
        {
          key: "machineOccupation.subMachineOccupations",
          type: "datatable",
          props: {
            onRowClick: (model: any) => this.onRowClick.emit(model),
            multiple: true,
            validationMessage: "*Es müssen alle Unteraufträge gespeichert werden.",
            columns: [
              {
                name: "BA-Nr.",
                id: "externalId"
              },
              {
                name: "Artikel",
                id: "product"
              },
              {
                name: "Menge",
                id: "amount"
              },
              {
                name: "V/VL Rohde",
                id: "ff_OvenPlace1"
              },
              {
                name: "M/VR Rohde",
                id: "ff_OvenPlace2"
              },
              {
                name: "H/HL Rohde",
                id: "ff_Ovenplace3"
              },
              {
                name: "HR Rohde",
                id: "ff_Ovenplace4"
              },
              {
                name: "Einbau",
                id: "ff_Assembly"
              },
              {
                name: "Brandstein-Nummer",
                id: "ff_BurningStoneNumber"
              },
              {
                name: "Bemerkung",
                id: "ff_Note_1_1"
              },
              {
                name: "Text",
                id: "info_WorkoderText"
              }
            ]
          },
          fieldArray: {
            fieldGroup: [
              {
                "type": "simple",
                "id": "externalId",
                "key": "productionOrder.name",
              },
              {
                "type": "simple",
                "id": "product",
                "key": "productionOrder.product.name"
              },
              {
                "type": "simple",
                "id": "amount",
                "key": "productionOrder.measurement.amount"
              },
              {
                "type": "simple",
                "id": "info_WorkoderText",
                "key": "ptext"
              },
              {
                "type": "head-table-action",
                "id": "action-button",
                "key": "action-button",
                props: {
                  sendFinishWithoutCamunda: (model: any) => this.onFinishWithoutCamunda(model),
                  editForm: (model: any) => {
                    this.editForm(model)
                  },
                },
              }
            ]
          }
        }

        if(!this.isReadOnly())
        {
          layout.props.columns.push(
            {
              name: "Aktion",
              id: "action-button"
            })
        }

      filteredFields = formFields.filter((formField: any) => {
        let properties
        if (formField.properties) {
          properties = JSON.parse(formField.properties)
        }
        return properties['collection-order-table-position'] === 'table'
      }).forEach((formField: any) => {
        const field = this.getFormlyFieldTable(formField);
        layout.fieldArray?.fieldGroup?.push(field);
      })
      console.log(layout)
      return layout
    }

    //console.log(layout)
    return layout
  }

  setValidationRules(formLayout: any, camundaFields: any): any {

    camundaFields.filter((field: any) => field.validationConstraints).map((field: any) => {
      //console.log(field);
      if (field.validationConstraints) {
        let constraints = JSON.parse(field.validationConstraints)
        //console.log("constraints", constraints)
        constraints.forEach((constraint: any) => {
          //console.log(constraint)
          if (camundaFields?.controlType == "datepicker" && constraint.name != ValidationRule.Required) {
            //DateTime fields use the datetime picker, which has it's own validation, except required attribute
          } else {
            //console.log(field.key, constraint)
            let configuration = constraint.configuration
            switch (constraint.name) {
              case ValidationRule.RequiredFromCamunda: {
                break;
              }
              case ValidationRule.PatternFromCamunda: {
                constraint.name = ValidationRule.Pattern
                break;
              }
              case ValidationRule.MinFromCamunda: {
                configuration = Number(configuration);
                break;
              }
              case ValidationRule.MaxFromCamunda: {
                configuration = Number(configuration) - 1;
                break;
              }
              case ValidationRule.MinLengthFromCamunda: {
                constraint.name = ValidationRule.MinLength
                configuration = Number(configuration);
                break;
              }
              case ValidationRule.MaxLengthFromCamunda: {
                constraint.name = ValidationRule.MaxLength
                // Camunda excludes the number, formly includes it
                configuration = Number(configuration) - 1;
                break;
              }
            }
            //console.log(constraint.name)
            if(this.getFormFieldByKey(formLayout, field.key)) {
              this.getFormFieldByKey(formLayout, field.key).props[constraint.name] = configuration;
            }
          }
          //console.log(field.key, this.getFormFieldByKey(formLayout, field.key).props);
        })
      }
    })
  }

  setDefaultValues(formLayout: any, camundaFields: any): any {
    //console.log(formLayout, camundaFields)
    camundaFields.forEach((field: any) => {
      //console.log(field)
      if (field.value) {
        let value = field.value
        switch (field.type) {
          case "long": {
            value = parseInt(field.value)
            break;
          }
          case "double": {
            value = parseFloat(field.value)
            break;
          }
          case "boolean": {
            value = field.value === "true"
          }
        }
        let formfield = this.getFormFieldByKey(formLayout, field.key);
        if(formfield != null || formfield != undefined)
        {
          formfield['defaultValue'] = value;
        }
      }
      else
      {
        //console.log(this.machineOccupation)
        if(this.machineOccupation?.currentSubProductionStep?.subProductionStep?.keyValueMap[field.key])
        {
          //console.log(this.machineOccupation?.currentSubProductionStep?.subProductionStep?.keyValueMap[field.key])
        }
      }
    })
  }

  getFormFieldByKey(formLayout: any, key: String): any {
    var result = null;
    //console.log(formLayout, key)
    if (formLayout instanceof Array) {
      for (var i = 0; i < formLayout.length; i++) {
        result = this.getFormFieldByKey(formLayout[i], key);
        if (result) {
          break;
        }
      }
    } else {
      for (var prop in formLayout) {
        if (prop == 'key') {
          if (formLayout[prop] == key) {
            return formLayout;
          }
        }
        if (formLayout[prop] instanceof Object || formLayout[prop] instanceof Array) {
          result = this.getFormFieldByKey(formLayout[prop], key);
          if (result) {
            break;
          }
        }
      }
    }
    //console.log(result)
    return result;
  }
  private getFormlyFieldFromCamundaFormField(formField: any) {
    //console.log(formField)
    let constraints = []
    let properties = []
    if (formField.validationConstraints) {
      constraints = JSON.parse(formField.validationConstraints)
    }
    if (formField.properties) {
      properties = JSON.parse(formField.properties)
    }
    //console.log(properties)

    let cssClass = "dyn-form grid-item";
    let styleClassName = formField.key;

    cssClass = "dyn-form-collection grid-item-collection " + styleClassName

    //console.log(constraints, properties)
    let options: any[] = []
    let field = {
      style: "height: 200px",
      className: cssClass,
      type: "",
      key: formField.key,
      id: formField.key,
      props: {
        label: formField.label,
        type: "",
        options: options,
        addonLeft: {
          "icon": "edit"
        }
      }
    }

    //Read-only Formfields
    if (constraints.filter((value: any) => value.name === "readonly").length > 0) {
      if (typeof properties === "object" && properties["type"] !== null) {
        //console.log(properties['type'])
        switch (properties['type']) {
          case "pdf": {
            field.type = 'pdfviewer'
            field.className = cssClass
            // @ts-ignore
            field["defaultValue"] = "assets/demoComp.pdf"
            break;
          }
          case "video": {
            field.type = 'videoplayer'
            field.className = cssClass
            // @ts-ignore
            field["defaultValue"] = "assets/demoComp.mp4"
            break;
          }
          case "png": {
            field.type = 'imageviewer'
            field.className = cssClass
            // @ts-ignore
            field["defaultValue"] = "assets/demoComp.png"
            break;
          }
          case "hicumes_ToolSettings": {

            //Don't show hicumes Toolsettings in collection order
            return undefined
            /*field.type = 'toolSettingTable'
            field.className = cssClass
            //Skip ToolSettingsfield, if values are empty
            var vals = JSON.parse(formField.value);
            console.log(vals)
            if(vals != undefined && vals.length <= 0)
            {
              return undefined
            }
            console.log(vals)
            break;*/
          }
          default: {
            field.type = 'readonly-formfield'
            field.className = cssClass
            break;
          }
        }
      } else {
        field.type = 'readonly-formfield'
        field.className = cssClass
      }
    }

    //Input Formfields
    else
    {
      switch (formField?.type) {
        case 'string': {
          field.type = 'input';
          break;
        }
        case 'number': {
          field.type = 'input';
          field.props.type = 'number'
          break;
        }
        case 'double': {
          field.type = 'input';
          field.props.type = 'number'
          break;
        }
        case 'boolean': {
          field.type = 'toggle'
          break;
        }
        case 'date': {
          field.type = 'datetimepicker'
          break;
        }
        case 'enum': {
          field.type = 'select'
          formField.options.forEach((option: any) => {
            let selectOption = {value: option.key, label: option.value};
            field.props.options.push(selectOption)
          })
          break;
        }
        case 'dynamicEnum': {

          field.type = 'select'

          if (formField.value) {

            if (formField.value.toString().startsWith("[{") && formField.value.toString().endsWith("}]")) {

              var vals = JSON.parse(formField.value);

              vals.forEach((option: any) => {

                let valOption = {value: option.id, label: option.value};
                field.props.options.push(valOption);
              })

              formField.value = null;
            }
          }

          break;
        }
        case 'custom': {
          break;
        }
        default: {
          break;
        }
      }
    }

    this.setGridStyle(styleClassName, properties)
    return field;
  }

  private setGridStyle(styleClassName: string, properties: any)
  {
    let colStart = properties['col-start'] ? Number(properties['col-start']) : this.gridColumn
    let colEnd = properties['col-width'] ? colStart + Number(properties['col-width']) : colStart + 6
    let rowSpan = properties['row-height'] ? Number(properties['row-height']) : 2

    //console.log(colStart, colEnd, this.gridColumn, this.gridRow)

    if (colEnd > 13) {
      this.gridRow += rowSpan;

      colStart = 1;
      colEnd = colEnd - 12;
    }

    let rowStart = properties['row-start'] ? Number(properties['row-start']) : this.gridRow
    let rowEnd = properties['row-height'] ? rowStart + Number(properties['row-height']) : rowStart + 2

    //console.log(colStart, colEnd, rowStart, rowEnd, this.gridColumn, this.gridRow)
    //console.log("." + styleClassName, {
    //  "grid-column-start": colStart,
    //  "grid-column-end": colEnd,
    //  "grid-row-start": rowStart,
    //  "grid-row-end": rowEnd
    //})
    this.styleService.setStyles("." + styleClassName, {
      "grid-column-start": colStart.toString(),
      "grid-column-end": colEnd.toString(),
      "grid-row-start": rowStart.toString(),
      "grid-row-end": rowEnd.toString()
    });
    this.gridColumn = colEnd
  }

  private getFormlyFieldTable(formField: any) {

    let constraints = []
    let properties: any = []
    if (formField.validationConstraints) {
      constraints = JSON.parse(formField.validationConstraints)
    }
    if (formField.properties) {
      properties = JSON.parse(formField.properties)
    }
    //console.log(properties)

    let cssClass = "dyn-form grid-item";
    let styleClassName = formField.key;
    let colStart = properties['col-start'] ? Number(properties['col-start']) : this.gridColumn
    let colEnd = properties['col-width'] ? colStart + Number(properties['col-width']) : colStart + 6
    let rowSpan = properties['row-height'] ? Number(properties['row-height']) : 2



    //console.log(colStart, colEnd, this.gridColumn, this.gridRow)

    if (colEnd > 13) {
      this.gridRow += rowSpan;

      colStart = 1;
      colEnd = colEnd - 12;
    }

    let rowStart = properties['row-start'] ? Number(properties['row-start']) : this.gridRow
    let rowEnd = properties['row-height'] ? rowStart + Number(properties['row-height']) : rowStart + 2

    //console.log(colStart, colEnd, rowStart, rowEnd, this.gridColumn, this.gridRow)
    //console.log("." + styleClassName, {
    //  "grid-column-start": colStart,
    //  "grid-column-end": colEnd,
    //  "grid-row-start": rowStart,
    //  "grid-row-end": rowEnd
    //})
    this.styleService.setStyles("." + styleClassName, {
      "grid-column-start": colStart.toString(),
      "grid-column-end": colEnd.toString(),
      "grid-row-start": rowStart.toString(),
      "grid-row-end": rowEnd.toString()
    });
    this.gridColumn = colEnd
    cssClass = "dyn-form-collection grid-item-collection " + styleClassName

    console.log(formField)


    console.log(this.model)
    if(properties['property-path'])
    {
      console.log(properties['property-path'])

      this.model.machineOccupation.subMachineOccupations.map((subMachineOccupation: any) => {

        if(subMachineOccupation?.subProductionSteps?.slice(-1)[0])
        {
          let propPath = properties['property-path']
          //console.log(subMachineOccupation.subProductionSteps.slice(-1)[0])
          let val = subMachineOccupation.subProductionSteps.slice(-1)[0][propPath];
          subMachineOccupation.subProductionSteps.slice(-1)[0][formField.key] = val
          //console.log(subMachineOccupation)
        }
      })
    }
    if(properties['model-path'])
    {
      //console.log(properties['model-path'])

      this.model.machineOccupation.subMachineOccupations.map((subMachineOccupation: any) => {
        subMachineOccupation["toolSettings"] = _(subMachineOccupation.activeToolSettings).groupBy('toolSettingParameter.externalId').value()
        //console.log(subMachineOccupation)
        if(subMachineOccupation?.subProductionSteps?.slice(-1)[0] && !subMachineOccupation.subProductionSteps.slice(-1)[0][formField.key])
        {
          let propPath = properties['model-path']
          //console.log(subMachineOccupation.subProductionSteps.slice(-1)[0])
          let val = subMachineOccupation[propPath];
          //console.log(val)
          subMachineOccupation.subProductionSteps.slice(-1)[0][formField.key] = val
          //console.log(subMachineOccupation)
        }
        else if( subMachineOccupation?.subProductionSteps.length <= 0){
          subMachineOccupation?.subProductionSteps.push({})
          let propPath = properties['model-path']
          let val = this.reflectionGet(subMachineOccupation, propPath.split('.')) ;
          //console.log(val)
          let subprodStep = {keyValueMap: {[formField.key]: val}, [formField.key]: val}
          subMachineOccupation.subProductionSteps[0] = subprodStep
          //console.log(subMachineOccupation)
        }
      })
    }

    let options: any[] = []
    let field = {
      style: "height: 200px; width: 50px",
      //className: cssClass,
      type: "switch-input",
      id: formField.key,
      props: {
        "editField": true,
      },
      fieldGroup: [{
        key: "subProductionSteps[0]." + formField.key,
        defaultValue: this.model.subProductionSteps?.slice(-1)[0]?.keyValueMap[String(formField.id)],
        props: {
          label: formField.label,
          options: options,
        },
        expressions: {
          'wrappers': (field: FormlyFieldConfig) =>
          {
            console.log(field)
            return field.model?.subProductionSteps?.slice(-1)[0]?.externalId? [] : ["form-field"]
          }
        },
        expressionProperties: {
          'type': (model: any) => {
            //Only persisted machineOccupation have an externalId
            return model?.subProductionSteps?.slice(-1)[0]?.externalId? "simple" : "input";
          }
        }
      }]
    }



    //Read-only Formfields
    if (constraints.filter((value: any) => value.name === "readonly").length > 0) {
      if (typeof properties === "object" && properties["type"] !== null) {
        //console.log(properties['type'])
        switch (properties['type']) {
          case "pdf": {
            field.type = 'pdfviewer'
            //field.className = cssClass
            // @ts-ignore
            field["defaultValue"] = "assets/demoComp.pdf"
            break;
          }
          case "video": {
            field.type = 'videoplayer'
            //field.className = cssClass
            // @ts-ignore
            field["defaultValue"] = "assets/demoComp.mp4"
            break;
          }
          case "png": {
            field.type = 'imageviewer'
            //field.className = cssClass
            // @ts-ignore
            field["defaultValue"] = "assets/demoComp.png"
            break;
          }
          case "hicumes_ToolSettings": {
            field.type = 'toolSettingTable'
            //field.className = cssClass
            break;
          }
          default: {
            field.type = 'switch-input'
            //field.className = cssClass
            break;
          }
        }
      } else {
        field.type = 'switch-input'
        //field.className = cssClass
      }
    }
    //console.log(field)

    //Input Formfields
    /*else
    {
      switch (formField?.type) {
        case 'string': {
          field.type = 'input';
          break;
        }
        case 'number': {
          field.type = 'input';
          field.props.type = 'number'
          break;
        }
        case 'double': {
          field.type = 'input';
          field.props.type = 'number'
          break;
        }
        case 'boolean': {
          field.type = 'toggle'
          break;
        }
        case 'date': {
          field.type = 'datetimepicker'
          break;
        }
        case 'enum': {
          field.type = 'select'
          formField.options.forEach((option: any) => {
            let selectOption = {value: option.key, label: option.value};
            field.props.options.push(selectOption)
          })
          break;
        }
        case 'dynamicEnum': {

          field.type = 'select'

          if (formField.value) {

            if (formField.value.toString().startsWith("[{") && formField.value.toString().endsWith("}]")) {

              var vals = JSON.parse(formField.value);

              vals.forEach((option: any) => {

                let valOption = {value: option.id, label: option.value};
                field.props.options.push(valOption);
              })

              formField.value = null;
            }
          }

          break;
        }
       case 'custom': {
          break;
        }
        default: {
          break;
        }
      }
    }*/

    return field;
  }

  add() {

    //console.log(this.tableForm)
    this.openModal(CollectionOrderModalComponent, result => {
      //console.log(result)
    }, {data: {parentMachineOccupation: this.machineOccupation ,machineOccupations: this.cMachineOccupationsFilterByCollectionOrder}});
  }

  private onFinishWithoutCamunda(model: any) {
    //console.log(model)
    //let taskId = this.machineOccupation?.camundaSubProductionSteps.slice(-1)[0].taskId

    let formfield: any =
      {
        parentCamundaMOId: this.machineOccupation?.id,
        subMachineOccupationId: model?.id,
        formFields: model.subProductionSteps.slice(-1)[0]
      }

    //console.log(formfield)
    this.serverRequestService.persistSubProductionStep(formfield);
    //this.disableResponseButtonStateService.disableButton(taskId);
    /*if(this.userData?.attributes.JumpToMain)
    {
      this.router.navigate(['/']);
    }
    //TODO: Add routing, if user enabled it in setting
    //
    /*.then(() => {
      setTimeout(() => {
      this.reloadEverything();
    }, 2000);
    });*/
  }

  getModel(): any {
    return this.model;
  }

  private editForm(model: any) {

    //this.tableLayout[0].fieldGroup[0].fieldGroup[0].fieldGroup[6].props.type = "input"

    //console.log(model.parent)
    model.parent.props["editEntries"] = true;
    model.parent.fieldGroup.filter((field : any) => field?.props?.editField).map((field: any) => {
        //console.log(field)
        field.fieldGroup[0].type = "input"
        field.fieldGroup[0].wrappers = ["form-field"]
    })
    /*model.parent.fieldGroup[6].fieldGroup[0].type = "input"
    model.parent.fieldGroup[6].fieldGroup[0].wrappers = ["form-field"]*/
    //console.log(this.tableLayout)
  }

  updateForm() {
    //console.log(this.tableForm)
    this.tableFormChange.emit(this.tableForm);
  }

  validate() {
    if(this.tableLayout)
    {
      this.tableLayout[0].fieldGroup.forEach((row: any) => {
        let errors = row?.formControl?.errors
        if(errors)
        {
          if(errors[0] == 'edit')
            row.className = "edit-error"

        }
      })

      this.tableForm.markAsPristine()
      this.tableForm.markAsUntouched()
      //console.log(this.tableLayout)
    }

  }

  isReadOnly() : boolean
  {
    const formFields = this.machineOccupation?.camundaSubProductionSteps.slice(-1)[0].formField;
    let counterNotReadOnly = 0;
    for(let formField of formFields)
    {
      let validationConstraints
      if (formField.validationConstraints) {
        validationConstraints = JSON.parse(formField.validationConstraints)
        let hasReadOnlyProp = false
        for(let constraint of validationConstraints)
        {
          if(constraint?.name === "readonly" && constraint?.configuration === "true")
          {
            hasReadOnlyProp = true
          }
        }
        if(!hasReadOnlyProp)
        {
          counterNotReadOnly++
        }
      }
      else
      {
        counterNotReadOnly++
      }
    }
    //console.log(counterNotReadOnly == 0)
    return counterNotReadOnly == 0
  }

  private reflectionGet(element: any, props: any): any
  {
    //console.log(element, props)
    var prop = props.shift()
    //console.log("Get ", props, prop, element)
    if(prop != undefined)
    {
      var obj = Reflect.get(element, prop)
      //console.log(obj)
      if(obj != undefined) {
        return this.reflectionGet(obj, props)
      }
    }
    else {
      return element
    }
  }
}
