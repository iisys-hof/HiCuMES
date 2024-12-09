import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ReplaySubject, Subject} from "rxjs";
import {MappingAndDataSource} from "../interfaces/mapping-request";
import {MappingResponse} from "../interfaces/mapping-response";
import {environment} from "../../environments/environment";
import {PluginInformation} from "../interfaces/plugin-information";

@Injectable({
  providedIn: 'root'
})
export class MappingRequestService {
  public stateMessage$: Subject<string>;
  public availableMappings$: Subject<MappingAndDataSource[]>;
  public availablePlugins$: Subject<PluginInformation[]>

  urlSave = environment.baseUrl + '/mappingBackend/data/mappingEndpoint/saveMapping';
  urlLoad = environment.baseUrl + '/mappingBackend/data/mappingEndpoint/availableMappings';
  urlPluginInfo = environment.baseUrl + '/mappingBackend/data/mappingEndpoint/pluginInfos';

  constructor(private http: HttpClient) {
    this.stateMessage$ = new ReplaySubject<string>();
    this.availableMappings$ = new ReplaySubject<MappingAndDataSource[]>();
    this.availablePlugins$ = new ReplaySubject<PluginInformation[]>();

  }

  loadPluginInfos()
  {
    this.http.get<PluginInformation[]>(this.urlPluginInfo).subscribe(
      (data) => {
        this.availablePlugins$.next(data);
      },(error: HttpErrorResponse) => {
        let errorMessage = "Fehler beim laden der verfügbaren Plugins: " + error.message + " wegen: " + error.error.message;
        console.error(errorMessage);
        console.log(error);
        this.stateMessage$.next(errorMessage);
      });
  }

  save(mappingAndDataSource: MappingAndDataSource) {
    console.log("Versuche Mapping zu speichern:");
    console.log(mappingAndDataSource);
    this.http.post<MappingResponse>(this.urlSave, mappingAndDataSource).subscribe(
      (data) => {
        this.stateMessage$.next("Mapping erfolgreich gespeichert");
        this.load();
      },(error: HttpErrorResponse) => {
        let errorMessage = "Fehler beim speichern des Mappings: " + error.message + " wegen: " + error.error.message;
        console.error(errorMessage);
        console.log(error);
        this.stateMessage$.next(errorMessage);
      });
  }

  load() {
    this.http.get<MappingAndDataSource[]>(this.urlLoad).subscribe(
      (data) => {
        this.availableMappings$.next(data);
      },(error: HttpErrorResponse) => {
        let errorMessage = "Fehler beim laden der verfügbaren Mappings: " + error.message + " wegen: " + error.error.message;
        console.error(errorMessage);
        console.log(error);
        this.stateMessage$.next(errorMessage);
      });
  }

  getPluginInfos() {
    return this.availablePlugins$.asObservable();
  }

  getStateMessageObservable() {
    return this.stateMessage$.asObservable();
  }

  getAvailableMappings() {
    return this.availableMappings$.asObservable();
  }
}
