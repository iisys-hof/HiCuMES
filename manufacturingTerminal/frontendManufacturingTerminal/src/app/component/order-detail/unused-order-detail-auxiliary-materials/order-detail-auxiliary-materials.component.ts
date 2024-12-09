import {Component, Input, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {CamundaMachineOccupation} from "../../../interfaces/camunda-machine-occupation";
import {ServerRequestService} from "../../../service/server-request.service";
import {ProductRelationship} from "../../../interfaces/product-relationship";


const ELEMENT_DATA_AUXILIARY_MATERIALS: any [] = [
  {
    extMAT: "-",
    extArtikel: '718439',
    extSuchwort: "R99-2",
    extBezeichnungZeichnung: "R99-2",
    extLager: 'KSKR0HLP',
    extMenge: '99g'
  },
  {
    extMAT: "-",
    extArtikel: '860066',
    extSuchwort: "P022X16",
    extBezeichnungZeichnung: "Pappflansch 22 x 16 Neutral",
    extLager: 'ZUKAUFLP',
    extMenge: '22 Stk.'
  }
];


@Component({
  selector: 'app-order-detail-auxiliary-materials',
  templateUrl: './order-detail-auxiliary-materials.component.html',
  styleUrls: ['./order-detail-auxiliary-materials.component.scss']
})
export class OrderDetailAuxiliaryMaterialsComponent implements OnInit {

  @Input() machineOccupation: CamundaMachineOccupation | null | undefined;

  productRelationships$: Observable<ProductRelationship[]> | undefined;

  displayedColumnsAuxiliaryMaterials: string[] = ['extMAT', 'extArtikel', 'extSuchwort', 'extBezeichnungZeichnung', 'extLager', 'extMenge'];
  dataSourceAuxiliaryMaterials = ELEMENT_DATA_AUXILIARY_MATERIALS;

  constructor(private serverRequestService: ServerRequestService) {
  }

  ngOnInit(): void {
    console.log(this.machineOccupation)
    this.serverRequestService.loadProductRelationshipsByProductId(this.machineOccupation?.machineOccupation.productionOrder.product.id, this.machineOccupation?.machineOccupation.productionOrder.externalId);
    this.productRelationships$ = this.serverRequestService.getProductRelationships()
      this.productRelationships$.subscribe((productRelationships) =>
    {
      this.dataSourceAuxiliaryMaterials = [];
      productRelationships.forEach(relationship => {
        if(this.machineOccupation != undefined)
        {
          this.dataSourceAuxiliaryMaterials.push(
            {
              extMAT: "-",
              extArtikel: relationship.part.externalId,
              extSuchwort: relationship.part.name,
              extBezeichnungZeichnung: relationship.part.name,
              extLager: 'ZUKAUFLP',
              extMenge: `${relationship.measurement.amount * this.machineOccupation?.machineOccupation.productionOrder.measurement.amount} ${relationship.measurement.unitString}`
            }
          )
          console.log(this.dataSourceAuxiliaryMaterials)
        }
      })
    })
  }

}
