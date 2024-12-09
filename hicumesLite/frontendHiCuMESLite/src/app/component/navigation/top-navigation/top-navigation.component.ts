import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ServerRequestService} from "../../../service/server-request.service";
import {combineLatest, Observable} from "rxjs";
import {SideNavService} from "../../../service/side-nav.service";
import {filter, map} from "rxjs/operators";
import {NavigationEnd, Router} from "@angular/router";
import {MatOption} from "@angular/material/core";
import {MatSelect} from "@angular/material/select";

@Component({
  selector: 'app-top-navigation',
  templateUrl: './top-navigation.component.html',
  styleUrls: ['./top-navigation.component.scss']

})
export class TopNavigationComponent implements OnInit {

  navBarOpen: boolean = false;

  task: string = "";
  title: string = ""

  allSelectedDepartment: boolean= false;
  @ViewChild('selectDepartment') selectDepartment: MatSelect | undefined;
  allSelectedMachineType: boolean= false;
  @ViewChild('selectMachineType') selectMachineType: MatSelect | undefined;

  constructor(private serverRequestService: ServerRequestService, private sideNavService: SideNavService, public router: Router) {

    router.events.pipe(
      // @ts-ignore
      filter(event => event instanceof NavigationEnd)).subscribe((event: NavigationEnd) => {

      this.task = event.url.substr(1);
      if (this.task.includes(';')) {
        this.task = this.task.substr(0, this.task.indexOf(';'))
      }
    });

  }

  ngOnInit(): void {
    this.sideNavService.sideNavClosedSubject.subscribe((x) => {
      if (x !== "start")
        this.navBarOpen = false;
    });
    this.sideNavService.sideNavOpenedSubject.subscribe((x) => {
      if (x !== "start")
        this.navBarOpen = true;
    });
    this.sideNavService.sideNavShouldCloseSubject.subscribe((x) => {
      if (x !== "start")
        this.navBarOpen = false;
    });
  }

  toggleRightSidenav() {
    this.navBarOpen = !this.navBarOpen;
    this.sideNavService.toggle();
  }

  reload() {
    location.reload()
  }
}
