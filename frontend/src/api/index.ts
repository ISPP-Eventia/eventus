import { axios } from "./axios";
import {
  EventUs,
  Hosting,
  Location,
  Participation,
  Sponsorship,
  UploadFileAxios,
  User,
} from "types";

// utitlities
const mediaApi = {
  //bulk operations
  //individual operations
  getMedia: (id: number): Promise<string> =>
    axios
      .get(`/media/${id}`, {
        responseType: "blob",
        headers: { Accept: "*/*" },
      })
      .then((blob) => window.URL.createObjectURL(blob.data))
      .catch(() => ""),
  uploadMedia: async (options: UploadFileAxios): Promise<any> => {
    const { file, onProgress, onSuccess, onError } = options;
    const config = {
      headers: { "content-type": "multipart/form-data" },
      onUploadProgress: (event: any) => {
        onProgress({ percent: (event.loaded / event.total) * 100 });
      },
    };
    const fmData = new FormData();
    fmData.append("media", file);
    try {
      const res = await axios.post("/media", fmData, config);
      onSuccess("Ok");
      return res;
    } catch (err) {
      onError({ err });
    }
  },
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
  getLocationsByOwner: () => axios.get("/user/locations"),

  //individual operations
  getUserDetails: () => axios.get("/user"),
  updateUser: (user: User) => axios.put(`/user/${user.id}`, user),
};

const eventApi = {
  //bulk operations
  getEvents: () => axios.get("/events"),
  getRecommendedEvents: () => axios.get("/events/recommend"),
  getRecommendedEventsByEvent: (id: number) =>
    axios.get(`/events/recommend/${id}`),

  getUsersByEvent: (id: number) => axios.get(`/events/${id}/participants`),
  getParticipationsByEvent: (id: number) =>
    axios.get(`/events/${id}/participations`),
  getSponsorshipsByEvent: (id: number) =>
    axios.get(`/events/${id}/sponsorships`),

  //individual operations
  getEvent: (id: number) => axios.get(`/events/${id}`),
  createEvent: (event: EventUs) =>
    axios.post("/events?mediaIds=" + event.mediaIds, event),
  updateEvent: (event: EventUs) =>
    axios.put("/events?mediaIds=" + event.mediaIds, event),
  deleteEvent: (id: number) => axios.delete(`/events/${id}`),
};

const locationApi = {
  //bulk operations
  getLocations: () => axios.get("/locations"),

  //individual operations
  getLocation: (id: number) => axios.get(`/locations/${id}`),
  createLocation: (location: Location) =>
    axios.post("/locations?mediaIds=" + location.mediaIds, location),
  updateLocation: (location: Location) =>
    axios.put(
      `/locations/${location.id}?mediaIds=${location.mediaIds}`,
      location
    ),
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
    axios
      .get(`/participation/${id}/ticket`, {
        responseType: "blob",
      })
      .then((blob) => {
        // RETRIEVE THE BLOB AND CREATE LOCAL URL
        var _url = window.URL.createObjectURL(blob.data);
        window.open(_url, "_blank")?.focus(); // window.open + focus
      })
      .catch(() => {
        return null;
      });
  },
};

const sponsorshipApi = {
  //bulk operations
  getSponsorships: () => axios.get("/sponsorships"),

  //individual operations
  getSponsorship: (id: number) => axios.get(`/sponsorships/${id}`),
  createSponsorship: (sponsorship: Sponsorship) =>
    axios.post("/sponsorships?mediaIds=" + sponsorship.mediaIds, sponsorship),
  acceptSponsorship: (id: number, isAccepted: boolean) =>
    axios.post(`/sponsorships/${id}`, { isAccepted }),
  updateSponsorship: (sponsorship: Sponsorship) =>
    axios.put(
      `/sponsorships/${sponsorship.id}?mediaIds=${sponsorship.mediaIds}`,
      sponsorship
    ),
  deleteSponsorship: (id: number) => axios.delete(`/sponsorships/${id}`),
};

const paymentApi = {
  getPaymentIntent: () => axios.post("/stripe/initial"),
  getPaymentMethods: () => axios.get("/stripe/paymentmethods"),
};

const tagApi = {
  getTags: () => axios.get("/tags"),
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
  tagApi,
};
