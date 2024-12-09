import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {ProductionNumbersService} from "../../../service/production-numbers.service";

const ELEMENT_DATA_PRODUCTION_NUMBERS: any[] = [];

@Component({
  selector: 'app-production-numbers-table',
  templateUrl: './production-numbers-table.component.html',
  styleUrls: ['./production-numbers-table.component.scss']
})
export class ProductionNumbersTableComponent implements OnInit, AfterViewInit {

  displayedColumnsProductionNumbers: string[] = ['dateStart', 'dateEnd', 'orderName', 'extArbeitsgang', 'extGutMenge', 'extAusschussMenge', 'extAusschussGrund', 'extFehlzeitGrund', 'details'];
  dataSourceProductionNumbers = new MatTableDataSource<any>(ELEMENT_DATA_PRODUCTION_NUMBERS);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private productionNumbersService: ProductionNumbersService) {
    this.productionNumbersService.newProductionNumber.subscribe(result => {
      this.dataSourceProductionNumbers.data.push(result);
    })
  }

  ngAfterViewInit() {
    this.dataSourceProductionNumbers.paginator = this.paginator;
  }

  ngOnInit(): void {
    for (let i = 0; i < 2; i++) {
      this.dataSourceProductionNumbers.data.push({
        dateStart: "10:12\n02.02.2020",
        dateEnd: '15:47\n02.03.2020',
        orderName: "32636" + i,
        extArbeitsgang: "3110",
        extGutMenge: '57',
        extAusschussMenge: "7",
        extAusschussGrund: "Grund XYZ",
        extFehlzeitGrund: "Grund ABC "
      });
    }
  }

}
