import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Class, FieldExtension} from "../../interfaces/field-extension";
import {WindowSizeService} from "../../services/window-size.service";


@Component({
  selector: 'app-entity-master',
  templateUrl: './entity-master.component.html',
  styleUrls: ['./entity-master.component.scss']
})
export class EntityMasterComponent  {

  entitymasterHeight: string = '200px';

  @Output()
  changedSelection = new EventEmitter<Class>();

  @Input()
  fieldExtension!: FieldExtension | null;
  constructor(public windowSizeService: WindowSizeService) {
    windowSizeService.getHeightObservable().subscribe(height => {
      this.entitymasterHeight = height -180 +'px';
    })
  }


  onClick(c: Class): void {
    this.changedSelection.emit(c);
  }


}
