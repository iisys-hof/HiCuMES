import {Component, OnInit} from '@angular/core';
import {UiBuilderService} from "../../service/ui-builder.service";
import {ServerRequestService} from "../../service/server-request.service";
import {Observable} from "rxjs";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {ActivatedRoute, Params} from "@angular/router";
import {ProductRelationship} from "../../interfaces/product-relationship";
import * as _ from 'lodash';
import {Machine} from "../../interfaces/machine";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss']
})
export class OrderDetailComponent implements OnInit {
  machineOccupation$: Observable<CamundaMachineOccupation | undefined> | undefined;
  machineOccupations: CamundaMachineOccupation[] = [];
  productRelationships$: Observable<ProductRelationship[]> | undefined;

  public machines$: Observable<Machine[]> | undefined;

  constructor(private uiBuilder: UiBuilderService, private serverRequestService: ServerRequestService, private activatedRoute: ActivatedRoute, private spinner: NgxSpinnerService) {

    this.spinner.show();

  }

  ngOnInit(): void {
    console.log("init detail")

    this.machines$ = this.serverRequestService.getAllMachines();
    this.machines$.subscribe();

    let id = this.activatedRoute.snapshot.paramMap.get('id');
    if(id != null) {
      console.log("Request Data from order-detail")
      this.serverRequestService.loadMachineOccupationsByIdWithProductRelationship(id);
      this.serverRequestService.removeProductRelationships();
      this.machineOccupation$ = this.serverRequestService.getMachineOccupationsFilterById();
      this.productRelationships$ = this.serverRequestService.getProductRelationships();
      this.machineOccupation$.subscribe(value => {
        this.machineOccupations = []
        if(value?.machineOccupation.subMachineOccupations)
        {
          value.machineOccupation.subMachineOccupations.map(subMachineOccupation => {
            var subCamundaMachineOccupation = _.cloneDeep(value)
            subMachineOccupation.status = value.machineOccupation.status
            subCamundaMachineOccupation.machineOccupation = subMachineOccupation;
            this.machineOccupations.push(subCamundaMachineOccupation)
          })
        }
        else
        {
          if (value) {
            this.machineOccupations.push(value)
          }
        }
      })
    }
     else {
      //TODO hinweis anzeigen
    }
  }

  tabChange($event: any) {
    console.log($event)
  }
}
