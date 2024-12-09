import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {MappingRuleWithLoops} from "../../interfaces/mapping-rule-with-loops";
import {CodeModel} from "@ngstack/code-editor";

@Component({
  selector: 'app-all-mapping-rules-xslt',
  templateUrl: './all-mapping-rules-xslt.component.html',
  styleUrls: ['./all-mapping-rules-xslt.component.scss']
})
export class AllMappingRulesXSLTComponent implements OnInit , OnChanges{

  @Input()
  mappingRules?: string = "";
  @Input()
  componentTitle?: string;
  @Output()
  updateMapping = new EventEmitter<string>();
  @Output()
  saveToDB = new EventEmitter<string>();
  theme = 'vs';

  showEditor:boolean = false;

  codeModel: CodeModel = {
    language: 'xml',
    uri: 'main.xml',
    value: '{}'
  };

  options = {
    contextmenu: true,
    minimap: {
      enabled: true
    }
  };

  onCodeChanged(value: any) {
    this.mappingRules = value;
  }

  constructor() { }

  ngOnInit(): void {

    console.log(this.mappingRules)
    if(this.mappingRules)
    {
      this.codeModel = { ...this.codeModel, value: this.mappingRules };
      //Without this the editor window will not show in full width (see https://github.com/ngstack/code-editor/issues/510)
      setTimeout (() => {
        this.showEditor = true;
      }, 0);
    }

  }

  ngOnChanges() {
    this.ngOnInit()
  }

  update() {
    this.updateMapping.emit(this.mappingRules);
  }

  save() {
    this.saveToDB.emit(this.mappingRules);
  }
}
