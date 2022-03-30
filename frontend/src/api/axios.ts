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

// Session interceptors
axios.interceptors.response.use(
  (response) => {
    if (response.data.token && response.data.id) {
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("userId", response.data.id);
    }
    return response;
  },
  (error) => {
    if (error.response.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("userId");
      window.location.href = "/login";
    }
  }
);

axios.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (config.headers)
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    } else {
      delete config.headers.Authorization;
    }
  return config;
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
