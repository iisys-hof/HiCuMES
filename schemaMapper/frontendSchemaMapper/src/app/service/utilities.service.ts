import {Injectable} from '@angular/core';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root'
})
export class UtilitiesService {
  deepReplaceRecursive(search:string, replace:string, obj: any): any {

    _.each(obj, (val: any, key: any) => {
      if (val === search) {
        // replace val if it's equal to the old value
        obj[key] = replace;
      } else if (typeof(val) === 'object') {
        // if val has nested values, make recursive call
        this.deepReplaceRecursive(search, replace, val);
      }
    });

    return obj;
  }

  deepReplaceRecursiveWithKey(search:string, replace:string, obj: any, keySelector: string): any {

    _.each(obj, (val: any, key: any) => {
      if (val === search) {
        // replace val if it's equal to the old value
        if (keySelector== key) {
          obj[key] = replace;

        }
      } else if (typeof(val) === 'object') {
        // if val has nested values, make recursive call
        this.deepReplaceRecursiveWithKey(search, replace, val, keySelector);
      }
    });

    return obj;
  }

}
