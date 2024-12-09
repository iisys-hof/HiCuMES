import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {MappingRuleCreatorService} from "../../business/mapping-rule-creator.service";
import {SelectedTreeNode} from "../../interfaces/selected-tree-node";
import {MappingRuleWithLoops} from "../../interfaces/mapping-rule-with-loops";
import {WindowSizeService} from "../../service/window-size.service";

@Component({
  selector: 'app-new-mapping-rule',
  templateUrl: './new-mapping-rule.component.html',
  styleUrls: ['./new-mapping-rule.component.scss']
})
export class NewMappingRuleComponent implements OnChanges {

  @Input()
  selectedInputNode?: SelectedTreeNode;

  @Input()
  selectedOutputNode?: SelectedTreeNode;

  nextPossibleRule?: MappingRuleWithLoops;

  @Output()
  newMappingRule = new EventEmitter<MappingRuleWithLoops>();
  cardHeight: string = "500px";
  tableContent:{input: string, output: string}[]  = [];
  displayColumns: string[] = ['input', 'output'];

  constructor(private mappingRuleCreatorService: MappingRuleCreatorService,public windowSizeService: WindowSizeService) {
    windowSizeService.getHeightObservable().subscribe(height => {
      this.cardHeight = height -115 +'px';
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(this.selectedOutputNode && this.selectedInputNode) {
      this.nextPossibleRule = this.mappingRuleCreatorService.generateRule(this.selectedInputNode, this.selectedOutputNode);
      this.tableContent = [];
      for(let i in this.nextPossibleRule.inputLoops) {
        this.tableContent.push({
          input: this.nextPossibleRule.inputLoops[i],
          output: this.nextPossibleRule.outputLoops[i]
        })
      }
    }
  }

  addMapping() {
    this.newMappingRule.emit(this.nextPossibleRule);
  }
}
