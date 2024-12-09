export interface DynamicLayout {
  areas?: Area[];
}

export interface Payload {
  id: number;
  name: string;
  variable: string;
}

export interface Item {
  type: string;
  payload: Payload;
}

export interface Column {
  col: number;
  colSpan: number;
  colStart: number;
  item: Item;
}

export interface Row {
  columns: Column[];
}

export interface Area {
  heading: string;
  rows: Row[];
}


