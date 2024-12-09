import {APP_INITIALIZER, CUSTOM_ELEMENTS_SCHEMA, LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from "./material-modules";
import {FileTreeComponent} from './component/file-tree/file-tree.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NgxJsonViewerModule} from "ngx-json-viewer";
import {FormsModule} from '@angular/forms';
import {FormlyFieldConfig, FormlyModule} from '@ngx-formly/core';
import {FormlyMaterialModule} from '@ngx-formly/material';
import {FormlyFieldFile} from "./formlyExtensions/file-type.component";
import {FormlyDatetimePicker} from "./formlyExtensions/datetime-picker.component";
import {FormlyPdfviewer} from "./formlyExtensions/pdfviewer.component";
import {FileValueAccessor} from "./formlyExtensions/file-value-accessor";
import {TopNavigationComponent} from './component/navigation/top-navigation/top-navigation.component';
import {OrderOverviewComponent} from './container/order-overview/order-overview.component';
/*import {
  JsonToObjectPipe,
  OrderOverviewTableComponent
} from './component/order-overview/unused-order-overview-table/order-overview-table.component';*/
import {CamundaFormFieldComponent} from './component/camunda-form-field/camunda-form-field.component';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {SubproductionstepFormfieldComponent} from './container/subproductionstep-formfield/subproductionstep-formfield.component';
import {SideNavComponent} from './component/navigation/side-nav/side-nav.component';
import {OrderDetailComponent} from './container/order-detail/order-detail.component';
import {OrderDetailFormlyComponent} from './component/order-detail/order-detail-formly/order-detail-formly.component';
//import {OrderDetailAuxiliaryMaterialsComponent} from './component/order-detail/unused-order-detail-auxiliary-materials/order-detail-auxiliary-materials.component';
//import {OrderDetailSubProductionstepsComponent} from './component/order-detail/unused-order-detail-sub-productionsteps/order-detail-sub-productionsteps.component';
import {ReplaceLineBreaksPipe} from './pipes/replace-line-breaks.pipe';
//import {MaskProductionNumbersComponent} from './container/unused-mask-production-numbers/mask-production-numbers.component';
//import {ProductionNumbersTableComponent} from './component/form-masks/unused-production-numbers-table/production-numbers-table.component';
//import {ProductionNumbersFieldsComponent} from './component/form-masks/unused-production-numbers-fields/production-numbers-fields.component';
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";
import {
  OWL_DATE_TIME_FORMATS,
  OWL_DATE_TIME_LOCALE,
  OwlDateTimeModule
} from "@danielmoncada/angular-datetime-picker";
import { OwlMomentDateTimeModule } from '@danielmoncada/angular-datetime-picker-moment-adapter';
//import {ProductionNumbersAddDialogComponent} from './component/form-masks/unused-production-numbers-add-dialog/production-numbers-add-dialog.component';
import { DynamicFieldsComponent } from './container/dynamic-fields/dynamic-fields.component';
import { SanitizePipe } from './pipes/sanitize.pipe';
import { FormlyMatDatepickerModule } from '@ngx-formly/material/datepicker';
import { FormlyMatToggleModule } from '@ngx-formly/material/toggle';
import {DateAdapter, MatNativeDateModule, NativeDateAdapter} from '@angular/material/core';
import {FormlyDatatable} from "./formlyExtensions/datatable.component";
import {FormlySimple} from "./formlyExtensions/simple.component";
import {FormlyWrapperAddons} from "./formlyExtensions/addons.wrapper";
import {addonsExtension} from "./formlyExtensions/addons.extension";
//import { ProductionNumbersTableDynamicComponent } from './component/form-masks/unused-production-numbers-table-dynamic/production-numbers-table-dynamic.component';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import { MatPanelWrapper } from './formlyExtensions/matpanel.wrapper';
import {ToggleSimple} from "./formlyExtensions/toggle-simple";
import {StatusIcon} from "./formlyExtensions/status-icon.component";
/*import {
  OrderOverviewTableDynamicComponent
} from "./component/order-overview/unused-order-overview-table-dynamic/order-overview-table-dynamic.component";*/
import {MachineOccupationAction} from "./formlyExtensions/machineOccupation-action";
import {FormlyExpansiontable} from "./formlyExtensions/expansiontable.component";
import { SubproductionstepFormkey} from './formlyExtensions/subproductionstep-formkey.component';
import {FormlyJsonList} from "./formlyExtensions/jsonList.component";
import { DataTablesModule } from "angular-datatables";
import { MachineOccupationActionButtonsComponent } from './dataTablesTemplates/machine-occupation-action-buttons/machine-occupation-action-buttons.component';
import { OrderOverviewSubTableComponent } from './component/order-overview/order-overview-sub-table/order-overview-sub-table.component';
import { OrderOverviewDataTableComponent } from './component/order-overview/order-overview-data-table/order-overview-data-table.component';
import { LoginComponent } from './component/login/login.component';
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {environment} from "../environments/environment";
import { MachineOccupationNameAndDetailsButtonComponent } from './dataTablesTemplates/machine-occupation-name-and-details-button/machine-occupation-name-and-details-button.component';
import { AdvancedDataFilterComponent } from './dataTablesTemplates/advanced-data-filter/advanced-data-filter.component';
import { UniquePipe } from './pipes/unique.pipe';
import {
  MachineOccupationCheckboxComponent
} from "./dataTablesTemplates/machine-occupation-checkbox/machine-occupation-checkbox.component";
import { SplitOrderModalComponent } from './component/modals/split-order-modal/split-order-modal.component';
import {FormlySelectDatatable} from "./formlyExtensions/selectDatatable.component";
import {FormlyFieldStepper} from "./formlyExtensions/stepper.component";
import { CollectionOrderModalComponent } from './component/modals/collection-order-modal/collection-order-modal.component';
import {FormlyCamundaFormfieldOverview} from "./formlyExtensions/camundaFormfieldOverview";
import {DecimalPipe, JsonPipe, registerLocaleData} from '@angular/common';
import localeDe from '@angular/common/locales/de';
import localeDeExtra from '@angular/common/locales/extra/de';
import { BarcodeInputModalComponent } from './component/modals/barcode-input-modal/barcode-input-modal.component';
import { PdfViewerComponent } from './iframe/pdf-viewer/pdf-viewer.component';
import { SafePipe } from './pipes/safe-pipe.pipe';
import {FormlyVideoPlayer} from "./formlyExtensions/videoplayer.component";
import {FormlyImageViewer} from "./formlyExtensions/imageviewer.component";
import { ReadOnlyFormFieldComponent } from './formlyExtensions/read-only-form-field.component';
import {JsonToObjectPipe} from "./pipes/json-to-object.pipe";
import {ToolSettingTable} from "./formlyExtensions/toolSettingTable.component";
import { SettingsComponent } from './container/settings/settings.component';
import {MatpanelModalWrapper} from "./formlyExtensions/matpanelModal.wrapper";
import { PdfViewerPopoutComponent } from './component/modals/pdf-viewer-popout/pdf-viewer-popout.component';
import {UserOrderOverviewComponent} from "./container/user-order-overview/user-order-overview.component";
import {PdfJsViewerModule} from "ng2-pdfjs-viewer";
import {FormlyPdfjsviewer} from "./formlyExtensions/pdfjsviewer.component";
import {KeyValueListComponent} from "./formlyExtensions/key-value-list.component";
import { ServiceWorkerModule } from '@angular/service-worker';
import { NoteFormFieldComponent } from './component/note-form-field/note-form-field.component';
import {FormlyNoteList} from "./formlyExtensions/noteList";
import {PauseModalComponent} from "./component/modals/pause-modal/pause-modal.component";
import {FormlyDatatableAdvanced} from "./formlyExtensions/datatable_advanced.component";
import {UnitTranslationPipe} from "./pipes/unit-translation.pipe";
import { ScrollToTopComponent } from './component/scroll-to-top/scroll-to-top.component';
import { CollectionOrderEditTableComponent } from './component/collection-order-edit-table/collection-order-edit-table.component';
import {InputSimple} from "./formlyExtensions/input-simple";
import {NgxDatatableComponent} from "./formlyExtensions/ngx-datatable.component";
import {NgxDatatableModule} from "@swimlane/ngx-datatable";
import {FomrfieldHeadTableAction} from "./formlyExtensions/fomrfield-head-table-action";
import {FormlySwitch} from "./formlyExtensions/switch-input-simple.component";
import {SortPipe} from "./pipes/sort.pipe";
import { MatIconRegistry } from '@angular/material/icon';
import {NgSelectModule} from "@ng-select/ng-select";
import {NgSelectFormlyComponent} from "./formlyExtensions/autocomplete.component";
import {FormlySelectionTableBarcode} from "./formlyExtensions/selection-table-barcode.component";
import { FocusOnShowDirective } from './directives/focus-on-show.directive';
import { ChangeMachineOccupationStatusComponent } from './component/change-machine-occupation-status/change-machine-occupation-status.component';
import { ChangeMachineOccupationContainerComponent } from './container/change-machine-occupation-container/change-machine-occupation-container.component';
import {BookingOverviewComponent} from "./component/booking-overview/booking-overview.component";
import {PopupInfoButtonComponent} from "./component/order-overview/popup-info-button/popup-info-button.component";
import {FormlySimpleModal} from "./formlyExtensions/simple_modal.component";
import {ResendBookingButton} from "./formlyExtensions/resend-booking-button.component";
import {NgxSpinnerModule} from "ngx-spinner";
import {OverheadCostModal} from "./component/modals/overhead-cost-modal/overhead-cost-modal";
import {StopOverheadCostButton} from "./formlyExtensions/stop-overhead-cost-button.component";
import { OverheadCostsComponent } from './component/overhead-costs/overhead-costs.component';
import { OverheadCostsContainerComponent } from './container/overhead-costs-container/overhead-costs-container.component';
import {
  OverheadCostActionButtonsComponent
} from "./dataTablesTemplates/overhead-cost-action-buttons/overhead-cost-action-buttons.component";
import {OverheadCostNoteModal} from "./component/modals/overhead-cost-note-modal/overhead-cost-note-modal";
import {ListOpenModalComponent} from "./component/modals/list-open-modal/list-open-modal.component";
import { ScrollComponent } from './component/scroll/scroll.component';
import { SimpleModalComponent } from './component/modals/simple-modal/simple-modal.component';
import {FormlyFormfieldModal} from "./formlyExtensions/formfield_modal.component";
import {UserBookingOverviewTableComponent} from "./component/user-booking-overview-table/user-booking-overview-table.component";
import {UserBookingOverviewComponent} from "./container/user-booking-overview/user-booking-overview.component";
import {FormlyDurationModal} from "./formlyExtensions/duration_modal.component";
import {CustomDurationInput} from "./formlyExtensions/customDuration-input";
import {ProductRelationshipsTable} from "./formlyExtensions/productRelationshipsTable.component";
import {AngularSplitModule} from "angular-split";


registerLocaleData(localeDe, 'de', localeDeExtra);

export class CustomDateAdapter extends NativeDateAdapter{
  getFirstDayOfWeek(): number {
    return 1;
  }
}

export const CUSTOM_DATEPICKER_FORMAT = {
  parseInput: 'L LT',
  fullPickerInput: 'L LT',
  datePickerInput: 'L',
  timePickerInput: 'LT',
  monthYearLabel: 'MMM YYYY',
  dateA11yLabel: 'LL',
  monthYearA11yLabel: 'MMMM YYYY',
};

function initializeKeycloak(keycloak: KeycloakService): () => Promise<boolean> {
  return () =>
    keycloak.init({
      config: {
        url: environment.keycloakUrl,
        realm: environment.keycloakRealm,
        clientId: environment.keycloakClientId
      },
      loadUserProfileAtStartUp: true,
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + environment.baseref + '/assets/silent-check-sso.html',
      },
    });
}


@NgModule({
    declarations: [
        AppComponent,
        FileTreeComponent,
        FileValueAccessor,
        FormlyFieldFile,
        FormlyDatetimePicker,
        FormlyPdfviewer,
        TopNavigationComponent,
        OrderOverviewComponent,
        //OrderOverviewTableComponent,
        //OrderOverviewTableDynamicComponent,
        PopupInfoButtonComponent,
        CamundaFormFieldComponent,
        SubproductionstepFormfieldComponent,
        SideNavComponent,
        JsonToObjectPipe,
        OrderDetailComponent,
        OrderDetailFormlyComponent,
        //OrderDetailAuxiliaryMaterialsComponent,
        //OrderDetailSubProductionstepsComponent,
        ReplaceLineBreaksPipe,
        UnitTranslationPipe,
        //MaskProductionNumbersComponent,
        //ProductionNumbersTableComponent,
        //ProductionNumbersFieldsComponent,
        //ProductionNumbersAddDialogComponent,
        DynamicFieldsComponent,
        SanitizePipe,
        FormlyDatatable,
        ToolSettingTable,
        FormlyExpansiontable,
        FormlySimple,
        FormlyJsonList,
        StatusIcon,
        FormlyWrapperAddons,
        //ProductionNumbersTableDynamicComponent,
        MatPanelWrapper,
        MatpanelModalWrapper,
        ToggleSimple,
        MachineOccupationAction,
        SubproductionstepFormkey,
        MachineOccupationActionButtonsComponent,
        OrderOverviewSubTableComponent,
        OrderOverviewDataTableComponent,
        OrderOverviewDataTableComponent,
        LoginComponent,
        MachineOccupationNameAndDetailsButtonComponent,
        AdvancedDataFilterComponent,
        UniquePipe,
        MachineOccupationCheckboxComponent,
        SplitOrderModalComponent,
        FormlySelectDatatable,
        FormlyFieldStepper,
        CollectionOrderModalComponent,
        KeyValueListComponent,
        PauseModalComponent,
      FormlyPdfjsviewer,
      FormlyCamundaFormfieldOverview,
      BarcodeInputModalComponent,
      PdfViewerComponent,
      SafePipe,
      FormlyVideoPlayer,
      FormlyImageViewer,
      ReadOnlyFormFieldComponent,
      SettingsComponent,
      PdfViewerPopoutComponent,
      UserOrderOverviewComponent,
      NoteFormFieldComponent,
      FormlyNoteList,
      FormlyDatatableAdvanced,
      ScrollToTopComponent,
      CollectionOrderEditTableComponent,
      InputSimple,
      NgxDatatableComponent,
      FomrfieldHeadTableAction,
      FormlySwitch,
      SortPipe,
      NgSelectFormlyComponent,
      FormlySelectionTableBarcode,
      FocusOnShowDirective,
      ChangeMachineOccupationStatusComponent,
      ChangeMachineOccupationContainerComponent,
      BookingOverviewComponent,
      FormlySimpleModal,
      ResendBookingButton,
      OverheadCostModal,
      StopOverheadCostButton,
      OverheadCostsComponent,
      OverheadCostsContainerComponent,
      OverheadCostActionButtonsComponent,
      OverheadCostNoteModal,
      ScrollComponent,
      ListOpenModalComponent,
      SimpleModalComponent,
      FormlyFormfieldModal,
      UserBookingOverviewComponent,
      UserBookingOverviewTableComponent,
      FormlyDurationModal,
      CustomDurationInput,
      ProductRelationshipsTable
    ],
    imports: [
        HttpClientModule,
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MaterialModule,
        NgbModule,
        NgxJsonViewerModule,
        NgxMaterialTimepickerModule.setLocale('de-DE'),
        OwlDateTimeModule,
        OwlMomentDateTimeModule,
        NgxDatatableModule,
        DataTablesModule,
        KeycloakAngularModule,
        PdfJsViewerModule,
       AngularSplitModule,
        FormlyModule.forRoot({
            extras: {
                lazyRender: true,
                resetFieldOnHide: false
            },
            validationMessages: [
                {name: 'required', message: 'Dieses Feld wird benötigt!'},
                {
                    name: 'minLength',
                    message: (err: any, field: FormlyFieldConfig) => `Der Wert muss mehr als ${field.props?.minLength} Zeichen haben!`
                },
                {
                    name: 'maxLength',
                    message: (err: any, field: FormlyFieldConfig) => `Der Wert muss weniger als ${field.props?.maxLength} Zeichen haben!`
                },
                {
                    name: 'min',
                    message: (err: any, field: FormlyFieldConfig) => `Der Wert muss größer als ${field.props?.min} sein!`
                },
                {
                    name: 'max',
                    message: (err: any, field: FormlyFieldConfig) => `Der Wert muss kleiner als ${field.props?.max} sein!`
                },
                {
                    name: 'pattern',
                    message: (err: any, field: FormlyFieldConfig) => `Der Wert stimmt nicht mit dem Muster "${field.props?.pattern}" überein!`
                }
            ],
            wrappers: [
                {name: 'addons', component: FormlyWrapperAddons},
                {name: 'mat-panel', component: MatPanelWrapper},
                {name: 'mat-panel-modal', component: MatpanelModalWrapper},
            ],
            extensions: [
                {name: 'addons', extension: {onPopulate: addonsExtension}},
            ],

            types: [
                {name: 'status-icon', component: StatusIcon},
                {name: 'subproductionstep-formkey', component: SubproductionstepFormkey},
                {
                    name: 'simple', component: FormlySimple, defaultOptions: {
                        defaultValue: {},
                        templateOptions: {
                            loading: true,
                        },
                    }
                },
                {name: 'simple-modal', component: FormlySimpleModal},
                {name: 'duration-modal', component: FormlyDurationModal},
                {name: 'formfield-modal', component: FormlyFormfieldModal},
                {name: 'json-list', component: FormlyJsonList},
                {name: 'camunda-formfield-overview', component: FormlyCamundaFormfieldOverview},
                {name: 'file', component: FormlyFieldFile, wrappers: ['form-field']},
                {name: 'datetimepicker', component: FormlyDatetimePicker, wrappers: ['form-field']},
                {name: 'pdfviewer', component: FormlyPdfjsviewer, wrappers: ['mat-panel-modal']},
                {name: 'videoplayer', component: FormlyVideoPlayer, wrappers: ['mat-panel']},
                {name: 'imageviewer', component: FormlyImageViewer, wrappers: ['mat-panel']},
                {name: 'readonly-formfield', component: ReadOnlyFormFieldComponent, wrappers: ['mat-panel']},
                {name: 'toolSettingTable', component: ToolSettingTable, wrappers: ['mat-panel']},
                {name: 'productRelationshipTable', component: ProductRelationshipsTable, wrappers: ['mat-panel']},
                {name: 'toolSettingTableDetails', component: ToolSettingTable},
                {name: 'togglesimple', component: ToggleSimple},
                {name: 'resend-button', component: ResendBookingButton},
                {name: 'overhead-cost-button', component: StopOverheadCostButton},
                {name: 'inputsimple', component: InputSimple},
                {name: 'switch-input', component: FormlySwitch},
                {name: 'machineoccupationaction', component: MachineOccupationAction},
                {name: 'head-table-action', component: FomrfieldHeadTableAction},
                {name: 'select-datatable', component: FormlySelectDatatable},
                {name: 'customDuration', component: CustomDurationInput},
                {name: 'stepper', component: FormlyFieldStepper},
                {
                    name: "hicumes-autocomplete",
                    component: NgSelectFormlyComponent,
                    wrappers: ['form-field']
                },
                {
                    name: 'datatable',
                    component: FormlyDatatable,
                    defaultOptions: {
                        templateOptions: {
                            columnMode: 'force',
                            rowHeight: 'auto',
                            headerHeight: '40',
                            footerHeight: '40',
                            limit: '10',
                            scrollbarH: 'true',
                            reorderable: 'reorderable'
                        },
                    },
                },
                {
                    name: 'datatable-advanced',
                    component: FormlyDatatableAdvanced,
                    defaultOptions: {
                        templateOptions: {
                            columnMode: 'force',
                            rowHeight: 'auto',
                            headerHeight: '40',
                            footerHeight: '40',
                            limit: '10',
                            scrollbarH: 'true',
                            reorderable: 'reorderable'
                        },
                    },
                },
                {
                    name: 'selectiontable-barcode-advanced',
                    component: FormlySelectionTableBarcode,
                    defaultOptions: {
                        templateOptions: {
                            columnMode: 'force',
                            rowHeight: 'auto',
                            headerHeight: '40',
                            footerHeight: '40',
                            limit: '10',
                            scrollbarH: 'true',
                            reorderable: 'reorderable'
                        },
                    },
                },
                {
                    name: 'ngx-datatable',
                    component: NgxDatatableComponent,
                    defaultOptions: {
                        templateOptions: {
                            columnMode: 'force',
                            rowHeight: 'auto',
                            headerHeight: '40',
                            footerHeight: '40',
                            limit: '10',
                            scrollbarH: 'true',
                            reorderable: 'reorderable'
                        },
                    },
                }, {
                    name: 'noteList',
                    component: FormlyNoteList,
                    defaultOptions: {
                        templateOptions: {
                            columnMode: 'force',
                            rowHeight: 'auto',
                            headerHeight: '40',
                            footerHeight: '40',
                            limit: '10',
                            scrollbarH: 'true',
                            reorderable: 'reorderable'
                        },
                    },
                },
                {
                    name: 'key-value-list',
                    component: KeyValueListComponent,
                    defaultOptions: {
                        defaultValue: {},
                        templateOptions: {
                            columnMode: 'force',
                            rowHeight: 'auto',
                            headerHeight: '40',
                            footerHeight: '40',
                            limit: '10',
                            scrollbarH: 'true',
                            reorderable: 'reorderable'
                        },
                    },
                },
                {
                    name: 'expansiontable',
                    component: FormlyExpansiontable,
                    defaultOptions: {
                        templateOptions: {
                            columnMode: 'force',
                            rowHeight: 'auto',
                            headerHeight: '40',
                            footerHeight: '40',
                            limit: '10',
                            scrollbarH: 'true',
                            reorderable: 'reorderable'
                        },
                    },
                },
            ],

        }),
        FormlyMaterialModule,
        FormsModule,
        MatNativeDateModule,
        FormlyMatDatepickerModule,
        FormlyMatToggleModule,
        NgxExtendedPdfViewerModule,
        ServiceWorkerModule.register('ngsw-worker.js', {
            enabled: environment.production,
            // Register the ServiceWorker as soon as the application is stable
            // or after 30 seconds (whichever comes first).
            registrationStrategy: 'registerWhenStable:30000'
        }),
        FormlyModule,
        NgSelectModule,
        NgxSpinnerModule
    ],
  providers: [
    DecimalPipe,
    JsonPipe,
    UnitTranslationPipe,
    {
    provide: APP_INITIALIZER,
    useFactory: initializeKeycloak,
    multi: true,
    deps: [KeycloakService],
  },
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}},
    {provide: LOCALE_ID, useValue: 'de'},
    {provide: OWL_DATE_TIME_LOCALE, useValue: 'de'},
    {provide: OWL_DATE_TIME_FORMATS, useValue: CUSTOM_DATEPICKER_FORMAT},
    {provide: DateAdapter, useClass: CustomDateAdapter}
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class AppModule {
  constructor(private readonly iconRegistry: MatIconRegistry) {
    iconRegistry.setDefaultFontSetClass('material-symbols-outlined');
  }
}
