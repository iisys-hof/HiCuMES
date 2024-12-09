import {Component, OnInit} from '@angular/core';
import {MappingRequestService} from "../../service/mapping-request.service";
import {Observable} from "rxjs";
import {FormsDatasource} from "../../interfaces/forms-datasource";
import {TreeNode} from "../../interfaces/mapping-response";
import {MatDialogRef} from "@angular/material/dialog";
import {FormsDatasourceService} from "../../service/forms-datasource.service";
import {SelectedTreeNode} from "../../interfaces/selected-tree-node";

@Component({
  selector: 'app-modal-load-mapping',
  templateUrl: './modal-load-mapping.component.html',
  styleUrls: ['./modal-load-mapping.component.scss']
})
export class ModalLoadMappingComponent implements OnInit {

  constructor(public mappingRequestService: MappingRequestService) { }

  ngOnInit(): void {
  }
}
