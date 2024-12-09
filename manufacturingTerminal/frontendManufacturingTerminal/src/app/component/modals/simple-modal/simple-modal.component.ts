import {Component, Inject, Input, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {JsonPipe} from "@angular/common";

@Component({
  selector: 'app-simple-modal',
  templateUrl: './simple-modal.component.html',
  styleUrls: ['./simple-modal.component.scss']
})
export class SimpleModalComponent implements OnInit {

  @ViewChild('openModalView') openModalView!: TemplateRef<any>

  @Input()
  data: any
  ngOnInit(): void {
  }
  constructor(private dialog: MatDialog) {
  }

  openModal() {
    let dialogRef = this.dialog.open(this.openModalView);
    dialogRef.afterClosed().subscribe(result => {})
  }

  getField(): any {
    return this.data
  }


}
