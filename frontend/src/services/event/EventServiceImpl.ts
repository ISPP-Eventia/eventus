import { EventDataBody, EventUs } from "types";
import { EventService } from "./EventService";

export class EventServiceImpl extends EventService {
    createEvent(values: EventDataBody): Promise<EventUs> {
        throw new Error("Method not implemented.");
    }
    readEvent(id: number): Promise<EventUs> {
        throw new Error("Method not implemented.");
    }
    updateEvent(event: EventUs): Promise<EventUs> {
        throw new Error("Method not implemented.");
    }
    deleteEvent(id: number): Promise<boolean> {
        throw new Error("Method not implemented.");
    }
}

export const eventServiceImpl = new EventServiceImpl();
