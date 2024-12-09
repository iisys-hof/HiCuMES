import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'unitTranslation'
})
export class UnitTranslationPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case "pcs":
        return "stk"
      default:
        return value
    }
  }

}
