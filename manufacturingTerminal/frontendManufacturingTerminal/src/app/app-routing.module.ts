import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrderOverviewComponent} from "./container/order-overview/order-overview.component";
import {SubproductionstepFormfieldComponent} from "./container/subproductionstep-formfield/subproductionstep-formfield.component";
import {OrderDetailComponent} from "./container/order-detail/order-detail.component";
import {DynamicFieldsComponent} from "./container/dynamic-fields/dynamic-fields.component";
import {LoginComponent} from "./component/login/login.component";
import {AuthGuard} from "./service/authguard.service";
import {PdfViewerComponent} from "./iframe/pdf-viewer/pdf-viewer.component";
import {SettingsComponent} from "./container/settings/settings.component";
import {UserOrderOverviewComponent} from "./container/user-order-overview/user-order-overview.component";
import {
  ChangeMachineOccupationStatusComponent
} from "./component/change-machine-occupation-status/change-machine-occupation-status.component";
import {
  ChangeMachineOccupationContainerComponent
} from "./container/change-machine-occupation-container/change-machine-occupation-container.component";
import {BookingOverviewComponent} from "./component/booking-overview/booking-overview.component";
import {OverheadCostsComponent} from "./component/overhead-costs/overhead-costs.component";
import {OverheadCostsContainerComponent} from "./container/overhead-costs-container/overhead-costs-container.component";
import {UserBookingOverviewTableComponent} from "./component/user-booking-overview-table/user-booking-overview-table.component";
import {UserBookingOverviewComponent} from "./container/user-booking-overview/user-booking-overview.component";

const routes: Routes = [
  {path: '', component: OrderOverviewComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},
  {path: 'login', component: LoginComponent},
  {path: 'order-overview', component: OrderOverviewComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},
  {path: 'user-order-overview', component: UserOrderOverviewComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},
  {path: 'order-detail', component: OrderDetailComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},
  {path: 'settings', component: SettingsComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},
  {path: 'formfields', component: SubproductionstepFormfieldComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},
  {path: 'ConfirmProductionTask', component: SubproductionstepFormfieldComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},
  {path: 'change-status', component: ChangeMachineOccupationContainerComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin']}},
  {path: 'booking-overview', component: BookingOverviewComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin']}},
  {path: 'user-booking-overview', component: UserBookingOverviewComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},
  {path: 'overhead-costs', component: OverheadCostsContainerComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},

  /*{path: 'production-numbers', component: MaskProductionNumbersComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},
  {path: 'dynamic-fields', component: DynamicFieldsComponent,
    canActivate: [AuthGuard],
    data: {role: ['admin', 'worker']}},*/
  {path: 'extended-pdf-viewer', component: PdfViewerComponent, outlet: "root"},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
