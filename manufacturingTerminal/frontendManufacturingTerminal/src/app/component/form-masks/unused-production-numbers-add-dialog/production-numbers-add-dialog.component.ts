import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
//import {DataElement} from "../production-numbers-fields/production-numbers-fields.component";


export interface DialogData {
  elements: any;
  elementNames: any;

}

@Component({
  selector: 'app-production-numbers-add-dialog',
  templateUrl: './production-numbers-add-dialog.component.html',
  styleUrls: ['./production-numbers-add-dialog.component.scss']
})
export class ProductionNumbersAddDialogComponent implements OnInit {


  constructor(public dialogRef: MatDialogRef<ProductionNumbersAddDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogData) {
    console.log(data);
  }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
