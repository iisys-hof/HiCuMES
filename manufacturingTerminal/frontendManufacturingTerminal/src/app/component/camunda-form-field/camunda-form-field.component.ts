import {
  AfterViewInit,
  ApplicationRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges, ViewChild
} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {CamundaSubProductionStep} from "../../interfaces/camunda-sub-production-step";
import {UtilitiesService} from "../../service/utilities.service";
import {UiBuilderService} from "../../service/ui-builder.service";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import * as moment from 'moment';
import {Moment, now} from 'moment';
import {ValidationRule} from "../../interfaces/enum-validation-rules";
import {MachineOccupation} from "../../interfaces/machine-occupation";
import * as _ from "lodash";
import {SelectionStateService} from "../../service/selection-state.service";
import {Router} from "@angular/router";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {StyleService} from "../../service/style.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ComponentType} from "@angular/cdk/overlay";
import {PauseModalComponent} from "../modals/pause-modal/pause-modal.component";
import {FormlyFieldConfig, FormlyFormOptions} from "@ngx-formly/core";
import {CollectionOrderEditTableComponent} from "../collection-order-edit-table/collection-order-edit-table.component";
import {NgxSpinnerService} from "ngx-spinner";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserOccupation} from "../../interfaces/user-occupation";
import {UserdataService} from "../../service/userdata.service";
import {AuthGuard} from "../../service/authguard.service";

@Component({
  selector: 'app-camunda-form-field',
  animations: [
    trigger('openCloseSideMenuSmall', [
      state('open', style({
        opacity: 1,
        contentVisibility: "auto",
        width: "*"
      })),
      state('close', style({
        opacity: 0,
        contentVisibility: "hidden",
        width: 0
      })),
      transition('open <=> closed', [
        animate('0.1s')
      ])
    ]),
    trigger('openCloseSideMenuLarge', [
      state('open', style({
        width: "*",
        opacity: 1,
        contentVisibility: "auto"
      })),
      state('close', style({
        width: 0,
        minWidth: 0,
        padding: 0,
        opacity: 0,
        contentVisibility: "hidden"
      })),
      transition('open <=> closed', [
        animate('0.1s')
      ])
    ])
  ],
  templateUrl: './camunda-form-field.component.html',
  styleUrls: ['./camunda-form-field.component.scss']
})
export class CamundaFormFieldComponent implements OnInit, OnChanges, AfterViewInit {

  isLargeMenu = true;
  oldScrollValue: number = 0;
  lastScrollWasUp: boolean = false;
  sameScrollDirectionAmount: number = 0;
  cloneMachineOccupation: CamundaMachineOccupation | undefined = undefined;
  selectedSubMachineOccupation: any
  @Input()
  machineOccupation!: CamundaMachineOccupation;
  @Input()
  userOccupation!: UserOccupation | null | undefined;
  camundaSubProductionStep!: CamundaSubProductionStep;
  @Output()
  onFinish: EventEmitter<any> = new EventEmitter();
  @Output()
  onPause: EventEmitter<any> = new EventEmitter();
  @Output()
  onFinishWithPause: EventEmitter<any> = new EventEmitter();
  @Output()
  onContinue: EventEmitter<any> = new EventEmitter();
  @Output()
  onJoin: EventEmitter<any> = new EventEmitter();
  afterRequest: boolean = false;
  formForm = new FormGroup({});
  titleForm = new FormGroup({});
  headerForm = new FormGroup({});
  footerForm = new FormGroup({});
  model: any = {};
  modelHeaderFooter: any = {}
  modelFooter: any = {}
  fields: FormlyFieldConfig[] = [];
  options: FormlyFormOptions = {};
  fieldErrors: any[] = [];
  color: string = "#01579b";
  icon: string = "";
  formLayout: any;
  titleFormLayout: any;
  headerFormLayout: any;
  footerFormLayout: any;
  updatePaused: boolean = false;

  gridColumn: number = 1;
  gridRow: number = 1;

  interval: any;
  progressValue = 0;
  holdInterval = 1000;

  @ViewChild('collectionOrderForm') collectionOrderForm: CollectionOrderEditTableComponent | undefined;


  constructor(private dialog: MatDialog, private utilites: UtilitiesService, private uiBuilder: UiBuilderService, private appRef: ApplicationRef, private stateService: SelectionStateService, private router: Router, private styleService: StyleService,  private spinner: NgxSpinnerService, private snackBar: MatSnackBar, private authGuard: AuthGuard) {

    this.spinner.show();
    this.formForm.statusChanges.subscribe(value => {

      //console.log(value)
      this.saveToSessionStorage()
    })
  }

  toggleLargeMenu() {
    this.isLargeMenu = !this.isLargeMenu;
  }

  openSnackBar() {
    this.snackBar.open('Buchung abgeschickt!', "", {
      panelClass: "info-snackbar",
      duration: 5000
    });
  }


  onSubmit(model: any, suspensionType?: string): void {
    //console.log(model)
    if(this.collectionOrderForm)
    {
      //console.log(this.collectionOrderForm.getModel())
      //console.log(this.model)
      model = this.collectionOrderForm.getModel();
    }
    this.afterRequest = true;
    //console.log(this.machineOccupation)
    sessionStorage.removeItem('Formfield_' + this.machineOccupation.machineOccupation.subProductionSteps.slice(-1)[0].externalId)
    if(suspensionType)
    {
      let data =
        {
          suspensionType: suspensionType,
          filledFormfield: model
        }
      this.onFinishWithPause.emit(data);
    }
    else
    {
      this.onFinish.emit(model);
    }
  }

  buildHeaderFooter() {
    let machineOccupationModel = this.machineOccupation
    //Set the first submachineOccupation as model for machine Occupation
    if(this.machineOccupation?.machineOccupation.subMachineOccupations)
    {
      machineOccupationModel =  _.cloneDeep(this.machineOccupation)
      machineOccupationModel.machineOccupation = this.machineOccupation.machineOccupation.subMachineOccupations[0]
    }

    this.modelHeaderFooter = {
      "machineOccupations": [this.utilites.restructureCamundaMachineOccupation(machineOccupationModel)],
      "machineOccupation": this.utilites.restructureCamundaMachineOccupation(machineOccupationModel),
      "camundaSubProductionSteps": machineOccupationModel?.camundaSubProductionSteps,
      "product": [machineOccupationModel?.machineOccupation.productionOrder?.product],
      "qualityControlFeatures": machineOccupationModel?.machineOccupation.productionOrder?.product?.qualityControlFeatures,
      "productRelationships": [],
      "productionSteps": machineOccupationModel?.machineOccupation?.productionSteps
    }

    this.modelFooter = {
      "machineOccupations": [this.utilites.restructureCamundaMachineOccupation(machineOccupationModel)],
      "machineOccupation": this.utilites.restructureCamundaMachineOccupation(machineOccupationModel),
      "camundaSubProductionSteps": machineOccupationModel?.camundaSubProductionSteps,
      "product": [machineOccupationModel?.machineOccupation.productionOrder?.product],
      "qualityControlFeatures": machineOccupationModel?.machineOccupation.productionOrder?.product?.qualityControlFeatures,
      "productRelationships": [],
      "productionSteps": machineOccupationModel?.machineOccupation?.productionSteps
    }

    //console.log("asdf", this.modelHeaderFooter)

    this.getHeaderLayout();
    this.getFooterLayout();
  }

  buildFormFields(): void {
    this.color = this.utilites.getSubProdcuctionStepSettings(this.camundaSubProductionStep.formKey).color;
    this.icon = this.utilites.getSubProdcuctionStepSettings(this.camundaSubProductionStep.formKey).icon;
    //console.log(this.camundaSubProductionStep)
    const fieldErrors: any[] = [];
    const fields: FormlyFieldConfig[] = [];

    this.model = {};
    if(sessionStorage.getItem('Formfield_' + this.machineOccupation.machineOccupation.subProductionSteps.slice(-1)[0].externalId))
    {
      this.model = this.loadFromSessionStorage()
    }
    else {

      this.model["startDateTime"] = this.getPreviousEndDateTime();
      this.model["endDateTime"] = moment();
      this.model["tool"] = this.machineOccupation.machineOccupation.tool;
      this.model["product"] = this.machineOccupation.machineOccupation.productionOrder?.product.externalId;
      //TODO Change to group by toolsettingparameter
      this.model["toolSettings"] = this.findAllToolSettings(this.machineOccupation.machineOccupation);
      this.model["CheckQualityJson"] = []
      this.machineOccupation.machineOccupation.productionOrder?.product.qualityControlFeatures.forEach((feature: any) => {
        this.model.CheckQualityJson.push(
          {
            "qualityOk": false,
            "qualityControlFeature": feature,
            "note": "Bemerkung"
          }
        )
      });

    }


    //Use this, when same UI definitions can be used for each user task type
    /*
    if (this.camundaSubProductionStep.taskDefinitionKey.startsWith('ConfirmProductionTask')) {
      dynamicFormLayout = "confirmProductionTaskForm"
    } else if (this.camundaSubProductionStep.taskDefinitionKey.startsWith('SetParameterTask')) {
      dynamicFormLayout = "setParameterTaskForm"
    } else if (this.camundaSubProductionStep.taskDefinitionKey.startsWith('ReworkTask')) {
      dynamicFormLayout = "reworkTaskForm"
    } else if (this.camundaSubProductionStep.taskDefinitionKey.startsWith('SelectOrderTask')) {
      dynamicFormLayout = "selectOrderTaskForm"
    } else if (this.camundaSubProductionStep.taskDefinitionKey.startsWith('CheckQualityTask')) {
      dynamicFormLayout = "checkQualityTaskForm"
    } else {
      dynamicFormLayout = this.camundaSubProductionStep.taskDefinitionKey
    }*/

    let dynamicFormLayout = this.camundaSubProductionStep.taskDefinitionKey

    //console.log(this.model)

    const camundaFields = this.camundaSubProductionStep.formField;
    const camundaFormKey = this.camundaSubProductionStep.formKey;
    //console.log(this.machineOccupation)
    if(this.machineOccupation.machineOccupation?.subMachineOccupations)
    {
      //console.log("collection order")
    }
    else
    {
      //Get form layouts by their camunda taskDefinitionKey (ID of Task in general tab of camunda modeler)
      this.uiBuilder.getDynamicFormLayout(dynamicFormLayout).subscribe((formLayout) => {
        //console.log(formLayout)
        if (formLayout == undefined) {
          //console.log(camundaFields)
          //console.log("Undefined Layout, using automatically generated one")
          this.formLayout = [this.generateFormLayout(camundaFormKey, camundaFields)]
          this.setValidationRules(this.formLayout, camundaFields)
          this.setDefaultValues(this.formLayout, camundaFields)

          //console.log("Dynamic Form Layout", this.formLayout);
        } else {
          this.formLayout = formLayout;
          //console.log(camundaFields)
          this.setValidationRules(this.formLayout, camundaFields)
          this.setDefaultValues(this.formLayout, camundaFields)
          //console.log("Dynamic Form Layout", this.formLayout);
        }
      });
    }

  }

  generateFormLayout(formKey: any, formFields: any) {
    this.gridColumn = 1;
    this.gridRow = 1;
    //console.log("FormKey", formKey)
    //console.log("FormFields", formFields)

    let layout: FormlyFieldConfig =
      {
        fieldGroupClassName: "grid",
        fieldGroup: []
      }

    //console.log("Formfields: ", formFields)
    formFields.forEach((formField: any) => {
      const field = this.getFormlyFieldFromCamundaFormField(formField);
      layout.fieldGroup?.push(field);
    })

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
          //console.log(camundaFields)
          if (field?.controlType == "datepicker" && constraint.name != ValidationRule.Required) {
            //DateTime fields use the datetime picker, which has it's own validation, except required attribute
          }  else {
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
            //console.log(constraint.name, this.getFormFieldByKey(formLayout, field.key)?.props , this.getFormFieldByKey(formLayout, field.key)?.props[constraint.name])
            if(this.getFormFieldByKey(formLayout, field.key)?.props) {
              this.getFormFieldByKey(formLayout, field.key).props[constraint.name] = configuration;
            }
          }
          //console.log(field.key, this.getFormFieldByKey(formLayout, field.key).props);
        })
      }
    })
  }

  setDefaultValues(formLayout: any, camundaFields: any): any {

    camundaFields.forEach((field: any) => {
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
            break;
          }
        }

        let constraints = []
        let properties = []
        if (field.validationConstraints) {
          constraints = JSON.parse(field.validationConstraints)
        }
        if (field.properties) {
          properties = JSON.parse(field.properties)
        }
        //console.log(field)
        if (constraints.filter((value: any) => value.name === "readonly").length > 0) {
          if (typeof properties === "object" && properties["type"] !== null) {
            //console.log(properties['type'])
            switch (properties['type']) {
              case "hicumes_ToolSettings": {
                if(this.machineOccupation?.machineOccupation?.tool?.toolSettings?.length) {
                  value = this.machineOccupation?.machineOccupation?.tool?.toolSettings
                }
              }
            }
          }
        }

        this.getFormFieldByKey(formLayout, field.key)['defaultValue'] = value;
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
      for (let prop in formLayout) {
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

  getPreviousEndDateTime(): Moment {
    let startDateTime;

    if (this.machineOccupation.camundaSubProductionSteps[this.machineOccupation.camundaSubProductionSteps.length - 2]?.filledFormField) {
      startDateTime = moment(JSON.parse(this.machineOccupation.camundaSubProductionSteps[this.machineOccupation.camundaSubProductionSteps.length - 2]?.filledFormField)["endDateTime"]);
    }
    if (startDateTime === undefined) {
      startDateTime = moment();
    }

    return startDateTime;
  }

  ngOnInit(): void {

  }

  ngOnChanges(changes: SimpleChanges) {
    //console.log(changes)
    //console.log("ONINIT")
    this.afterRequest = false;
    //console.log("MO", this.machineOccupation)
    this.camundaSubProductionStep = this.machineOccupation.camundaSubProductionSteps.slice(-1)[0];
    //console.log("Subproductionstep", this.camundaSubProductionStep)
    if (this.machineOccupation !== null && this.machineOccupation !== undefined) {
      if (this.machineOccupation.machineOccupation.status === "FINISHED") {
        this.router.navigate(['/']);
      }
      else if(this.machineOccupation?.camundaSubProductionSteps[0]?.formKey === "")
      {
        this.openSnackBar();
        this.router.navigate(['/']);
      }
      this.stateService.updateCurrentMachineOccupation(this.machineOccupation)
    }
    this.cloneMachineOccupation = _.cloneDeep(this.machineOccupation)
    if(this.cloneMachineOccupation?.machineOccupation?.subMachineOccupations)
    {
      this.selectedSubMachineOccupation = this.cloneMachineOccupation?.machineOccupation?.subMachineOccupations[0]
    }
    else
    {
      this.selectedSubMachineOccupation = this.machineOccupation.machineOccupation
    }
    this.buildHeaderFooter()
    //this.setFields();
    let previousChangesProductionStep = changes?.machineOccupation?.previousValue?.camundaSubProductionSteps.slice(-1)[0]
    let currentChangesProductionStep = changes?.machineOccupation?.currentValue?.camundaSubProductionSteps.slice(-1)[0]
    //console.log(previousChangesProductionStep, currentChangesProductionStep)

    if (previousChangesProductionStep?.taskId != currentChangesProductionStep?.taskId) {
      this.formLayout = {};
      this.fields = [];
      this.buildFormFields();
    }
    this.updatePaused = false;
    this.formForm.markAsUntouched()
    //console.log(this.formForm)
  }

  ngAfterViewInit() {
  }

  submit() {

    this.collectionOrderForm?.validate()
    this.formForm.markAllAsTouched()
    //console.log(this.formForm)
    if (this.formForm.valid) {
      this.onSubmit(this.model);
    }
  }

  submitWithPause() {
    this.formForm.markAllAsTouched()
    //console.log(this.formForm)
    if (this.formForm.valid) {
      this.openModal(PauseModalComponent, result => {
        if(result != undefined)
        {
          //console.log(result)
          this.updatePaused = true;
          this.onSubmit(this.model, result);
        }
      }, {data: this.machineOccupation});
    }
  }



  resetModel() {
    //this.ngOnInit()
    if (this.options.resetModel !== undefined) {
      this.options.resetModel()
    }
  }

  cancel() {
    this.router.navigate(["order-overview"])
  }

  onScroll($event: any) {
    //console.log($event)
    let scrollValue = $event.target.scrollTop
    if (scrollValue - this.oldScrollValue < 0) {
      if (this.lastScrollWasUp) {
        this.sameScrollDirectionAmount++
      } else {
        this.sameScrollDirectionAmount = 0
      }

      if (this.sameScrollDirectionAmount > 2) {
        this.isLargeMenu = true
      }
      this.lastScrollWasUp = true
    } else if (scrollValue - this.oldScrollValue > 0) {
      if (!this.lastScrollWasUp) {
        this.sameScrollDirectionAmount++
      } else {
        this.sameScrollDirectionAmount = 0
      }
      if (this.sameScrollDirectionAmount > 2) {
        this.isLargeMenu = false
      }
      this.lastScrollWasUp = false
    }
    this.oldScrollValue = scrollValue
  }

  private getFormlyFieldFromCamundaFormField(formField: any) {
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
    let colStart = properties['col-start'] ? Number(properties['col-start']) : this.gridColumn
    let colEnd = properties['col-width'] ? colStart + Number(properties['col-width']) : colStart + 6
    let rowSpan = properties['row-height'] ? Number(properties['row-height']) : 2

    //console.log(colStart, colEnd, this.gridColumn, this.gridRow)

    if (colEnd > 13) {
      this.gridRow += rowSpan;

      colStart = 1;
      colEnd = properties['col-width'] ? colStart + Number(properties['col-width']) : colStart + 6
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
    cssClass = "dyn-form grid-item " + styleClassName


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
          case "image": {
            field.type = 'imageviewer'
            field.className = cssClass
            // @ts-ignore
            field["defaultValue"] = "assets/demoComp.png"
            break;
          }
          case "hicumes_ToolSettings": {
            //console.log(this.model, field)
            field.type = 'toolSettingTable'

            if(properties["path"] && properties["path"] == 'machineOccupation')
            {
              //@ts-ignore
              field.defaultValue = this.machineOccupation?.machineOccupation?.activeToolSettings
              //@ts-ignore
              field.value = this.machineOccupation?.machineOccupation?.activeToolSettings
            }
            else {
              //@ts-ignore
              field.defaultValue = this.machineOccupation?.machineOccupation?.tool?.toolSettings
              //@ts-ignore
              field.value = this.machineOccupation?.machineOccupation?.tool?.toolSettings
            }
            //console.log(this.machineOccupation)
            /*if(this.model?.tool?.name)
            {
              field.props.label += " für Werkzeug " + this.model.tool.name
            }*/
            field.className = cssClass
            //console.log(field)
            break;
          }
          case "hicumes_ProductRelationships": {
            //console.log(this.model, field)
            field.type = 'productRelationshipTable'
            if(properties["layoutName"])
            {
              // @ts-ignore
              field.props["layout"] = properties["layoutName"]
            }

            let relationships = []
            let sessStorage = sessionStorage.getItem('FormfieldRelationship_' + this.machineOccupation.machineOccupation.subProductionSteps.slice(-1)[0].externalId)
            //console.log('FormfieldRelationship_' + this.machineOccupation.machineOccupation.subProductionSteps.slice(-1)[0].externalId)
            if(sessStorage)
            {
              relationships = JSON.parse(sessStorage)
            }

            let value = {
              'productId': this.machineOccupation?.machineOccupation.productionOrder.product.id,
              'productionOrderExtId': this.machineOccupation?.machineOccupation.productionOrder.externalId,
              'subProdStepExtId': this.machineOccupation.machineOccupation.subProductionSteps.slice(-1)[0].externalId,
              'amount': this.machineOccupation?.machineOccupation.productionOrder.measurement.amount,
              'productRelationships': relationships
            }

            //console.log(this.model)
            //@ts-ignore
            field.defaultValue = value
            //@ts-ignore
            field.value = value
            //console.log(this.machineOccupation)
            /*if(this.model?.tool?.name)
            {
              field.props.label += " für Werkzeug " + this.model.tool.name
            }*/
            field.className = cssClass
            //console.log(field)
            break;
          }
          case "hicumes_CustomDuration": {
            //console.log(this.model, field)
            field.type = 'customDuration'

            field.className = cssClass
            //console.log(field)
            break;
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

          field.props.options.push({value: null
            , label: ""}) //Add empty field
          formField.options.forEach((option: any) => {
            let selectOption = {value: option.key, label: option.value};
            field.props.options.push(selectOption)
          })
          break;
        }
        case 'dynamicEnum': {

          field.type = 'select'

          if (formField.value) {
            //console.log(formField.value)
            var parseFormfield = JSON.parse(formField.value)
            //console.log(dings)
            if(typeof parseFormfield  === "object")
            {
              if(parseFormfield.selected)
              {
                //@ts-ignore
                field.defaultValue = parseFormfield.selected;
              }
              if(parseFormfield.value)
              {
                field.props.options.push({value: null
                  , label: ""}) //Add empty field

                //console.log(formField.value, vals)
                parseFormfield.value.forEach((option: any) => {

                  //console.log(formField.value, vals)
                  let valOption = {value: option.id, label: option.value};
                  field.props.options.push(valOption);
                })

                formField.value = null;
              }
            }
            else if (formField.value.toString().startsWith("[{") && formField.value.toString().endsWith("}]")) {

              field.props.options.push({value: null
                , label: ""}) //Add empty field

              var vals = JSON.parse(formField.value);

              //console.log(formField.value, vals)
              vals.forEach((option: any) => {

                //console.log(formField.value, vals)
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
      //Check if custom type should be used
      if (typeof properties === "object" && properties["type"] !== null) {
        //console.log(properties['type'])
        switch (properties['type']) {
          case "hicumes_CustomDuration": {
            //console.log(this.model, field)
            field.type = 'customDuration'

            field.className = cssClass
            //console.log(field)
            break;
          }
        }
      }
    }

    return field;
  }

  private findAllToolSettings(machineOccupation: MachineOccupation) {
    let toolSettings: any[] = [];
    machineOccupation.productionSteps.forEach((productionStep: any) => {
      //console.log(productionStep);
      productionStep.toolSettingParameters?.forEach((parameter: any) => {
        //console.log(parameter);
        toolSettings.push(parameter?.toolSettings);
      })
    })

    return _.flatten(toolSettings);
  }

  private getHeaderLayout() {
    this.uiBuilder.getDynamicFormLayout(this.machineOccupation.activeProductionStep.camundaProcessName + "-header").subscribe((formLayout) => {
      if (formLayout == undefined) {
        this.uiBuilder.getDynamicFormLayout(this.machineOccupation.machineOccupation?.camundaProcessName + "-header").subscribe((formLayout) => {
          if (formLayout == undefined) {
            this.uiBuilder.getDynamicFormLayout("globalheader").subscribe((formLayout) => {
              this.headerFormLayout = formLayout;
            });
          }
          this.headerFormLayout = formLayout;
        });
      }
      this.headerFormLayout = formLayout;
    });
    this.uiBuilder.getDynamicFormLayout(this.machineOccupation.activeProductionStep.camundaProcessName + "-title").subscribe((formLayout) => {
      if (formLayout == undefined) {
        this.uiBuilder.getDynamicFormLayout(this.machineOccupation.machineOccupation?.camundaProcessName + "-title").subscribe((formLayout) => {
          if (formLayout == undefined) {
            this.uiBuilder.getDynamicFormLayout("globaltitle").subscribe((formLayout) => {
              this.titleFormLayout = formLayout;
            });
          }
          this.titleFormLayout = formLayout;
        });
      }
      this.titleFormLayout = formLayout;
    });
  }

  private getFooterLayout() {
    this.uiBuilder.getDynamicFormLayout(this.machineOccupation.activeProductionStep.camundaProcessName + "-footer").subscribe((formLayout) => {
      if (formLayout == undefined) {
        this.uiBuilder.getDynamicFormLayout(this.machineOccupation.machineOccupation?.camundaProcessName + "-footer").subscribe((formLayout) => {
          if (formLayout == undefined) {
            this.uiBuilder.getDynamicFormLayout("globalfooter").subscribe((formLayout) => {
              this.footerFormLayout = formLayout;
            });
          }
          this.footerFormLayout = formLayout;
        });
      }
      this.footerFormLayout = formLayout;
    });
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

  pause() {
    this.openModal(PauseModalComponent, result => {
      //console.log(result)
      if (result != undefined) {
        this.onPause.emit(result)
        this.updatePaused = true;
      }
    }, {data: this.machineOccupation});
  }

  continue() {
    this.updatePaused = true;
    this.onContinue.emit();
  }

  joinMO() {
    this.onJoin.emit();
  }

  getBreakTime()
  {
    let start = moment(this.machineOccupation.camundaSubProductionSteps.slice(-1)[0].subProductionStep?.timeRecords.slice(-1)[0].startDateTime)
    let end = moment(now());
    return moment.duration(end.diff(start)).locale("de").humanize()
  }

  getPercentage() {
    return (this.machineOccupation?.machineOccupation?.totalProductionNumbers?.acceptedMeasurement?.amount / this.machineOccupation?.machineOccupation?.productionOrder?.measurement?.amount) * 100;
  }

  //TODO AUS DB LADEN
  getSuspensionName() {
    switch(this.machineOccupation.camundaSubProductionSteps.slice(-1)[0].subProductionStep?.timeRecords.slice(-1)[0].suspensionType.name)
    {
      case "BREAK_DEFAULT": return "Pause"
      case "BREAK_GeneralExpense": return "Gemeinkosten"
      case "BREAK_OrderChange": return "Auftragswechsel"
    }
    return ""
  }

  toDetailMask(machineOccupation: CamundaMachineOccupation) {
    this.router.navigate(['/order-detail' , {id: machineOccupation.id}]);
  }

  changeHeaderFooterModel($event: any) {
    //console.log(this.selectedSubMachineOccupation)
    //console.log($event.value)
    //console.log(this.cloneMachineOccupation)
    if(this.cloneMachineOccupation != undefined) {
      var subCamundaMachineOccupation = _.cloneDeep(this.cloneMachineOccupation)
      subCamundaMachineOccupation.machineOccupation = $event.value;
      //Need to use a different object than this.modelHeaderFooter, otherwise the values will be overwritten
      let modelHeaderFooter = {
        "machineOccupations": [this.utilites.restructureCamundaMachineOccupation(subCamundaMachineOccupation)],
        "machineOccupation": this.utilites.restructureCamundaMachineOccupation(subCamundaMachineOccupation),
        "camundaSubProductionSteps": subCamundaMachineOccupation?.camundaSubProductionSteps,
        "product": [subCamundaMachineOccupation?.machineOccupation.productionOrder?.product],
        "qualityControlFeatures": subCamundaMachineOccupation?.machineOccupation.productionOrder?.product?.qualityControlFeatures,
        "productRelationships": [],
        "productionSteps": subCamundaMachineOccupation?.machineOccupation?.productionSteps
      }
      this.modelHeaderFooter = _.cloneDeep(modelHeaderFooter)
      //Update the formcontrol with the new value
      this.headerForm.patchValue(modelHeaderFooter)
      this.footerForm.patchValue(modelHeaderFooter)
      this.titleForm.patchValue(modelHeaderFooter)
    }
  }

  onCollectionOrderRowSelect(model: any) {
    //console.log(model)
    this.selectedSubMachineOccupation = this.cloneMachineOccupation?.machineOccupation?.subMachineOccupations?.filter((machineOccupation) => model.externalId == machineOccupation.externalId)[0]

    //console.log(this.selectedSubMachineOccupation)
    this.changeHeaderFooterModel({value: model})
  }

  handleMouseDown(event: MouseEvent) {
    if (event.button === 0) {
      this.startTimer();
    }
  }

  handleTouchStart(event: TouchEvent) {
    if (event.touches.length === 1) {
      this.startTimer();
    }
  }

  startTimer() {
    console.log(this.model)
    let tickTimeout = 10;
    this.interval = setInterval(() => {
      this.progressValue += (tickTimeout/this.holdInterval) * 100
      //console.log(this.progressValue)
      if(this.progressValue > 110)
      {
        this.submit()
        this.stopTimer()
      }
    }, tickTimeout);
  }

  stopTimer() {
    this.progressValue = 0
    clearInterval(this.interval);
  }

  saveToSessionStorage() {
    //console.log("saving", this.model)
    let id = this.machineOccupation.machineOccupation.subProductionSteps.slice(-1)[0].externalId
    if(id) {
      sessionStorage.setItem('Formfield_' + id, JSON.stringify(this.model))
    }
  }

  loadFromSessionStorage() {
    //console.log("loading", this.model)
    let sessionValue = sessionStorage.getItem('Formfield_' + this.machineOccupation.machineOccupation.subProductionSteps.slice(-1)[0].externalId)
    let sessRelationship = sessionStorage.getItem('FormfieldRelationship_' + this.machineOccupation.machineOccupation.subProductionSteps.slice(-1)[0].externalId)
    if(sessionValue)
    {
      let json =  JSON.parse(sessionValue)
      if(sessRelationship)
      {
        json["HICUMES_PRODUCTRELATIONSHIPS"] = JSON.parse(sessRelationship)
      }
      return json
    }
    else
    {
      return {}
    }
  }

  isActiveUser() {
    //Make sure, older processes are still working
    if(!this.userOccupation || this.userOccupation.activeUsers?.length == 0)
    {
      return true;
    }

    if(this.userOccupation?.activeUsers?.find(value => value.userName == this.authGuard.getUsername()))
    {
      return true
    }
    else
    {
      return false
    }
  }
}
