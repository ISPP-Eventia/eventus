import { useLocation, useNavigate } from "react-router";
import { Link } from "react-router-dom";

import { sessionApi } from "api";
import { LoginFormValues, SignupFormValues } from "types";
import utils from "utils";

import { LoginForm, SignupForm } from "components/organisms";
import Page from "../page";

const SessionPage = () => {
  const navigate = useNavigate();
  const action = useLocation().pathname.split("/")[1];

  const onLogin = (values: LoginFormValues) => {
    sessionApi.login(values.email, values.password).then((r) => {
      navigate("/events");
    });
  };

  const onSignup = (values: SignupFormValues) => {
    const user = utils.parsers.signupFormValuesToUser(values);
    sessionApi.signup(user).then((r) => {
      navigate("/events");
    });
  };

  return (
    <Page title={action}>
      {action === "login" ? (
        <LoginForm onSubmit={onLogin} />
      ) : (
        <SignupForm onSubmit={onSignup} />
      )}
      <Link to={`/${action === "login" ? "signup" : "login"}`}>
        {action === "login"
          ? "No tienes cuenta? Registrate ya!"
          : "Ya tienes cuenta? Inicia sesión!"}
      </Link>
    </Page>
  );
};

export default SessionPage;
