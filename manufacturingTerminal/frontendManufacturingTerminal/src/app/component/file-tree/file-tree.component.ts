import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {UtilitiesService} from "../../service/utilities.service";
import {WindowSizeService} from "../../service/window-size.service";

@Component({
  selector: 'app-file-tree',
  templateUrl: './file-tree.component.html',
  styleUrls: ['./file-tree.component.scss']
})
export class FileTreeComponent implements OnChanges {

  sourcetreeId = 'sourcetreeId' + Math.floor(Math.random() * 1000000);
  // @Input()
  // treeNode?: TreeNode;

  //@Output()
  //changedSelection = new EventEmitter<SelectedTreeNode>();
  @Input()
  componentTitle?: string;

  copyOldState: string = "";
  cardHeight: string = "500px";

  constructor(private uttilitiesService: UtilitiesService, public windowSizeService: WindowSizeService) {
    windowSizeService.getHeightObservable().subscribe(height => {
      this.cardHeight = height - 190 + 'px';
    })
  }

  ngOnChanges(changes: SimpleChanges): void {
    /* if (_.isNil(this.treeNode)) {
       return;
     }
     var copyNewState = JSON.stringify(this.treeNode);*/
    /* if (copyNewState == this.copyOldState) {
       return;
     }
     this.copyOldState = copyNewState;*/
    let enumPathMapping = {
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
    // var recievedData: any = _.cloneDeep(this.treeNode);
    for (let mapping in enumPathMapping) {
      // @ts-ignore
      recievedData = this.uttilitiesService.deepReplaceRecursiveWithKey(mapping, "./assets/img/" + enumPathMapping[mapping], recievedData, "icon");
    }
    // @ts-ignore
    var treeElement: any = $('#' + this.sourcetreeId);
    var searchElement: any = $('#search' + this.sourcetreeId);
    treeElement.jstree('destroy');

    treeElement.jstree({
      core: {
        multiple: false,
        // data : [recievedData]
      },
      search: {
        show_only_matches: true
      },
      //$.jstree.defaults.search.show_only_matches
      plugins: ['sort', 'search'],
      sort(a: any, b: any) {
        // find the parent node of elements that being sorted
        const parent = this.get_node(a).parent;
        // check if parent node is the path attribute node
        const nodeName = this.get_node(parent).text;
        return this.get_node(a).text > this.get_node(b).text ? 1 : -1;

      }
    });
    var to: any = false;
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
    treeElement.jstree('open_all');
    let jsTreeObject = treeElement.jstree(true)
    /*    treeElement.on("activate_node.jstree", (event: any, data: any) => {
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
        });*/
  }

}
