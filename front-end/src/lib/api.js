import axios from "axios";

export const signIn = (data) => {
  return axios.post("/login", data);
};

export const signUp = (data) => {
  return axios.post("/join", data);
};
