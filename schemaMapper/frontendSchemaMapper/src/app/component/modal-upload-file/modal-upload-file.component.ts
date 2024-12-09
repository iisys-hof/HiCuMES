import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-modal-upload-file',
  templateUrl: './modal-upload-file.component.html',
  styleUrls: ['./modal-upload-file.component.scss']
})
export class ModalUploadFileComponent implements OnInit {

  fileData: any

  constructor(public matDialogRef: MatDialogRef<ModalUploadFileComponent>) { }

  ngOnInit(): void {
  }

  loadMapping()
  {
    this.matDialogRef.close(this.fileData);
  }

  changeListener($event: { target: any; }) : void {
    this.readFile($event.target);
  }

  readFile(inputValue: any) : void {
    var file: File = inputValue.files[0];
    var fileReader: FileReader = new FileReader();

    fileReader.onloadend = (evt) => {
      if (typeof fileReader.result === "string") {
        this.fileData = JSON.parse(fileReader.result)
        console.log(this.fileData)
      }
    }

    fileReader.readAsText(file);
  }
}
