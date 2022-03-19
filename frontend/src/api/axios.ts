import Axios from "axios";

/* TODO CREATE SESSION UTILS IN ORDER TO LOG IN AND LOGOUT
 import { sessionUtils } from "utils"; */


const BASE_URL = window.location.href.includes("localhost")
  ? "http://localhost:3000/"
  : window.location.origin + "/landing";

const API_URL = BASE_URL + "/api";

export const axios = Axios.create({
  baseURL: API_URL,
  //withCredentials: true,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

// No auth implemented yet (logout)
//TODO IMPLEMENT SESSION UTILS
/* axios.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response?.status === 403 || error.response?.status === 401) {
      sessionUtils.removeToken();
      window.location.reload();
    }
    return Promise.reject(error);
  }
); */

// Error handling interceptor
axios.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);
