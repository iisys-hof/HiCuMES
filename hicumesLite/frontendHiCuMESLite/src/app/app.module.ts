import {APP_INITIALIZER, CUSTOM_ELEMENTS_SCHEMA, LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from "./material-modules";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';
import {FormlyFieldConfig, FormlyModule} from '@ngx-formly/core';
import {FormlyMaterialModule} from '@ngx-formly/material';
import {FormlyDatetimePicker} from "./formlyExtensions/datetime-picker.component";
import {TopNavigationComponent} from './component/navigation/top-navigation/top-navigation.component';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {SideNavComponent} from './component/navigation/side-nav/side-nav.component';
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";
import {
  OWL_DATE_TIME_FORMATS,
  OWL_DATE_TIME_LOCALE,
  OwlDateTimeModule
} from "@danielmoncada/angular-datetime-picker";
import { OwlMomentDateTimeModule } from '@danielmoncada/angular-datetime-picker-moment-adapter';
import { SanitizePipe } from './pipes/sanitize.pipe';
import { FormlyMatDatepickerModule } from '@ngx-formly/material/datepicker';
import { FormlyMatToggleModule } from '@ngx-formly/material/toggle';
import {DateAdapter, MatNativeDateModule, NativeDateAdapter} from '@angular/material/core';
import {FormlyDatatable} from "./formlyExtensions/datatable.component";
import {FormlySimple} from "./formlyExtensions/simple.component";
import {FormlyWrapperAddons} from "./formlyExtensions/addons.wrapper";
import {addonsExtension} from "./formlyExtensions/addons.extension";
import { MatPanelWrapper } from './formlyExtensions/matpanel.wrapper';
import {ToggleSimple} from "./formlyExtensions/toggle-simple";
import {StatusIcon} from "./formlyExtensions/status-icon.component";
import {FormlyJsonList} from "./formlyExtensions/jsonList.component";
import {environment} from "../environments/environment";
import {FormlySelectDatatable} from "./formlyExtensions/selectDatatable.component";
import {FormlyFieldStepper} from "./formlyExtensions/stepper.component";
import {DecimalPipe, JsonPipe, registerLocaleData} from '@angular/common';
import localeDe from '@angular/common/locales/de';
import localeDeExtra from '@angular/common/locales/extra/de';
import { SafePipe } from './pipes/safe-pipe.pipe';
import { ReadOnlyFormFieldComponent } from './formlyExtensions/read-only-form-field.component';
import {JsonToObjectPipe} from "./pipes/json-to-object.pipe";
import {KeyValueListComponent} from "./formlyExtensions/key-value-list.component";
import { ServiceWorkerModule } from '@angular/service-worker';
import {FormlyDatatableAdvanced} from "./formlyExtensions/datatable_advanced.component";
import {InputSimple} from "./formlyExtensions/input-simple";
import {FormlySwitch} from "./formlyExtensions/switch-input-simple.component";
import { MatIconRegistry } from '@angular/material/icon';
import {NgSelectModule} from "@ng-select/ng-select";
import {NgSelectFormlyComponent} from "./formlyExtensions/autocomplete.component";
import {FormlySelectionTableBarcode} from "./formlyExtensions/selection-table-barcode.component";
import { FocusOnShowDirective } from './directives/focus-on-show.directive';
import {FormlySimpleModal} from "./formlyExtensions/simple_modal.component";
import {NgxSpinnerModule} from "ngx-spinner";
import {FormlyFormfieldModal} from "./formlyExtensions/formfield_modal.component";
import {FormlyDurationModal} from "./formlyExtensions/duration_modal.component";
import {CustomDurationInput} from "./formlyExtensions/customDuration-input";
import {BookingTerminalComponent} from "./component/booking-terminal/booking-terminal.component";
import { BookingTerminalViewComponent } from './container/booking-terminal-view/booking-terminal-view.component';
import {BreakToggleComponent} from "./formlyExtensions/break_toggle.component";

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


@NgModule({
  declarations: [
    AppComponent,
    FormlyDatetimePicker,
    TopNavigationComponent,
    SideNavComponent,
    JsonToObjectPipe,
    SanitizePipe,
    FormlyDatatable,
    FormlySimple,
    FormlyJsonList,
    StatusIcon,
    FormlyWrapperAddons,
    MatPanelWrapper,
    ToggleSimple,
    FormlySelectDatatable,
    FormlyFieldStepper,
    KeyValueListComponent,
    SafePipe,
    ReadOnlyFormFieldComponent,
    FormlyDatatableAdvanced,
    InputSimple,
    FormlySwitch,
    NgSelectFormlyComponent,
    FormlySelectionTableBarcode,
    FocusOnShowDirective,
    FormlySimpleModal,
    FormlyFormfieldModal,
    FormlyDurationModal,
    CustomDurationInput,
    BookingTerminalComponent,
    BookingTerminalComponent,
    BookingTerminalViewComponent,
    BreakToggleComponent
  ],
    imports: [
        HttpClientModule,
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MaterialModule,
        NgbModule,
        NgxMaterialTimepickerModule.setLocale('de-DE'),
        OwlDateTimeModule,
        OwlMomentDateTimeModule,
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
            ],
            extensions: [
                {name: 'addons', extension: {onPopulate: addonsExtension}},
            ],

            types: [
                {name: 'status-icon', component: StatusIcon},
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
                {name: 'datetimepicker', component: FormlyDatetimePicker, wrappers: ['form-field']},
                {name: 'readonly-formfield', component: ReadOnlyFormFieldComponent, wrappers: ['mat-panel']},
                {name: 'togglesimple', component: ToggleSimple},
                {name: 'inputsimple', component: InputSimple},
                {name: 'switch-input', component: FormlySwitch},
                {name: 'select-datatable', component: FormlySelectDatatable},
                {name: 'customDuration', component: CustomDurationInput},
                {name: 'breakToggle', component: BreakToggleComponent},
                {name: 'stepper', component: FormlyFieldStepper},
                {
                    name: "hicumes-autocomplete",
                    component: NgSelectFormlyComponent,
                    wrappers: ['form-field']
                },
                {
                    name: 'datatable',
                    component: FormlyDatatable,
                    wrappers: ['mat-panel'],
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
            ],

        }),
        FormlyMaterialModule,
        FormsModule,
        MatNativeDateModule,
        FormlyMatDatepickerModule,
        FormlyMatToggleModule,
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
