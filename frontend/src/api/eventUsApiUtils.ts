import { axios } from "./axios";
import { EventUs } from "types"

const eventApi = {
    //bulk operations
    getEvents: () => axios.get("/events"),
    
    //individual operations
    getEvent: (id: number) => axios.get(`/events/${id}`),
    createEvent: (event: EventUs) => axios.post("/events", event),
    updateEvent: (id: number, event: EventUs) =>
      axios.put(`/events/${id}`, event),
    deleteEvent: (id: number) => axios.delete(`/events/${id}`),
}

export default eventApi;
