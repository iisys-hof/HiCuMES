import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {KeycloakAuthGuard, KeycloakEventType, KeycloakService} from 'keycloak-angular';
import {environment} from "../../environments/environment";
import {now} from "moment";
import * as moment from "moment";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {
  constructor(
    protected readonly router: Router,
    protected readonly keycloak: KeycloakService
  ) {
    super(router, keycloak);
    keycloak.keycloakEvents$.subscribe({
      next: (e) =>
      {
        //console.log(e)
        if(e.type == KeycloakEventType.OnAuthError)
        {
          //keycloak.login();
          //console.log("KC: Auth error", moment(now()).format("yyyy-MM-DD_HH:mm:SS"))
        }
        if(e.type == KeycloakEventType.OnAuthLogout)
        {
          //keycloak.login();
          //console.log("KC: Session max reached - redirecting user", moment(now()).format("yyyy-MM-DD_HH:mm:SS"))
          keycloak.login(
            {
              redirectUri:
                window.location.origin + environment.baseref + this.router.url,
            }
          ).then();
        }
        if(e.type == KeycloakEventType.OnAuthRefreshError)
        {
          //keycloak.login();
          //console.log("KC: Refresh error", moment(now()).format("yyyy-MM-DD_HH:mm:SS"))
        }
        if(e.type == KeycloakEventType.OnAuthRefreshSuccess)
        {
          //keycloak.login();
          //console.log("KC: Token refreshed successfully", moment(now()).format("yyyy-MM-DD_HH:mm:SS"))
        }
        if(e.type == KeycloakEventType.OnTokenExpired)
        {
          //keycloak.login();
          //console.log("KC: Token expired - refreshing", moment(now()).format("yyyy-MM-DD_HH:mm:SS"))
          keycloak.updateToken(20);
        }
      }
    })
  }

  public getUsername()
  {
      return this.keycloak.getUsername();
  }

  public async isLoggedIn(): Promise<boolean> {
    return this.keycloak.isLoggedIn()
  }

  public async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) {
    // Force the user to log in if currently unauthenticated.
    if (!this.authenticated) {
      await this.keycloak.login({
        redirectUri: window.location.origin + state.url
      });
    }

    // Get the roles required from the route.
    const requiredRoles = route.data.role;


    //console.log(this.roles, requiredRoles)
    //console.log(requiredRoles.map((role: any) => this.roles.includes(role)).includes(true));
    // Allow the user to to proceed if no additional roles are required to access the route.
    if (!(requiredRoles instanceof Array) || requiredRoles.length === 0) {
      return true;
    }

    if (!requiredRoles.map((role: any) => this.roles.includes(role)).includes(true)) {
      this.router.navigate(['/']);
    }

    // Allow the user to proceed if any of the role is present.
    return requiredRoles.map((role: any) => this.roles.includes(role)).includes(true)
  }
}
