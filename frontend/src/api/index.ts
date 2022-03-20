import { axios } from "./axios";
import { EventUs, Participation } from "types";

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
  getParticipations: () => axios.get("/participations"),

  //individual operations
  getParticipation: (id: number) => axios.get(`/participations/${id}`),
  createParticipation: (participation: Participation) => axios.post("/participations", participation),
  updateParticipation: (participation: Participation) => axios.put(`/participations/${participation.id}`, participation),
  getUsersByEvent: (id: number) => axios.get(`/event/${id}/participants`),
  getParticipationsByUser: (id: number) => axios.get(`/user/${id}/participations`),
  getParticipationsByEvent: (id: number) => axios.get(`/event/${id}/participations`),
  deleteParticipation: (id: number) => axios.delete(`/participations/${id}`),

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
