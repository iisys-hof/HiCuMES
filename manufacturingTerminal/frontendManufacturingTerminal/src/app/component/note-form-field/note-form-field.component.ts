import {Component, Input, OnInit} from '@angular/core';
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {ServerRequestService} from "../../service/server-request.service";
import {FormControl, FormGroup} from "@angular/forms";
import {MachineOccupation} from "../../interfaces/machine-occupation";

@Component({
  selector: 'app-note-form-field',
  templateUrl: './note-form-field.component.html',
  styleUrls: ['./note-form-field.component.scss']
})
export class NoteFormFieldComponent implements OnInit {

  @Input()
  machineOccupation!: MachineOccupation;
  noteString: string = "";
  formControl: FormControl = new FormControl();
  form: any;

  constructor(private serverRequestService: ServerRequestService) { }

  ngOnInit(): void {
  }

  addNoteToMachineOccupation() {
    console.log(this.machineOccupation)
    if(this.noteString != "")
    {
      this.serverRequestService.addNoteToMachineOccupation(this.machineOccupation, this.noteString)
      this.formControl.reset()
    }
  }
}
