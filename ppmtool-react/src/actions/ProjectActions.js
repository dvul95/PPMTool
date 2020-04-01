import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT } from "./types";

//create Projects
export const createProject = (project, history) => async dispatch => {
  try {
    const res = await axios.post("http://localhost:8080/api/project", project);
    history.push("/dashboard");
  } catch (e) {
    dispatch({
      type: GET_ERRORS,
      payload: e.response.data
    });
  }
};

//get Projects
export const getProjects = () => async dispatch => {
  const res = await axios.get("http://localhost:8080/api/project/projects");
  dispatch({
    type: GET_PROJECTS,
    payload: res.data
  });
};

//get Project
export const getProject = (id, history) => async dispatch => {
  try {
    const res = await axios.get(`http://localhost:8080/api/project/${id}`);
    dispatch({
      type: GET_PROJECT,
      payload: res.data
    });
  } catch (error) {
    history.push("/dashboard");
  }
};
