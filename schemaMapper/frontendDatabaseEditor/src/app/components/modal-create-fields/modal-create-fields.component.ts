import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {FieldExtension} from "../../interfaces/field-extension";

@Component({
  selector: 'app-modal-create-fields',
  templateUrl: './modal-create-fields.component.html',
  styleUrls: ['./modal-create-fields.component.scss']
})
export class ModalCreateFieldsComponent implements OnInit {



  constructor(@Inject(MAT_DIALOG_DATA) public data: FieldExtension) {

  }

  ngOnInit(): void {
  }

}
