import { API_URL, axios } from "./axios";
import {
  EventUs,
  Hosting,
  Location,
  Participation,
  Sponsorship,
  User,
} from "types";

// utitlities
const mediaApi = {
  //bulk operations
  //individual operations
};

const sessionApi = {
  login: (email: string, password: string) =>
    axios.post("/session/login", { email, password }),
  logout: () => axios.post("/session/logout"),
  signup: (user: User) => axios.post("/session/signup", user),
};

// main entities
const userApi = {
  //bulk operations
  getEventsByOrganizer: () => axios.get("/users/events"),
  getParticipationsByParticipant: (id: number) =>
    axios.get(`/user/${id}/participations`),
  getLocationsByOwner: (id: number) => axios.get(`/user/locations`),

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
  updateEvent: (event: EventUs) => axios.put("/events", event),
  deleteEvent: (id: number) => axios.delete(`/events/${id}`),
};

const locationApi = {
  //bulk operations
  getLocations: () => axios.get("/locations"),

  //individual operations
  getLocation: (id: number) => axios.get(`/locations/${id}`),
  createLocation: (location: Location) => axios.post("/locations", location),
  updateLocation: (location: Location) =>
    axios.put(`/locations/${location.id}`, location),
  deleteLocation: (id: number) => axios.delete(`/locations/${id}`),
};

// relations
const hostingApi = {
  //bulk operations
  getHostings: (locationId: number) =>
    axios.get(`/locations/${locationId}/hostings`),
  //individual operations
  createHosting: (hosting: Hosting) => axios.post("/hostings", hosting),
  acceptHosting: (id: number, isAccepted: boolean) =>
    axios.post(`/hostings/${id}`, { isAccepted }),
};

const participationApi = {
  //bulk operations
  getParticipations: () => axios.get("/participations"),
  getParticipationsByUser: () => axios.get("/user/participations"),

  //individual operations
  getParticipation: (id: number) => axios.get(`/participations/${id}`),
  createParticipation: (eventId: number) =>
    axios.post("/participations", { eventId }),
  updateParticipation: (participation: Participation) =>
    axios.put(`/participations/${participation.id}`, participation),
  deleteParticipation: (id: number) => axios.delete(`/participations/${id}`),
  getTicket: (id: number) => {
    const ticketUrl = API_URL + `/participation/${id}/ticket`;
    // Change this to use your HTTP client
    axios
      .get(ticketUrl, {
        responseType: "blob",
      })
      .then((blob) => {
        // RETRIEVE THE BLOB AND CREATE LOCAL URL
        var _url = window.URL.createObjectURL(blob.data);
        window.open(_url, "_blank")?.focus(); // window.open + focus
      })
      .catch(() => {});
  },
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

const paymentApi = {
  getPaymentIntent: () => axios.post("/stripe/initial"),
  getPaymentMethods: () => axios.get("/stripe/paymentmethods"),
};

export {
  mediaApi,
  sessionApi,
  userApi,
  eventApi,
  locationApi,
  hostingApi,
  participationApi,
  sponsorshipApi,
  paymentApi,
};
