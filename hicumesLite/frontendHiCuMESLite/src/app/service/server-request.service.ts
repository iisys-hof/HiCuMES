import {Injectable} from '@angular/core';
import {combineLatest, merge, ReplaySubject, Subject} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {map} from 'rxjs/operators'
import * as _ from 'lodash';
import {Router} from "@angular/router";
import {BookingEntry} from "../interfaces/bookingEntry";
import {User} from "../interfaces/user";

@Injectable({
  providedIn: 'root'
})
export class ServerRequestService {

  urlEndpoint = '/hicumesLite/hicumesLite';

  urlBookingEntries = environment.baseUrl + this.urlEndpoint + '/bookingentries/list';
  urlBookingEntriesUserOpen = environment.baseUrl + this.urlEndpoint + '/bookingentries/list/user';
  urlSingleBookingEntry = environment.baseUrl + this.urlEndpoint + '/bookingentries';
  urlUser = environment.baseUrl + this.urlEndpoint + '/user'

  private bookingEntries$: ReplaySubject<BookingEntry[]>;
  private allBookingEntriesUser$: ReplaySubject<BookingEntry[]>;
  private user$: ReplaySubject<User>;

  private errors$: Subject<string>;

  startBooking: string = "";
  endBooking: string = "";
  startUserBooking: string = "";
  endUserBooking: string = "";

  constructor(private http: HttpClient, private router: Router) {
    this.bookingEntries$ = new ReplaySubject(1);
    this.allBookingEntriesUser$ = new ReplaySubject(1);
    this.user$ = new ReplaySubject(1);

    this.errors$ = new ReplaySubject(1);
    console.log("init server request service")
    //this.requestNewData(undefined)
  }

  loadBookingEntries(externalId: string) {
    this.http.get<BookingEntry[]>(this.urlBookingEntries + "/" + externalId).subscribe(
      (data) => {
        this.bookingEntries$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Buchungen", error);
      });
  }

  loadBookingEntriesUser(personalNumber: string) {
    this.http.get<BookingEntry[]>(this.urlBookingEntriesUserOpen + "/" + personalNumber).subscribe(
      (data) => {
        this.allBookingEntriesUser$.next(data);
      }, (error: HttpErrorResponse) => {
        this.error("Fehler beim laden der Buchungen", error);
      });
  }

  getBookingEntries()
  {
    return this.bookingEntries$.asObservable()
  }
 loadUser(personalNumber: string) {
    //this.user$.next(undefined)
    this.http.get<User>(this.urlUser + "/" +  personalNumber).subscribe(
      (data) => {
        this.user$.next(data);
      }, (error: HttpErrorResponse) => {
        this.user$.next(undefined);
        this.error("Fehler beim laden des Benutzers", error);
      });
  }

  getUser()
  {
    return this.user$.asObservable()
  }

  getAllBookingEntriesUser()
  {
    return this.allBookingEntriesUser$.asObservable()
  }

  private error(message: string, error: HttpErrorResponse) {
    console.log(message, error)
    let errorMessage = message + " wegen: " + error.message;
    console.error(errorMessage);
    console.log(error);
    this.errors$.next(errorMessage);
  }

  postBookingEntry(bookingEntry: BookingEntry | undefined, id?: number) {
    if(bookingEntry != undefined && bookingEntry.externalId != undefined)
    {
      this.http.post(this.urlSingleBookingEntry + "/" + bookingEntry.externalId, bookingEntry).subscribe(
        (data) => {
          this.loadBookingEntries(bookingEntry.externalId)
        }, (error: HttpErrorResponse) => {
          this.error("Fehler beim anlegen der Buchung", error);
        });
    }
  }

  clearBookingEntries() {
    this.bookingEntries$.next(undefined)
  }

  clearBookingEntriesUser() {
    this.allBookingEntriesUser$.next(undefined)
  }

  clearUser() {
    this.user$.next(undefined)
  }
}
