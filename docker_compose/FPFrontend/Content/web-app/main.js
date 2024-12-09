(self["webpackChunkweb_app"] = self["webpackChunkweb_app"] || []).push([["main"],{

/***/ 9090:
/*!****************************************************!*\
  !*** ./apps/web-app/src/app/app-routing.module.ts ***!
  \****************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppRoutingModule": () => (/* binding */ AppRoutingModule)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ 124);



const routes = [{
  path: 'vis-timeline',
  loadChildren: () => __webpack_require__.e(/*! import() */ "apps_web-app_src_app_gantt_demo_gantt-demo_module_ts").then(__webpack_require__.bind(__webpack_require__, /*! ./gantt demo/gantt-demo.module */ 5582)).then(m => m.GanttDemoModule)
}, {
  path: '**',
  redirectTo: 'vis-timeline/timeline'
}];
let AppRoutingModule = class AppRoutingModule {};
AppRoutingModule = (0,tslib__WEBPACK_IMPORTED_MODULE_0__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_1__.NgModule)({
  imports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__.RouterModule.forRoot(routes)],
  exports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__.RouterModule]
})], AppRoutingModule);


/***/ }),

/***/ 2635:
/*!********************************************!*\
  !*** ./apps/web-app/src/app/app.module.ts ***!
  \********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppModule": () => (/* binding */ AppModule)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/common/http */ 8987);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/platform-browser */ 4497);
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/platform-browser/animations */ 7146);
/* harmony import */ var _angular_service_worker__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! @angular/service-worker */ 3769);
/* harmony import */ var _ngrx_data__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @ngrx/data */ 781);
/* harmony import */ var _ngrx_effects__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! @ngrx/effects */ 5405);
/* harmony import */ var _ngrx_router_store__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @ngrx/router-store */ 1611);
/* harmony import */ var _ngrx_store__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @ngrx/store */ 3488);
/* harmony import */ var _ngrx_store_devtools__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! @ngrx/store-devtools */ 5242);
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../environments/environment */ 7960);
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./app-routing.module */ 9090);
/* harmony import */ var _core_containers_app_app_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./core/containers/app/app.component */ 5942);
/* harmony import */ var _core_core_module__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./core/core.module */ 2814);
/* harmony import */ var _core_store_data_entity_metadata__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./core/store/data/entity-metadata */ 7128);
/* harmony import */ var _core_store_effects__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./core/store/effects */ 3520);
/* harmony import */ var _core_store_reducers__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./core/store/reducers */ 5986);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_26__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _material_material_module__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./material/material.module */ 4653);
/* harmony import */ var ngx_toastr__WEBPACK_IMPORTED_MODULE_22__ = __webpack_require__(/*! ngx-toastr */ 4817);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! @angular/material/button */ 4522);
/* harmony import */ var _ngx_formly_core__WEBPACK_IMPORTED_MODULE_23__ = __webpack_require__(/*! @ngx-formly/core */ 4599);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_25__ = __webpack_require__(/*! @angular/forms */ 2508);
/* harmony import */ var _ngx_formly_material__WEBPACK_IMPORTED_MODULE_24__ = __webpack_require__(/*! @ngx-formly/material */ 9009);
/* harmony import */ var _extensions_FormlyExtensionSimpleField__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./extensions/FormlyExtensionSimpleField */ 4104);
/* harmony import */ var _extensions_FormlyWrapperDynamicStyle__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./extensions/FormlyWrapperDynamicStyle */ 8393);



























let AppModule = class AppModule {};
AppModule = (0,tslib__WEBPACK_IMPORTED_MODULE_10__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_11__.NgModule)({
  imports: [_angular_platform_browser__WEBPACK_IMPORTED_MODULE_12__.BrowserModule, _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_13__.BrowserAnimationsModule, _angular_common_http__WEBPACK_IMPORTED_MODULE_14__.HttpClientModule, _app_routing_module__WEBPACK_IMPORTED_MODULE_1__.AppRoutingModule, _ngrx_store__WEBPACK_IMPORTED_MODULE_15__.StoreModule.forRoot(_core_store_reducers__WEBPACK_IMPORTED_MODULE_6__.reducers, {
    metaReducers: _core_store_reducers__WEBPACK_IMPORTED_MODULE_6__.metaReducers,
    runtimeChecks: {
      strictStateImmutability: true,
      strictActionImmutability: true,
      strictStateSerializability: true,
      strictActionSerializability: true
    }
  }), _ngrx_router_store__WEBPACK_IMPORTED_MODULE_16__.StoreRouterConnectingModule.forRoot({
    routerState: 1 /* RouterState.Minimal */
  }), _ngrx_effects__WEBPACK_IMPORTED_MODULE_17__.EffectsModule.forRoot(_core_store_effects__WEBPACK_IMPORTED_MODULE_5__.effects), _ngrx_data__WEBPACK_IMPORTED_MODULE_18__.EntityDataModule.forRoot(_core_store_data_entity_metadata__WEBPACK_IMPORTED_MODULE_4__.entityConfig), !_environments_environment__WEBPACK_IMPORTED_MODULE_0__.environment.production ? _ngrx_store_devtools__WEBPACK_IMPORTED_MODULE_19__.StoreDevtoolsModule.instrument() : [], _angular_service_worker__WEBPACK_IMPORTED_MODULE_20__.ServiceWorkerModule.register('/ngsw-worker.js', {
    enabled: _environments_environment__WEBPACK_IMPORTED_MODULE_0__.environment.production
  }), _core_core_module__WEBPACK_IMPORTED_MODULE_3__.CoreModule, _material_material_module__WEBPACK_IMPORTED_MODULE_7__.MaterialModule, _angular_material_button__WEBPACK_IMPORTED_MODULE_21__.MatButtonModule, _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_13__.BrowserAnimationsModule, ngx_toastr__WEBPACK_IMPORTED_MODULE_22__.ToastrModule.forRoot({
    positionClass: 'toast-bottom-right'
  }), _ngx_formly_core__WEBPACK_IMPORTED_MODULE_23__.FormlyModule.forRoot({
    types: [{
      name: 'simple',
      component: _extensions_FormlyExtensionSimpleField__WEBPACK_IMPORTED_MODULE_8__.FormlyExtensionSimpleField
    }],
    wrappers: [{
      name: 'dynamic-style-wrapper',
      component: _extensions_FormlyWrapperDynamicStyle__WEBPACK_IMPORTED_MODULE_9__.FormlyWrapperDynamicStyle
    }]
  }), _ngx_formly_material__WEBPACK_IMPORTED_MODULE_24__.FormlyMaterialModule, _angular_forms__WEBPACK_IMPORTED_MODULE_25__.ReactiveFormsModule, _ngx_formly_material__WEBPACK_IMPORTED_MODULE_24__.FormlyMaterialModule],
  providers: [_angular_common__WEBPACK_IMPORTED_MODULE_26__.DatePipe],
  bootstrap: [_core_containers_app_app_component__WEBPACK_IMPORTED_MODULE_2__.AppComponent]
})], AppModule);


/***/ }),

/***/ 8126:
/*!*************************************************************************************!*\
  !*** ./apps/web-app/src/app/core/components/main-toolbar/main-toolbar.component.ts ***!
  \*************************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "MainToolbarComponent": () => (/* binding */ MainToolbarComponent)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _main_toolbar_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./main-toolbar.component.html?ngResource */ 3806);
/* harmony import */ var _main_toolbar_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./main-toolbar.component.scss?ngResource */ 3720);
/* harmony import */ var _main_toolbar_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_main_toolbar_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);




let MainToolbarComponent = class MainToolbarComponent {
  constructor() {
    this.toggleSidenav = new _angular_core__WEBPACK_IMPORTED_MODULE_2__.EventEmitter();
  }
  emitToggleSidenav() {
    this.toggleSidenav.emit(true);
  }
};
MainToolbarComponent.propDecorators = {
  toggleSidenav: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_2__.Output
  }]
};
MainToolbarComponent = (0,tslib__WEBPACK_IMPORTED_MODULE_3__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_2__.Component)({
  selector: 'app-main-toolbar',
  template: _main_toolbar_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__,
  styles: [(_main_toolbar_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default())]
})], MainToolbarComponent);


/***/ }),

/***/ 7945:
/*!***********************************************************************************************!*\
  !*** ./apps/web-app/src/app/core/components/sidenav-list-item/sidenav-list-item.component.ts ***!
  \***********************************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "SidenavListItemComponent": () => (/* binding */ SidenavListItemComponent)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _sidenav_list_item_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./sidenav-list-item.component.html?ngResource */ 8108);
/* harmony import */ var _sidenav_list_item_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./sidenav-list-item.component.scss?ngResource */ 8470);
/* harmony import */ var _sidenav_list_item_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_sidenav_list_item_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);




let SidenavListItemComponent = class SidenavListItemComponent {
  constructor() {
    this.icon = '';
    this.hint = '';
    this.routerLink = '/';
    this.navigate = new _angular_core__WEBPACK_IMPORTED_MODULE_2__.EventEmitter();
  }
};
SidenavListItemComponent.propDecorators = {
  icon: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_2__.Input
  }],
  hint: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_2__.Input
  }],
  routerLink: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_2__.Input
  }],
  navigate: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_2__.Output
  }]
};
SidenavListItemComponent = (0,tslib__WEBPACK_IMPORTED_MODULE_3__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_2__.Component)({
  selector: 'app-sidenav-list-item',
  template: _sidenav_list_item_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__,
  styles: [(_sidenav_list_item_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default())]
})], SidenavListItemComponent);


/***/ }),

/***/ 3428:
/*!***************************************************************************!*\
  !*** ./apps/web-app/src/app/core/components/sidenav/sidenav.component.ts ***!
  \***************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "SidenavComponent": () => (/* binding */ SidenavComponent)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _sidenav_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./sidenav.component.html?ngResource */ 5934);
/* harmony import */ var _sidenav_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./sidenav.component.scss?ngResource */ 6129);
/* harmony import */ var _sidenav_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_sidenav_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);




let SidenavComponent = class SidenavComponent {
  constructor() {
    this.toggleSidenav = new _angular_core__WEBPACK_IMPORTED_MODULE_2__.EventEmitter();
    this.closeSidenav = new _angular_core__WEBPACK_IMPORTED_MODULE_2__.EventEmitter();
  }
};
SidenavComponent.propDecorators = {
  toggleSidenav: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_2__.Output
  }],
  closeSidenav: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_2__.Output
  }]
};
SidenavComponent = (0,tslib__WEBPACK_IMPORTED_MODULE_3__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_2__.Component)({
  selector: 'app-sidenav',
  template: _sidenav_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__,
  styles: [(_sidenav_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default())]
})], SidenavComponent);


/***/ }),

/***/ 5942:
/*!*******************************************************************!*\
  !*** ./apps/web-app/src/app/core/containers/app/app.component.ts ***!
  \*******************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppComponent": () => (/* binding */ AppComponent)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _app_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./app.component.html?ngResource */ 144);
/* harmony import */ var _app_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./app.component.scss?ngResource */ 8654);
/* harmony import */ var _app_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_app_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _ngrx_store__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @ngrx/store */ 3488);
/* harmony import */ var _store_actions__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../store/actions */ 5576);
/* harmony import */ var _store_reducers__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../store/reducers */ 5986);







let AppComponent = class AppComponent {
  constructor(store) {
    this.store = store;
    this.showSidenav$ = this.store.pipe((0,_ngrx_store__WEBPACK_IMPORTED_MODULE_4__.select)(_store_reducers__WEBPACK_IMPORTED_MODULE_3__.getShowSidenav));
    this.title$ = this.store.pipe((0,_ngrx_store__WEBPACK_IMPORTED_MODULE_4__.select)(_store_reducers__WEBPACK_IMPORTED_MODULE_3__.getTitle));
  }
  openSidenav() {
    this.store.dispatch(_store_actions__WEBPACK_IMPORTED_MODULE_2__.openSidenav());
  }
  closeSidenav() {
    this.store.dispatch(_store_actions__WEBPACK_IMPORTED_MODULE_2__.closeSidenav());
  }
  toggleSidenav() {
    this.store.dispatch(_store_actions__WEBPACK_IMPORTED_MODULE_2__.toggleSidenav());
  }
  sidenavChanged(sidenavOpened) {
    if (sidenavOpened) {
      this.openSidenav();
    } else {
      this.closeSidenav();
    }
  }
};
AppComponent.ctorParameters = () => [{
  type: _ngrx_store__WEBPACK_IMPORTED_MODULE_4__.Store
}];
AppComponent = (0,tslib__WEBPACK_IMPORTED_MODULE_5__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_6__.Component)({
  selector: 'app-root',
  changeDetection: _angular_core__WEBPACK_IMPORTED_MODULE_6__.ChangeDetectionStrategy.OnPush,
  template: _app_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__,
  styles: [(_app_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default())]
}), (0,tslib__WEBPACK_IMPORTED_MODULE_5__.__metadata)("design:paramtypes", [_ngrx_store__WEBPACK_IMPORTED_MODULE_4__.Store])], AppComponent);


/***/ }),

/***/ 8397:
/*!*********************************************************************!*\
  !*** ./apps/web-app/src/app/core/containers/home/home.component.ts ***!
  \*********************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "HomeComponent": () => (/* binding */ HomeComponent)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _home_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./home.component.html?ngResource */ 6623);
/* harmony import */ var _home_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./home.component.scss?ngResource */ 3965);
/* harmony import */ var _home_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_home_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _ngrx_store__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @ngrx/store */ 3488);
/* harmony import */ var _store_actions__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../store/actions */ 5576);
/* harmony import */ var _store_reducers__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../store/reducers */ 5986);







let HomeComponent = class HomeComponent {
  constructor(store) {
    this.store = store;
    this.store.dispatch(_store_actions__WEBPACK_IMPORTED_MODULE_2__.setTitle({
      title: 'App Home'
    }));
    this.title$ = this.store.pipe((0,_ngrx_store__WEBPACK_IMPORTED_MODULE_4__.select)(_store_reducers__WEBPACK_IMPORTED_MODULE_3__.getTitle));
  }
};
HomeComponent.ctorParameters = () => [{
  type: _ngrx_store__WEBPACK_IMPORTED_MODULE_4__.Store
}];
HomeComponent = (0,tslib__WEBPACK_IMPORTED_MODULE_5__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_6__.Component)({
  selector: 'app-home',
  changeDetection: _angular_core__WEBPACK_IMPORTED_MODULE_6__.ChangeDetectionStrategy.OnPush,
  template: _home_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__,
  styles: [(_home_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default())]
}), (0,tslib__WEBPACK_IMPORTED_MODULE_5__.__metadata)("design:paramtypes", [_ngrx_store__WEBPACK_IMPORTED_MODULE_4__.Store])], HomeComponent);


/***/ }),

/***/ 2814:
/*!**************************************************!*\
  !*** ./apps/web-app/src/app/core/core.module.ts ***!
  \**************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "COMPONENTS": () => (/* binding */ COMPONENTS),
/* harmony export */   "CoreModule": () => (/* binding */ CoreModule)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/common/http */ 8987);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var ngx_markdown__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ngx-markdown */ 982);
/* harmony import */ var _shared__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../shared */ 9961);
/* harmony import */ var _shared_material__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../shared/material */ 8349);
/* harmony import */ var _components_main_toolbar_main_toolbar_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./components/main-toolbar/main-toolbar.component */ 8126);
/* harmony import */ var _components_sidenav_list_item_sidenav_list_item_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./components/sidenav-list-item/sidenav-list-item.component */ 7945);
/* harmony import */ var _components_sidenav_sidenav_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./components/sidenav/sidenav.component */ 3428);
/* harmony import */ var _containers_app_app_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./containers/app/app.component */ 5942);
/* harmony import */ var _containers_home_home_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./containers/home/home.component */ 8397);
/* harmony import */ var _services__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./services */ 3084);














const COMPONENTS = [_containers_app_app_component__WEBPACK_IMPORTED_MODULE_5__.AppComponent, _containers_home_home_component__WEBPACK_IMPORTED_MODULE_6__.HomeComponent, _components_main_toolbar_main_toolbar_component__WEBPACK_IMPORTED_MODULE_2__.MainToolbarComponent, _components_sidenav_sidenav_component__WEBPACK_IMPORTED_MODULE_4__.SidenavComponent, _components_sidenav_list_item_sidenav_list_item_component__WEBPACK_IMPORTED_MODULE_3__.SidenavListItemComponent];
let CoreModule = class CoreModule {
  constructor(parentModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded. Import it in the AppModule only');
    }
  }
};
CoreModule.ctorParameters = () => [{
  type: CoreModule,
  decorators: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_8__.Optional
  }, {
    type: _angular_core__WEBPACK_IMPORTED_MODULE_8__.SkipSelf
  }]
}];
CoreModule = (0,tslib__WEBPACK_IMPORTED_MODULE_9__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_8__.NgModule)({
  imports: [_angular_common__WEBPACK_IMPORTED_MODULE_10__.CommonModule, _angular_router__WEBPACK_IMPORTED_MODULE_11__.RouterModule, _shared_material__WEBPACK_IMPORTED_MODULE_1__.MaterialModule, ngx_markdown__WEBPACK_IMPORTED_MODULE_12__.MarkdownModule.forRoot(), _shared__WEBPACK_IMPORTED_MODULE_0__.SharedModule],
  declarations: COMPONENTS,
  exports: COMPONENTS,
  providers: [{
    provide: _angular_common_http__WEBPACK_IMPORTED_MODULE_13__.HTTP_INTERCEPTORS,
    useClass: _services__WEBPACK_IMPORTED_MODULE_7__.HttpInterceptorService,
    multi: true
  }]
}), (0,tslib__WEBPACK_IMPORTED_MODULE_9__.__metadata)("design:paramtypes", [CoreModule])], CoreModule);


/***/ }),

/***/ 6958:
/*!***********************************************************!*\
  !*** ./apps/web-app/src/app/core/services/app.service.ts ***!
  \***********************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppService": () => (/* binding */ AppService)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material/snack-bar */ 930);
/* harmony import */ var _angular_service_worker__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/service-worker */ 3769);





let AppService = class AppService {
  constructor(swUpdate, snackBar, platformId) {
    this.swUpdate = swUpdate;
    this.snackBar = snackBar;
    this.platformId = platformId;
    this.checkForSwUpdate();
  }
  checkForSwUpdate() {
    if (this.swUpdate.isEnabled) {
      this.swUpdate.available.subscribe(() => {
        const message = `App update avalable! Reload?`;
        this.snackBar.open(message, 'OK', {
          duration: 15000
        }).onAction().subscribe(() => {
          this.swUpdate.activateUpdate().then(() => document.location.reload());
        });
      });
    }
  }
  getMdSidenavContent() {
    let mdSidenavContent = null;
    if ((0,_angular_common__WEBPACK_IMPORTED_MODULE_0__.isPlatformBrowser)(this.platformId)) {
      const appSidenavContainer = document.getElementById('appSidenavContainer');
      if (appSidenavContainer) {
        const sideNavContent = appSidenavContainer.getElementsByClassName('mat-drawer-content');
        if (sideNavContent.length) {
          mdSidenavContent = sideNavContent[0];
        }
      }
    }
    return mdSidenavContent;
  }
  scrollToTop(animate = false) {
    if ((0,_angular_common__WEBPACK_IMPORTED_MODULE_0__.isPlatformBrowser)(this.platformId)) {
      window.scrollTo(0, 0);
      const mdSidenavContent = this.getMdSidenavContent();
      if (mdSidenavContent) {
        if (animate) {
          // t: current time, b: begInnIng value, c: change In value, d: duration
          const easeOutExpo = (x, t, b, c, d) => {
            return t === d ? b + c : c * (-Math.pow(2, -10 * t / d) + 1) + b;
          };
          const start = Date.now();
          const duration = 1000;
          const ease = x => {
            const t = (Date.now() - start) / 10;
            const b = mdSidenavContent.scrollTop;
            const c = -b;
            const d = duration;
            return Math.floor(easeOutExpo(x, t, b, c, d));
          };
          const loop = () => {
            if (mdSidenavContent.scrollTop > 0) {
              mdSidenavContent.scrollTop = ease(mdSidenavContent.scrollTop);
              setTimeout(() => {
                loop();
              }, 0);
            }
          };
          loop();
        } else {
          mdSidenavContent.scrollTop = 0;
        }
      }
    }
  }
};
AppService.ctorParameters = () => [{
  type: _angular_service_worker__WEBPACK_IMPORTED_MODULE_1__.SwUpdate
}, {
  type: _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_2__.MatSnackBar
}, {
  type: undefined,
  decorators: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_3__.Inject,
    args: [_angular_core__WEBPACK_IMPORTED_MODULE_3__.PLATFORM_ID]
  }]
}];
AppService = (0,tslib__WEBPACK_IMPORTED_MODULE_4__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_3__.Injectable)({
  providedIn: 'root'
}), (0,tslib__WEBPACK_IMPORTED_MODULE_4__.__metadata)("design:paramtypes", [_angular_service_worker__WEBPACK_IMPORTED_MODULE_1__.SwUpdate, _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_2__.MatSnackBar, Object])], AppService);


/***/ }),

/***/ 6609:
/*!************************************************************************!*\
  !*** ./apps/web-app/src/app/core/services/http-interceptor.service.ts ***!
  \************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "HttpInterceptorService": () => (/* binding */ HttpInterceptorService)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ 8987);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/snack-bar */ 930);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs */ 5474);
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/operators */ 9337);
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! rxjs/operators */ 3158);
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../environments/environment */ 7960);








let HttpInterceptorService = class HttpInterceptorService {
  constructor(router, snackBar) {
    this.router = router;
    this.snackBar = snackBar;
    this.handleServiceError = error => {
      let errMsg;
      if (error instanceof _angular_common_http__WEBPACK_IMPORTED_MODULE_1__.HttpErrorResponse) {
        if (error.status === 403) {
          errMsg = '403 - Forbidden';
        } else if (error.status === 401) {
          errMsg = '401 - Unauthorized';
        }
      } else {
        errMsg = error.message ? error.message : error.toString();
      }
      this.snackBar.open(errMsg);
      return (0,rxjs__WEBPACK_IMPORTED_MODULE_2__.throwError)(error);
    };
    this.logDebug = !_environments_environment__WEBPACK_IMPORTED_MODULE_0__.environment.production;
  }
  intercept(req, next) {
    const started = Date.now();
    const authToken = '';
    // You could add auth tokens here to all HTTPClient requests if needed
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${authToken}`
      }
    });
    // Pass on the cloned request instead of the original request.
    return next.handle(req).pipe((0,rxjs_operators__WEBPACK_IMPORTED_MODULE_3__.tap)(event => {
      if (event instanceof _angular_common_http__WEBPACK_IMPORTED_MODULE_1__.HttpResponse) {
        const elapsed = Date.now() - started;
        if (this.logDebug) {
          console.log(`Request for ${req.method}: ${req.urlWithParams} ` + `took ${elapsed} ms ` + `with status ${event.status} - ${event.statusText}. ` + `Got data: `, event.body);
          if (req.method === 'PUT' || req.method === 'POST') {
            console.log(`Sent ${req.method} with body:`, req.body);
          }
        }
      }
    }), (0,rxjs_operators__WEBPACK_IMPORTED_MODULE_4__.catchError)((error, caught) => {
      console.log('Interceptor caught error', error, caught);
      return this.handleServiceError(error);
    }));
  }
};
HttpInterceptorService.ctorParameters = () => [{
  type: _angular_router__WEBPACK_IMPORTED_MODULE_5__.Router
}, {
  type: _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_6__.MatSnackBar
}];
HttpInterceptorService = (0,tslib__WEBPACK_IMPORTED_MODULE_7__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_8__.Injectable)(), (0,tslib__WEBPACK_IMPORTED_MODULE_7__.__metadata)("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_5__.Router, _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_6__.MatSnackBar])], HttpInterceptorService);


/***/ }),

/***/ 3084:
/*!*****************************************************!*\
  !*** ./apps/web-app/src/app/core/services/index.ts ***!
  \*****************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "AppService": () => (/* reexport safe */ _app_service__WEBPACK_IMPORTED_MODULE_0__.AppService),
/* harmony export */   "HttpInterceptorService": () => (/* reexport safe */ _http_interceptor_service__WEBPACK_IMPORTED_MODULE_1__.HttpInterceptorService)
/* harmony export */ });
/* harmony import */ var _app_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./app.service */ 6958);
/* harmony import */ var _http_interceptor_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./http-interceptor.service */ 6609);



/***/ }),

/***/ 5576:
/*!**********************************************************!*\
  !*** ./apps/web-app/src/app/core/store/actions/index.ts ***!
  \**********************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "closeSidenav": () => (/* reexport safe */ _layout_action__WEBPACK_IMPORTED_MODULE_0__.closeSidenav),
/* harmony export */   "openSidenav": () => (/* reexport safe */ _layout_action__WEBPACK_IMPORTED_MODULE_0__.openSidenav),
/* harmony export */   "setTitle": () => (/* reexport safe */ _layout_action__WEBPACK_IMPORTED_MODULE_0__.setTitle),
/* harmony export */   "toggleSidenav": () => (/* reexport safe */ _layout_action__WEBPACK_IMPORTED_MODULE_0__.toggleSidenav)
/* harmony export */ });
/* harmony import */ var _layout_action__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./layout.action */ 292);


/***/ }),

/***/ 292:
/*!******************************************************************!*\
  !*** ./apps/web-app/src/app/core/store/actions/layout.action.ts ***!
  \******************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "closeSidenav": () => (/* binding */ closeSidenav),
/* harmony export */   "openSidenav": () => (/* binding */ openSidenav),
/* harmony export */   "setTitle": () => (/* binding */ setTitle),
/* harmony export */   "toggleSidenav": () => (/* binding */ toggleSidenav)
/* harmony export */ });
/* harmony import */ var _ngrx_store__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @ngrx/store */ 3488);

const openSidenav = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_0__.createAction)('[Layout] Open Sidenav');
const closeSidenav = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_0__.createAction)('[Layout] Close Sidenav');
const toggleSidenav = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_0__.createAction)('[Layout] Toggle Sidenav');
const setTitle = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_0__.createAction)('[Layout] Set Title', (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_0__.props)());

/***/ }),

/***/ 9371:
/*!******************************************************************!*\
  !*** ./apps/web-app/src/app/core/store/actions/router.action.ts ***!
  \******************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "NAVIGATION": () => (/* binding */ NAVIGATION),
/* harmony export */   "back": () => (/* binding */ back),
/* harmony export */   "forward": () => (/* binding */ forward),
/* harmony export */   "go": () => (/* binding */ go),
/* harmony export */   "routerNavigation": () => (/* binding */ routerNavigation)
/* harmony export */ });
/* harmony import */ var _ngrx_router_store__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @ngrx/router-store */ 1611);
/* harmony import */ var _ngrx_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @ngrx/store */ 3488);


const NAVIGATION = _ngrx_router_store__WEBPACK_IMPORTED_MODULE_0__.ROUTER_NAVIGATION;
const routerNavigation = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.createAction)(_ngrx_router_store__WEBPACK_IMPORTED_MODULE_0__.ROUTER_NAVIGATION, (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.props)());
const go = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.createAction)('[Router] Go', (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.props)());
const back = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.createAction)('[Router] Back');
const forward = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.createAction)('[Router] Forward');

/***/ }),

/***/ 7128:
/*!*****************************************************************!*\
  !*** ./apps/web-app/src/app/core/store/data/entity-metadata.ts ***!
  \*****************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "entityConfig": () => (/* binding */ entityConfig)
/* harmony export */ });
const entityMetadata = {
  WeatherForecast: {}
};
const pluralNames = {};
const entityConfig = {
  entityMetadata,
  pluralNames
};

/***/ }),

/***/ 3520:
/*!**********************************************************!*\
  !*** ./apps/web-app/src/app/core/store/effects/index.ts ***!
  \**********************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "effects": () => (/* binding */ effects)
/* harmony export */ });
/* harmony import */ var _layout_effect__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./layout.effect */ 3502);
/* harmony import */ var _router_effect__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./router.effect */ 4192);


const effects = [_layout_effect__WEBPACK_IMPORTED_MODULE_0__.LayoutEffects, _router_effect__WEBPACK_IMPORTED_MODULE_1__.RouterEffects];

/***/ }),

/***/ 3502:
/*!******************************************************************!*\
  !*** ./apps/web-app/src/app/core/store/effects/layout.effect.ts ***!
  \******************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "LayoutEffects": () => (/* binding */ LayoutEffects)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/platform-browser */ 4497);
/* harmony import */ var _ngrx_effects__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @ngrx/effects */ 5405);
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs/operators */ 9337);
/* harmony import */ var _store_actions__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../store/actions */ 5576);






let LayoutEffects = class LayoutEffects {
  constructor(actions$, titleService) {
    this.actions$ = actions$;
    this.titleService = titleService;
    this.setTitle$ = (0,_ngrx_effects__WEBPACK_IMPORTED_MODULE_1__.createEffect)(() => this.actions$.pipe((0,_ngrx_effects__WEBPACK_IMPORTED_MODULE_1__.ofType)(_store_actions__WEBPACK_IMPORTED_MODULE_0__.setTitle), (0,rxjs_operators__WEBPACK_IMPORTED_MODULE_2__.tap)(({
      title
    }) => {
      this.titleService.setTitle(title + ' | Demo App');
    })), {
      dispatch: false
    });
  }
};
LayoutEffects.ctorParameters = () => [{
  type: _ngrx_effects__WEBPACK_IMPORTED_MODULE_1__.Actions
}, {
  type: _angular_platform_browser__WEBPACK_IMPORTED_MODULE_3__.Title
}];
LayoutEffects = (0,tslib__WEBPACK_IMPORTED_MODULE_4__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_5__.Injectable)(), (0,tslib__WEBPACK_IMPORTED_MODULE_4__.__metadata)("design:paramtypes", [_ngrx_effects__WEBPACK_IMPORTED_MODULE_1__.Actions, _angular_platform_browser__WEBPACK_IMPORTED_MODULE_3__.Title])], LayoutEffects);


/***/ }),

/***/ 4192:
/*!******************************************************************!*\
  !*** ./apps/web-app/src/app/core/store/effects/router.effect.ts ***!
  \******************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "RouterEffects": () => (/* binding */ RouterEffects)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/router */ 124);
/* harmony import */ var _ngrx_effects__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @ngrx/effects */ 5405);
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/operators */ 9337);
/* harmony import */ var _core_services__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../core/services */ 3084);
/* harmony import */ var _core_store_actions_router_action__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../core/store/actions/router.action */ 9371);








let RouterEffects = class RouterEffects {
  constructor(actions$, router, location, appService) {
    this.actions$ = actions$;
    this.router = router;
    this.location = location;
    this.appService = appService;
    this.routerNavigation = (0,_ngrx_effects__WEBPACK_IMPORTED_MODULE_2__.createEffect)(() => this.actions$.pipe((0,_ngrx_effects__WEBPACK_IMPORTED_MODULE_2__.ofType)(_core_store_actions_router_action__WEBPACK_IMPORTED_MODULE_1__.routerNavigation), (0,rxjs_operators__WEBPACK_IMPORTED_MODULE_3__.tap)(() => {
      this.appService.scrollToTop(false);
    })), {
      dispatch: false
    });
    this.navigate = (0,_ngrx_effects__WEBPACK_IMPORTED_MODULE_2__.createEffect)(() => this.actions$.pipe((0,_ngrx_effects__WEBPACK_IMPORTED_MODULE_2__.ofType)(_core_store_actions_router_action__WEBPACK_IMPORTED_MODULE_1__.go), (0,rxjs_operators__WEBPACK_IMPORTED_MODULE_3__.tap)(({
      path,
      query: queryParams,
      extras
    }) => this.router.navigate(path, Object.assign({
      queryParams
    }, extras)))), {
      dispatch: false
    });
    this.navigateBack = (0,_ngrx_effects__WEBPACK_IMPORTED_MODULE_2__.createEffect)(() => this.actions$.pipe((0,_ngrx_effects__WEBPACK_IMPORTED_MODULE_2__.ofType)(_core_store_actions_router_action__WEBPACK_IMPORTED_MODULE_1__.back), (0,rxjs_operators__WEBPACK_IMPORTED_MODULE_3__.tap)(() => this.location.back())), {
      dispatch: false
    });
    this.navigateForward = (0,_ngrx_effects__WEBPACK_IMPORTED_MODULE_2__.createEffect)(() => this.actions$.pipe((0,_ngrx_effects__WEBPACK_IMPORTED_MODULE_2__.ofType)(_core_store_actions_router_action__WEBPACK_IMPORTED_MODULE_1__.forward), (0,rxjs_operators__WEBPACK_IMPORTED_MODULE_3__.tap)(() => this.location.forward())), {
      dispatch: false
    });
  }
};
RouterEffects.ctorParameters = () => [{
  type: _ngrx_effects__WEBPACK_IMPORTED_MODULE_2__.Actions
}, {
  type: _angular_router__WEBPACK_IMPORTED_MODULE_4__.Router
}, {
  type: _angular_common__WEBPACK_IMPORTED_MODULE_5__.Location
}, {
  type: _core_services__WEBPACK_IMPORTED_MODULE_0__.AppService
}];
RouterEffects = (0,tslib__WEBPACK_IMPORTED_MODULE_6__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_7__.Injectable)(), (0,tslib__WEBPACK_IMPORTED_MODULE_6__.__metadata)("design:paramtypes", [_ngrx_effects__WEBPACK_IMPORTED_MODULE_2__.Actions, _angular_router__WEBPACK_IMPORTED_MODULE_4__.Router, _angular_common__WEBPACK_IMPORTED_MODULE_5__.Location, _core_services__WEBPACK_IMPORTED_MODULE_0__.AppService])], RouterEffects);


/***/ }),

/***/ 5986:
/*!***********************************************************!*\
  !*** ./apps/web-app/src/app/core/store/reducers/index.ts ***!
  \***********************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "CustomRouterStateSerializer": () => (/* binding */ CustomRouterStateSerializer),
/* harmony export */   "getLayoutState": () => (/* binding */ getLayoutState),
/* harmony export */   "getShowSidenav": () => (/* binding */ getShowSidenav),
/* harmony export */   "getTitle": () => (/* binding */ getTitle),
/* harmony export */   "logger": () => (/* binding */ logger),
/* harmony export */   "metaReducers": () => (/* binding */ metaReducers),
/* harmony export */   "reducers": () => (/* binding */ reducers)
/* harmony export */ });
/* harmony import */ var _ngrx_router_store__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @ngrx/router-store */ 1611);
/* harmony import */ var _ngrx_store__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @ngrx/store */ 3488);
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../../environments/environment */ 7960);
/* harmony import */ var _store_reducers_layout_reducer__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../store/reducers/layout.reducer */ 6458);




class CustomRouterStateSerializer {
  serialize(routerState) {
    let route = routerState.root;
    while (route.firstChild) {
      route = route.firstChild;
    }
    const {
      url
    } = routerState;
    const queryParams = routerState.root.queryParams;
    const params = route.params;
    // Only return an object including the URL, params and query params
    // instead of the entire snapshot
    return {
      url,
      params,
      queryParams
    };
  }
}
const reducers = {
  layout: _store_reducers_layout_reducer__WEBPACK_IMPORTED_MODULE_1__.reducer,
  routerReducer: _ngrx_router_store__WEBPACK_IMPORTED_MODULE_2__.routerReducer
};
function logger(reducer) {
  return (state, action) => {
    console.log('state', state);
    console.log('action', action);
    return reducer(state, action);
  };
}
const metaReducers = !_environments_environment__WEBPACK_IMPORTED_MODULE_0__.environment.production ? [logger] : [];
/**
 * Layout Reducers
 */
const getLayoutState = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_3__.createFeatureSelector)('layout');
const getShowSidenav = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_3__.createSelector)(getLayoutState, _store_reducers_layout_reducer__WEBPACK_IMPORTED_MODULE_1__.getShowSidenav);
const getTitle = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_3__.createSelector)(getLayoutState, _store_reducers_layout_reducer__WEBPACK_IMPORTED_MODULE_1__.getTitle);

/***/ }),

/***/ 6458:
/*!********************************************************************!*\
  !*** ./apps/web-app/src/app/core/store/reducers/layout.reducer.ts ***!
  \********************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "getShowSidenav": () => (/* binding */ getShowSidenav),
/* harmony export */   "getTitle": () => (/* binding */ getTitle),
/* harmony export */   "reducer": () => (/* binding */ reducer)
/* harmony export */ });
/* harmony import */ var _ngrx_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @ngrx/store */ 3488);
/* harmony import */ var _actions__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../actions */ 5576);


const initialState = {
  showSidenav: false,
  title: 'Home'
};
const layoutReducer = (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.createReducer)(initialState, (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.on)(_actions__WEBPACK_IMPORTED_MODULE_0__.closeSidenav, state => Object.assign(Object.assign({}, state), {
  showSidenav: false
})), (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.on)(_actions__WEBPACK_IMPORTED_MODULE_0__.openSidenav, state => Object.assign(Object.assign({}, state), {
  showSidenav: true
})), (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.on)(_actions__WEBPACK_IMPORTED_MODULE_0__.toggleSidenav, state => Object.assign(Object.assign({}, state), {
  showSidenav: !state.showSidenav
})), (0,_ngrx_store__WEBPACK_IMPORTED_MODULE_1__.on)(_actions__WEBPACK_IMPORTED_MODULE_0__.setTitle, (state, {
  title
}) => Object.assign(Object.assign({}, state), {
  title
})));
function reducer(state, action) {
  return layoutReducer(state, action);
}
const getShowSidenav = state => state.showSidenav;
const getTitle = state => state.title;

/***/ }),

/***/ 4104:
/*!***********************************************************************!*\
  !*** ./apps/web-app/src/app/extensions/FormlyExtensionSimpleField.ts ***!
  \***********************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "FormlyExtensionSimpleField": () => (/* binding */ FormlyExtensionSimpleField)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _ngx_formly_material_form_field__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @ngx-formly/material/form-field */ 2334);
/* harmony import */ var lodash__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! lodash */ 2938);
/* harmony import */ var lodash__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(lodash__WEBPACK_IMPORTED_MODULE_0__);




let FormlyExtensionSimpleField = class FormlyExtensionSimpleField extends _ngx_formly_material_form_field__WEBPACK_IMPORTED_MODULE_1__.FieldType {
  getField() {
    console.log("SIMPLE FIELD");
    let t = (0,lodash__WEBPACK_IMPORTED_MODULE_0__.get)(this.model, this.key);
    if (t === "" || t === undefined || t === null) {
      t = "---";
    }
    return t;
  }
};
FormlyExtensionSimpleField = (0,tslib__WEBPACK_IMPORTED_MODULE_2__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_3__.Component)({
  selector: 'formly-simple',
  template: `
    {{getField()}}

  `
})], FormlyExtensionSimpleField);


/***/ }),

/***/ 8393:
/*!**********************************************************************!*\
  !*** ./apps/web-app/src/app/extensions/FormlyWrapperDynamicStyle.ts ***!
  \**********************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "FormlyWrapperDynamicStyle": () => (/* binding */ FormlyWrapperDynamicStyle)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _ngx_formly_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @ngx-formly/core */ 4599);



let FormlyWrapperDynamicStyle = class FormlyWrapperDynamicStyle extends _ngx_formly_core__WEBPACK_IMPORTED_MODULE_0__.FieldWrapper {
  constructor(elRef) {
    super();
    this.elRef = elRef;
    this.JSON = JSON;
  }
  ngAfterViewInit() {
    console.log("closest       ", this.elRef.nativeElement.closest("." + this.props.options['class']));
    var x = document.getElementsByClassName(this.props.options['class'])[0];
    x.setAttribute('style', this.props.options['style']);
    console.log("querySelectorAll       ", x.style);
  }
};
FormlyWrapperDynamicStyle.ctorParameters = () => [{
  type: _angular_core__WEBPACK_IMPORTED_MODULE_1__.ElementRef
}];
FormlyWrapperDynamicStyle = (0,tslib__WEBPACK_IMPORTED_MODULE_2__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_1__.Component)({
  selector: 'formly-wrapper-dynamic-style',
  template: `
    <ng-container #fieldComponent></ng-container>
  `
}), (0,tslib__WEBPACK_IMPORTED_MODULE_2__.__metadata)("design:paramtypes", [_angular_core__WEBPACK_IMPORTED_MODULE_1__.ElementRef])], FormlyWrapperDynamicStyle);


/***/ }),

/***/ 4653:
/*!**********************************************************!*\
  !*** ./apps/web-app/src/app/material/material.module.ts ***!
  \**********************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "MaterialModule": () => (/* binding */ MaterialModule)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_material_autocomplete__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/material/autocomplete */ 8550);
/* harmony import */ var _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/checkbox */ 4792);
/* harmony import */ var _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/datepicker */ 2298);
/* harmony import */ var _angular_material_form_field__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/form-field */ 5074);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/input */ 8562);
/* harmony import */ var _angular_material_radio__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/radio */ 2922);
/* harmony import */ var _angular_material_select__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/material/select */ 7371);
/* harmony import */ var _angular_material_slider__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/material/slider */ 5682);
/* harmony import */ var _angular_material_slide_toggle__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/material/slide-toggle */ 4714);
/* harmony import */ var _angular_material_components_datetime_picker__WEBPACK_IMPORTED_MODULE_38__ = __webpack_require__(/*! @angular-material-components/datetime-picker */ 820);
/* harmony import */ var _angular_material_menu__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/material/menu */ 8589);
/* harmony import */ var _angular_material_sidenav__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/material/sidenav */ 6643);
/* harmony import */ var _angular_material_toolbar__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/material/toolbar */ 2543);
/* harmony import */ var _angular_material_card__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/material/card */ 2156);
/* harmony import */ var _angular_material_divider__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @angular/material/divider */ 1528);
/* harmony import */ var _angular_material_expansion__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! @angular/material/expansion */ 7591);
/* harmony import */ var _angular_material_grid_list__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @angular/material/grid-list */ 2642);
/* harmony import */ var _angular_material_list__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! @angular/material/list */ 6517);
/* harmony import */ var _angular_material_stepper__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! @angular/material/stepper */ 4193);
/* harmony import */ var _angular_material_tabs__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! @angular/material/tabs */ 5892);
/* harmony import */ var _angular_material_tree__WEBPACK_IMPORTED_MODULE_22__ = __webpack_require__(/*! @angular/material/tree */ 3453);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_23__ = __webpack_require__(/*! @angular/material/button */ 4522);
/* harmony import */ var _angular_material_button_toggle__WEBPACK_IMPORTED_MODULE_24__ = __webpack_require__(/*! @angular/material/button-toggle */ 9837);
/* harmony import */ var _angular_material_badge__WEBPACK_IMPORTED_MODULE_25__ = __webpack_require__(/*! @angular/material/badge */ 3335);
/* harmony import */ var _angular_material_chips__WEBPACK_IMPORTED_MODULE_26__ = __webpack_require__(/*! @angular/material/chips */ 1169);
/* harmony import */ var _angular_material_icon__WEBPACK_IMPORTED_MODULE_27__ = __webpack_require__(/*! @angular/material/icon */ 7822);
/* harmony import */ var _angular_material_progress_spinner__WEBPACK_IMPORTED_MODULE_28__ = __webpack_require__(/*! @angular/material/progress-spinner */ 1708);
/* harmony import */ var _angular_material_progress_bar__WEBPACK_IMPORTED_MODULE_29__ = __webpack_require__(/*! @angular/material/progress-bar */ 1294);
/* harmony import */ var _angular_material_core__WEBPACK_IMPORTED_MODULE_30__ = __webpack_require__(/*! @angular/material/core */ 9121);
/* harmony import */ var _angular_material_bottom_sheet__WEBPACK_IMPORTED_MODULE_31__ = __webpack_require__(/*! @angular/material/bottom-sheet */ 4865);
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_32__ = __webpack_require__(/*! @angular/material/dialog */ 1484);
/* harmony import */ var _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_33__ = __webpack_require__(/*! @angular/material/snack-bar */ 930);
/* harmony import */ var _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_34__ = __webpack_require__(/*! @angular/material/tooltip */ 6896);
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_35__ = __webpack_require__(/*! @angular/material/paginator */ 6060);
/* harmony import */ var _angular_material_sort__WEBPACK_IMPORTED_MODULE_36__ = __webpack_require__(/*! @angular/material/sort */ 2197);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_37__ = __webpack_require__(/*! @angular/material/table */ 5288);
/* harmony import */ var _angular_material_components_moment_adapter__WEBPACK_IMPORTED_MODULE_39__ = __webpack_require__(/*! @angular-material-components/moment-adapter */ 3047);



// Material Form Controls










// Material Navigation



// Material Layout








// Material Buttons & Indicators








// Material Popups & Modals




// Material Data tables




let MaterialModule = class MaterialModule {};
MaterialModule = (0,tslib__WEBPACK_IMPORTED_MODULE_0__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_1__.NgModule)({
  declarations: [],
  imports: [_angular_common__WEBPACK_IMPORTED_MODULE_2__.CommonModule, _angular_material_autocomplete__WEBPACK_IMPORTED_MODULE_3__.MatAutocompleteModule, _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_4__.MatCheckboxModule, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_5__.MatDatepickerModule, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_6__.MatFormFieldModule, _angular_material_input__WEBPACK_IMPORTED_MODULE_7__.MatInputModule, _angular_material_radio__WEBPACK_IMPORTED_MODULE_8__.MatRadioModule, _angular_material_select__WEBPACK_IMPORTED_MODULE_9__.MatSelectModule, _angular_material_slider__WEBPACK_IMPORTED_MODULE_10__.MatSliderModule, _angular_material_slide_toggle__WEBPACK_IMPORTED_MODULE_11__.MatSlideToggleModule, _angular_material_menu__WEBPACK_IMPORTED_MODULE_12__.MatMenuModule, _angular_material_sidenav__WEBPACK_IMPORTED_MODULE_13__.MatSidenavModule, _angular_material_toolbar__WEBPACK_IMPORTED_MODULE_14__.MatToolbarModule, _angular_material_card__WEBPACK_IMPORTED_MODULE_15__.MatCardModule, _angular_material_divider__WEBPACK_IMPORTED_MODULE_16__.MatDividerModule, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_17__.MatExpansionModule, _angular_material_grid_list__WEBPACK_IMPORTED_MODULE_18__.MatGridListModule, _angular_material_list__WEBPACK_IMPORTED_MODULE_19__.MatListModule, _angular_material_stepper__WEBPACK_IMPORTED_MODULE_20__.MatStepperModule, _angular_material_tabs__WEBPACK_IMPORTED_MODULE_21__.MatTabsModule, _angular_material_tree__WEBPACK_IMPORTED_MODULE_22__.MatTreeModule, _angular_material_button__WEBPACK_IMPORTED_MODULE_23__.MatButtonModule, _angular_material_button_toggle__WEBPACK_IMPORTED_MODULE_24__.MatButtonToggleModule, _angular_material_badge__WEBPACK_IMPORTED_MODULE_25__.MatBadgeModule, _angular_material_chips__WEBPACK_IMPORTED_MODULE_26__.MatChipsModule, _angular_material_icon__WEBPACK_IMPORTED_MODULE_27__.MatIconModule, _angular_material_progress_spinner__WEBPACK_IMPORTED_MODULE_28__.MatProgressSpinnerModule, _angular_material_progress_bar__WEBPACK_IMPORTED_MODULE_29__.MatProgressBarModule, _angular_material_core__WEBPACK_IMPORTED_MODULE_30__.MatRippleModule, _angular_material_bottom_sheet__WEBPACK_IMPORTED_MODULE_31__.MatBottomSheetModule, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_32__.MatDialogModule, _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_33__.MatSnackBarModule, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_34__.MatTooltipModule, _angular_material_paginator__WEBPACK_IMPORTED_MODULE_35__.MatPaginatorModule, _angular_material_sort__WEBPACK_IMPORTED_MODULE_36__.MatSortModule, _angular_material_table__WEBPACK_IMPORTED_MODULE_37__.MatTableModule],
  exports: [_angular_material_autocomplete__WEBPACK_IMPORTED_MODULE_3__.MatAutocompleteModule, _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_4__.MatCheckboxModule, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_5__.MatDatepickerModule, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_6__.MatFormFieldModule, _angular_material_input__WEBPACK_IMPORTED_MODULE_7__.MatInputModule, _angular_material_radio__WEBPACK_IMPORTED_MODULE_8__.MatRadioModule, _angular_material_select__WEBPACK_IMPORTED_MODULE_9__.MatSelectModule, _angular_material_slider__WEBPACK_IMPORTED_MODULE_10__.MatSliderModule, _angular_material_slide_toggle__WEBPACK_IMPORTED_MODULE_11__.MatSlideToggleModule, _angular_material_menu__WEBPACK_IMPORTED_MODULE_12__.MatMenuModule, _angular_material_sidenav__WEBPACK_IMPORTED_MODULE_13__.MatSidenavModule, _angular_material_toolbar__WEBPACK_IMPORTED_MODULE_14__.MatToolbarModule, _angular_material_card__WEBPACK_IMPORTED_MODULE_15__.MatCardModule, _angular_material_divider__WEBPACK_IMPORTED_MODULE_16__.MatDividerModule, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_17__.MatExpansionModule, _angular_material_grid_list__WEBPACK_IMPORTED_MODULE_18__.MatGridListModule, _angular_material_list__WEBPACK_IMPORTED_MODULE_19__.MatListModule, _angular_material_stepper__WEBPACK_IMPORTED_MODULE_20__.MatStepperModule, _angular_material_tabs__WEBPACK_IMPORTED_MODULE_21__.MatTabsModule, _angular_material_tree__WEBPACK_IMPORTED_MODULE_22__.MatTreeModule, _angular_material_button__WEBPACK_IMPORTED_MODULE_23__.MatButtonModule, _angular_material_button_toggle__WEBPACK_IMPORTED_MODULE_24__.MatButtonToggleModule, _angular_material_badge__WEBPACK_IMPORTED_MODULE_25__.MatBadgeModule, _angular_material_chips__WEBPACK_IMPORTED_MODULE_26__.MatChipsModule, _angular_material_icon__WEBPACK_IMPORTED_MODULE_27__.MatIconModule, _angular_material_progress_spinner__WEBPACK_IMPORTED_MODULE_28__.MatProgressSpinnerModule, _angular_material_progress_bar__WEBPACK_IMPORTED_MODULE_29__.MatProgressBarModule, _angular_material_core__WEBPACK_IMPORTED_MODULE_30__.MatRippleModule, _angular_material_bottom_sheet__WEBPACK_IMPORTED_MODULE_31__.MatBottomSheetModule, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_32__.MatDialogModule, _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_33__.MatSnackBarModule, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_34__.MatTooltipModule, _angular_material_paginator__WEBPACK_IMPORTED_MODULE_35__.MatPaginatorModule, _angular_material_sort__WEBPACK_IMPORTED_MODULE_36__.MatSortModule, _angular_material_table__WEBPACK_IMPORTED_MODULE_37__.MatTableModule, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_6__.MatFormFieldModule, _angular_material_input__WEBPACK_IMPORTED_MODULE_7__.MatInputModule, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_5__.MatDatepickerModule, _angular_material_core__WEBPACK_IMPORTED_MODULE_30__.MatNativeDateModule, _angular_material_components_datetime_picker__WEBPACK_IMPORTED_MODULE_38__.NgxMatTimepickerModule, _angular_material_components_datetime_picker__WEBPACK_IMPORTED_MODULE_38__.NgxMatDatetimePickerModule, _angular_material_core__WEBPACK_IMPORTED_MODULE_30__.MatNativeDateModule, _angular_material_components_datetime_picker__WEBPACK_IMPORTED_MODULE_38__.NgxMatDatetimePickerModule, _angular_material_components_datetime_picker__WEBPACK_IMPORTED_MODULE_38__.NgxMatTimepickerModule, _angular_material_components_datetime_picker__WEBPACK_IMPORTED_MODULE_38__.NgxMatNativeDateModule, _angular_material_components_moment_adapter__WEBPACK_IMPORTED_MODULE_39__.NgxMatMomentModule]
})], MaterialModule);


/***/ }),

/***/ 9961:
/*!**********************************************!*\
  !*** ./apps/web-app/src/app/shared/index.ts ***!
  \**********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "SharedModule": () => (/* reexport safe */ _shared_module__WEBPACK_IMPORTED_MODULE_0__.SharedModule)
/* harmony export */ });
/* harmony import */ var _shared_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./shared.module */ 8656);


/***/ }),

/***/ 8349:
/*!*******************************************************!*\
  !*** ./apps/web-app/src/app/shared/material/index.ts ***!
  \*******************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "MaterialModule": () => (/* reexport safe */ _material_module__WEBPACK_IMPORTED_MODULE_0__.MaterialModule)
/* harmony export */ });
/* harmony import */ var _material_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./material.module */ 5964);


/***/ }),

/***/ 5964:
/*!*****************************************************************!*\
  !*** ./apps/web-app/src/app/shared/material/material.module.ts ***!
  \*****************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "MaterialModule": () => (/* binding */ MaterialModule)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/material/button */ 4522);
/* harmony import */ var _angular_material_card__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material/card */ 2156);
/* harmony import */ var _angular_material_icon__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/icon */ 7822);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/material/input */ 8562);
/* harmony import */ var _angular_material_list__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/list */ 6517);
/* harmony import */ var _angular_material_progress_spinner__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/progress-spinner */ 1708);
/* harmony import */ var _angular_material_sidenav__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/sidenav */ 6643);
/* harmony import */ var _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/material/snack-bar */ 930);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/material/table */ 5288);
/* harmony import */ var _angular_material_toolbar__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/toolbar */ 2543);
/* harmony import */ var _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/material/tooltip */ 6896);
/* harmony import */ var _angular_cdk_table__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/cdk/table */ 9673);














const MatModules = [_angular_cdk_table__WEBPACK_IMPORTED_MODULE_0__.CdkTableModule, _angular_material_input__WEBPACK_IMPORTED_MODULE_1__.MatInputModule, _angular_material_card__WEBPACK_IMPORTED_MODULE_2__.MatCardModule, _angular_material_button__WEBPACK_IMPORTED_MODULE_3__.MatButtonModule, _angular_material_sidenav__WEBPACK_IMPORTED_MODULE_4__.MatSidenavModule, _angular_material_list__WEBPACK_IMPORTED_MODULE_5__.MatListModule, _angular_material_icon__WEBPACK_IMPORTED_MODULE_6__.MatIconModule, _angular_material_toolbar__WEBPACK_IMPORTED_MODULE_7__.MatToolbarModule, _angular_material_progress_spinner__WEBPACK_IMPORTED_MODULE_8__.MatProgressSpinnerModule, _angular_material_table__WEBPACK_IMPORTED_MODULE_9__.MatTableModule, _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_10__.MatSnackBarModule, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_11__.MatTooltipModule];
let MaterialModule = class MaterialModule {};
MaterialModule = (0,tslib__WEBPACK_IMPORTED_MODULE_12__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_13__.NgModule)({
  imports: MatModules,
  exports: MatModules
})], MaterialModule);


/***/ }),

/***/ 8656:
/*!******************************************************!*\
  !*** ./apps/web-app/src/app/shared/shared.module.ts ***!
  \******************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "SharedModule": () => (/* binding */ SharedModule)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _myorg_common_big_button__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @myorg/common/big-button */ 5850);
/* harmony import */ var _myorg_common_page_container__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @myorg/common/page-container */ 4141);
/* harmony import */ var _myorg_common_page_toolbar__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @myorg/common/page-toolbar */ 6947);
/* harmony import */ var _myorg_common_page_toolbar_button__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @myorg/common/page-toolbar-button */ 971);







let SharedModule = class SharedModule {};
SharedModule = (0,tslib__WEBPACK_IMPORTED_MODULE_4__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_5__.NgModule)({
  imports: [_angular_common__WEBPACK_IMPORTED_MODULE_6__.CommonModule, _myorg_common_big_button__WEBPACK_IMPORTED_MODULE_0__.BigButtonModule, _myorg_common_page_container__WEBPACK_IMPORTED_MODULE_1__.PageContainerModule, _myorg_common_page_toolbar__WEBPACK_IMPORTED_MODULE_2__.PageToolbarModule, _myorg_common_page_toolbar_button__WEBPACK_IMPORTED_MODULE_3__.PageToolbarButtonModule],
  exports: [_myorg_common_big_button__WEBPACK_IMPORTED_MODULE_0__.BigButtonModule, _myorg_common_page_container__WEBPACK_IMPORTED_MODULE_1__.PageContainerModule, _myorg_common_page_toolbar__WEBPACK_IMPORTED_MODULE_2__.PageToolbarModule, _myorg_common_page_toolbar_button__WEBPACK_IMPORTED_MODULE_3__.PageToolbarButtonModule]
})], SharedModule);


/***/ }),

/***/ 7960:
/*!******************************************************!*\
  !*** ./apps/web-app/src/environments/environment.ts ***!
  \******************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "environment": () => (/* binding */ environment)
/* harmony export */ });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
const environment = {
  production: false
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.

/***/ }),

/***/ 7407:
/*!**********************************!*\
  !*** ./apps/web-app/src/main.ts ***!
  \**********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ 6057);
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./app/app.module */ 2635);
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./environments/environment */ 7960);




if (_environments_environment__WEBPACK_IMPORTED_MODULE_1__.environment.production) {
  (0,_angular_core__WEBPACK_IMPORTED_MODULE_2__.enableProdMode)();
}
(0,_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_3__.platformBrowserDynamic)().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_0__.AppModule).catch(err => console.error(err));

/***/ }),

/***/ 634:
/*!************************************************************!*\
  !*** ./libs/common/src/big-button/big-button.component.ts ***!
  \************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "BigButtonComponent": () => (/* binding */ BigButtonComponent)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _big_button_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./big-button.component.html?ngResource */ 2459);
/* harmony import */ var _big_button_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./big-button.component.scss?ngResource */ 2218);
/* harmony import */ var _big_button_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_big_button_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ 2560);




let BigButtonComponent = class BigButtonComponent {};
BigButtonComponent = (0,tslib__WEBPACK_IMPORTED_MODULE_2__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_3__.Component)({
  selector: 'app-big-button',
  template: _big_button_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__,
  styles: [(_big_button_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default())]
})], BigButtonComponent);


/***/ }),

/***/ 1998:
/*!*********************************************************!*\
  !*** ./libs/common/src/big-button/big-button.module.ts ***!
  \*********************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "BigButtonModule": () => (/* binding */ BigButtonModule)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/button */ 4522);
/* harmony import */ var _big_button_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./big-button.component */ 634);





let BigButtonModule = class BigButtonModule {};
BigButtonModule = (0,tslib__WEBPACK_IMPORTED_MODULE_1__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_2__.NgModule)({
  imports: [_angular_common__WEBPACK_IMPORTED_MODULE_3__.CommonModule, _angular_material_button__WEBPACK_IMPORTED_MODULE_4__.MatButtonModule],
  declarations: [_big_button_component__WEBPACK_IMPORTED_MODULE_0__.BigButtonComponent],
  exports: [_big_button_component__WEBPACK_IMPORTED_MODULE_0__.BigButtonComponent]
})], BigButtonModule);


/***/ }),

/***/ 5850:
/*!*********************************************!*\
  !*** ./libs/common/src/big-button/index.ts ***!
  \*********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "BigButtonModule": () => (/* reexport safe */ _big_button_module__WEBPACK_IMPORTED_MODULE_0__.BigButtonModule)
/* harmony export */ });
/* harmony import */ var _big_button_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./big-button.module */ 1998);


/***/ }),

/***/ 4141:
/*!*************************************************!*\
  !*** ./libs/common/src/page-container/index.ts ***!
  \*************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "PageContainerModule": () => (/* reexport safe */ _page_container_module__WEBPACK_IMPORTED_MODULE_0__.PageContainerModule)
/* harmony export */ });
/* harmony import */ var _page_container_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./page-container.module */ 3190);


/***/ }),

/***/ 4015:
/*!********************************************************************!*\
  !*** ./libs/common/src/page-container/page-container.component.ts ***!
  \********************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "PageContainerComponent": () => (/* binding */ PageContainerComponent)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _page_container_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./page-container.component.html?ngResource */ 9572);
/* harmony import */ var _page_container_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./page-container.component.scss?ngResource */ 9485);
/* harmony import */ var _page_container_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_page_container_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ 2560);




let PageContainerComponent = class PageContainerComponent {};
PageContainerComponent = (0,tslib__WEBPACK_IMPORTED_MODULE_2__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_3__.Component)({
  selector: 'app-page-container',
  template: _page_container_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__,
  styles: [(_page_container_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default())]
})], PageContainerComponent);


/***/ }),

/***/ 3190:
/*!*****************************************************************!*\
  !*** ./libs/common/src/page-container/page-container.module.ts ***!
  \*****************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "PageContainerModule": () => (/* binding */ PageContainerModule)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _page_container_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./page-container.component */ 4015);




let PageContainerModule = class PageContainerModule {};
PageContainerModule = (0,tslib__WEBPACK_IMPORTED_MODULE_1__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_2__.NgModule)({
  imports: [_angular_common__WEBPACK_IMPORTED_MODULE_3__.CommonModule],
  declarations: [_page_container_component__WEBPACK_IMPORTED_MODULE_0__.PageContainerComponent],
  exports: [_page_container_component__WEBPACK_IMPORTED_MODULE_0__.PageContainerComponent]
})], PageContainerModule);


/***/ }),

/***/ 971:
/*!******************************************************!*\
  !*** ./libs/common/src/page-toolbar-button/index.ts ***!
  \******************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "PageToolbarButtonModule": () => (/* reexport safe */ _page_toolbar_button_module__WEBPACK_IMPORTED_MODULE_0__.PageToolbarButtonModule)
/* harmony export */ });
/* harmony import */ var _page_toolbar_button_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./page-toolbar-button.module */ 9218);


/***/ }),

/***/ 8639:
/*!******************************************************************************!*\
  !*** ./libs/common/src/page-toolbar-button/page-toolbar-button.component.ts ***!
  \******************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "PageToolbarButtonComponent": () => (/* binding */ PageToolbarButtonComponent)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _page_toolbar_button_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./page-toolbar-button.component.html?ngResource */ 3014);
/* harmony import */ var _page_toolbar_button_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./page-toolbar-button.component.scss?ngResource */ 2303);
/* harmony import */ var _page_toolbar_button_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_page_toolbar_button_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);




let PageToolbarButtonComponent = class PageToolbarButtonComponent {
  ngOnInit() {
    this.tooltipDisabled = !this.tooltip;
  }
};
PageToolbarButtonComponent.propDecorators = {
  tooltip: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_2__.Input
  }]
};
PageToolbarButtonComponent = (0,tslib__WEBPACK_IMPORTED_MODULE_3__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_2__.Component)({
  selector: 'app-page-toolbar-button',
  template: _page_toolbar_button_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__,
  styles: [(_page_toolbar_button_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default())]
})], PageToolbarButtonComponent);


/***/ }),

/***/ 9218:
/*!***************************************************************************!*\
  !*** ./libs/common/src/page-toolbar-button/page-toolbar-button.module.ts ***!
  \***************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "PageToolbarButtonModule": () => (/* binding */ PageToolbarButtonModule)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/button */ 4522);
/* harmony import */ var _angular_material_icon__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/icon */ 7822);
/* harmony import */ var _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/tooltip */ 6896);
/* harmony import */ var _page_toolbar_button_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./page-toolbar-button.component */ 8639);







let PageToolbarButtonModule = class PageToolbarButtonModule {};
PageToolbarButtonModule = (0,tslib__WEBPACK_IMPORTED_MODULE_1__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_2__.NgModule)({
  imports: [_angular_common__WEBPACK_IMPORTED_MODULE_3__.CommonModule, _angular_material_button__WEBPACK_IMPORTED_MODULE_4__.MatButtonModule, _angular_material_icon__WEBPACK_IMPORTED_MODULE_5__.MatIconModule, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_6__.MatTooltipModule],
  declarations: [_page_toolbar_button_component__WEBPACK_IMPORTED_MODULE_0__.PageToolbarButtonComponent],
  exports: [_page_toolbar_button_component__WEBPACK_IMPORTED_MODULE_0__.PageToolbarButtonComponent]
})], PageToolbarButtonModule);


/***/ }),

/***/ 6947:
/*!***********************************************!*\
  !*** ./libs/common/src/page-toolbar/index.ts ***!
  \***********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "PageToolbarModule": () => (/* reexport safe */ _page_toolbar_module__WEBPACK_IMPORTED_MODULE_0__.PageToolbarModule)
/* harmony export */ });
/* harmony import */ var _page_toolbar_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./page-toolbar.module */ 6017);


/***/ }),

/***/ 3201:
/*!****************************************************************!*\
  !*** ./libs/common/src/page-toolbar/page-toolbar.component.ts ***!
  \****************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "PageToolbarComponent": () => (/* binding */ PageToolbarComponent)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _page_toolbar_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./page-toolbar.component.html?ngResource */ 2952);
/* harmony import */ var _page_toolbar_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./page-toolbar.component.scss?ngResource */ 3411);
/* harmony import */ var _page_toolbar_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_page_toolbar_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);




let PageToolbarComponent = class PageToolbarComponent {};
PageToolbarComponent.propDecorators = {
  title: [{
    type: _angular_core__WEBPACK_IMPORTED_MODULE_2__.Input
  }]
};
PageToolbarComponent = (0,tslib__WEBPACK_IMPORTED_MODULE_3__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_2__.Component)({
  selector: 'app-page-toolbar',
  template: _page_toolbar_component_html_ngResource__WEBPACK_IMPORTED_MODULE_0__,
  styles: [(_page_toolbar_component_scss_ngResource__WEBPACK_IMPORTED_MODULE_1___default())]
})], PageToolbarComponent);


/***/ }),

/***/ 6017:
/*!*************************************************************!*\
  !*** ./libs/common/src/page-toolbar/page-toolbar.module.ts ***!
  \*************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "PageToolbarModule": () => (/* binding */ PageToolbarModule)
/* harmony export */ });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! tslib */ 4929);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common */ 4666);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ 2560);
/* harmony import */ var _page_toolbar_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./page-toolbar.component */ 3201);




let PageToolbarModule = class PageToolbarModule {};
PageToolbarModule = (0,tslib__WEBPACK_IMPORTED_MODULE_1__.__decorate)([(0,_angular_core__WEBPACK_IMPORTED_MODULE_2__.NgModule)({
  imports: [_angular_common__WEBPACK_IMPORTED_MODULE_3__.CommonModule],
  declarations: [_page_toolbar_component__WEBPACK_IMPORTED_MODULE_0__.PageToolbarComponent],
  exports: [_page_toolbar_component__WEBPACK_IMPORTED_MODULE_0__.PageToolbarComponent]
})], PageToolbarModule);


/***/ }),

/***/ 3720:
/*!**************************************************************************************************!*\
  !*** ./apps/web-app/src/app/core/components/main-toolbar/main-toolbar.component.scss?ngResource ***!
  \**************************************************************************************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

// Imports
var ___CSS_LOADER_API_SOURCEMAP_IMPORT___ = __webpack_require__(/*! ../../../../../../../node_modules/css-loader/dist/runtime/sourceMaps.js */ 9579);
var ___CSS_LOADER_API_IMPORT___ = __webpack_require__(/*! ../../../../../../../node_modules/css-loader/dist/runtime/api.js */ 931);
var ___CSS_LOADER_EXPORT___ = ___CSS_LOADER_API_IMPORT___(___CSS_LOADER_API_SOURCEMAP_IMPORT___);
// Module
___CSS_LOADER_EXPORT___.push([module.id, ".app-main-toolbar {\n  position: fixed;\n  top: 0;\n  width: 100%;\n  z-index: 2020;\n  height: 56px;\n  background: var(--navbar);\n  color: rgb(255, 255, 255);\n}\n@media (prefers-color-scheme: dark) {\n  .app-main-toolbar {\n    background: rgb(40, 40, 40);\n  }\n}\n.app-main-toolbar .mat-icon-button {\n  margin: 0 8px;\n}\n.app-main-toolbar .mat-icon-button:first-child {\n  margin-left: 0;\n}\n.app-main-toolbar .mat-icon-button:last-child {\n  margin-right: 0;\n}\n.app-main-toolbar .logo {\n  height: 56px;\n}\n.app-main-toolbar .logo img {\n  height: 48px;\n  width: 48px;\n  margin: 4px;\n}\n.app-main-toolbar a.logo,\n.app-main-toolbar a.logo:visited,\n.app-main-toolbar a.logo:hover,\n.app-main-toolbar a.logo-text,\n.app-main-toolbar a.logo-text:visited,\n.app-main-toolbar a.logo-text:hover {\n  text-decoration: none;\n  color: rgb(255, 255, 255);\n}", "",{"version":3,"sources":["webpack://./apps/web-app/src/app/core/components/main-toolbar/main-toolbar.component.scss","webpack://./apps/web-app/src/styles/app-variables.scss"],"names":[],"mappings":"AAEA;EACE,eAAA;EACA,MAAA;EACA,WAAA;EACA,aAAA;EACA,YCDwB;EDExB,yBAAA;EACA,yBCFuB;ADCzB;AAGE;EATF;IAUI,2BCO+B;EDPjC;AACF;AAEE;EACE,aAAA;AAAJ;AAEI;EACE,cAAA;AAAN;AAGI;EACE,eAAA;AADN;AAKE;EACE,YCtBsB;ADmB1B;AAKI;EACE,YAAA;EACA,WAAA;EACA,WAAA;AAHN;AAOE;;;;;;EAME,qBAAA;EACA,yBCrCqB;ADgCzB","sourcesContent":["@import 'apps/web-app/src/styles/app-variables.scss';\r\n\r\n.app-main-toolbar {\r\n  position: fixed;\r\n  top: 0;\r\n  width: 100%;\r\n  z-index: 2020;\r\n  height: $app-main-toolbar-height;\r\n  background: $app-main-toolbar-background;\r\n  color: $app-main-toolbar-color;\r\n\r\n  @media (prefers-color-scheme: dark) {\r\n    background: $app-dark-main-toolbar-background;\r\n  }\r\n\r\n  .mat-icon-button {\r\n    margin: 0 8px;\r\n\r\n    &:first-child {\r\n      margin-left: 0;\r\n    }\r\n\r\n    &:last-child {\r\n      margin-right: 0;\r\n    }\r\n  }\r\n\r\n  .logo {\r\n    height: $app-main-toolbar-height;\r\n\r\n    img {\r\n      height: 48px;\r\n      width: 48px;\r\n      margin: 4px;\r\n    }\r\n  }\r\n\r\n  a.logo,\r\n  a.logo:visited,\r\n  a.logo:hover,\r\n  a.logo-text,\r\n  a.logo-text:visited,\r\n  a.logo-text:hover {\r\n    text-decoration: none;\r\n    color: $app-main-toolbar-color;\r\n  }\r\n}\r\n","$app-font-family: 'Roboto', sans-serif;\r\n$app-mobile-max-width: 720px;\r\n$app-non-mobile-min-width: 721px;\r\n\r\n$app-mat-drawer-container-height: calc(100% - 56px);\r\n$app-mat-drawer-container-background-color: rgb(250, 250, 250);\r\n$app-main-toolbar-height: 56px;\r\n$app-main-toolbar-color: rgba(255, 255, 255, 1);\r\n$app-main-toolbar-background: var(--navbar);\r\n\r\n$app-page-toolbar-height: 38px;\r\n$app-page-toolbar-background: rgba(255, 255, 255, 0.95);\r\n\r\n$app-pre-background: #f6f8fa;\r\n\r\n// .dark-theme\r\n$app-dark-pre-background: rgb(18, 18, 18);\r\n$app-dark-mat-drawer-container-background-color: rgb(33, 33, 33);\r\n$app-dark-page-toolbar-background: rgb(54, 54, 54);\r\n$app-dark-main-toolbar-background: rgb(40, 40, 40);\r\n\r\n$btn-primary-bg: #1d569a;\r\n"],"sourceRoot":""}]);
// Exports
module.exports = ___CSS_LOADER_EXPORT___.toString();


/***/ }),

/***/ 8470:
/*!************************************************************************************************************!*\
  !*** ./apps/web-app/src/app/core/components/sidenav-list-item/sidenav-list-item.component.scss?ngResource ***!
  \************************************************************************************************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

// Imports
var ___CSS_LOADER_API_SOURCEMAP_IMPORT___ = __webpack_require__(/*! ../../../../../../../node_modules/css-loader/dist/runtime/sourceMaps.js */ 9579);
var ___CSS_LOADER_API_IMPORT___ = __webpack_require__(/*! ../../../../../../../node_modules/css-loader/dist/runtime/api.js */ 931);
var ___CSS_LOADER_EXPORT___ = ___CSS_LOADER_API_IMPORT___(___CSS_LOADER_API_SOURCEMAP_IMPORT___);
// Module
___CSS_LOADER_EXPORT___.push([module.id, "", "",{"version":3,"sources":[],"names":[],"mappings":"","sourceRoot":""}]);
// Exports
module.exports = ___CSS_LOADER_EXPORT___.toString();


/***/ }),

/***/ 6129:
/*!****************************************************************************************!*\
  !*** ./apps/web-app/src/app/core/components/sidenav/sidenav.component.scss?ngResource ***!
  \****************************************************************************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

// Imports
var ___CSS_LOADER_API_SOURCEMAP_IMPORT___ = __webpack_require__(/*! ../../../../../../../node_modules/css-loader/dist/runtime/sourceMaps.js */ 9579);
var ___CSS_LOADER_API_IMPORT___ = __webpack_require__(/*! ../../../../../../../node_modules/css-loader/dist/runtime/api.js */ 931);
var ___CSS_LOADER_EXPORT___ = ___CSS_LOADER_API_IMPORT___(___CSS_LOADER_API_SOURCEMAP_IMPORT___);
// Module
___CSS_LOADER_EXPORT___.push([module.id, ".mat-sidenav {\n  width: 240px;\n}\n\n.app-sidebar-content {\n  padding: 8px;\n}", "",{"version":3,"sources":["webpack://./apps/web-app/src/app/core/components/sidenav/sidenav.component.scss"],"names":[],"mappings":"AAEA;EACE,YAAA;AADF;;AAIA;EACE,YAAA;AADF","sourcesContent":["@import 'apps/web-app/src/styles/app-variables.scss';\r\n\r\n.mat-sidenav {\r\n  width: 240px;\r\n}\r\n\r\n.app-sidebar-content {\r\n  padding: 8px;\r\n}\r\n"],"sourceRoot":""}]);
// Exports
module.exports = ___CSS_LOADER_EXPORT___.toString();


/***/ }),

/***/ 8654:
/*!********************************************************************************!*\
  !*** ./apps/web-app/src/app/core/containers/app/app.component.scss?ngResource ***!
  \********************************************************************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

// Imports
var ___CSS_LOADER_API_SOURCEMAP_IMPORT___ = __webpack_require__(/*! ../../../../../../../node_modules/css-loader/dist/runtime/sourceMaps.js */ 9579);
var ___CSS_LOADER_API_IMPORT___ = __webpack_require__(/*! ../../../../../../../node_modules/css-loader/dist/runtime/api.js */ 931);
var ___CSS_LOADER_EXPORT___ = ___CSS_LOADER_API_IMPORT___(___CSS_LOADER_API_SOURCEMAP_IMPORT___);
// Module
___CSS_LOADER_EXPORT___.push([module.id, ".mat-drawer-container {\n  margin-top: 56px;\n  height: calc(100% - 56px);\n  background-color: rgb(250, 250, 250);\n}\n@media (prefers-color-scheme: dark) {\n  .mat-drawer-container {\n    background-color: rgb(33, 33, 33);\n  }\n}", "",{"version":3,"sources":["webpack://./apps/web-app/src/app/core/containers/app/app.component.scss","webpack://./apps/web-app/src/styles/app-variables.scss"],"names":[],"mappings":"AAEA;EACE,gBCGwB;EDFxB,yBAAA;EACA,oCAAA;AADF;AAGE;EALF;IAMI,iCCS6C;EDT/C;AACF","sourcesContent":["@import 'apps/web-app/src/styles/app-variables.scss';\r\n\r\n.mat-drawer-container {\r\n  margin-top: $app-main-toolbar-height;\r\n  height: $app-mat-drawer-container-height;\r\n  background-color: $app-mat-drawer-container-background-color;\r\n\r\n  @media (prefers-color-scheme: dark) {\r\n    background-color: $app-dark-mat-drawer-container-background-color;\r\n  }\r\n}\r\n","$app-font-family: 'Roboto', sans-serif;\r\n$app-mobile-max-width: 720px;\r\n$app-non-mobile-min-width: 721px;\r\n\r\n$app-mat-drawer-container-height: calc(100% - 56px);\r\n$app-mat-drawer-container-background-color: rgb(250, 250, 250);\r\n$app-main-toolbar-height: 56px;\r\n$app-main-toolbar-color: rgba(255, 255, 255, 1);\r\n$app-main-toolbar-background: var(--navbar);\r\n\r\n$app-page-toolbar-height: 38px;\r\n$app-page-toolbar-background: rgba(255, 255, 255, 0.95);\r\n\r\n$app-pre-background: #f6f8fa;\r\n\r\n// .dark-theme\r\n$app-dark-pre-background: rgb(18, 18, 18);\r\n$app-dark-mat-drawer-container-background-color: rgb(33, 33, 33);\r\n$app-dark-page-toolbar-background: rgb(54, 54, 54);\r\n$app-dark-main-toolbar-background: rgb(40, 40, 40);\r\n\r\n$btn-primary-bg: #1d569a;\r\n"],"sourceRoot":""}]);
// Exports
module.exports = ___CSS_LOADER_EXPORT___.toString();


/***/ }),

/***/ 3965:
/*!**********************************************************************************!*\
  !*** ./apps/web-app/src/app/core/containers/home/home.component.scss?ngResource ***!
  \**********************************************************************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

// Imports
var ___CSS_LOADER_API_SOURCEMAP_IMPORT___ = __webpack_require__(/*! ../../../../../../../node_modules/css-loader/dist/runtime/sourceMaps.js */ 9579);
var ___CSS_LOADER_API_IMPORT___ = __webpack_require__(/*! ../../../../../../../node_modules/css-loader/dist/runtime/api.js */ 931);
var ___CSS_LOADER_EXPORT___ = ___CSS_LOADER_API_IMPORT___(___CSS_LOADER_API_SOURCEMAP_IMPORT___);
// Module
___CSS_LOADER_EXPORT___.push([module.id, "::ng-deep pre {\n  padding: 16px;\n  overflow: auto;\n  font-size: 85%;\n  line-height: 1.45;\n  background-color: #f6f8fa;\n  border-radius: 3px;\n  border: 1px solid #e1e1e8;\n}\n@media (prefers-color-scheme: dark) {\n  ::ng-deep pre {\n    background-color: rgb(18, 18, 18);\n  }\n}\n::ng-deep blockquote {\n  background-color: #f6f8fa;\n  padding: 1px 16px;\n  margin-left: 0;\n  margin-right: 0;\n  border-left: #e1e1e8 solid 10px;\n  border-radius: 3px;\n}\n@media (prefers-color-scheme: dark) {\n  ::ng-deep blockquote {\n    background-color: rgb(18, 18, 18);\n  }\n}\n::ng-deep code {\n  padding: 2px 4px;\n  background-color: #f6f8fa;\n}\n@media (prefers-color-scheme: dark) {\n  ::ng-deep code {\n    background-color: rgb(18, 18, 18);\n  }\n}", "",{"version":3,"sources":["webpack://./apps/web-app/src/app/core/containers/home/home.component.scss","webpack://./apps/web-app/src/styles/app-variables.scss"],"names":[],"mappings":"AAGE;EACE,aAAA;EACA,cAAA;EACA,cAAA;EACA,iBAAA;EACA,yBCKiB;EDJjB,kBAAA;EACA,yBAAA;AAFJ;AAII;EATF;IAUI,iCCGoB;EDJxB;AACF;AAIE;EACE,yBCLiB;EDMjB,iBAAA;EACA,cAAA;EACA,eAAA;EACA,+BAAA;EACA,kBAAA;AAFJ;AAII;EARF;IASI,iCCVoB;EDSxB;AACF;AAIE;EACE,gBAAA;EACA,yBCnBiB;ADiBrB;AAII;EAJF;IAKI,iCCnBoB;EDkBxB;AACF","sourcesContent":["@import 'apps/web-app/src/styles/app-variables.scss';\r\n\r\n::ng-deep {\r\n  pre {\r\n    padding: 16px;\r\n    overflow: auto;\r\n    font-size: 85%;\r\n    line-height: 1.45;\r\n    background-color: $app-pre-background;\r\n    border-radius: 3px;\r\n    border: 1px solid #e1e1e8;\r\n\r\n    @media (prefers-color-scheme: dark) {\r\n      background-color: $app-dark-pre-background;\r\n    }\r\n  }\r\n\r\n  blockquote {\r\n    background-color: $app-pre-background;\r\n    padding: 1px 16px;\r\n    margin-left: 0;\r\n    margin-right: 0;\r\n    border-left: #e1e1e8 solid 10px;\r\n    border-radius: 3px;\r\n\r\n    @media (prefers-color-scheme: dark) {\r\n      background-color: $app-dark-pre-background;\r\n    }\r\n  }\r\n\r\n  code {\r\n    padding: 2px 4px;\r\n    background-color: $app-pre-background;\r\n\r\n    @media (prefers-color-scheme: dark) {\r\n      background-color: $app-dark-pre-background;\r\n    }\r\n  }\r\n}\r\n","$app-font-family: 'Roboto', sans-serif;\r\n$app-mobile-max-width: 720px;\r\n$app-non-mobile-min-width: 721px;\r\n\r\n$app-mat-drawer-container-height: calc(100% - 56px);\r\n$app-mat-drawer-container-background-color: rgb(250, 250, 250);\r\n$app-main-toolbar-height: 56px;\r\n$app-main-toolbar-color: rgba(255, 255, 255, 1);\r\n$app-main-toolbar-background: var(--navbar);\r\n\r\n$app-page-toolbar-height: 38px;\r\n$app-page-toolbar-background: rgba(255, 255, 255, 0.95);\r\n\r\n$app-pre-background: #f6f8fa;\r\n\r\n// .dark-theme\r\n$app-dark-pre-background: rgb(18, 18, 18);\r\n$app-dark-mat-drawer-container-background-color: rgb(33, 33, 33);\r\n$app-dark-page-toolbar-background: rgb(54, 54, 54);\r\n$app-dark-main-toolbar-background: rgb(40, 40, 40);\r\n\r\n$btn-primary-bg: #1d569a;\r\n"],"sourceRoot":""}]);
// Exports
module.exports = ___CSS_LOADER_EXPORT___.toString();


/***/ }),

/***/ 2218:
/*!*************************************************************************!*\
  !*** ./libs/common/src/big-button/big-button.component.scss?ngResource ***!
  \*************************************************************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

// Imports
var ___CSS_LOADER_API_SOURCEMAP_IMPORT___ = __webpack_require__(/*! ../../../../node_modules/css-loader/dist/runtime/sourceMaps.js */ 9579);
var ___CSS_LOADER_API_IMPORT___ = __webpack_require__(/*! ../../../../node_modules/css-loader/dist/runtime/api.js */ 931);
var ___CSS_LOADER_EXPORT___ = ___CSS_LOADER_API_IMPORT___(___CSS_LOADER_API_SOURCEMAP_IMPORT___);
// Module
___CSS_LOADER_EXPORT___.push([module.id, ":host .mat-button {\n  background-color: rgba(0, 0, 0, 0.12);\n  margin: 0px 4px;\n  line-height: 42px;\n  font-size: 16px;\n}\n:host .mat-button:first-child {\n  margin-left: 0;\n}\n:host .mat-button:last-child {\n  margin-right: 0;\n}\n:host .mat-button .mat-icon {\n  height: 28px;\n}", "",{"version":3,"sources":["webpack://./libs/common/src/big-button/big-button.component.scss"],"names":[],"mappings":"AAAA;EACE,qCAAA;EACA,eAAA;EAUA,iBAAA;EACA,eAAA;AARF;AADE;EACE,cAAA;AAGJ;AAAE;EACE,eAAA;AAEJ;AAIE;EACE,YAAA;AAFJ","sourcesContent":[":host .mat-button {\r\n  background-color: rgba(0, 0, 0, 0.12);\r\n  margin: 0px 4px;\r\n\r\n  &:first-child {\r\n    margin-left: 0;\r\n  }\r\n\r\n  &:last-child {\r\n    margin-right: 0;\r\n  }\r\n\r\n  line-height: 42px;\r\n  font-size: 16px;\r\n\r\n  .mat-icon {\r\n    height: 28px;\r\n  }\r\n}\r\n"],"sourceRoot":""}]);
// Exports
module.exports = ___CSS_LOADER_EXPORT___.toString();


/***/ }),

/***/ 9485:
/*!*********************************************************************************!*\
  !*** ./libs/common/src/page-container/page-container.component.scss?ngResource ***!
  \*********************************************************************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

// Imports
var ___CSS_LOADER_API_SOURCEMAP_IMPORT___ = __webpack_require__(/*! ../../../../node_modules/css-loader/dist/runtime/sourceMaps.js */ 9579);
var ___CSS_LOADER_API_IMPORT___ = __webpack_require__(/*! ../../../../node_modules/css-loader/dist/runtime/api.js */ 931);
var ___CSS_LOADER_EXPORT___ = ___CSS_LOADER_API_IMPORT___(___CSS_LOADER_API_SOURCEMAP_IMPORT___);
// Module
___CSS_LOADER_EXPORT___.push([module.id, ".app-page-container {\n  padding: 8px;\n}", "",{"version":3,"sources":["webpack://./libs/common/src/page-container/page-container.component.scss"],"names":[],"mappings":"AAAA;EACE,YAAA;AACF","sourcesContent":[".app-page-container {\r\n  padding: 8px;\r\n}\r\n"],"sourceRoot":""}]);
// Exports
module.exports = ___CSS_LOADER_EXPORT___.toString();


/***/ }),

/***/ 2303:
/*!*******************************************************************************************!*\
  !*** ./libs/common/src/page-toolbar-button/page-toolbar-button.component.scss?ngResource ***!
  \*******************************************************************************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

// Imports
var ___CSS_LOADER_API_SOURCEMAP_IMPORT___ = __webpack_require__(/*! ../../../../node_modules/css-loader/dist/runtime/sourceMaps.js */ 9579);
var ___CSS_LOADER_API_IMPORT___ = __webpack_require__(/*! ../../../../node_modules/css-loader/dist/runtime/api.js */ 931);
var ___CSS_LOADER_EXPORT___ = ___CSS_LOADER_API_IMPORT___(___CSS_LOADER_API_SOURCEMAP_IMPORT___);
// Module
___CSS_LOADER_EXPORT___.push([module.id, "", "",{"version":3,"sources":[],"names":[],"mappings":"","sourceRoot":""}]);
// Exports
module.exports = ___CSS_LOADER_EXPORT___.toString();


/***/ }),

/***/ 3411:
/*!*****************************************************************************!*\
  !*** ./libs/common/src/page-toolbar/page-toolbar.component.scss?ngResource ***!
  \*****************************************************************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

// Imports
var ___CSS_LOADER_API_SOURCEMAP_IMPORT___ = __webpack_require__(/*! ../../../../node_modules/css-loader/dist/runtime/sourceMaps.js */ 9579);
var ___CSS_LOADER_API_IMPORT___ = __webpack_require__(/*! ../../../../node_modules/css-loader/dist/runtime/api.js */ 931);
var ___CSS_LOADER_EXPORT___ = ___CSS_LOADER_API_IMPORT___(___CSS_LOADER_API_SOURCEMAP_IMPORT___);
// Module
___CSS_LOADER_EXPORT___.push([module.id, ".app-page-toolbar {\n  padding: 0px 16px;\n  background: rgba(255, 255, 255, 0.95);\n  height: 38px;\n  line-height: 38px;\n  display: flex;\n  box-sizing: border-box;\n  flex-direction: row;\n  align-items: center;\n  white-space: nowrap;\n  margin-bottom: 8px;\n}\n@media (prefers-color-scheme: dark) {\n  .app-page-toolbar {\n    background: rgb(54, 54, 54);\n  }\n}\n.app-page-toolbar h1.title {\n  font-size: 18px;\n  font-weight: normal;\n  margin-block-start: 0;\n  margin-block-end: 0;\n  display: inline;\n}", "",{"version":3,"sources":["webpack://./libs/common/src/page-toolbar/page-toolbar.component.scss","webpack://./apps/web-app/src/styles/app-variables.scss"],"names":[],"mappings":"AAEA;EACE,iBAAA;EACA,qCCO4B;EDN5B,YCKwB;EDJxB,iBCIwB;EDHxB,aAAA;EACA,sBAAA;EACA,mBAAA;EACA,mBAAA;EACA,mBAAA;EACA,kBAAA;AADF;AAGE;EAZF;IAaI,2BCG+B;EDHjC;AACF;AAGI;EACE,eAAA;EACA,mBAAA;EACA,qBAAA;EACA,mBAAA;EACA,eAAA;AADN","sourcesContent":["@import 'apps/web-app/src/styles/app-variables.scss';\r\n\r\n.app-page-toolbar {\r\n  padding: 0px 16px;\r\n  background: $app-page-toolbar-background;\r\n  height: $app-page-toolbar-height;\r\n  line-height: $app-page-toolbar-height;\r\n  display: flex;\r\n  box-sizing: border-box;\r\n  flex-direction: row;\r\n  align-items: center;\r\n  white-space: nowrap;\r\n  margin-bottom: 8px;\r\n\r\n  @media (prefers-color-scheme: dark) {\r\n    background: $app-dark-page-toolbar-background;\r\n  }\r\n\r\n  h1 {\r\n    &.title {\r\n      font-size: 18px;\r\n      font-weight: normal;\r\n      margin-block-start: 0;\r\n      margin-block-end: 0;\r\n      display: inline;\r\n    }\r\n  }\r\n}\r\n","$app-font-family: 'Roboto', sans-serif;\r\n$app-mobile-max-width: 720px;\r\n$app-non-mobile-min-width: 721px;\r\n\r\n$app-mat-drawer-container-height: calc(100% - 56px);\r\n$app-mat-drawer-container-background-color: rgb(250, 250, 250);\r\n$app-main-toolbar-height: 56px;\r\n$app-main-toolbar-color: rgba(255, 255, 255, 1);\r\n$app-main-toolbar-background: var(--navbar);\r\n\r\n$app-page-toolbar-height: 38px;\r\n$app-page-toolbar-background: rgba(255, 255, 255, 0.95);\r\n\r\n$app-pre-background: #f6f8fa;\r\n\r\n// .dark-theme\r\n$app-dark-pre-background: rgb(18, 18, 18);\r\n$app-dark-mat-drawer-container-background-color: rgb(33, 33, 33);\r\n$app-dark-page-toolbar-background: rgb(54, 54, 54);\r\n$app-dark-main-toolbar-background: rgb(40, 40, 40);\r\n\r\n$btn-primary-bg: #1d569a;\r\n"],"sourceRoot":""}]);
// Exports
module.exports = ___CSS_LOADER_EXPORT___.toString();


/***/ }),

/***/ 6700:
/*!***************************************************!*\
  !*** ./node_modules/moment/locale/ sync ^\.\/.*$ ***!
  \***************************************************/
/***/ ((module, __unused_webpack_exports, __webpack_require__) => {

var map = {
	"./af": 8685,
	"./af.js": 8685,
	"./ar": 254,
	"./ar-dz": 4312,
	"./ar-dz.js": 4312,
	"./ar-kw": 2614,
	"./ar-kw.js": 2614,
	"./ar-ly": 8630,
	"./ar-ly.js": 8630,
	"./ar-ma": 8674,
	"./ar-ma.js": 8674,
	"./ar-ps": 6235,
	"./ar-ps.js": 6235,
	"./ar-sa": 9032,
	"./ar-sa.js": 9032,
	"./ar-tn": 4730,
	"./ar-tn.js": 4730,
	"./ar.js": 254,
	"./az": 3052,
	"./az.js": 3052,
	"./be": 150,
	"./be.js": 150,
	"./bg": 3069,
	"./bg.js": 3069,
	"./bm": 3466,
	"./bm.js": 3466,
	"./bn": 8516,
	"./bn-bd": 557,
	"./bn-bd.js": 557,
	"./bn.js": 8516,
	"./bo": 6273,
	"./bo.js": 6273,
	"./br": 9588,
	"./br.js": 9588,
	"./bs": 9815,
	"./bs.js": 9815,
	"./ca": 3331,
	"./ca.js": 3331,
	"./cs": 1320,
	"./cs.js": 1320,
	"./cv": 2219,
	"./cv.js": 2219,
	"./cy": 8266,
	"./cy.js": 8266,
	"./da": 6427,
	"./da.js": 6427,
	"./de": 7435,
	"./de-at": 2871,
	"./de-at.js": 2871,
	"./de-ch": 2994,
	"./de-ch.js": 2994,
	"./de.js": 7435,
	"./dv": 2357,
	"./dv.js": 2357,
	"./el": 5649,
	"./el.js": 5649,
	"./en-au": 7159,
	"./en-au.js": 7159,
	"./en-ca": 9878,
	"./en-ca.js": 9878,
	"./en-gb": 3924,
	"./en-gb.js": 3924,
	"./en-ie": 864,
	"./en-ie.js": 864,
	"./en-il": 1579,
	"./en-il.js": 1579,
	"./en-in": 940,
	"./en-in.js": 940,
	"./en-nz": 6181,
	"./en-nz.js": 6181,
	"./en-sg": 4301,
	"./en-sg.js": 4301,
	"./eo": 5291,
	"./eo.js": 5291,
	"./es": 4529,
	"./es-do": 3764,
	"./es-do.js": 3764,
	"./es-mx": 2584,
	"./es-mx.js": 2584,
	"./es-us": 3425,
	"./es-us.js": 3425,
	"./es.js": 4529,
	"./et": 5203,
	"./et.js": 5203,
	"./eu": 678,
	"./eu.js": 678,
	"./fa": 3483,
	"./fa.js": 3483,
	"./fi": 6262,
	"./fi.js": 6262,
	"./fil": 2521,
	"./fil.js": 2521,
	"./fo": 4555,
	"./fo.js": 4555,
	"./fr": 3131,
	"./fr-ca": 8239,
	"./fr-ca.js": 8239,
	"./fr-ch": 1702,
	"./fr-ch.js": 1702,
	"./fr.js": 3131,
	"./fy": 267,
	"./fy.js": 267,
	"./ga": 3821,
	"./ga.js": 3821,
	"./gd": 1753,
	"./gd.js": 1753,
	"./gl": 4074,
	"./gl.js": 4074,
	"./gom-deva": 2762,
	"./gom-deva.js": 2762,
	"./gom-latn": 5969,
	"./gom-latn.js": 5969,
	"./gu": 2809,
	"./gu.js": 2809,
	"./he": 5402,
	"./he.js": 5402,
	"./hi": 315,
	"./hi.js": 315,
	"./hr": 410,
	"./hr.js": 410,
	"./hu": 8288,
	"./hu.js": 8288,
	"./hy-am": 7928,
	"./hy-am.js": 7928,
	"./id": 1334,
	"./id.js": 1334,
	"./is": 6959,
	"./is.js": 6959,
	"./it": 4864,
	"./it-ch": 1124,
	"./it-ch.js": 1124,
	"./it.js": 4864,
	"./ja": 6141,
	"./ja.js": 6141,
	"./jv": 9187,
	"./jv.js": 9187,
	"./ka": 2136,
	"./ka.js": 2136,
	"./kk": 4332,
	"./kk.js": 4332,
	"./km": 8607,
	"./km.js": 8607,
	"./kn": 4305,
	"./kn.js": 4305,
	"./ko": 234,
	"./ko.js": 234,
	"./ku": 6003,
	"./ku-kmr": 9619,
	"./ku-kmr.js": 9619,
	"./ku.js": 6003,
	"./ky": 5061,
	"./ky.js": 5061,
	"./lb": 2786,
	"./lb.js": 2786,
	"./lo": 6183,
	"./lo.js": 6183,
	"./lt": 29,
	"./lt.js": 29,
	"./lv": 4169,
	"./lv.js": 4169,
	"./me": 8577,
	"./me.js": 8577,
	"./mi": 8177,
	"./mi.js": 8177,
	"./mk": 337,
	"./mk.js": 337,
	"./ml": 5260,
	"./ml.js": 5260,
	"./mn": 2325,
	"./mn.js": 2325,
	"./mr": 4695,
	"./mr.js": 4695,
	"./ms": 5334,
	"./ms-my": 7151,
	"./ms-my.js": 7151,
	"./ms.js": 5334,
	"./mt": 3570,
	"./mt.js": 3570,
	"./my": 7963,
	"./my.js": 7963,
	"./nb": 8028,
	"./nb.js": 8028,
	"./ne": 6638,
	"./ne.js": 6638,
	"./nl": 302,
	"./nl-be": 6782,
	"./nl-be.js": 6782,
	"./nl.js": 302,
	"./nn": 3501,
	"./nn.js": 3501,
	"./oc-lnc": 563,
	"./oc-lnc.js": 563,
	"./pa-in": 869,
	"./pa-in.js": 869,
	"./pl": 5302,
	"./pl.js": 5302,
	"./pt": 9687,
	"./pt-br": 4884,
	"./pt-br.js": 4884,
	"./pt.js": 9687,
	"./ro": 5773,
	"./ro.js": 5773,
	"./ru": 3627,
	"./ru.js": 3627,
	"./sd": 355,
	"./sd.js": 355,
	"./se": 3427,
	"./se.js": 3427,
	"./si": 1848,
	"./si.js": 1848,
	"./sk": 4590,
	"./sk.js": 4590,
	"./sl": 184,
	"./sl.js": 184,
	"./sq": 6361,
	"./sq.js": 6361,
	"./sr": 8965,
	"./sr-cyrl": 1287,
	"./sr-cyrl.js": 1287,
	"./sr.js": 8965,
	"./ss": 5456,
	"./ss.js": 5456,
	"./sv": 451,
	"./sv.js": 451,
	"./sw": 7558,
	"./sw.js": 7558,
	"./ta": 1356,
	"./ta.js": 1356,
	"./te": 3693,
	"./te.js": 3693,
	"./tet": 1243,
	"./tet.js": 1243,
	"./tg": 2500,
	"./tg.js": 2500,
	"./th": 5768,
	"./th.js": 5768,
	"./tk": 7761,
	"./tk.js": 7761,
	"./tl-ph": 5780,
	"./tl-ph.js": 5780,
	"./tlh": 9590,
	"./tlh.js": 9590,
	"./tr": 3807,
	"./tr.js": 3807,
	"./tzl": 3857,
	"./tzl.js": 3857,
	"./tzm": 654,
	"./tzm-latn": 8806,
	"./tzm-latn.js": 8806,
	"./tzm.js": 654,
	"./ug-cn": 845,
	"./ug-cn.js": 845,
	"./uk": 9232,
	"./uk.js": 9232,
	"./ur": 7052,
	"./ur.js": 7052,
	"./uz": 7967,
	"./uz-latn": 2233,
	"./uz-latn.js": 2233,
	"./uz.js": 7967,
	"./vi": 8615,
	"./vi.js": 8615,
	"./x-pseudo": 2320,
	"./x-pseudo.js": 2320,
	"./yo": 1313,
	"./yo.js": 1313,
	"./zh-cn": 4490,
	"./zh-cn.js": 4490,
	"./zh-hk": 5910,
	"./zh-hk.js": 5910,
	"./zh-mo": 8262,
	"./zh-mo.js": 8262,
	"./zh-tw": 4223,
	"./zh-tw.js": 4223
};


function webpackContext(req) {
	var id = webpackContextResolve(req);
	return __webpack_require__(id);
}
function webpackContextResolve(req) {
	if(!__webpack_require__.o(map, req)) {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	}
	return map[req];
}
webpackContext.keys = function webpackContextKeys() {
	return Object.keys(map);
};
webpackContext.resolve = webpackContextResolve;
module.exports = webpackContext;
webpackContext.id = 6700;

/***/ }),

/***/ 3806:
/*!**************************************************************************************************!*\
  !*** ./apps/web-app/src/app/core/components/main-toolbar/main-toolbar.component.html?ngResource ***!
  \**************************************************************************************************/
/***/ ((module) => {

"use strict";
module.exports = "<mat-toolbar class=\"app-main-toolbar\">\r\n  <button mat-icon-button\r\n          aria-label=\"Toggle side menu\"\r\n          (click)=\"emitToggleSidenav()\"\r\n          class=\"show-mobile\">\r\n    <mat-icon>menu</mat-icon>\r\n  </button>\r\n  <p class=\"logo\"\r\n     aria-label=\"Home Page\">\r\n  </p>\r\n  <a class=\"logo-text\"\r\n     [routerLink]=\"['/vis-timeline/timeline']\"> HiCuMES Feinplanung</a>\r\n  <span class=\"flex-spacer\"></span>\r\n  <div class=\"hide-mobile\">\r\n  </div>\r\n</mat-toolbar>\r\n";

/***/ }),

/***/ 8108:
/*!************************************************************************************************************!*\
  !*** ./apps/web-app/src/app/core/components/sidenav-list-item/sidenav-list-item.component.html?ngResource ***!
  \************************************************************************************************************/
/***/ ((module) => {

"use strict";
module.exports = "<a mat-list-item\r\n   [routerLink]=\"routerLink\"\r\n   (click)=\"navigate.emit()\">\r\n  <mat-icon mat-list-icon>{{ icon }}</mat-icon>\r\n  <span mat-line>\r\n    <ng-content></ng-content>\r\n  </span>\r\n  <span mat-line\r\n        class=\"secondary\">{{ hint }}</span>\r\n</a>";

/***/ }),

/***/ 5934:
/*!****************************************************************************************!*\
  !*** ./apps/web-app/src/app/core/components/sidenav/sidenav.component.html?ngResource ***!
  \****************************************************************************************/
/***/ ((module) => {

"use strict";
module.exports = "<div class=\"app-sidebar-content\">\r\n  <mat-nav-list>\r\n    <app-sidenav-list-item (navigate)=\"closeSidenav.emit()\"\r\n                           routerLink=\"/weather-forecast\"\r\n                           icon=\"get_app\"\r\n                           hint=\"Get Data Feature\">\r\n      Weather Forecasts\r\n    </app-sidenav-list-item>\r\n    <app-sidenav-list-item (navigate)=\"closeSidenav.emit()\"\r\n                           routerLink=\"/feature\"\r\n                           icon=\"hotel\"\r\n                           hint=\"Lazy Loaded Feature\">\r\n      Coutner\r\n    </app-sidenav-list-item>\r\n  </mat-nav-list>\r\n</div>";

/***/ }),

/***/ 144:
/*!********************************************************************************!*\
  !*** ./apps/web-app/src/app/core/containers/app/app.component.html?ngResource ***!
  \********************************************************************************/
/***/ ((module) => {

"use strict";
module.exports = "<app-main-toolbar (toggleSidenav)=\"toggleSidenav()\"></app-main-toolbar>\r\n\r\n<mat-sidenav-container id=\"appSidenavContainer\"\r\n                       fullscreen>\r\n  <mat-sidenav mode=\"over\"\r\n               [opened]=\"showSidenav$ | async\"\r\n               (openedChange)=\"sidenavChanged($event)\">\r\n    <app-sidenav (toggleSidenav)=\"toggleSidenav()\"\r\n                 (closeSidenav)=\"closeSidenav()\"></app-sidenav>\r\n  </mat-sidenav>\r\n  <div class=\"app-content\">\r\n    <router-outlet></router-outlet>\r\n  </div>\r\n</mat-sidenav-container>";

/***/ }),

/***/ 6623:
/*!**********************************************************************************!*\
  !*** ./apps/web-app/src/app/core/containers/home/home.component.html?ngResource ***!
  \**********************************************************************************/
/***/ ((module) => {

"use strict";
module.exports = "<app-page-toolbar [title]=\"title$ | async\"> </app-page-toolbar>\r\n<app-page-container>\r\n  <mat-card>\r\n    <markdown id=\"page-markdown\"\r\n              src=\"/assets/home.component.md\"></markdown>\r\n  </mat-card>\r\n</app-page-container>";

/***/ }),

/***/ 2459:
/*!*************************************************************************!*\
  !*** ./libs/common/src/big-button/big-button.component.html?ngResource ***!
  \*************************************************************************/
/***/ ((module) => {

"use strict";
module.exports = "<button mat-button>\r\n  <ng-content></ng-content>\r\n</button>";

/***/ }),

/***/ 9572:
/*!*********************************************************************************!*\
  !*** ./libs/common/src/page-container/page-container.component.html?ngResource ***!
  \*********************************************************************************/
/***/ ((module) => {

"use strict";
module.exports = "<div class=\"app-page-container\">\r\n  <ng-content></ng-content>\r\n</div>";

/***/ }),

/***/ 3014:
/*!*******************************************************************************************!*\
  !*** ./libs/common/src/page-toolbar-button/page-toolbar-button.component.html?ngResource ***!
  \*******************************************************************************************/
/***/ ((module) => {

"use strict";
module.exports = "<button mat-icon-button\r\n        [attr.aria-label]=\"tooltip\"\r\n        [matTooltip]=\"tooltip\"\r\n        [matTooltipDisabled]=\"tooltipDisabled\">\r\n  <ng-content></ng-content>\r\n</button>";

/***/ }),

/***/ 2952:
/*!*****************************************************************************!*\
  !*** ./libs/common/src/page-toolbar/page-toolbar.component.html?ngResource ***!
  \*****************************************************************************/
/***/ ((module) => {

"use strict";
module.exports = "<div class=\"app-page-toolbar\">\r\n  <h1 class=\"title\"\r\n      *ngIf=\"title\">{{ title }}</h1>\r\n  <span class=\"flex-spacer\"></span>\r\n  <ng-content></ng-content>\r\n</div>";

/***/ })

},
/******/ __webpack_require__ => { // webpackRuntimeModules
/******/ var __webpack_exec__ = (moduleId) => (__webpack_require__(__webpack_require__.s = moduleId))
/******/ __webpack_require__.O(0, ["vendor"], () => (__webpack_exec__(7407)));
/******/ var __webpack_exports__ = __webpack_require__.O();
/******/ }
]);
//# sourceMappingURL=main.js.map