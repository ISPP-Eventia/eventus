import { EventDataBody, EventUs } from "types";

export abstract class EventService {
  abstract createEvent(values: EventDataBody): Promise<EventUs>;
  abstract readEvent(id: number): Promise<EventUs>;
  abstract updateEvent(event: EventUs): Promise<EventUs>;
  abstract deleteEvent(id: number): Promise<boolean>;
}
