import {Component, OnInit} from '@angular/core';
import {OwlDateTimeIntl} from "@danielmoncada/angular-datetime-picker";
import {MatDialog} from "@angular/material/dialog";
import {ProductionNumbersAddDialogComponent} from "../unused-production-numbers-add-dialog/production-numbers-add-dialog.component";
import {ProductionNumbersService} from "../../../service/production-numbers.service";
import {FormGroup} from "@angular/forms";
import {FormlyFormOptions} from "@ngx-formly/core";
import {UiBuilderService} from "../../../service/ui-builder.service";

export interface DataElement {
  dateStart: string;
  dateEnd: string;
  note: string;
  orderName: string;
  extArbeitsgang: string;
  extGutMenge: string;
  extAusschussMenge: string;
  extAusschussGrund: string;
  extFehlzeitGrund: string;
}



@Component({
  selector: 'app-production-numbers-fields',
  templateUrl: './production-numbers-fields.component.html',
  styleUrls: ['./production-numbers-fields.component.scss']
})
export class ProductionNumbersFieldsComponent implements OnInit {
//TODO: Mit echten Datenschema tauschen
  dataElement: DataElement = {
    dateStart: "",
    dateEnd: '',
    note: "",
    orderName: "",
    extArbeitsgang: "",
    extGutMenge: '',
    extAusschussMenge: "",
    extAusschussGrund: "",
    extFehlzeitGrund: ""
  };
  dataElementNames = {
    dateStart: "Start",
    dateEnd: "Ende",
    note: "Bemerkung",
    extGutMenge: "Gut-Menge",
    extAusschussMenge: "Ausschuss-Menge",
    extAusschussGrund: "Ausschuss-Grund",
    extFehlzeitGrund: "Fehlzeit-Grund"
  };

  formForm = new FormGroup({});
  model: any;
  options: FormlyFormOptions = {};
  formLayout: any;

  constructor(private owlDateTimeIntl: OwlDateTimeIntl, public dialog: MatDialog, private productionNumbersService: ProductionNumbersService, private uiBuilder: UiBuilderService,) {
    this.owlDateTimeIntl.setBtnLabel = 'Übernehmen';
    this.owlDateTimeIntl.cancelBtnLabel = 'Abbrechen';


    uiBuilder.getTestDataSource()?.subscribe(x => {
      this.model = x;
    });

    this.uiBuilder.getDynamicFormLayout("productionNumberForms").subscribe((formLayout) => {
      this.formLayout = formLayout;
      console.log("Dynamic Form Layout", this.formLayout);
    });
  }

  onSubmit(model: any) {
    console.log(model);
  }

  submit() {
    alert(JSON.stringify(this.model));
  }
  openDialog() {
    console.log(this.dataElement);
    const dialogRef = this.dialog.open(ProductionNumbersAddDialogComponent, {
      data: {
        "elements": this.dataElement, "elementNames": this.dataElementNames
      },
      minWidth: "550px"
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed. Do something with it.', result);
      this.productionNumbersService.addProductionNumberEntry(result.elements);

    });
  }

  ngOnInit(): void {
  }

}

