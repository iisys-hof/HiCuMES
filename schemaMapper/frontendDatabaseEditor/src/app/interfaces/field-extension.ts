export interface FieldExtension {
  classes: Class[];
}


  export interface MemberFieldExtension {
    name: string;
    type: string;
    customerField: boolean;
  }

  export interface Class {
    name?: string;
    id: string;
    members: MemberFieldExtension[];
    description?: string
  }





