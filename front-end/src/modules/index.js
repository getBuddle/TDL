import { combineReducers } from "redux";
import loginSlice from "./loginSlice";
import userSlice from "./userSlice";

const rootReducer = combineReducers({
  login: loginSlice.reducer,
  user: userSlice.reducer,
});

export default rootReducer;
