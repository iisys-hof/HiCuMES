import {Component, OnInit} from '@angular/core';
import {MappingResponse} from "../../interfaces/mapping-response";
import {Observable} from "rxjs";
import {MappingEngineService} from "../../service/mapping-engine.service";
import {MappingAndDataSource, MappingRequest} from "../../interfaces/mapping-request";
import {WindowSizeService} from "../../service/window-size.service";
import {MappingRequestStateService} from "../../state/mapping-request-state.service";
import {SelectedTreeNode} from "../../interfaces/selected-tree-node";
import {MappingRuleWithLoops} from "../../interfaces/mapping-rule-with-loops";
import {AdvancedSheetComponent} from "../../component/advanced-sheet/advanced-sheet.component";
import {MatBottomSheet} from "@angular/material/bottom-sheet";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MappingRequestService} from "../../service/mapping-request.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ModalLoadMappingComponent} from "../../component/modal-load-mapping/modal-load-mapping.component";
import {ModalNewMappingComponent} from "../../component/modal-new-mapping/modal-new-mapping.component";
import {ModalModiflyRuleComponent} from "../../component/modal-modifly-rule/modal-modifly-rule.component";
import {FormsDatasourceService} from "../../service/forms-datasource.service";
import {ModalCamundaOverviewComponent} from "../../component/modal-camunda-overview/modal-camunda-overview.component";
import {ComponentType} from "@angular/cdk/overlay";
import {PluginInformation} from "../../interfaces/plugin-information";
import exportFromJSON from 'export-from-json'
import {ModalUploadFileComponent} from "../../component/modal-upload-file/modal-upload-file.component";

@Component({
  selector: 'app-complete-mapper',
  templateUrl: './complete-mapper.component.html',
  styleUrls: ['./complete-mapper.component.scss']
})
export class CompleteMapperComponent implements OnInit {

  mappingEngine$: Observable<MappingResponse>;
  mappingRequest$: Observable<MappingRequest>;
  mappingRules$: Observable<MappingRuleWithLoops[]>;
  pluginInformations$: Observable<PluginInformation[]>;
  pluginInformations: PluginInformation[] = [];

  isLoading$: Observable<boolean>;
  windowHeight$: Observable<number>;
  selectedInput?: SelectedTreeNode;
  selectedOutput?: SelectedTreeNode;
  httpError$: Observable<string>;
  mappingSaveLoadMessages$: Observable<string>;
  formsDataSourceError$: Observable<string>;


  constructor(private mappingEngineService: MappingEngineService, private  windowSizeService: WindowSizeService,
              private mappingRequestStateService: MappingRequestStateService, private _bottomSheet: MatBottomSheet,
              private _snackBar: MatSnackBar, private mappingRequestService: MappingRequestService,private dialog: MatDialog, private formsDatasourceService:FormsDatasourceService) {
    mappingRequestService.loadPluginInfos();
    this.mappingEngine$ = mappingEngineService.getObservable();
    this.isLoading$ = mappingEngineService.getIsLoadingObservable();
    this.windowHeight$ = windowSizeService.getHeightObservable();
    this.mappingRequest$ = mappingRequestStateService.getObservable();
    this.mappingRules$ = mappingRequestStateService.getObservableMappingRules();
    this.pluginInformations$ = mappingRequestService.getPluginInfos();
    this.httpError$ = mappingEngineService.getErrorObservable();
    this.mappingSaveLoadMessages$ = mappingRequestService.getStateMessageObservable();
    this.formsDataSourceError$ = formsDatasourceService.getErrorObservable();
  }

  ngOnInit(): void {
    console.log('init');

    this.mappingEngine$.subscribe(mappingResponse => {
      if(mappingResponse.readerResult) {
        console.log(mappingResponse.readerResult)
        this.mappingRequestStateService.setReaderResult(mappingResponse.readerResult);
      }
      if(mappingResponse.mappingLogging && mappingResponse.mappingLogging.error.length>0) {
        this._snackBar.open(mappingResponse.mappingLogging.error.join('\n\n'), "OK",{duration: 30000, panelClass:'hc-snackbar'})
      }
    });
    this.mappingRequest$.subscribe(mappingRequest => {
      console.log('auto update');
      this.mappingEngineService.update(mappingRequest);
    });

    this.pluginInformations$.subscribe(pluginInfos => {this.pluginInformations = pluginInfos});

    this.formsDataSourceError$.subscribe(this.openSnackbar.bind(this));

    this.httpError$.subscribe(this.openSnackbar.bind(this));
    this.mappingSaveLoadMessages$.subscribe(this.openSnackbar.bind(this));

    this.mappingRequestStateService.setExample();
    this.formsDatasourceService.update();
    this.mappingRequestService.load();
  }

  changedInputSelection($event: SelectedTreeNode) {
    this.selectedInput = $event;
  }

  changedOutputSelection($event: SelectedTreeNode) {
    this.selectedOutput = $event;
  }

  newMappingRule(mappingRuleWithLoops: MappingRuleWithLoops) {
    this.mappingRequestStateService.setNewMappingRule(mappingRuleWithLoops);
  }

  openBottomSheet(mappingEngine: MappingResponse): void {
    this._bottomSheet.open(AdvancedSheetComponent, {
      data: mappingEngine
    });
  }

  saveMapping() {
    this.mappingRequestService.save(this.mappingRequestStateService.getState().mappingAndDataSource);
  }

  exportMapping() {
    const data = this.mappingRequestStateService.getState().mappingAndDataSource
    let fileName = "Mapping"
    fileName = data.externalId? fileName + "_" + data.externalId : fileName + "_" +data.name
    const exportType =  exportFromJSON.types.json

    exportFromJSON({ data, fileName, exportType })
  }

  loadMapping() {
    this.mappingRequestService.load();
    this.openModalMapping(ModalLoadMappingComponent, result => {
      this.mappingRequestStateService.setState(result);
    });
  }

  loadMappingFromFile() {
    this.openModalMapping(ModalUploadFileComponent, result => {
      this.mappingRequestStateService.setState(result);
    });
  }

  newMapping($event: any) {
    this.openModalMapping(ModalNewMappingComponent, result => {
      console.log(result)
      this.mappingRequestStateService.setState(result);
    }, {data: {pluginInfos: this.pluginInformations, loadModel: $event? this.mappingRequestStateService.getState() : undefined}});
  }

  deleteMapping(mappingRule: MappingRuleWithLoops) {
    this.mappingRequestStateService.deleteMappingRule(mappingRule);

  }

  editMapping(mappingRule: MappingRuleWithLoops) {
    this.openModalMapping(ModalModiflyRuleComponent,result => {
      this.mappingRequestStateService.updateMappingRule(result);
    }, {
      data: mappingRule
    });
  }

  updateMapping(mappingRule: string) {
    this.mappingRequestStateService.updateMappingRule(undefined, mappingRule)
  }

  updateMappingAndSaveToDB(mappingRule: string) {
    this.mappingRequestStateService.updateMappingRule(undefined, mappingRule, true)
  }

  openCamundaOverview() {
    this.openModalMapping(ModalCamundaOverviewComponent, result => {
      this.mappingRequestStateService.setState(result);
    });
  }

  openModalMapping(modal: ComponentType<any>, callback: (result: any) => void, config: MatDialogConfig = {}) {
    if (this.dialog.openDialogs.length >0) {
      return;
    }
    const dialogRef = this.dialog.open(modal, config);

    dialogRef.afterClosed().subscribe((result: any) => {
      if(result) {
        callback(result);
      }
    });
  }

  private openSnackbar(message: string) {
    if (message) {
      this._snackBar.open(message, "OK", {duration: 15000, panelClass: 'hc-snackbar'});
    }
  }
}
