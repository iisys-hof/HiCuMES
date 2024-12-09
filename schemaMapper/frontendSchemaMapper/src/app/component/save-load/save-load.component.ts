import {AfterViewInit, Component, EventEmitter, OnInit, Output} from '@angular/core';
import {MappingRuleWithLoops} from "../../interfaces/mapping-rule-with-loops";
import {animate, state, style, transition, trigger,} from '@angular/animations';

@Component({
  selector: 'app-save-load',
  animations: [
    trigger('openCloseBtn', [
      state('open', style({
        marginBottom: '20px',
        opacity: 1
      })),
      state('closed', style({
        marginBottom: '-56px',
        width: "0px",
        opacity: 0
      })),
      transition('open <=> closed', [
        animate('0.5s')
      ])
    ]),
  ],
  templateUrl: './save-load.component.html',
  styleUrls: ['./save-load.component.scss']
})
export class SaveLoadComponent implements OnInit, AfterViewInit {
  //TODO: remove doubleCheckError
  isOpen = false;

  toggle() {
    this.isOpen = !this.isOpen;
  }

  @Output()
  saveMappingEvent = new EventEmitter<MappingRuleWithLoops>();
  @Output()
  exportMappingEvent = new EventEmitter<MappingRuleWithLoops>();
  @Output()
  loadMappingEvent = new EventEmitter<MappingRuleWithLoops>();
  @Output()
  newMappingEvent = new EventEmitter();
  @Output()
  loadFromFileEvent = new EventEmitter<MappingRuleWithLoops>();
  @Output()
  openCamundaOverviewEvent = new EventEmitter<MappingRuleWithLoops>();

  showIcons: boolean = false;

  constructor() {

  }
  ngAfterViewInit(): void {
    this.isOpen = true;
  }

  ngOnInit(): void {

  }

  saveMapping() {
    this.saveMappingEvent.emit();
  }

  loadMapping() {
    this.loadMappingEvent.emit();
  }

  newMapping() {
    this.newMappingEvent.emit();
  }

  loadFromFile() {
    this.loadFromFileEvent.emit();
  }

  openCamundaMapping() {
    this.openCamundaOverviewEvent.emit();
  }

  exportMapping() {
    this.exportMappingEvent.emit();
  }

  editMapping() {
    this.newMappingEvent.emit(true);
  }
}
