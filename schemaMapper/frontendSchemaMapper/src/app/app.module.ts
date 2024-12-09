import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CompleteMapperComponent} from './container/complete-mapper/complete-mapper.component';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from "./material-modules";
import {FileTreeComponent} from './component/file-tree/file-tree.component';
import {NewMappingRuleComponent} from './component/new-mapping-rule/new-mapping-rule.component';
import {AllMappingRulesComponent} from './component/all-mapping-rules/all-mapping-rules.component';
import {AdvancedSheetComponent} from './component/advanced-sheet/advanced-sheet.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NgxJsonViewerModule} from "ngx-json-viewer";
import {SaveLoadComponent} from './component/save-load/save-load.component';
import {ModalLoadMappingComponent} from './component/modal-load-mapping/modal-load-mapping.component';
import {ModalNewMappingComponent} from './component/modal-new-mapping/modal-new-mapping.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FormlyModule} from '@ngx-formly/core';
import {FormlyMaterialModule} from '@ngx-formly/material';
import {FormlyFieldFile} from "./formlyExtensions/file-type.component";
import {FileValueAccessor} from "./formlyExtensions/file-value-accessor";
import {ModalModiflyRuleComponent} from './component/modal-modifly-rule/modal-modifly-rule.component';
import { ModalCamundaOverviewComponent } from './component/modal-camunda-overview/modal-camunda-overview.component';
import {KeyValueTypeComponent} from "./formlyExtensions/key-value-type.component";
import { FormlyMatToggleModule } from '@ngx-formly/material/toggle';
import { ModalUploadFileComponent } from './component/modal-upload-file/modal-upload-file.component';
import {AllMappingRulesXSLTComponent} from "./component/all-mapping-rules-xslt/all-mapping-rules-xslt.component";
import {CodeEditorModule} from "@ngstack/code-editor";

@NgModule({
  declarations: [
    AppComponent,
    CompleteMapperComponent,
    FileTreeComponent,
    NewMappingRuleComponent,
    AllMappingRulesComponent,
    AllMappingRulesXSLTComponent,
    AdvancedSheetComponent,
    SaveLoadComponent,
    ModalLoadMappingComponent,
    ModalNewMappingComponent,
    FileValueAccessor,
    FormlyFieldFile,
    ModalModiflyRuleComponent,
    ModalCamundaOverviewComponent,
    KeyValueTypeComponent,
    ModalUploadFileComponent,

  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    NgbModule,
    NgxJsonViewerModule,
    ReactiveFormsModule,
    FormlyMatToggleModule,
    CodeEditorModule.forRoot(),
    FormlyModule.forRoot({
      extras: {lazyRender: true},
      validationMessages: [
        {name: 'required', message: 'Dieses Feld wird benötigt!'},
        {name: 'minlength', message: 'Die Länge muss mindestens 5 Zeichen betragen!'}
      ],
      types: [
        {name: 'file', component: FormlyFieldFile, wrappers: ['form-field']},
        {name: 'keyvalue', component: KeyValueTypeComponent},
      ],
    }),
    FormlyMaterialModule,
    FormsModule
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
