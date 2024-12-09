import {Pipe, PipeTransform} from "@angular/core";

@Pipe({name: 'jsonToObject'})
export class JsonToObjectPipe implements PipeTransform {
  transform(value: any): any {
    var returnValue = value
    returnValue = JSON.parse(returnValue);
    return returnValue;
  }
}
