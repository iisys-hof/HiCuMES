import {AfterViewInit, Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import { get, set } from "lodash";
import * as moment from 'moment';
import {DecimalPipe} from "@angular/common";
import {UnitTranslationPipe} from "../pipes/unit-translation.pipe";
import * as _ from 'lodash';
import {FieldType} from "@ngx-formly/material";
@Component({
  selector: 'formly-simple',
  template: `
    {{getField()}}

  `,
})
export class FormlySimple extends FieldType<any> {

  constructor(private decimalPipe: DecimalPipe, private unitTranslationPipe: UnitTranslationPipe) {
    super()
  }

  getField(){
    let t = get(this.model, this.key)
    if(String(this.key).includes("[last()]"))
    {
      t = this.getWithLast(this.model, String(this.key))
      //console.log(this.formControl.value)
      if(typeof this.formControl?.value === 'object' && Object?.keys(this.formControl?.value)?.length === 0)
      {
          this.formControl.setValue(t)
      }
      else
      {
        t = this.formControl.value
      }
    }
    if(this.field.props?.round)
    {
      t = t?.toFixed(this.field.props?.round);
    }

    if (t === "" || t === undefined || t === null || (typeof t === 'object' && Object.keys(t).length === 0)) {
      return "---"
    }
    else if(this.field.props?.type === "date")
    {
      const date = moment(t, 'YYYY-MM-DD HH:mm')
      //console.log("DATE", t);
      if(this.field.props?.dateFormat)
      {
        return date.format(this.field.props?.dateFormat);
      }
      return date.format('DD.MM.YYYY HH:mm');
    }
    else if(this.field.props?.type === "duration")
    {
      //console.log("DATE", t);
      if(this.field.props?.durationUnit)
      {
        switch(this.field.props?.durationUnit)
        {
          case "h":
            return this.decimalPipe.transform((t/ 3600).toFixed(2));
          case "min":
            return this.decimalPipe.transform((t/ 60).toFixed(2));
          default:
            return this.decimalPipe.transform(t.toFixed(2));
        }
      }
    }
    else if(this.field.props?.type === "measurementWithUnit" && this.field.props?.unitLabelSrc)
    {
      return this.decimalPipe.transform(t) + " " + this.unitTranslationPipe.transform(get(this.model, this.field.props?.unitLabelSrc));
    }
    else if(this.field.props?.type === "measurementWithUnit" && this.field.props?.unitLabel)
    {
      return this.decimalPipe.transform(t) + " " + this.field.props?.unitLabel
    }
    else if(this.field.props?.unitLabel)
    {
      return this.decimalPipe.transform(t) + " " + this.field.props?.unitLabel
    }
    else if(this.field.props?.split)
    {
      const splitChar = this.field.props.split
      return t.split(splitChar)[0]
    }
    else if(this.field.props?.concat)
    {
      let spacer = ""
      if(this.field.props?.concatSpacer)
      {
        spacer = this.field.props?.concatSpacer
      }
      const concatKeys = this.field.props.concat
      let concatString = "";
      for (const concatKey of concatKeys) {
        let value = this.getValue(t,concatKey)
        if(concatString != "")
        {
          concatString += spacer
        }
        if(this.field.props?.concatCastNumber) {
          value = Number(value)
        }
        if(typeof (value) === 'number')
        {
          concatString += this.decimalPipe.transform(value);
        }
        else if(value === "")
        {
          concatString += '---'
        }
        else {
          concatString += value
        }
      }
      return concatString
    }
    else if(this.field.props?.map)
    {
      return this.field.props?.map.find((f: any) => f.key == t).value
    }
    else if(this.field.props?.templateString)
    {
      //The regex matches all variables that are encased in '${}', then uses the reflection function getValue to get the value from the model for the field
      //Then it replaces the variable encased in '${}' with the value from the model
      const regex = /\$\{([^\}]*)\}/gm
      let str = this.field.props?.templateString;

      str = str.replace(regex, (group0: any, group1: any) => {
        let value = this.getValue(t, group1)
        if(typeof (value) === 'number')
        {
          return this.decimalPipe.transform(value);
        }
        else if(value === "")
        {
          return "AS"
        }
        else return value
      })

      return str
    }
    else if (typeof (t) === 'number')
    {
      return this.decimalPipe.transform(t);
    }
    else {
      return t;
    }
  }

  getValue(element: any, prop: any) {
    var props = prop.split(".");
    var value = this.reflectionGet(element, props);
    return value
  }

  private reflectionGet(element: any, props: []): any
  {
    //console.log(element, props)
    var prop = props.shift()
    //console.log("Get ", props, prop, element)
    if(prop != undefined)
    {
      var obj = Reflect.get(element, prop)
      if(obj != undefined) {
        return this.reflectionGet(obj, props)
      }
    }
    else {
      return element
    }
  }

  getWithLast<T>(object: T, path: string): any {
    //console.log(object)
    const parts = path.split('.'); // Split the property path by dot

    // Helper function to handle negative indices
    function resolveIndex(index: number, length: number): number {
      return index < 0 ? Math.max(length + index, 0) : index;
    }

    // Iterate through the path parts and resolve indices
    let basePath = "";
    const resolvedPath = parts.map((part) => {
      if (part.includes('[last()]')) {
        // Handle negative indices like [-1]
        const propName = part.slice(0, part.indexOf('[last()]'));
        const arr = _.get(object, basePath + "." + propName);
        const index = resolveIndex(arr.length - 1, arr.length)
        //console.log(propName, arr, index)
        return `${propName}[${index}]`;
      }
      if(basePath != "")
      {
        basePath += ("." + part)
      }
      else {
        basePath += part
      }
      return part;
    }).join('.');
    //console.log(resolvedPath)
    return _.get(object, resolvedPath); // Use Lodash's _.get with the resolved path
  }


}
