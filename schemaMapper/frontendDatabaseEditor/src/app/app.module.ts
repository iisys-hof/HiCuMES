import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';

import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from '@angular/forms';
import {FormlyModule} from '@ngx-formly/core';
import {RepeatTypeComponent} from './components/repeat-section.type';
import {FormlyBootstrapModule} from '@ngx-formly/bootstrap';
import {PanelWrapperComponent} from "./components/panel-wrapper.component";
import {EntityMasterComponent} from './components/entity-master/entity-master.component';
import {EntityDetailComponent} from './components/entity-detail/entity-detail.component';
import {MaterialModule} from "./materialModulesComponent";
import {ModalCreateFieldsComponent} from './components/modal-create-fields/modal-create-fields.component';

@NgModule({
  declarations: [
    AppComponent,
    RepeatTypeComponent,
    EntityMasterComponent,
    EntityDetailComponent,
    ModalCreateFieldsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CommonModule,
    ReactiveFormsModule,
    FormlyModule.forRoot({
      extras: {lazyRender: true},
      validationMessages: [

        { name: 'required', message: 'Dieses Feld wird benötigt!' },
        { name: 'minlength', message: 'Die Länge muss mindestens 5 Zeichen betragen!' }
      ],
      wrappers: [
        {name: 'panel', component: PanelWrapperComponent},
      ],
      types: [
        {name: 'repeat', component: RepeatTypeComponent},
      ],
    }),
    FormlyBootstrapModule,
    FormlyModule,
    MaterialModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
