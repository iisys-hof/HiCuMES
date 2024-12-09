import {AfterViewInit, Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {TreeNode} from "../../interfaces/mapping-response";
import {UtilitiesService} from "../../service/utilities.service";
import * as _ from 'lodash';
import {SelectedTreeNode} from "../../interfaces/selected-tree-node";
import {WindowSizeService} from "../../service/window-size.service";
import {max} from "rxjs/operators";

@Component({
  selector: 'app-file-tree',
  templateUrl: './file-tree.component.html',
  styleUrls: ['./file-tree.component.scss']
})
export class FileTreeComponent implements OnChanges, AfterViewInit {

  sourcetreeId = 'sourcetreeId' +Math.floor(Math.random()*1000000);
  @Input()
  treeNode?: TreeNode;

  @Output()
  changedSelection = new EventEmitter<SelectedTreeNode>();
  @Input()
  componentTitle?: string;
  @Input()
  smallerThanWindowSize:number = 190;

  @Input()
  filterFirstLevel?: string[];

  copyOldState: string = "";
  cardHeight: string = "500px";
  private treeElement: any;

  @Input()
  targetLevel = 0;

  constructor(private utilitiesService: UtilitiesService, public windowSizeService: WindowSizeService) {

  }

  ngAfterViewInit(): void {
    // @ts-ignore
    this.ngOnChanges(null);
    this.windowSizeService.getHeightObservable().subscribe(height => {
      this.cardHeight = height - this.smallerThanWindowSize +'px';
    })
  }

  ngOnChanges(changes: SimpleChanges): void {

    if (_.isNil(this.treeNode)) {
      return;
    }
    var copyNewState = JSON.stringify([this.treeNode, this.filterFirstLevel]);
    if (copyNewState == this.copyOldState) {
      return;
    }

    var treeElement: any = $('#'+this.sourcetreeId );
    this.treeElement = treeElement;
    var searchElement: any =$('#search'+this.sourcetreeId );
    if(treeElement.length < 1) {
      return;
    }
    // @ts-ignore

    this.copyOldState = copyNewState;
    let enumPathMapping = {
      "CAMUNDA_NO_MAPPING": "cross.png",
      "CAMUNDA_MAPPING": "ok.png",
      "OBJECT": "folder.png",
      "LOOP": "number.png",
      "INTEGER": "bool.png",
      "STRING": "bool.png",
      "LONG": "bool.png",
      "LOCALDATETIME": "bool.png",
      "LOCALTIME": "bool.png",
      "DATE": "bool.png",
      "TIME": "bool.png",
      "FLOAT": "bool.png",
      "DOUBLE": "bool.png",
      "BOOLEAN": "bool.png",
      "SHORT": "bool.png",
      "LOCALDATE": "bool.png",
      "UNKNOWN": "bool.png",
      "DURATION": "bool.png"
    }
    let receivedData: any = _.cloneDeep(this.treeNode);
    for (let mapping in enumPathMapping) {
      // @ts-ignore
      receivedData = this.utilitiesService.deepReplaceRecursiveWithKey(mapping, "./assets/img/"  + enumPathMapping[mapping] ,receivedData, "icon");
    }
    treeElement.jstree('destroy');

    console.log(receivedData)
    console.log(this.filterFirstLevel)
    if(!_.isNil(this.filterFirstLevel)) {
      receivedData.children = _.filter(receivedData.children, x =>  {
        console.log("dd", x);
        return this.filterFirstLevel?.includes(x.text);
      });
    }


    treeElement.jstree({
      core : {
        multiple : false,
        data : [receivedData]
      },
        search: {
          show_only_matches: true
    },
      //$.jstree.defaults.search.show_only_matches
      plugins : ['sort', 'search'],
      sort(a: any, b: any) {
        // find the parent node of elements that being sorted
        const parent = this.get_node(a).parent;
        // check if parent node is the path attribute node
        const nodeName = this.get_node(parent).text;
        return this.get_node(a).text > this.get_node(b).text ? 1 : -1;

      }
    });
    var to:any = false;
    searchElement.keyup(function () {
      if (to) {
        clearTimeout(to);
      }
      to = setTimeout(function () {
        var v = searchElement.val();
        treeElement.jstree(true).search(v);
      }, 10);
    });
    treeElement.jstree('refresh');
    let jsTreeObject = treeElement.jstree(true)
    treeElement.on("activate_node.jstree", (event: any, data: any) => {
      const selectedNode: TreeNode = data.node.original;

      const parentNodes = [];
      for (const parentId of data.node.parents) {
        const parentContent = jsTreeObject.get_node(parentId).original;
        if (!_.isNil(parentContent)) {
          parentNodes.push(parentContent);
        }
      }
      const selectedTreeNode: SelectedTreeNode = {
        treeNode: selectedNode,
        parentTreeNodes: parentNodes
      }
      this.changedSelection.emit(selectedTreeNode);
    });
    treeElement.on("ready.jstree", (event: any, data: any)=> {
      setTimeout(()=>{
        this.expandLevel(this.targetLevel);
      },200)

    })
  }

  collapseAll() {
    this.targetLevel = 0;
    this.treeElement.jstree('close_all');
  }

  expandAll() {
    this.targetLevel = this.getHighOfTree()-1
    this.treeElement.jstree('open_all');
  }

  expandLess() {
    this.targetLevel = Math.max(this.targetLevel -1,0);
    this.expandLevel(this.targetLevel);
  }

  expandMore() {
    this.targetLevel = Math.min(this.targetLevel + 1, this.getHighOfTree()-1);
    this.expandLevel(this.targetLevel);
  }

  expandLevel(targetLevel: number) {
    let nodes = this.treeElement.jstree(true).get_json('#', { flat: true });
    for (let currentNode of nodes) {

      // @ts-ignore
      let id:string = currentNode.id;
      let node = this.treeElement.jstree(true).get_node(id);
      let level = node.parents.length;

      if(level>targetLevel){
        this.treeElement.jstree(true).close_node({"id":id});
      } else {
        this.treeElement.jstree(true).open_node({"id":id});
      }
    }
  }

  getHighOfTree() {
    let nodes = this.treeElement.jstree(true).get_json('#', {flat: true});
    let maxLevel = 0;
    for (let currentNode of nodes) {

      // @ts-ignore
      let id: string = currentNode.id;
      let node = this.treeElement.jstree(true).get_node(id);
      let level = node.parents.length;
      maxLevel = Math.max(maxLevel, level);
    }
    return maxLevel;
  }
}
