import React from "react";
import { useSelector } from "react-redux";
import { Route, Redirect } from "react-router-dom";

function AuthRoute({ component: Component, ...rest }) {
  const { data } = useSelector((state) => state.login);

  return (
    <Route
      {...rest}
      render={(props) =>
        data ? (
          <Component {...props} />
        ) : (
          <Redirect
            to={{ pathname: "/login", state: { from: props.location } }}
          />
        )
      }
    />
  );
}

export default AuthRoute;
