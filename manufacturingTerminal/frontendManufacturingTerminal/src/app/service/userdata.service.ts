import {Injectable, OnInit} from '@angular/core';
import {Observable, ReplaySubject, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";
import {ServerRequestService} from "./server-request.service";

@Injectable({
  providedIn: 'root'
})
export class UserdataService implements OnInit {

  public userData$: Subject<Userdata>;
  private userData: any;
  private lastUser: any;

  constructor(private http: HttpClient, private router: Router) {
    this.userData$ = new ReplaySubject<Userdata>();
    this.updateUserdata();
  }

  ngOnInit(): void {
  }

  public updateUserdata(): void {
    this.userData$.next(undefined);
    this.requestUserdata().subscribe((data: any) => {
      let user = localStorage.getItem('lastUser')
      //console.log(user)
      if (user !== undefined && user !== null && user !== "undefined") {
        this.lastUser = JSON.parse(user)
      }
      else {
        localStorage.setItem('lastUser', JSON.stringify(data));
      }
      if(this.lastUser.id != data.id)
      {
        //console.log(this.lastUser)
        //console.log("Different User: ", this.lastUser, data)
        localStorage.setItem('lastUser', JSON.stringify(data));
        this.router.navigate(["/"])
      }
      //console.log(data)
      this.userData = data

      if(data.attributes?.JumpToMain && data.attributes?.JumpToMain[0] === "true")
      {
        this.userData.attributes.JumpToMain = true;
      }
      else
      {
        this.userData.attributes.JumpToMain = false;
      }

      if(data.attributes?.JumpToMask && data.attributes?.JumpToMask[0] === "false")
      {
        this.userData.attributes.JumpToMask = false;
      }
      else
      {
        this.userData.attributes.JumpToMask = true;
      }

      if(data.attributes?.DefaultDepartment && data.attributes?.DefaultDepartment[0] != undefined)
      {
        this.userData.attributes.DefaultDepartment = data.attributes?.DefaultDepartment[0];
      }
      else
      {
        this.userData.attributes.DefaultDepartment = "";
      }

      if(data.attributes?.DefaultMachineType && data.attributes?.DefaultMachineType[0] != undefined)
      {
        this.userData.attributes.DefaultMachineType = data.attributes?.DefaultMachineType[0];
      }
      else
      {
        this.userData.attributes.DefaultMachineType = "";
      }

      localStorage.setItem('defaultDepartment', this.userData.attributes.DefaultDepartment);
      localStorage.setItem('defaultMachineType', this.userData.attributes.DefaultMachineType);
      console.log(this.userData)
      this.userData$.next(this.userData);
    });
  }

  public changeUserdata(userData: Userdata): void {

    this.userData.attributes = userData.attributes;
    this.postUserdata(this.userData);
  }

  getUserdata(): Observable<Userdata> {
    return this.userData$.asObservable();
  }

  private requestUserdata(): Observable<Userdata> {
    return this.http.get<Userdata>(environment.keycloakUrl + 'realms/' + environment.keycloakRealm + '/account');
  }

  private postUserdata(userData: Userdata): any {
    const headers = {'content-type': 'application/json'};
    const body = JSON.stringify(userData);
    //console.log(body);
    return this.http.post(environment.keycloakUrl + 'realms/' + environment.keycloakRealm + '/account', body, {headers}).subscribe(
      () => {
      this.updateUserdata();
    });
  }
}

export interface Userdata {
  attributes: {
    JumpToMain: boolean;
    JumpToMask: boolean;
    DefaultDepartment: string,
    DefaultMachineType: string
  };
}

