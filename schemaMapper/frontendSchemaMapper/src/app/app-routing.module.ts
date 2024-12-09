import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CompleteMapperComponent} from "./container/complete-mapper/complete-mapper.component";

const routes: Routes = [
  {path: '', component: CompleteMapperComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
