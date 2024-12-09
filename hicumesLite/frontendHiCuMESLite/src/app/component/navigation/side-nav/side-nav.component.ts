import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSidenav} from "@angular/material/sidenav";
import {SideNavService} from "../../../service/side-nav.service";
import {NavigationService} from "../../../service/navigation.service";
import {Params, Router, UrlTree} from "@angular/router";
import {Subject} from "rxjs";
import build from "../../../../build";

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.scss']
})

export class SideNavComponent implements OnInit {

  public readonly build = build;
  @ViewChild('sidenav')
  public sidenav!: MatSidenav;
  scrollEventSubject: Subject<Event> = new Subject<Event>()

  public publicListEntries = [
    {"name": "Auftragsübersicht", "icon": "list", "routerLink": "order-overview", "params": {}},
    {"name": "Meine Auftragsübersicht", "icon": "patient_list", "routerLink": "user-order-overview", "params": {}},
    {"name": "Gemeinkosten", "icon": "receipt_long", "routerLink": "overhead-costs", "params": {}},
    {"name": "Meine Buchungen", "icon": "data_check", "routerLink": "user-booking-overview", "params": {}},
    {"name": "Einstellungen", "icon": "settings", "routerLink": "settings", "params": {}},
    /*  {"name": "Auftragsdetails", "icon": "list", "routerLink": "order-detail"},
      {"name": "Maschineneinstellung", "icon": "tune", "routerLink": "/dynamic-fields", "params":  {"form": ["setParameterTaskForm"]}},
      {"name": "Qualitätskontrolle", "icon": "rule", "routerLink": "/dynamic-fields", "params": {"form": ["checkQualityTaskForm"]}},
      {"name": "Nacharbeit", "icon": "bug_report", "routerLink": "/dynamic-fields", "params": {"form": ["reworkTaskForm"]}},

      {"name": "Rückmeldung", "icon": "assignment_return", "routerLink": "dynamic-fields", "params": {"form": ["productionNumberForms", "productionNumberTable"]}},*/
    // {"name":"Rückmeldung", "icon":"assignment_return_outline", "routerLink": "ConfirmProductionTask"}
  ]

  public adminListEntries = [/*{"name": "Schemazuordnung", "icon": "shuffle", "routerLink": "/a"},
    {"name": "Datenbank-Editor", "icon": "drive_file_rename_outline", "routerLink": "/a"},*/
    {"name": "Aufträge ändern", "icon": "rule_settings", "routerLink": "change-status", "params": {}},
    {"name": "Buchungsübersicht", "icon": "troubleshoot", "routerLink": "booking-overview", "params": {}}
  ]
  //public selectedMachineName: string = "-";

  constructor(private sideNavService: SideNavService, private navigationService: NavigationService, private router: Router) {

    /*this.selectionStateService.getSelectedMachineType().subscribe(selectedMachine => {
      this.selectedMachineName = selectedMachine ? selectedMachine.name : '-';
    })*/
  }

  ngOnInit(): void {
    this.sideNavService.sideNavToggleSubject.subscribe((x) => {
      if (x !== "start")
        this.sidenav.toggle();
    });

    this.sideNavService.sideNavToggleSubject.subscribe((x) => {
      console.log(x);
    });
  }


  emitScrollEvent($event: Event) {
    this.scrollEventSubject.next($event)
  }

  navBarNavigate(entry: any) {
      this.navBarShouldClose().then( x => {
        this.router.navigate(entry?.params ? [entry.routerLink, entry?.params] : entry.routerLink);
      });
  }
  navBarShouldClose() {
    this.sideNavService.sideNavShouldCloseSubject.next("");
    return this.sidenav.close();
  }
  navBarClosed($event: void) {
    this.sideNavService.sideNavClosedSubject.next("");
  }
  navBarOpened($event: void) {
    this.sideNavService.sideNavOpenedSubject.next("");
  }

}
