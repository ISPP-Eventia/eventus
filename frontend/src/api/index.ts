import { axios } from "./axios";
import { EventUs, Participation, Sponsorship } from "types";

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

  getUsersByEvent: (id: number) => axios.get(`/events/${id}/participants`),
  getParticipationsByEvent: (id: number) =>
    axios.get(`/events/${id}/participations`),
  getSponsorshipsByEvent: (id: number) =>
    axios.get(`/events/${id}/sponsorships`),

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
  getParticipationsByUser: (id: number) =>
    axios.get(`/user/${id}/participations`),

  //individual operations
  getParticipation: (id: number) => axios.get(`/participations/${id}`),
  createParticipation: (eventId: number) =>
    axios.post("/participations", { eventId }),
  updateParticipation: (participation: Participation) =>
    axios.put(`/participations/${participation.id}`, participation),
  deleteParticipation: (id: number) => axios.delete(`/participations/${id}`),
};

const sponsorshipApi = {
  //bulk operations
  getSponsorships: () => axios.get("/sponsorships"),

  //individual operations
  getSponsorship: (id: number) => axios.get(`/sponsorships/${id}`),
  createSponsorship: (sponsorship: Sponsorship) =>
    axios.post("/sponsorships", sponsorship),
  updateSponsorship: (sponsorship: Sponsorship) =>
    axios.put(`/sponsorships/${sponsorship.id}`, sponsorship),
  deleteSponsorship: (id: number) => axios.delete(`/sponsorships/${id}`),
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
