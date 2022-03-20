import { axios } from "./axios";
import { EventUs } from "types";

// utitlities
const mediaApi = {
  //bulk operations
  //individual operations
};

// main entities
const userApi = {
  //bulk operations
  //individual operations
};

const eventApi = {
  //bulk operations
  getEvents: () => axios.get("/events"),

  //individual operations
  getEvent: (id: number) => axios.get(`/events/${id}`),
  createEvent: (event: EventUs) => axios.post("/events", event),
  updateEvent: (event: EventUs) => axios.put(`/events/${event.id}`, event),
  deleteEvent: (id: number) => axios.delete(`/events/${id}`),
};

const locationApi = {
  //bulk operations
  //individual operations
};

// relations
const hostingApi = {
  //bulk operations
  //individual operations
};

const participationApi = {
  //bulk operations
  //individual operations
};

const sponsorshipApi = {
  //bulk operations
  //individual operations
};

export {
  mediaApi,
  userApi,
  eventApi,
  locationApi,
  hostingApi,
  participationApi,
  sponsorshipApi,
};
