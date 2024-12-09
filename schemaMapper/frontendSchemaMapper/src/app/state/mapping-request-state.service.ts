import {Injectable} from '@angular/core';
import {MappingAndDataSource, MappingConfiguration, MappingRequest, ReaderResult} from "../interfaces/mapping-request";
import {Observable, ReplaySubject, Subject} from "rxjs";
import {MappingRuleWithLoops} from "../interfaces/mapping-rule-with-loops";
import {map} from "rxjs/operators";
import * as _ from "lodash";

@Injectable({
  providedIn: 'root'
})
export class MappingRequestStateService {
  //TODO observable
  mappingRequest: MappingRequest;
  mappingRuleWithLoops: MappingRuleWithLoops[];
  public mappingRequest$: Subject<MappingRequest>;
  public mappingRule$: Observable<MappingRuleWithLoops[]>;

  constructor() {
    this.mappingRequest = {
      mappingAndDataSource: {
        id: Math.floor(Math.random()*1000000)
      },
      simulate: true,
      useSavedData: true
    };
    this.mappingRuleWithLoops =[];

    this.mappingRequest$ = new ReplaySubject<MappingRequest>();

    this.mappingRule$ = this.mappingRequest$.pipe(
      map((mappingRequest: MappingRequest) => {
        this.mappingRuleWithLoops = this.transformMappingRequest(mappingRequest?.mappingAndDataSource?.mappingConfiguration, [], []);

        return this.mappingRuleWithLoops;
      })
    );
    this.updateObservable();
  }

  private updateObservable() {
    this.mappingRequest$.next(this.mappingRequest);
  }

  transformMappingRequest(mappingConfiguration: MappingConfiguration | undefined, inputRules: string[], outputRules: string[]): MappingRuleWithLoops[] {
    if (_.isNil(mappingConfiguration)) {
      return [];
    }
    let mappings:MappingRuleWithLoops[] = [];
    let inputLoops: string[] =[...inputRules];

    if(!_.isNil(mappingConfiguration.inputSelector)) {
      inputLoops.push(mappingConfiguration.inputSelector);
    }
    let outputLoops: string[] =[...outputRules];
    if(!_.isNil(mappingConfiguration.outputSelector)) {
      outputLoops.push(mappingConfiguration.outputSelector);
    }
    for (const rule of mappingConfiguration.mappings) {
      mappings.push(<MappingRuleWithLoops>{
        mappingRule: rule,
        inputLoops: inputLoops,
        outputLoops: outputLoops
      });
    }

    for (const mappingConfig of mappingConfiguration.loops) {
      mappings = mappings.concat(this.transformMappingRequest(mappingConfig, inputLoops, outputLoops));
    }
    return mappings;
  }

  getObservable(): Observable<MappingRequest> {
    return this.mappingRequest$.asObservable();
  }
  getObservableMappingRules(): Observable<MappingRuleWithLoops[]> {
    return this.mappingRule$;
  }

  setState(mappingAndDataSource: MappingAndDataSource) {
    this.mappingRequest.mappingAndDataSource = mappingAndDataSource;

    if(mappingAndDataSource.readerResult == undefined || mappingAndDataSource.readerResult.result == '')
    {
      this.mappingRequest.useSavedData = false;
    }
    else {
      this.mappingRequest.useSavedData = true;
    }
    this.updateObservable();

  }

  getState() {
    return this.mappingRequest;
  }
  setReaderResult(readerResult: ReaderResult) {
    this.mappingRequest.mappingAndDataSource.readerResult = readerResult;
  }

  setExample() {
    this.mappingRequest = {
      "mappingAndDataSource": {
        "id": 1,
        "name": "Beispiel Mapping",
        "readerResult": {
          "result": "{  \"d\": {    \"__metadata\": {      \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OrderHeaderSet('1000023')\",      \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OrderHeaderSet('1000023')\",      \"type\": \"ZFR_FDZ_SRV.OrderHeader\"    },    \"DeletionFlag\": false,    \"ExplDate\": \"\\/Date(1621814400000)\\/\",    \"SchedReleaseDate\": \"\\/Date(1621814400000)\\/\",    \"ActualReleaseDate\": \"\\/Date(1621814400000)\\/\",    \"FinishDate\": \"\\/Date(1621814400000)\\/\",    \"EnterDate\": \"\\/Date(1621814400000)\\/\",    \"DateOfExpiry\": null,    \"DateOfManufacture\": null,    \"StartDate\": \"\\/Date(1621814400000)\\/\",    \"ProductionFinishDate\": \"\\/Date(1621814400000)\\/\",    \"ProductionStartDate\": \"\\/Date(1621814400000)\\/\",    \"ActualStartDate\": null,    \"ActualFinishDate\": null,    \"Scrap\": \"0\",    \"TargetQuantity\": \"1\",    \"ConfirmedQuantity\": \"0\",    \"OrderNumber\": \"1000023\",    \"ProductionPlant\": \"HD00\",    \"MrpController\": \"000\",    \"ProductionScheduler\": \"000\",    \"Material\": \"FDZ_ENDPRODUKT\",    \"RoutingNo\": \"0000000044\",    \"ReservationNumber\": \"0000000079\",    \"Unit\": \"EA\",    \"UnitIso\": \"EA\",    \"Priority\": \"\",    \"OrderType\": \"PP04\",    \"EnteredBy\": \"FROSSNER\",    \"WbsElement\": \"000000000000000000000000\",    \"ConfNo\": \"0000000000\",    \"ConfCnt\": \"00000000\",    \"IntObjNo\": \"000000000000000000\",    \"CollectiveOrder\": \"\",    \"OrderSeqNo\": \"00000000000000\",    \"LeadingOrder\": \"\",    \"SalesOrder\": \"11\",    \"SalesOrderItem\": \"000020\",    \"ProdSchedProfile\": \"10\",    \"MaterialText\": \"gefertigte Produktpalette\",    \"SystemStatus\": \"REL  MSPT PRC  MANC\",    \"PlanPlant\": \"HD00\",    \"Batch\": \"\",    \"MaterialExternal\": \"\",    \"MaterialGuid\": \"\",    \"MaterialVersion\": \"\",    \"MaterialLong\": \"FDZ_ENDPRODUKT\",    \"SchedFinTime\": \"PT00H00M00S\",    \"SchedStartTime\": \"PT00H00M00S\",    \"FinishTime\": \"PT00H00M00S\",    \"StartTime\": \"PT00H00M00S\",    \"ActualStartTime\": \"PT00H00M00S\",    \"OrderHeaderOperation\": {      \"results\": [        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0010',RoutingNo='0000000044')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0010',RoutingNo='0000000044')\",            \"type\": \"ZFR_FDZ_SRV.Operation\"          },          \"EarlSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"LateSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"Quantity\": \"1.000\",          \"Scrap\": \"0.000\",          \"ActivityType1\": \"\",          \"ActivityType2\": \"\",          \"ActivityType3\": \"\",          \"ActivityType4\": \"\",          \"ActivityType5\": \"\",          \"ActivityType6\": \"\",          \"ConfCnt\": \"00000000\",          \"ConfNo\": \"0000000332\",          \"Counter\": \"00000063\",          \"Description\": \"Nachfrage Farbe_Smartie\",          \"Description2\": \"\",          \"GroupCounter\": \"1\",          \"Number\": \"\",          \"OperationNumber\": \"0010\",          \"OprCntrlKey\": \"ASSY\",          \"ProdPlant\": \"HD00\",          \"PurchaseReqItem\": \"00000\",          \"PurchaseReqNo\": \"\",          \"RoutingNo\": \"0000000044\",          \"SequenceNo\": \"0\",          \"StandardValueKey\": \"\",          \"Suboperation\": \"\",          \"SystemStatus\": \"REL\",          \"TaskListGroup\": \"50021980\",          \"TaskListType\": \"N\",          \"Unit\": \"EA\",          \"UnitIso\": \"EA\",          \"WorkCenter\": \"\",          \"WorkCenterText\": \"\",          \"EarlSchedFinTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeProc\": \"PT00H00M00S\",          \"EarlSchedStartTimeTeard\": \"PT00H00M00S\",          \"LateSchedFinTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeProc\": \"PT00H00M00S\",          \"LateSchedStartTimeTeard\": \"PT00H00M00S\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0020',RoutingNo='0000000044')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0020',RoutingNo='0000000044')\",            \"type\": \"ZFR_FDZ_SRV.Operation\"          },          \"EarlSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"LateSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"Quantity\": \"1.000\",          \"Scrap\": \"0.000\",          \"ActivityType1\": \"\",          \"ActivityType2\": \"\",          \"ActivityType3\": \"\",          \"ActivityType4\": \"\",          \"ActivityType5\": \"\",          \"ActivityType6\": \"\",          \"ConfCnt\": \"00000000\",          \"ConfNo\": \"0000000338\",          \"Counter\": \"00000069\",          \"Description\": \"Produktpalette einschleusen\",          \"Description2\": \"\",          \"GroupCounter\": \"1\",          \"Number\": \"\",          \"OperationNumber\": \"0020\",          \"OprCntrlKey\": \"ASSY\",          \"ProdPlant\": \"HD00\",          \"PurchaseReqItem\": \"00000\",          \"PurchaseReqNo\": \"\",          \"RoutingNo\": \"0000000044\",          \"SequenceNo\": \"0\",          \"StandardValueKey\": \"\",          \"Suboperation\": \"\",          \"SystemStatus\": \"REL\",          \"TaskListGroup\": \"50021980\",          \"TaskListType\": \"N\",          \"Unit\": \"EA\",          \"UnitIso\": \"EA\",          \"WorkCenter\": \"\",          \"WorkCenterText\": \"\",          \"EarlSchedFinTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeProc\": \"PT00H00M00S\",          \"EarlSchedStartTimeTeard\": \"PT00H00M00S\",          \"LateSchedFinTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeProc\": \"PT00H00M00S\",          \"LateSchedStartTimeTeard\": \"PT00H00M00S\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0030',RoutingNo='0000000044')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0030',RoutingNo='0000000044')\",            \"type\": \"ZFR_FDZ_SRV.Operation\"          },          \"EarlSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"LateSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"Quantity\": \"1.000\",          \"Scrap\": \"0.000\",          \"ActivityType1\": \"\",          \"ActivityType2\": \"\",          \"ActivityType3\": \"\",          \"ActivityType4\": \"\",          \"ActivityType5\": \"\",          \"ActivityType6\": \"\",          \"ConfCnt\": \"00000000\",          \"ConfNo\": \"0000000344\",          \"Counter\": \"00000075\",          \"Description\": \"Lagerpalette (Farbe) einschleusen\",          \"Description2\": \"\",          \"GroupCounter\": \"1\",          \"Number\": \"\",          \"OperationNumber\": \"0030\",          \"OprCntrlKey\": \"ASSY\",          \"ProdPlant\": \"HD00\",          \"PurchaseReqItem\": \"00000\",          \"PurchaseReqNo\": \"\",          \"RoutingNo\": \"0000000044\",          \"SequenceNo\": \"0\",          \"StandardValueKey\": \"\",          \"Suboperation\": \"\",          \"SystemStatus\": \"REL\",          \"TaskListGroup\": \"50021980\",          \"TaskListType\": \"N\",          \"Unit\": \"EA\",          \"UnitIso\": \"EA\",          \"WorkCenter\": \"\",          \"WorkCenterText\": \"\",          \"EarlSchedFinTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeProc\": \"PT00H00M00S\",          \"EarlSchedStartTimeTeard\": \"PT00H00M00S\",          \"LateSchedFinTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeProc\": \"PT00H00M00S\",          \"LateSchedStartTimeTeard\": \"PT00H00M00S\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0040',RoutingNo='0000000044')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0040',RoutingNo='0000000044')\",            \"type\": \"ZFR_FDZ_SRV.Operation\"          },          \"EarlSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"LateSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"Quantity\": \"1.000\",          \"Scrap\": \"0.000\",          \"ActivityType1\": \"\",          \"ActivityType2\": \"\",          \"ActivityType3\": \"\",          \"ActivityType4\": \"\",          \"ActivityType5\": \"\",          \"ActivityType6\": \"\",          \"ConfCnt\": \"00000000\",          \"ConfNo\": \"0000000350\",          \"Counter\": \"00000081\",          \"Description\": \"Bestücke Produktpalette mit Smarties\",          \"Description2\": \"\",          \"GroupCounter\": \"1\",          \"Number\": \"\",          \"OperationNumber\": \"0040\",          \"OprCntrlKey\": \"ASSY\",          \"ProdPlant\": \"HD00\",          \"PurchaseReqItem\": \"00000\",          \"PurchaseReqNo\": \"\",          \"RoutingNo\": \"0000000044\",          \"SequenceNo\": \"0\",          \"StandardValueKey\": \"\",          \"Suboperation\": \"\",          \"SystemStatus\": \"REL\",          \"TaskListGroup\": \"50021980\",          \"TaskListType\": \"N\",          \"Unit\": \"EA\",          \"UnitIso\": \"EA\",          \"WorkCenter\": \"\",          \"WorkCenterText\": \"\",          \"EarlSchedFinTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeProc\": \"PT00H00M00S\",          \"EarlSchedStartTimeTeard\": \"PT00H00M00S\",          \"LateSchedFinTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeProc\": \"PT00H00M00S\",          \"LateSchedStartTimeTeard\": \"PT00H00M00S\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0050',RoutingNo='0000000044')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0050',RoutingNo='0000000044')\",            \"type\": \"ZFR_FDZ_SRV.Operation\"          },          \"EarlSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"LateSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"Quantity\": \"1.000\",          \"Scrap\": \"0.000\",          \"ActivityType1\": \"\",          \"ActivityType2\": \"\",          \"ActivityType3\": \"\",          \"ActivityType4\": \"\",          \"ActivityType5\": \"\",          \"ActivityType6\": \"\",          \"ConfCnt\": \"00000000\",          \"ConfNo\": \"0000000351\",          \"Counter\": \"00000082\",          \"Description\": \"Lagerpalette (Farbe) ausschleusen\",          \"Description2\": \"\",          \"GroupCounter\": \"1\",          \"Number\": \"\",          \"OperationNumber\": \"0050\",          \"OprCntrlKey\": \"ASSY\",          \"ProdPlant\": \"HD00\",          \"PurchaseReqItem\": \"00000\",          \"PurchaseReqNo\": \"\",          \"RoutingNo\": \"0000000044\",          \"SequenceNo\": \"0\",          \"StandardValueKey\": \"\",          \"Suboperation\": \"\",          \"SystemStatus\": \"REL\",          \"TaskListGroup\": \"50021980\",          \"TaskListType\": \"N\",          \"Unit\": \"EA\",          \"UnitIso\": \"EA\",          \"WorkCenter\": \"\",          \"WorkCenterText\": \"\",          \"EarlSchedFinTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeProc\": \"PT00H00M00S\",          \"EarlSchedStartTimeTeard\": \"PT00H00M00S\",          \"LateSchedFinTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeProc\": \"PT00H00M00S\",          \"LateSchedStartTimeTeard\": \"PT00H00M00S\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0060',RoutingNo='0000000044')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/OperationSet(OperationNumber='0060',RoutingNo='0000000044')\",            \"type\": \"ZFR_FDZ_SRV.Operation\"          },          \"EarlSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"EarlSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"LateSchedFinDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateExec\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateProc\": \"\\/Date(1621814400000)\\/\",          \"LateSchedStartDateTeard\": \"\\/Date(1621814400000)\\/\",          \"Quantity\": \"1.000\",          \"Scrap\": \"0.000\",          \"ActivityType1\": \"\",          \"ActivityType2\": \"\",          \"ActivityType3\": \"\",          \"ActivityType4\": \"\",          \"ActivityType5\": \"\",          \"ActivityType6\": \"\",          \"ConfCnt\": \"00000000\",          \"ConfNo\": \"0000000357\",          \"Counter\": \"00000088\",          \"Description\": \"Fertiges Smartie_Endprodukt entnehmen\",          \"Description2\": \"\",          \"GroupCounter\": \"1\",          \"Number\": \"\",          \"OperationNumber\": \"0060\",          \"OprCntrlKey\": \"ASSY\",          \"ProdPlant\": \"HD00\",          \"PurchaseReqItem\": \"00000\",          \"PurchaseReqNo\": \"\",          \"RoutingNo\": \"0000000044\",          \"SequenceNo\": \"0\",          \"StandardValueKey\": \"\",          \"Suboperation\": \"\",          \"SystemStatus\": \"REL\",          \"TaskListGroup\": \"50021980\",          \"TaskListType\": \"N\",          \"Unit\": \"EA\",          \"UnitIso\": \"EA\",          \"WorkCenter\": \"\",          \"WorkCenterText\": \"\",          \"EarlSchedFinTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeExec\": \"PT00H00M00S\",          \"EarlSchedStartTimeProc\": \"PT00H00M00S\",          \"EarlSchedStartTimeTeard\": \"PT00H00M00S\",          \"LateSchedFinTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeExec\": \"PT00H00M00S\",          \"LateSchedStartTimeProc\": \"PT00H00M00S\",          \"LateSchedStartTimeTeard\": \"PT00H00M00S\"        }      ]    },    \"OrderHeaderComponents\": {      \"results\": [        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0001')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0001')\",            \"type\": \"ZFR_FDZ_SRV.Component\"          },          \"ReservationNumber\": \"0000000079\",          \"ReservationItem\": \"0001\",          \"ReservationType\": \"\",          \"DeletionIndicator\": false,          \"Material\": \"FDZ_PRODUKTPALETTE\",          \"ProdPlant\": \"HD00\",          \"StorageLocation\": \"\",          \"SupplyArea\": \"\",          \"Batch\": \"\",          \"SpecialStock\": \"E\",          \"ReqDate\": \"\\/Date(1621814400000)\\/\",          \"ReqQuan\": \"1.000\",          \"BaseUom\": \"EA\",          \"BaseUomIso\": \"EA\",          \"WithdrawnQuantity\": \"0.000\",          \"EntryQuantity\": \"1.000\",          \"EntryUom\": \"EA\",          \"EntryUomIso\": \"EA\",          \"OrderNumber\": \"1000023\",          \"MovementType\": \"261\",          \"ItemCategory\": \"C\",          \"ItemNumber\": \"0010\",          \"Sequence\": \"0\",          \"Operation\": \"0010\",          \"Backflush\": false,          \"ValuationSpecStock\": \"M\",          \"SystemStatus\": \"REL\",          \"MaterialDescription\": \"Produktpalette_leer\",          \"CommitedQuantity\": \"0.000\",          \"Shortage\": \"1.000\",          \"PurchaseReqNo\": \"\",          \"PurchaseReqItem\": \"00000\",          \"MaterialExternal\": \"\",          \"MaterialGuid\": \"\",          \"MaterialVersion\": \"\",          \"ReqSegment\": \"\",          \"StockSegment\": \"\",          \"MaterialLong\": \"FDZ_PRODUKTPALETTE\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0002')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0002')\",            \"type\": \"ZFR_FDZ_SRV.Component\"          },          \"ReservationNumber\": \"0000000079\",          \"ReservationItem\": \"0002\",          \"ReservationType\": \"\",          \"DeletionIndicator\": false,          \"Material\": \"SMARTIE_GRÜN\",          \"ProdPlant\": \"HD00\",          \"StorageLocation\": \"\",          \"SupplyArea\": \"\",          \"Batch\": \"\",          \"SpecialStock\": \"E\",          \"ReqDate\": \"\\/Date(1621814400000)\\/\",          \"ReqQuan\": \"3.000\",          \"BaseUom\": \"EA\",          \"BaseUomIso\": \"EA\",          \"WithdrawnQuantity\": \"0.000\",          \"EntryQuantity\": \"3.000\",          \"EntryUom\": \"EA\",          \"EntryUomIso\": \"EA\",          \"OrderNumber\": \"1000023\",          \"MovementType\": \"261\",          \"ItemCategory\": \"C\",          \"ItemNumber\": \"0040\",          \"Sequence\": \"0\",          \"Operation\": \"0010\",          \"Backflush\": false,          \"ValuationSpecStock\": \"M\",          \"SystemStatus\": \"REL\",          \"MaterialDescription\": \"Smartie Gruen\",          \"CommitedQuantity\": \"0.000\",          \"Shortage\": \"3.000\",          \"PurchaseReqNo\": \"\",          \"PurchaseReqItem\": \"00000\",          \"MaterialExternal\": \"\",          \"MaterialGuid\": \"\",          \"MaterialVersion\": \"\",          \"ReqSegment\": \"\",          \"StockSegment\": \"\",          \"MaterialLong\": \"SMARTIE_GRÜN\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0003')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0003')\",            \"type\": \"ZFR_FDZ_SRV.Component\"          },          \"ReservationNumber\": \"0000000079\",          \"ReservationItem\": \"0003\",          \"ReservationType\": \"\",          \"DeletionIndicator\": false,          \"Material\": \"\",          \"ProdPlant\": \"HD00\",          \"StorageLocation\": \"\",          \"SupplyArea\": \"\",          \"Batch\": \"\",          \"SpecialStock\": \"\",          \"ReqDate\": \"\\/Date(1621814400000)\\/\",          \"ReqQuan\": \"1.000\",          \"BaseUom\": \"PC\",          \"BaseUomIso\": \"PCE\",          \"WithdrawnQuantity\": \"0.000\",          \"EntryQuantity\": \"1.000\",          \"EntryUom\": \"PC\",          \"EntryUomIso\": \"PCE\",          \"OrderNumber\": \"1000023\",          \"MovementType\": \"\",          \"ItemCategory\": \"T\",          \"ItemNumber\": \"0140\",          \"Sequence\": \"0\",          \"Operation\": \"0010\",          \"Backflush\": false,          \"ValuationSpecStock\": \"\",          \"SystemStatus\": \"REL\",          \"MaterialDescription\": \"Zeile A:GG--G--\",          \"CommitedQuantity\": \"0.000\",          \"Shortage\": \"1.000\",          \"PurchaseReqNo\": \"\",          \"PurchaseReqItem\": \"00000\",          \"MaterialExternal\": \"\",          \"MaterialGuid\": \"\",          \"MaterialVersion\": \"\",          \"ReqSegment\": \"\",          \"StockSegment\": \"\",          \"MaterialLong\": \"\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0004')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0004')\",            \"type\": \"ZFR_FDZ_SRV.Component\"          },          \"ReservationNumber\": \"0000000079\",          \"ReservationItem\": \"0004\",          \"ReservationType\": \"\",          \"DeletionIndicator\": false,          \"Material\": \"\",          \"ProdPlant\": \"HD00\",          \"StorageLocation\": \"\",          \"SupplyArea\": \"\",          \"Batch\": \"\",          \"SpecialStock\": \"\",          \"ReqDate\": \"\\/Date(1621814400000)\\/\",          \"ReqQuan\": \"1.000\",          \"BaseUom\": \"PC\",          \"BaseUomIso\": \"PCE\",          \"WithdrawnQuantity\": \"0.000\",          \"EntryQuantity\": \"1.000\",          \"EntryUom\": \"PC\",          \"EntryUomIso\": \"PCE\",          \"OrderNumber\": \"1000023\",          \"MovementType\": \"\",          \"ItemCategory\": \"T\",          \"ItemNumber\": \"0150\",          \"Sequence\": \"0\",          \"Operation\": \"0010\",          \"Backflush\": false,          \"ValuationSpecStock\": \"\",          \"SystemStatus\": \"REL\",          \"MaterialDescription\": \"Zeile B:-------\",          \"CommitedQuantity\": \"0.000\",          \"Shortage\": \"1.000\",          \"PurchaseReqNo\": \"\",          \"PurchaseReqItem\": \"00000\",          \"MaterialExternal\": \"\",          \"MaterialGuid\": \"\",          \"MaterialVersion\": \"\",          \"ReqSegment\": \"\",          \"StockSegment\": \"\",          \"MaterialLong\": \"\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0005')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0005')\",            \"type\": \"ZFR_FDZ_SRV.Component\"          },          \"ReservationNumber\": \"0000000079\",          \"ReservationItem\": \"0005\",          \"ReservationType\": \"\",          \"DeletionIndicator\": false,          \"Material\": \"\",          \"ProdPlant\": \"HD00\",          \"StorageLocation\": \"\",          \"SupplyArea\": \"\",          \"Batch\": \"\",          \"SpecialStock\": \"\",          \"ReqDate\": \"\\/Date(1621814400000)\\/\",          \"ReqQuan\": \"1.000\",          \"BaseUom\": \"PC\",          \"BaseUomIso\": \"PCE\",          \"WithdrawnQuantity\": \"0.000\",          \"EntryQuantity\": \"1.000\",          \"EntryUom\": \"PC\",          \"EntryUomIso\": \"PCE\",          \"OrderNumber\": \"1000023\",          \"MovementType\": \"\",          \"ItemCategory\": \"T\",          \"ItemNumber\": \"0160\",          \"Sequence\": \"0\",          \"Operation\": \"0010\",          \"Backflush\": false,          \"ValuationSpecStock\": \"\",          \"SystemStatus\": \"REL\",          \"MaterialDescription\": \"Zeile C:-------\",          \"CommitedQuantity\": \"0.000\",          \"Shortage\": \"1.000\",          \"PurchaseReqNo\": \"\",          \"PurchaseReqItem\": \"00000\",          \"MaterialExternal\": \"\",          \"MaterialGuid\": \"\",          \"MaterialVersion\": \"\",          \"ReqSegment\": \"\",          \"StockSegment\": \"\",          \"MaterialLong\": \"\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0006')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0006')\",            \"type\": \"ZFR_FDZ_SRV.Component\"          },          \"ReservationNumber\": \"0000000079\",          \"ReservationItem\": \"0006\",          \"ReservationType\": \"\",          \"DeletionIndicator\": false,          \"Material\": \"\",          \"ProdPlant\": \"HD00\",          \"StorageLocation\": \"\",          \"SupplyArea\": \"\",          \"Batch\": \"\",          \"SpecialStock\": \"\",          \"ReqDate\": \"\\/Date(1621814400000)\\/\",          \"ReqQuan\": \"1.000\",          \"BaseUom\": \"PC\",          \"BaseUomIso\": \"PCE\",          \"WithdrawnQuantity\": \"0.000\",          \"EntryQuantity\": \"1.000\",          \"EntryUom\": \"PC\",          \"EntryUomIso\": \"PCE\",          \"OrderNumber\": \"1000023\",          \"MovementType\": \"\",          \"ItemCategory\": \"T\",          \"ItemNumber\": \"0170\",          \"Sequence\": \"0\",          \"Operation\": \"0010\",          \"Backflush\": false,          \"ValuationSpecStock\": \"\",          \"SystemStatus\": \"REL\",          \"MaterialDescription\": \"Zeile D:-------\",          \"CommitedQuantity\": \"0.000\",          \"Shortage\": \"1.000\",          \"PurchaseReqNo\": \"\",          \"PurchaseReqItem\": \"00000\",          \"MaterialExternal\": \"\",          \"MaterialGuid\": \"\",          \"MaterialVersion\": \"\",          \"ReqSegment\": \"\",          \"StockSegment\": \"\",          \"MaterialLong\": \"\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0007')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0007')\",            \"type\": \"ZFR_FDZ_SRV.Component\"          },          \"ReservationNumber\": \"0000000079\",          \"ReservationItem\": \"0007\",          \"ReservationType\": \"\",          \"DeletionIndicator\": false,          \"Material\": \"\",          \"ProdPlant\": \"HD00\",          \"StorageLocation\": \"\",          \"SupplyArea\": \"\",          \"Batch\": \"\",          \"SpecialStock\": \"\",          \"ReqDate\": \"\\/Date(1621814400000)\\/\",          \"ReqQuan\": \"1.000\",          \"BaseUom\": \"PC\",          \"BaseUomIso\": \"PCE\",          \"WithdrawnQuantity\": \"0.000\",          \"EntryQuantity\": \"1.000\",          \"EntryUom\": \"PC\",          \"EntryUomIso\": \"PCE\",          \"OrderNumber\": \"1000023\",          \"MovementType\": \"\",          \"ItemCategory\": \"T\",          \"ItemNumber\": \"0180\",          \"Sequence\": \"0\",          \"Operation\": \"0010\",          \"Backflush\": false,          \"ValuationSpecStock\": \"\",          \"SystemStatus\": \"REL\",          \"MaterialDescription\": \"Zeile E:-------\",          \"CommitedQuantity\": \"0.000\",          \"Shortage\": \"1.000\",          \"PurchaseReqNo\": \"\",          \"PurchaseReqItem\": \"00000\",          \"MaterialExternal\": \"\",          \"MaterialGuid\": \"\",          \"MaterialVersion\": \"\",          \"ReqSegment\": \"\",          \"StockSegment\": \"\",          \"MaterialLong\": \"\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0008')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0008')\",            \"type\": \"ZFR_FDZ_SRV.Component\"          },          \"ReservationNumber\": \"0000000079\",          \"ReservationItem\": \"0008\",          \"ReservationType\": \"\",          \"DeletionIndicator\": false,          \"Material\": \"\",          \"ProdPlant\": \"HD00\",          \"StorageLocation\": \"\",          \"SupplyArea\": \"\",          \"Batch\": \"\",          \"SpecialStock\": \"\",          \"ReqDate\": \"\\/Date(1621814400000)\\/\",          \"ReqQuan\": \"1.000\",          \"BaseUom\": \"PC\",          \"BaseUomIso\": \"PCE\",          \"WithdrawnQuantity\": \"0.000\",          \"EntryQuantity\": \"1.000\",          \"EntryUom\": \"PC\",          \"EntryUomIso\": \"PCE\",          \"OrderNumber\": \"1000023\",          \"MovementType\": \"\",          \"ItemCategory\": \"T\",          \"ItemNumber\": \"0190\",          \"Sequence\": \"0\",          \"Operation\": \"0010\",          \"Backflush\": false,          \"ValuationSpecStock\": \"\",          \"SystemStatus\": \"REL\",          \"MaterialDescription\": \"Zeile F:-------\",          \"CommitedQuantity\": \"0.000\",          \"Shortage\": \"1.000\",          \"PurchaseReqNo\": \"\",          \"PurchaseReqItem\": \"00000\",          \"MaterialExternal\": \"\",          \"MaterialGuid\": \"\",          \"MaterialVersion\": \"\",          \"ReqSegment\": \"\",          \"StockSegment\": \"\",          \"MaterialLong\": \"\"        },        {          \"__metadata\": {            \"id\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0009')\",            \"uri\": \"https://s06lp1.ucc.in.tum.de:8001/sap/opu/odata/sap/ZFR_FDZ_SRV/ComponentSet(ReservationNumber='0000000079',ReservationItem='0009')\",            \"type\": \"ZFR_FDZ_SRV.Component\"          },          \"ReservationNumber\": \"0000000079\",          \"ReservationItem\": \"0009\",          \"ReservationType\": \"\",          \"DeletionIndicator\": false,          \"Material\": \"\",          \"ProdPlant\": \"HD00\",          \"StorageLocation\": \"\",          \"SupplyArea\": \"\",          \"Batch\": \"\",          \"SpecialStock\": \"\",          \"ReqDate\": \"\\/Date(1621814400000)\\/\",          \"ReqQuan\": \"1.000\",          \"BaseUom\": \"PC\",          \"BaseUomIso\": \"PCE\",          \"WithdrawnQuantity\": \"0.000\",          \"EntryQuantity\": \"1.000\",          \"EntryUom\": \"PC\",          \"EntryUomIso\": \"PCE\",          \"OrderNumber\": \"1000023\",          \"MovementType\": \"\",          \"ItemCategory\": \"T\",          \"ItemNumber\": \"0200\",          \"Sequence\": \"0\",          \"Operation\": \"0010\",          \"Backflush\": false,          \"ValuationSpecStock\": \"\",          \"SystemStatus\": \"REL\",          \"MaterialDescription\": \"Zeile G:-------\",          \"CommitedQuantity\": \"0.000\",          \"Shortage\": \"1.000\",          \"PurchaseReqNo\": \"\",          \"PurchaseReqItem\": \"00000\",          \"MaterialExternal\": \"\",          \"MaterialGuid\": \"\",          \"MaterialVersion\": \"\",          \"ReqSegment\": \"\",          \"StockSegment\": \"\",          \"MaterialLong\": \"\"        }      ]    }  }}"
        },
        "dataReader": {
          "parserID": "parserPlugin-JSON",
          "readerID": "inputPlugin-FileReader"
        },
        "dataWriter": {
          "writerID": "outputPlugin-DatabaseWriter"
        },
        "mappingConfiguration": {
          "mappings": [
          ],
          "loops": [
            {
              "mappings": [
                {
                  "rule": "output.machineStatus.name = input.d.PlanPlant + ' hallo'",
                  "inputSelector": "input.d.PlanPlant",
                  "outputSelector": "output.machineStatus.name",
                  "uiId": 4
                }
              ],
              "loops": [   {
                "mappings": [
                  {
                    "rule": "output.externalId = input.MaterialLong",
                    "inputSelector": "input.MaterialLong",
                    "outputSelector": "output.externalId",
                    "uiId": 1
                  }
                ],
                "loops": [
                ],
                "inputSelector": "input.d.OrderHeaderComponents.results",
                "outputSelector": "output.machineStatus.machineStatusHistories"
              }
              ],
              "inputSelector": "input",
              "outputSelector": "output"
            },
            {
              "mappings": [
                {
                  "rule": " output.cD_Machine.name =  input.MaterialDescription",
                  "inputSelector": " input.MaterialDescription",
                  "outputSelector": "output.cD_Machine.name",
                  "uiId": 5
                },
                {
                  "rule": " output.cD_Machine.externalId =  input.MaterialDescription",
                  "inputSelector": " input.MaterialDescription",
                  "outputSelector": "output.cD_Machine.externalId",
                  "uiId": 6
                }
              ],
              "loops": [
              ],
              "inputSelector": "input.d.OrderHeaderComponents.results",
              "outputSelector": "output"
            }
          ],
          "inputSelector": undefined,
          "outputSelector": undefined
        }
      },
      "simulate": true,
      "useSavedData": true
    }
    this.updateObservable();
  }


  setNewMappingRule(mappingRule: MappingRuleWithLoops) {
    let newRules = _.cloneDeep(this.mappingRuleWithLoops);
    newRules.push(mappingRule);
    this.reconstructMappingConfigurationFromMappingRulesWithLoop(newRules);
  }

  updateMappingRule(mappingRule: MappingRuleWithLoops | undefined, xsltRules?: string, saveToDB?: boolean) {
    if(xsltRules)
    {
      this.reconstructMappingConfigurationFromMappingRulesWithLoop(undefined, xsltRules, saveToDB);
    }
    else {
      let newRules = _.cloneDeep(this.mappingRuleWithLoops);
      for (const currentRule in newRules) {
        if (newRules[currentRule].mappingRule.uiId === mappingRule!.mappingRule.uiId) {
          if (mappingRule) {
            newRules[currentRule] = mappingRule;
          }
        }
      }
      this.reconstructMappingConfigurationFromMappingRulesWithLoop(newRules);
    }
  }

  deleteMappingRule(mappingRule: MappingRuleWithLoops) {
    let newRules = _.cloneDeep(this.mappingRuleWithLoops);
    for (const currentRule in newRules) {
      if (newRules[currentRule].mappingRule.uiId === mappingRule.mappingRule.uiId) {
        newRules.splice(Number(currentRule),1);
      }
    }
    this.reconstructMappingConfigurationFromMappingRulesWithLoop(newRules);
  }

  reconstructMappingConfigurationFromMappingRulesWithLoop(mappingRules: MappingRuleWithLoops[] | undefined, xsltRules?: string, saveToDB?: boolean) {
    if(mappingRules != undefined)
    {
      let newMappingConfiguration: MappingConfiguration = {
      mappings:[],
      loops: [],
      inputSelector: undefined,
      outputSelector: undefined,
      xsltRules: ''
    }
      for(const rule of mappingRules) {
        newMappingConfiguration = this.addToRightPosition(newMappingConfiguration, rule);
      }
      this.mappingRequest.mappingAndDataSource.mappingConfiguration = newMappingConfiguration;
      this.updateObservable();
    }
    else if(xsltRules)
    {
      this.mappingRequest.mappingAndDataSource!.mappingConfiguration!.xsltRules = xsltRules;
      if(saveToDB)
      {
        this.mappingRequest.simulate = false;
      }
      else
      {
        this.mappingRequest.simulate = true;
      }
      this.updateObservable()
    }
  }

  addToRightPosition(mappingConfiguration: MappingConfiguration, mappingRule: MappingRuleWithLoops) {
    mappingRule = _.cloneDeep(mappingRule);
    const hasRemainingParents = mappingRule.outputLoops.length !== 0;
    if (hasRemainingParents) {
      const inputSelector = mappingRule.inputLoops.shift();
      const outputSelector = mappingRule.outputLoops.shift();
      for (const innerConfig of mappingConfiguration.loops) {

          const hasSameSelectors = innerConfig.inputSelector === inputSelector && innerConfig.outputSelector === outputSelector;
          if(hasSameSelectors) {
            this.addToRightPosition(innerConfig, mappingRule);
            return mappingConfiguration;
          }
        }
      const newInnerConfiguration = {
        loops:[],
        outputSelector: outputSelector,
        inputSelector: inputSelector,
        mappings: []
      };

      mappingConfiguration.loops.push(newInnerConfiguration);
      this.addToRightPosition(newInnerConfiguration, mappingRule);
      return mappingConfiguration;
    }
    mappingConfiguration.mappings.push(mappingRule.mappingRule)
    return mappingConfiguration;
    }
}
