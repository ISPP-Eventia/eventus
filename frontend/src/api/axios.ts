import Axios from "axios";

const BASE_URL = window.location.href.includes("localhost")
  ? "http://localhost:8080"
  : window.location.origin;

const API_URL = BASE_URL;

export const axios = Axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

// Error handling interceptor
axios.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);
