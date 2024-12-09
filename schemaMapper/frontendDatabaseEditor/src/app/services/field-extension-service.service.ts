import {Injectable} from '@angular/core';
import {Observable, ReplaySubject, Subject} from "rxjs";
import {FieldExtension} from "../interfaces/field-extension";
import {HttpClient} from "@angular/common/http";
import * as _ from "lodash";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class FieldExtensionServiceService {
  url = environment.baseUrl + '/mappingBackend/data/fieldExtension';
  classesToRemove = 'entitySuperClass';
  fieldToRemove = 'id';

  descriptions = {
    auxiliaryMaterials: 'Hilfsstoffe für den <strong>SubProductionStep</strong>.',
    customerOrder: 'Hier werden die einzelnen Kundenaufträge gespeichert',
    machine: 'Die Maschine, die den aktuellen <strong>ProductionStep</strong> durchführt, um das <strong>Product</strong> anzufertigen. Pro <strong>ProductionStep</strong> kann genau eine Maschine verwaltet werden. Die Zuordnung erfolgt vorgelagert in der Fertigungsplanung während der Erstellung des Maschinenbelegungsplans <strong>(MachineOccupation)</strong>.',
    machineOccupation: 'Der Belegungsplan der <strong>Machine</strong> wird hier mit <strong>ProductionOrder</strong> und <strong>ProductionStep</strong> verknüpft.',
    machineStatus: 'Legt fest welche Zustände <strong>Machine</strong> einnehmen kann.',
    machineStatusHistory: 'Ist der Verlauf von <strong>MachineStatus</strong> von <strong>Machine</strong> .',
    machineType: 'Definiert die Typen von verschiedenen <strong>Machine</strong> .',
    product: 'Das <strong>Product</strong>, das in einer <strong>ProductionOrder</strong> hergestellt werden soll. Auch Halbfertigprodukte mit eigener Artikelnummer zählen als <strong>Product</strong>.',
    productionNumber: 'Hier werden die Rückmeldungen der Mengen erfasst, die von den Produktionsmitarbeitern bei einer <strong>MachineOccupation</strong> gemacht werden.',
    productionOrder: 'Hier wird der Fertigungsauftrag mit der Anzahl von <strong>Product</strong> gespeichert.',
    productionStep: 'Definiert <strong>ProductionStep</strong>, die für Herstellung von <strong>Product</strong> notwendig sind.',
    productRelationship: 'Beziehungen zwischen <strong>Product</strong> und Halbfertigprodukte, welche wiederum <strong>Product</strong> sind.',
    qualityManagement: 'Hier werden die Rückmeldungen der Qualitätssicherung gespeichert, die von den Produktionsmitarbeitern bei einer <strong>MachineOccupation</strong> gemacht werden.',
    setUp: 'Hier werden die Rückmeldungen des Rüstvorgangs gespeichert, die von den Produktionsmitarbeitern bei einer <strong>MachineOccupation</strong> gemacht werden.',
    subProductionStep: 'Kleinste Unterschritte von <strong>ProductionStep</strong> mit Zeitmessung. Rüstvorgang (<strong>SetUp</strong>), Qualitätssicherung(<strong>QualityManagement</strong>), Rückmeldungen(<strong>ProductionNumber</strong>) und Hilfsstoffe(<strong>AuxiliaryMaterials</strong>) gehören dazu',
    tool: 'Hier werden Werkzeuge passend zum <strong>ToolType</strong> angelegt.',
    toolSetting: 'Die möglichen Einstellungen von <strong>MachineType</strong> in Verbindung mit <strong>ToolType</strong>.',
    toolSettingParameter: 'Konkrete Konfiguration von <strong>ToolSetting</strong> für eine <strong>Machine</strong> und <strong>Tool</strong> für eine <strong>MachineOccupation</strong>.',
    toolType: 'Definiert die Arten von <strong>Tool</strong>.'
  }

  public measurements$: Subject<FieldExtension>;
  public isLoading$: Subject<boolean>;
  public state$: Subject<string>;


  constructor(private http: HttpClient) {
    this.measurements$ = new ReplaySubject<FieldExtension>();
    this.isLoading$ = new ReplaySubject<boolean>();
    this.state$ = new ReplaySubject<string>();

  }

  update()
  {
    this.isLoading$.next(true);
    this.state$.next('Hole Daten von Server');
    this.http.get<FieldExtension>(this.url).subscribe((data) => {
      data.classes = _.sortBy(data.classes, 'name');
      for (var selectedClass of data.classes) {

        // @ts-ignore
        var description = this.descriptions[selectedClass.name]
        selectedClass.description = description;

        var indexToRemoveField = _.find(selectedClass.members, { 'name': this.fieldToRemove });
        _.remove(selectedClass.members, indexToRemoveField);
        selectedClass.members = _.sortBy(selectedClass.members, ['name'])
      }

      var indexToRemove = _.find(data.classes, { 'name': this.classesToRemove });
      _.remove(data.classes, indexToRemove);
      this.measurements$.next(data);
      this.state$.next('Daten erfolgreich geladen');
      this.isLoading$.next(false);
    }, error => {
      this.state$.next('Versuche erneut zum Server zu verbinden');
      setTimeout(()=> {
        this.update();
      }, 5000);
    });
  }



  sendNewFields(fieldExtension: FieldExtension) {
    console.log(fieldExtension);
    this.state$.next('Sende neue Attribute an Server');
    this.isLoading$.next(true);
    this.http.post<any>(this.url, fieldExtension).subscribe(data => {
      this.state$.next('Warte bis zu 30 Sekunden auf Server-Neustart...');

      setTimeout(()=> {
      this.update();
    }, 10000);
    });
  }
  getIsLoadingObservable(): Observable<boolean> {
    return this.isLoading$.asObservable();
  }

  getObservable(): Observable<FieldExtension> {
    return this.measurements$.asObservable();
  }

  getStateObservable(): Observable<string> {
    return this.state$.asObservable();
  }
}
