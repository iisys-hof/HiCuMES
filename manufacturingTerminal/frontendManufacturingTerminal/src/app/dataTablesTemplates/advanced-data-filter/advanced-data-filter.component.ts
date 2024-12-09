import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {FormBuilder, FormGroup} from "@angular/forms";
import * as _ from "lodash";

@Component({
  selector: 'app-advanced-data-filter',
  templateUrl: './advanced-data-filter.component.html',
  styleUrls: ['./advanced-data-filter.component.scss']
})
export class AdvancedDataFilterComponent implements OnInit {

  @Input()
  model!: any;

  @Input()
  filteredModel!: any;

  @Input()
  fields: any;

  @Output()
  filteredModelChange = new EventEmitter<any>();
  filterForm: any = new FormGroup({});
  formFields: any = [];

  constructor(private formBuilder: FormBuilder) {
    this.filteredModel = _.cloneDeep(this.model)
  }

  generateForm(): FormGroup {
    const formControls: any = {};

    this.fields[0].fieldArray?.fieldGroup.forEach((field: any) => {
      //console.log(field)
      let key = field.key;
      if(field.filterValue)
      {
        key = field.filterValue
      }
      formControls[key] = ['']; // Set initial value and validation if needed
    });
    //console.log(formControls)
    return this.formBuilder.group(formControls);
  }

  ngOnInit(): void {
    //console.log(this.model)
    //console.log(this.fields)
    this.filterForm = this.generateForm()
  }

  reset() {
  }


  getFilter(camundaMachineOccupation: any, filter: any) {
    //console.log(_.get(camundaMachineOccupation, filter.prop))
    return _.get(camundaMachineOccupation, filter.prop)
  }

  groupBy(model: any, filter: any): any[] {

    let key = filter.key;
    if(filter.filterValue)
    {
      key = filter.filterValue
    }
    //console.log(model, filter)
    //console.log(_.map(model["camundaMachineOccupations"], (item) =>_.get(item, key)))
    const extractedValues = _.uniq(_.map(model["camundaMachineOccupations"], (item) => _.get(item, key)));
    //console.log(extractedValues)
    return extractedValues
  }

  updateModel($event: any) {
    console.log(this.filterForm)

    let touchedControls: any = [];

    Object.keys(this.filterForm.controls).forEach(key => {
      const control = this.filterForm.controls[key];
      console.log(control)
      if (!control.pristine) {
        touchedControls.push({"key": key, "control": control});
      }
    });

    console.log(touchedControls)
    this.filteredModel = _.cloneDeep(this.model)
    touchedControls.forEach(((control:any) => {
      this.filteredModel["camundaMachineOccupations"] = this.filteredModel["camundaMachineOccupations"].filter((value:any) => {
        if(control.control.value == "")
        {
          return true
        }
        console.log(value, control)
        console.log(_.get(value, $event.source._id.prop))
        console.log($event.value.includes(_.get(value, $event.source._id.prop)))
        return control.control.value.includes(_.get(value, control.key))
      })
    }))

    console.log(this.filteredModel)
    console.log(this.model)
    this.filteredModelChange.emit(this.filteredModel)
  }

  getFilterLabel(filter: any) {
    //console.log(filter)
    //console.log( this.fields)
    return this.fields[0].props?.columns.find((f:any) => f.id === filter.id)?.name;
  }

  getFormFields() {
    return this.fields[0].fieldArray?.fieldGroup.filter((f:any) => f.id)
  }

  getFilterKey(filter: any) {
    let key = filter.key;
    if(filter.filterValue)
    {
      key = filter.filterValue
    }
    return key
  }
}
