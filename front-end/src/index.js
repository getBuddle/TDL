import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import { Provider } from "react-redux";
import rootReducer from "./modules";
import { BrowserRouter } from "react-router-dom";
import { configureStore, getDefaultMiddleware } from "@reduxjs/toolkit";

const store = configureStore({
	reducer: rootReducer,
	middlewares: [...getDefaultMiddleware()],
	devTools: process.env.NODE_ENV !== 'production',
})

ReactDOM.render(
  <Provider store={store}>
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
  </Provider>,
  document.getElementById("root")
);
