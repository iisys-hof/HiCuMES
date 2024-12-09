import {Injectable} from '@angular/core';
import {Observable, ReplaySubject, Subject} from "rxjs";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {MappingResponse} from "../interfaces/mapping-response";
import {MappingRequest} from "../interfaces/mapping-request";
import {environment} from "../../environments/environment";
import exportFromJSON from "export-from-json";


@Injectable({
  providedIn: 'root'
})
export class MappingEngineService {

  url = environment.baseUrl + '/mappingBackend/data/mappingEndpoint/updateMappingState';
  public measurements$: Subject<MappingResponse>;
  public isLoading$: Subject<boolean>;
  public error$: Subject<string>;

  constructor(private http: HttpClient) {
    this.measurements$ = new ReplaySubject<MappingResponse>();
    this.isLoading$ = new ReplaySubject<boolean>();
    this.error$ = new ReplaySubject<string>();
    this.measurements$.next({
      inputTree: undefined,
      mappingInput: undefined,
      mappingLogging: undefined,
      mappingOutput: undefined,
      outputTree: undefined,
    });
  }

  update(mappingRequest: MappingRequest) {
    this.error$.next(undefined);
    this.isLoading$.next(true);
    this.http.post<MappingResponse>(this.url, mappingRequest).subscribe(
      (data) => {
      this.measurements$.next(data);
        this.isLoading$.next(false);
        console.log("Mapping Response Data: ", data)
        /*const result:object = data.mappingOutput?.result
        let fileName = "Mapping"
        fileName = "MappedData" + "_" + mappingRequest.mappingAndDataSource.name
        const exportType =  exportFromJSON.types.json
        console.log(typeof result)
        exportFromJSON({ data, fileName, exportType})*/
      },(error: HttpErrorResponse) => {
        let errorMessage = "Fehler beim updaten des Mappings: " + error.message + " wegen: " + error.error.message;
        console.error(errorMessage);
        console.log(error);

      this.error$.next(errorMessage);
      this.isLoading$.next(false);
      });
  }

  getObservable(): Observable<MappingResponse> {
    return this.measurements$.asObservable();
  }

  getIsLoadingObservable(): Observable<boolean> {
    return this.isLoading$.asObservable();
  }

  getErrorObservable(): Observable<string> {
    return this.error$.asObservable();
  }
}
