import axios from "axios";
import { GET_ERRORS, SET_CURR_USER } from "./types";
import setJWToken from "../SecurityUtils/setJWToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (e) {
    dispatch({
      type: GET_ERRORS,
      payload: e.response.data,
    });
  }
};

export const login = (LoginRequest) => async (dispatch) => {
  try {
    const res = await axios.post("/api/users/login", LoginRequest);
    const { token } = res.data;
    localStorage.setItem("jwtToken", token);
    setJWToken(token);
    const decoded = jwt_decode(token);

    dispatch({
      type: SET_CURR_USER,
      payload: decoded,
    });
  } catch (e) {
    dispatch({
      type: GET_ERRORS,
      payload: e.response.data,
    });
  }
};

export const logout = () => (dispatch) => {
  localStorage.removeItem("jwtToken");
  setJWToken(false);
  dispatch({
    type: SET_CURR_USER,
    payload: {},
  });
};
