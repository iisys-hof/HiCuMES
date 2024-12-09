import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MappingRuleWithLoops} from "../../interfaces/mapping-rule-with-loops";

@Component({
  selector: 'app-all-mapping-rules',
  templateUrl: './all-mapping-rules.component.html',
  styleUrls: ['./all-mapping-rules.component.scss']
})
export class AllMappingRulesComponent implements OnInit {

  @Input()
  mappingRules?: MappingRuleWithLoops[];
  @Input()
  componentTitle?: string;
  @Output()
  deleteMapping = new EventEmitter<MappingRuleWithLoops>();

  @Output()
  editMapping = new EventEmitter<MappingRuleWithLoops>();

  constructor() { }

  ngOnInit(): void {
  }

  removeMapping(mappingRule: MappingRuleWithLoops) {
    this.deleteMapping.emit(mappingRule);
  }

  openTransformModal(mappingRule: MappingRuleWithLoops) {
    this.editMapping.emit(mappingRule);
  }
}
