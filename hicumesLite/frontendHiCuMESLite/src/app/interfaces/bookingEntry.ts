import {User} from "./user";

export interface BookingEntry {

  id?: number
  externalId: string

  user: User
  amount?: number
  startDateTime?: any
  endDateTime?: any
  duration?: any
  breakDuration?: any
  status?: any
  note?: string
}
