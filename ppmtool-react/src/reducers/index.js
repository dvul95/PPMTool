import { combineReducers } from "redux";
import ErrorReducer from "./ErrorReducer";
import ProjectReducer from "./ProjectReducer";
import BacklogReducer from "./BacklogReducer";

export default combineReducers({
  errors: ErrorReducer,
  project: ProjectReducer,
  backlog: BacklogReducer,
});
