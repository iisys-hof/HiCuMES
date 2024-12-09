import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {MappingRuleWithLoops} from "../../interfaces/mapping-rule-with-loops";
import {FormGroup} from "@angular/forms";
import {FormlyFieldConfig, FormlyFormOptions} from "@ngx-formly/core";

@Component({
  selector: 'app-modal-modifly-rule',
  templateUrl: './modal-modifly-rule.component.html',
  styleUrls: ['./modal-modifly-rule.component.scss']
})
export class ModalModiflyRuleComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: MappingRuleWithLoops) {
    this.model = data;
    this.input = data.mappingRule.inputSelector;
    this.output = data.mappingRule.outputSelector + ' =';
  }
  input: string;
  output: string;
  form = new FormGroup({});
  model;

  options: FormlyFormOptions = {};
  fields: FormlyFieldConfig[] = [{
    key: 'mappingRule.rule',
    type: 'textarea',
    className: '',
    templateOptions: {
      placeholder: 'Zuordnungsregel',
      required: true,
      rows: 5
    }
  }];
  ngOnInit(): void {
  }

}
