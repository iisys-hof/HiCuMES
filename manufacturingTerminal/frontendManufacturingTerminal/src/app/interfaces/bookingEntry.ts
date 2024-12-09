import {MachineOccupation} from "./machine-occupation";
import {User} from "./user";

export interface BookingEntry {

  id: number
  externalId: string

  machineOccupation: MachineOccupation
  user: User
  bookingState: string
  message: string
  response: string
}
