import {Component} from '@angular/core';
import {Observable} from "rxjs";
import {Class, FieldExtension, MemberFieldExtension} from "./interfaces/field-extension";
import {FieldExtensionServiceService} from "./services/field-extension-service.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {ModalCreateFieldsComponent} from "./components/modal-create-fields/modal-create-fields.component";
import {FieldValidationService} from "./services/field-validation.service";
import * as _ from 'lodash';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  fieldExtension$: Observable<FieldExtension>;
  selectedClass?: Class;
  classesToExtend: FieldExtension;
  originalClass: FieldExtension;

  selectedClassToExtend?: Class;
  isLoading$: Observable<boolean>;
  state$: Observable<string>;

  constructor(private fieldExtensionService: FieldExtensionServiceService, private _snackBar: MatSnackBar,private dialog: MatDialog, private fieldValidationService: FieldValidationService) {
    this.fieldExtension$ = fieldExtensionService.getObservable();
    this.isLoading$ = fieldExtensionService.getIsLoadingObservable();
    this.state$ = fieldExtensionService.getStateObservable();
    this.originalClass = {
      classes:[]
    }
    this.classesToExtend = {
      classes:[]
    }
    this.fieldExtension$.subscribe(fieldExtension => {
      this.originalClass = _.cloneDeep(fieldExtension);

      var newClasses: Class[] = [];
      for (const selectedClass of fieldExtension.classes) {
        var members: MemberFieldExtension[] = [];
        for (const selectedMember of selectedClass.members) {
          if (selectedMember.customerField) {
            members.push(selectedMember);
          }
        }
        newClasses.push({
          id: selectedClass.id,
          name: selectedClass.name,
          members: members
        })
      }
      let classesToExtend = {
        classes:newClasses
      }
      this.classesToExtend = classesToExtend;
      this.selectedClass = fieldExtension.classes[0];
      this.selectedClassToExtend = classesToExtend.classes[0];
    });

    this.state$.subscribe(stateString => {
      this.openSnackBar(stateString);
    })
  }

  ngOnInit(): void {
    this.fieldExtensionService.update();
  }

  changedSelection(c: Class) {
    this.selectedClass = c;
    for (var searchClass of this.classesToExtend.classes) {
      if (searchClass.id === c.id ) {
        this.selectedClassToExtend = searchClass;
      }
    }
  }

  exportSchema() {
    let validatedClasses = this.fieldValidationService.validate(this.classesToExtend);
  console.log(validatedClasses);
    const dialogRef = this.dialog.open(ModalCreateFieldsComponent, {data:
      validatedClasses
        });

    dialogRef.afterClosed().subscribe(result => {
      if(result === true) {
        this.fieldExtensionService.sendNewFields(validatedClasses);
      }
    });


  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "OK",{duration: 5000, panelClass:'hc-snackbar'});
  }
}
