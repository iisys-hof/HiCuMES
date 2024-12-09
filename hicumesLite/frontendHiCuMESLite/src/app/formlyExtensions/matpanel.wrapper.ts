// panel-wrapper.component.ts
import { Component, ViewChild, ViewContainerRef } from '@angular/core';
import { FieldWrapper } from '@ngx-formly/core';

@Component({
  selector: 'mat-panel',
  template: `
 <div class="card" style="height: 100%; margin-bottom: 15px">
   <span class="card-header" style="margin: 0; padding: 5px 10px;">{{ to.label }}</span>
   <div class="card-body" style="white-space: pre-wrap; padding: 5px">
     <ng-container #fieldComponent></ng-container>
   </div>
 </div>
`,
})
export class MatPanelWrapper extends FieldWrapper {
}
