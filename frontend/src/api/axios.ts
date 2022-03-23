import Axios from "axios";

const BASE_URL = process.env.REACT_APP_API_BASE_URL ?? "http://localhost:8080"

export const API_URL = BASE_URL;

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
