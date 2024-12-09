import {Injectable} from '@angular/core';
import {Class, FieldExtension, MemberFieldExtension} from "../interfaces/field-extension";

@Injectable({
  providedIn: 'root'
})
export class FieldValidationService {

  constructor() { }

  validate(fields: FieldExtension) : FieldExtension {
    let filteredClasses: Class[] = [];

    for(let currentClass of fields.classes) {
      let filteredMembers: MemberFieldExtension[] = [];

      for(let member of currentClass.members) {
        if (this.isMemberValid(member)) {
          filteredMembers.push(member);
        }
      }
      if(filteredMembers.length > 0 ) {
        filteredClasses.push({
          id: currentClass.id,
          name: currentClass.name,
          members: filteredMembers,
          description: currentClass.description
        });
      }
    }


    let result: FieldExtension = {
      classes: filteredClasses
    };

    return result;
  }


  isMemberValid(member: MemberFieldExtension) : boolean {
    if (member.name === undefined) {
      return false;
    }
    if (member.name.length < 5) {
      return false;
    }
    if (member.type === null) {
      return false;
    }
    return true;
  }
}

