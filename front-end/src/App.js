import React from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import AuthRoute from "./components/AuthRoute";
import LoginPage from "./pages/loginPage";
import ProfilePage from "./pages/profilePage";

const App = () => {
  return (
    <>
      <Switch>
        <Route exact path="/">
          <Redirect to="/login" />
        </Route>
        <Route exact path="/login" component={LoginPage} />
        <AuthRoute path="/profile" component={ProfilePage} />
      </Switch>
    </>
  );
};

export default App;
