import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BookingTerminalViewComponent} from "./container/booking-terminal-view/booking-terminal-view.component";

const routes: Routes = [
  {path: '', component: BookingTerminalViewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
