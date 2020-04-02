import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

//create Projects
export const createProject = (project, history) => async dispatch => {
  try {
    const res = await axios.post("/api/project", project);
    history.push("/dashboard");

    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (e) {
    dispatch({
      type: GET_ERRORS,
      payload: e.response.data
    });
  }
};

//get Projects
export const getProjects = () => async dispatch => {
  const res = await axios.get("/api/project/projects");
  dispatch({
    type: GET_PROJECTS,
    payload: res.data
  });
};

//get Project
export const getProject = (id, history) => async dispatch => {
  try {
    const res = await axios.get(`/api/project/${id}`);
    dispatch({
      type: GET_PROJECT,
      payload: res.data
    });
  } catch (error) {
    history.push("/dashboard");
  }
};

//delete Project
export const deleteProject = id => async dispatch => {
  if (window.confirm("Do you want to DELETE the project and its data?")) {
    await axios.delete(`/api/project/${id}`);
    dispatch({
      type: DELETE_PROJECT,
      payload: id
    });
  }
};
