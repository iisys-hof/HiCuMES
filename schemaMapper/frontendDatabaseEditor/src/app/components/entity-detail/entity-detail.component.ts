import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {Class} from "../../interfaces/field-extension";
import {FormGroup} from "@angular/forms";
import {FormlyFieldConfig, FormlyFormOptions} from "@ngx-formly/core";

@Component({
  selector: 'app-entity-detail',
  templateUrl: './entity-detail.component.html',
  styleUrls: ['./entity-detail.component.scss']
})
export class EntityDetailComponent implements OnChanges {

  selectionNewFields = [ {
    key: 'members',
    type: 'repeat',
    templateOptions: {
      addText: 'Weiteres Attribut hinzufügen'
    },
    fieldArray: {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          type: 'input',
          key: 'name',
          className: 'col-sm-4',
          templateOptions: {
            label: 'Name des Attribut',
            required: true,
            minLength: 5
          },
        },
        {
          type: 'select',
          key: 'type',
          className: 'col-sm-4',
          templateOptions: {
            label: 'Auswahl des Datentyps',
            options: [
              {label: 'String', value: 'java.lang.String'},
              {label: 'Long', value: 'java.lang.Long'},
              {label: 'LocalDateTime', value: 'java.time.LocalDateTime'},
              {label: 'Integer', value: 'java.lang.Integer'},
              {label: 'Double', value: 'java.lang.Double'},
              {label: 'Boolean', value: 'java.lang.Boolean'},
              {label: 'Duration', value: 'java.time.Duration'},
            ],
            required: true,
          },
        },
      ],
    },
  },
  ];

  @Output()
  changedSelection = new EventEmitter<Class>();

  @Input()
  selectedClass?: Class;
  displayedColumns: string[] = ['name', 'type'];

  constructor() {
  }


  form = new FormGroup({});
  @Input()
  model?: Class;
  options: FormlyFormOptions = {};
  fields: FormlyFieldConfig[] = this.selectionNewFields;


  ngOnChanges(changes: SimpleChanges): void {
    if (this.selectedClass == undefined) {
      return;
    }
  }
}
