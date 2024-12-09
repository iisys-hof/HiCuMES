// panel-wrapper.component.ts
import { Component, ViewChild, ViewContainerRef } from '@angular/core';
import { FieldWrapper } from '@ngx-formly/core';

@Component({
  selector: 'mat-panel',
  template: `
 <div class="card" style="height: 100%">
   <h3 class="card-header">{{ to.label }}</h3>
   <div class="card-body" style="white-space: pre-wrap">
     <ng-container #fieldComponent></ng-container>
   </div>
 </div>
`,
})
export class MatPanelWrapper extends FieldWrapper {
}
