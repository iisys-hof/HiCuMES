import {Injectable} from '@angular/core';
import {MappingRuleWithLoops} from "../interfaces/mapping-rule-with-loops";
import {SelectedTreeNode} from "../interfaces/selected-tree-node";
import {MappingRule} from "../interfaces/mapping-request";

@Injectable({
  providedIn: 'root'
})
export class MappingRuleCreatorService {

  constructor() { }

  generateRule(selectedInputNode: SelectedTreeNode, selectedOutputNode: SelectedTreeNode):MappingRuleWithLoops {
    const rule = selectedOutputNode.treeNode.selector + ' = ' + selectedInputNode.treeNode.selector;
    const mappingRule: MappingRule = {
      rule: rule,
      uiId: Math.floor(Math.random()*1000000),
      inputSelector: selectedInputNode.treeNode.selector,
      outputSelector: selectedOutputNode.treeNode.selector
    };
    const outputLoops = this.extractLoops(selectedOutputNode);
    const inputLoops = this.extractLoops(selectedInputNode);

    while (outputLoops.length != inputLoops.length || outputLoops.length === 0 || outputLoops[0] !== 'output') {
      if (outputLoops.length <= inputLoops.length) {
        outputLoops.unshift('output');
      } else {
        inputLoops.unshift('input');
      }
    }
    console.log('input')
    console.log(inputLoops)

    return {
      mappingRule: mappingRule,
      outputLoops: outputLoops,
      inputLoops: inputLoops
    }
  }

  private extractLoops(selectedOutputNode: SelectedTreeNode) {
    const outputLoops = [];
    for (const node of selectedOutputNode.parentTreeNodes) {
      if (node.type == 'LOOP') {
        outputLoops.push(node.selector);
      }
    }
    return outputLoops.reverse();
  }
}
