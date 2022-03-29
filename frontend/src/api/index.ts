import { axios } from "./axios";
import { EventUs, Hosting, Location, Participation, Sponsorship } from "types";

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
  getLocations: () => axios.get("/locations"),

  //individual operations
  getLocation: (id: number) => axios.get(`/locations/${id}`),
  createLocation: (location: Location) => axios.post("/locations", location),
  updateLocation: (location: Location) => axios.put(`/locations/${location.id}`, location),
  deleteLocation: (id: number) => axios.delete(`/locations/${id}`),

  //Other operations with user
  getLocationsByUser: (id: number) => axios.get(`/users/${id}/locations`)
};

// relations
const hostingApi = {
  //bulk operations
  getHostings: (locationId: number) => axios.get(`/locations/${locationId}/hostings`),
  //individual operations
  createHosting: (hosting: Hosting) => axios.post("/hostings", hosting),
  acceptHosting: (id: number, isAccepted: boolean) =>
  axios.post(`/hostings/${id}`, { isAccepted }),
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
  acceptSponsorship: (id: number, isAccepted: boolean) =>
    axios.post(`/sponsorships/${id}`, { isAccepted }),
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
