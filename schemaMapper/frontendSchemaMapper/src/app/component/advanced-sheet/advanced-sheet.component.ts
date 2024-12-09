import {Component, Inject, OnInit} from '@angular/core';
import {MAT_BOTTOM_SHEET_DATA, MatBottomSheetRef} from "@angular/material/bottom-sheet";
import {MappingResponse} from "../../interfaces/mapping-response";

@Component({
  selector: 'app-advanced-sheet',
  templateUrl: './advanced-sheet.component.html',
  styleUrls: ['./advanced-sheet.component.scss']
})
export class AdvancedSheetComponent implements OnInit {

  constructor(private _bottomSheetRef: MatBottomSheetRef<AdvancedSheetComponent>, @Inject(MAT_BOTTOM_SHEET_DATA) public data: MappingResponse) { }

  ngOnInit(): void {
  }

  hideSheet(event: MouseEvent): void {
    this._bottomSheetRef.dismiss();
    event.preventDefault();
  }
}
