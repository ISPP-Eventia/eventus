import Axios from "axios";

const BASE_URL = process.env.REACT_APP_API_BASE_URL ?? "http://localhost:8080";

export const API_URL = BASE_URL;

export const axios = Axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

axios.interceptors.request.use((request) => {
  if (!request.headers) {
    request.headers = {};
  }
  request.headers["Authorization"] = "Bearer " + localStorage.getItem("token");
  return request
});

// Session interceptor
axios.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response.status === 401) {
      window.location.href = "/login";
    }
  }
);

// Error handling interceptor
axios.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);
