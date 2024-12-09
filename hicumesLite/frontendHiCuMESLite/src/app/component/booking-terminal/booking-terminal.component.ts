import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatStepper} from "@angular/material/stepper";
import {ServerRequestService} from "../../service/server-request.service";
import {UiBuilderService} from "../../service/ui-builder.service";
import {FormlyFormOptions} from "@ngx-formly/core";
import {BookingEntry} from "../../interfaces/bookingEntry";
import * as moment from "moment";
import {MatSnackBar} from "@angular/material/snack-bar";
import {NgxSpinnerService} from "ngx-spinner";
import {User} from "../../interfaces/user";
import * as _ from "lodash";
import {CdkStepper} from "@angular/cdk/stepper";

@Component({
  selector: 'app-booking-terminal',
  templateUrl: './booking-terminal.component.html',
  styleUrls: ['./booking-terminal.component.scss']
})
export class BookingTerminalComponent implements OnInit {

  @Input()
  overViewName: string = 'Meine Buchungen'

  firstFormGroup!: FormGroup;
  secondFormGroup!: FormGroup;


  options: FormlyFormOptions = {};
  tableLayout: any;
  tableLayoutOpen: any;
  tableLayoutOpenAll: any;
  tableModel: any;
  formLayout: any;
  formModel: BookingEntry | undefined;
  openModel: any;
  user: User | undefined;

  private targetId = 'input0';

  personalNumberInput: any
  productionStepInput: any;
  form = new FormGroup({})
  formOpen = new FormGroup({})
  isloading: boolean = false;
  userError: boolean = false;
  agError: boolean =false;

  @ViewChild('stepper') private stepper: MatStepper | undefined;
  openBookingEntriesUser: BookingEntry[] | undefined;
  modelOpenAll: any;



  constructor(private spinner: NgxSpinnerService, private uiBuilder: UiBuilderService, private formBuilder: FormBuilder, private serverRequestService: ServerRequestService,  private snackBar: MatSnackBar) { }

  ngOnInit(): void {

    this.spinner.show();
    this.firstFormGroup = this.formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this.formBuilder.group({
      secondCtrl: ['', Validators.required]
    });

    this.uiBuilder.getDynamicFormLayout("bookingOverview").subscribe((formLayout) => {
      this.tableLayout = formLayout;
    });  this.uiBuilder.getDynamicFormLayout("bookingOverviewUser").subscribe((formLayout) => {
      this.tableLayoutOpen = formLayout;
    });
    this.uiBuilder.getDynamicFormLayout("bookingOverviewUserAll").subscribe((formLayout) => {
      this.tableLayoutOpenAll = formLayout;
    });
    this.uiBuilder.getDynamicFormLayout("bookingForm").subscribe((formLayout) => {
      this.formLayout = formLayout;
    });

    this.serverRequestService.getUser().subscribe(value =>
      {
        console.log(value)
        this.isloading = false;
        this.user = value
        if(value != undefined)
        {
          this.userError = false;
          if(this.stepper?.selectedIndex == 0)
          {
            this.stepper.next()
          }
        }
        else
        {
          this.userError = true;
        }
      }
    )
    this.serverRequestService.getAllBookingEntriesUser().subscribe(value => {
        console.log(value)
        this.isloading = false;
        this.openBookingEntriesUser = value
        this.modelOpenAll = {bookingEntries: this.openBookingEntriesUser};
      }
    )
    this.serverRequestService.getBookingEntries().subscribe(value =>
    {
      console.log(value)
      this.isloading = false;
      this.tableModel = {bookingEntries: value?.slice(-5)};
      let userEntries = value?.filter((entry: BookingEntry) => entry?.user.personalNumber == this.personalNumberInput)
      console.log(userEntries)
      if(userEntries?.length > 0 && userEntries?.slice(-1)[0]?.endDateTime == undefined)
      {
        this.openModel = {bookingEntries: [_.cloneDeep(userEntries.slice(-1)[0])]}
        console.log(this.openModel)
        this.formModel = _.cloneDeep(userEntries.slice(-1)[0])
        this.formModel["endDateTime"] = moment()
        if(this.formModel?.amount == 0) {
          delete this.formModel["amount"]
        }
        console.log(this.formModel)
      }
      if(this.stepper?.selectedIndex == 1)
      {
        this.stepper.next()
      }
    })
  }

  openSnackBar() {
    this.snackBar.open('Buchung abgeschickt!', "", {
      panelClass: "info-snackbar",
      duration: 5000
    });
  }

  setFocus() {
    const targetElem = document.getElementById(this.targetId);
    targetElem?.focus();
  }

  setTargetId(event: any) {
    console.log(event)
    if(event.selectedIndex < event.previouslySelectedIndex)
    {
    }
    this.targetId = `input${event.selectedIndex}`;
  }

  onEnterPersonalNumber(stepper: MatStepper) {
    this.serverRequestService.loadUser(this.personalNumberInput);
    this.serverRequestService.loadBookingEntriesUser(this.personalNumberInput);
    this.isloading = true;

  }

  onEnterProductionStepNumber(stepper: MatStepper) {
    if(this.productionStepInput.includes("-"))
    {
      this.productionStepInput = this.productionStepInput.replace(/-/g, "")
    }
    this.productionStepInput = this.productionStepInput.replace(/\s+/g, "")

    if(this.productionStepInput != undefined && this.productionStepInput.length == 9)
    {
      this.serverRequestService.loadBookingEntries(this.productionStepInput);

      this.isloading = true;

    }
    else
    {
      this.agError = true;
    }


  }


  createNewBookingEntry(stepper: MatStepper) {
    if(this.formModel == undefined) {
      this.formModel =
        {
          externalId: this.productionStepInput,
          amount: undefined,
          user: {
            personalNumber: this.personalNumberInput
          },
          status: "CREATED",
          startDateTime: moment()
        }
    }
    else
    {

    }
    stepper.next()
  }

  sendEntry(stepper: MatStepper) {
    if(this.formModel != undefined) {
      delete this.formModel["startDateTime"]
      delete this.formModel["endDateTime"]
      if(this.formModel["duration"] != null && this.formModel["duration"] != 0)
      {
        delete this.formModel["breakDuration"]
      }
      console.log(this.formModel)
      this.serverRequestService.postBookingEntry(this.formModel, this.formModel?.id)
      this.openSnackBar()
      stepper.next()
    }
  }

  reset(stepper: MatStepper) {
    this.serverRequestService.clearBookingEntries()
    this.serverRequestService.clearUser()
    this.serverRequestService.clearBookingEntriesUser()
    this.formModel = undefined;
    this.tableModel = undefined
    this.modelOpenAll = undefined
    this.openModel = undefined
    this.personalNumberInput = undefined
    this.productionStepInput = undefined
    this.user = undefined
    this.userError = false
    this.agError = false
    console.log(this.form)
    this.form?.reset()
    console.log(this.formModel)
    console.log(this.tableModel)
    stepper.reset()
  }

  nextStep(stepper: MatStepper) {
    stepper.next()
  }

  resetInputError() {
    this.userError = false
    this.agError = false
  }

  resetProductionStep(stepper: MatStepper) {
    this.tableModel = undefined
    this.openModel = undefined
    this.formModel = undefined
    this.agError = false
  }
}
