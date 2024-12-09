import { Component, OnInit } from "@angular/core";
import { AbstractControl, FormControl } from "@angular/forms";
import { FieldType } from "@ngx-formly/material";
import { Observable, of } from "rxjs";


@Component({

    selector: "hicumes-autocomplete",
    styles: [`
      .ng-dropdown-panel {
        margin-top: 1.25em;;
        background: #fff;
        left: 0;
      }
      .ng-dropdown-panel.ng-select-top {
        bottom: calc(100% - 0.84375em);
        box-shadow: 0 -5px 5px -3px rgba(0, 0, 0, 0.2),
        0 -8px 10px 1px rgba(0, 0, 0, 0.14), 0 -3px 14px 2px rgba(0, 0, 0, 0.12);
      }
      .ng-dropdown-panel.ng-select-right {
        left: 100%;
        top: calc(0% + 0.84375em);
        box-shadow: 0 -5px 5px -3px rgba(0, 0, 0, 0.2),
        0 -8px 10px 1px rgba(0, 0, 0, 0.14), 0 -3px 14px 2px rgba(0, 0, 0, 0.12);
        margin-left: 4px;
      }
      .ng-dropdown-panel.ng-select-bottom {
        top: calc(100% - 1.25em);
        box-shadow: 0 5px 5px -3px rgba(0, 0, 0, 0.2),
        0 8px 10px 1px rgba(0, 0, 0, 0.14), 0 3px 14px 2px rgba(0, 0, 0, 0.12);
      }
      .ng-dropdown-panel.ng-select-left {
        left: calc(-100% - 4px);
        top: calc(0% + 0.84375em);
        box-shadow: 0 -5px 5px -3px rgba(0, 0, 0, 0.2),
        0 -8px 10px 1px rgba(0, 0, 0, 0.14), 0 -3px 14px 2px rgba(0, 0, 0, 0.12);
      }
      .ng-dropdown-panel.multiple .ng-option.selected {
        background: #fff;
      }
      .ng-dropdown-panel.multiple .ng-option.marked {
        background: rgba(0, 0, 0, 0.04);
      }
      .ng-dropdown-panel .ng-dropdown-header {
        border-bottom: 1px solid rgba(0, 0, 0, 0.12);
        padding: 0 16px;
        line-height: 3em;
        min-height: 3em;
      }
      .ng-dropdown-panel .ng-dropdown-footer {
        border-top: 1px solid rgba(0, 0, 0, 0.12);
        padding: 0 16px;
        line-height: 3em;
        min-height: 3em;
      }
      .ng-dropdown-panel .ng-dropdown-panel-items .ng-optgroup {
        user-select: none;
        cursor: pointer;
        line-height: 3em;
        height: 3em;
        padding: 0 16px;
        color: rgba(0, 0, 0, 0.54);
        font-weight: 500;
      }
      .ng-dropdown-panel .ng-dropdown-panel-items .ng-optgroup.ng-option-marked {
        background: rgba(0, 0, 0, 0.04);
      }
      .ng-dropdown-panel .ng-dropdown-panel-items .ng-optgroup.ng-option-disabled {
        cursor: default;
      }
      .ng-dropdown-panel .ng-dropdown-panel-items .ng-optgroup.ng-option-selected {
        background: rgba(0, 0, 0, 0.12);
        color: #3f51b5;
      }
      .ng-dropdown-panel .ng-dropdown-panel-items .ng-option {
        line-height: 3em;
        min-height: 3em;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        padding: 0 16px;
        text-decoration: none;
        position: relative;
        color: rgba(0, 0, 0, 0.87);
        text-align: left;
      }
      [dir="rtl"] .ng-dropdown-panel .ng-dropdown-panel-items .ng-option {
        text-align: right;
      }
      .ng-dropdown-panel .ng-dropdown-panel-items .ng-option.ng-option-marked {
        background: rgba(0, 0, 0, 0.04);
        color: rgba(0, 0, 0, 0.87);
      }
      .ng-dropdown-panel .ng-dropdown-panel-items .ng-option.ng-option-selected {
        background: rgba(0, 0, 0, 0.12);
        color: #3f51b5;
      }
      .ng-dropdown-panel .ng-dropdown-panel-items .ng-option.ng-option-disabled {
        color: rgba(0, 0, 0, 0.38);
      }
      .ng-dropdown-panel .ng-dropdown-panel-items .ng-option.ng-option-child {
        padding-left: 32px;
      }
      [dir="rtl"]
      .ng-dropdown-panel
      .ng-dropdown-panel-items
      .ng-option.ng-option-child {
        padding-right: 32px;
        padding-left: 0;
      }
      .ng-dropdown-panel .ng-dropdown-panel-items .ng-option .ng-tag-label {
        padding-right: 5px;
        font-size: 80%;
        font-weight: 400;
        color: rgba(0, 0, 0, 0.38);
      }
      [dir="rtl"]
      .ng-dropdown-panel
      .ng-dropdown-panel-items
      .ng-option
      .ng-tag-label {
        padding-left: 5px;
        padding-right: 0;
      }
    `],
    template: `
            <ng-select matInput
                [items]="this.optionItems"
                [bindValue]="props['bindValue'] || 'value'"
                [formControl]="formControl"
                [class.is-invalid]="showError"
                >
            </ng-select>
    `
})

export class NgSelectFormlyComponent extends FieldType<any> implements OnInit{

    // @ts-ignore
    formControl: FormControl;
    optionItems:any[] = [];

    ngOnInit(){
        this.getOptions();
        //console.log(this.formControl, this.props)
        if(this.props["selected"])
        {
          this.formControl.setValue(this.props["selected"])
        }
        //console.log(this.formControl, this.props)
    }

    getOptions(){

        if(this.props.options) {
           this.props.options.forEach((e:any) => {
            let valOption = {value: e.value, label: e.label};
            this.optionItems.push(valOption);
          });
        }
        return [];
    }
}
