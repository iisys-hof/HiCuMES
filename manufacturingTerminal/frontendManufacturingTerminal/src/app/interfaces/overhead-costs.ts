import {User} from "./user";
import {OverheadCostCenters} from "./overhead-cost-centers";

export interface OverheadCosts {
  id: number,
  user: User,
  timeRecord: any
  timeDuration: number,
  note: string,
  overheadCostCenter: OverheadCostCenters
}
