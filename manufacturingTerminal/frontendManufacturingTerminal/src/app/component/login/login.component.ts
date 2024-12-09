import {Component, OnChanges, OnInit} from '@angular/core';
import {KeycloakProfile} from 'keycloak-js';
import {KeycloakService} from 'keycloak-angular';
import {environment} from '../../../environments/environment';
import {Router} from '@angular/router';
import {ServerRequestService} from "../../service/server-request.service";
import {OverheadCosts} from "../../interfaces/overhead-costs";
import {CamundaMachineOccupation} from "../../interfaces/camunda-machine-occupation";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ComponentType} from "@angular/cdk/overlay";
import {ListOpenModalComponent} from "../modals/list-open-modal/list-open-modal.component";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public isLoggedIn = false;
  public userProfile: KeycloakProfile | null = null;
  private openData: any= undefined
  private isOpen: boolean = false


  constructor(private readonly keycloak: KeycloakService, private router: Router,  private serverRequestService: ServerRequestService, private dialog: MatDialog) {

  }

  openModal(modal: ComponentType<any>, callback: (result: any) => void, config: MatDialogConfig = {}) {
    if (this.dialog.openDialogs.length > 0) {
      return;
    }
    const dialogRef = this.dialog.open(modal, config);

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        callback(result);
      }
    });
  }

  openListOpenModal(data: any) {
    this.openModal(ListOpenModalComponent, result => {
      console.log(result)
      if(result?.forceLogout)
      {
        sessionStorage.clear()
        this.keycloak.logout().then();
      }

    });
  }

  public async ngOnInit(): Promise<void> {
    this.isLoggedIn = await this.keycloak.isLoggedIn();

    if (this.isLoggedIn) {
      this.userProfile = await this.keycloak.loadUserProfile();
      this.serverRequestService.loadOpenOverheadCosts()
      this.serverRequestService.loadOpenMachineOccupations()

      this.serverRequestService.getAllOpen().subscribe(value =>
        {
          //console.log(value)
          this.openData = value;
          if((this.openData.overheadCosts && this.openData.overheadCosts?.length > 0) || (this.openData.machineOccupations && this.openData.machineOccupations?.length > 0))
          {
            this.isOpen = true
          }
          else if ((this.openData.overheadCosts && this.openData.overheadCosts?.length <= 0) && (this.openData.machineOccupations && this.openData.machineOccupations?.length <= 0))
          {
            this.isOpen = false
          }
        }
      )
    }
  }

  public login(): void {
    this.keycloak.login(
      {
        redirectUri:
          window.location.origin + environment.baseref + this.router.url,
      }
    ).then();

  }

  public logout(): void {
    if(this.isOpen)
    {
      this.openListOpenModal(this.openData)
    }
    else
    {
      sessionStorage.clear()
      this.keycloak.logout().then();
    }


  }
}
