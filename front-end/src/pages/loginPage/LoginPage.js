import React, { useEffect, useRef } from "react";
import { Redirect, useLocation } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import KakaoLogin from "react-kakao-login";
import { signIn } from "modules/loginSlice";
import { Box, Button, makeStyles } from "@material-ui/core";
import loginButtonImage from "resources/images/kakao_login_large_narrow.png";
import { signUp } from "modules/userSlice";
import axios from "axios";

const token = "4d3093214b6fa6d7a83a304b794a96e1";

const useStyles = makeStyles({
  loginButton: {
    width: "366px",
    height: "90px",
    transform: "scale(0.7)",
    backgroundImage: `url(${loginButtonImage})`,
  },
});

const LoginPage = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const location = useLocation();

  const requestUser = useRef();
  const { data: loginData, error: loginError } = useSelector(
    (state) => state.login
  );
  const { data: joinData, error: joinError } = useSelector(
    (state) => state.user
  );

  useEffect(() => {
    if (loginError && !joinData) {
      dispatch(signUp(requestUser.current));
    }

    if (joinData && !loginData) {
      dispatch(signIn(requestUser.current));
    }

    if (joinError) {
      alert("회원가입 실패");
    }
  }, [loginError, joinData, joinError]);

  const successLogin = (res) => {
    const data = {
      username: res.profile.id,
      password: 1234,
      email: res.profile.kakao_account.email,
      nickname: res.profile.properties.nickname,
    };

    requestUser.current = data;
    dispatch(signIn(data));
  };

  if (loginData) {
    axios.defaults.headers.common["Authorization"] = loginData.Authorization;
    const path = location.state ? location.state.from.pathname : "/profile";
    return <Redirect to={path} />;
  }

  return (
    <Box
      display="flex"
      alignItems="center"
      justifyContent="center"
      height="100vh"
    >
      <KakaoLogin
        token={token}
        onSuccess={successLogin}
        onFail={() => alert("로그인 실패")}
        render={({ onClick }) => (
          <Button className={classes.loginButton} onClick={() => onClick()} />
        )}
      />
    </Box>
  );
};

export default LoginPage;
