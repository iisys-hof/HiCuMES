import {Pipe, PipeTransform} from "@angular/core";
import * as _ from "lodash";

@Pipe({name: 'sort'})
export class SortPipe implements PipeTransform {
  transform(value: any[], order: "asc" | "desc" = "asc"): any[] {
    return _.sortBy(value)
  }
}
