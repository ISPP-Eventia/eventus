import { useLocation, useNavigate } from "react-router";

import Page from "../page";
import { LoginForm, SignupForm } from "components/organisms";
import { Link } from "react-router-dom";
import { sessionApi } from "api";
import { LoginFormValues, SignupFormValues, User } from "types";
import utils from "utils";

const SessionPage = () => {
  const navigate = useNavigate();
  const action = useLocation().pathname.split("/")[1];

  const onLogin = (values: LoginFormValues) => {
    sessionApi.login(values.email, values.password).then((r) => {
      localStorage.setItem("userId", r.data.id);
      navigate("/events");
    });
  };

  const onSignup = (values: SignupFormValues) => {
    const user: User = utils.parsers.signupFormValuesToUser(values);
    sessionApi.signup(user).then((r) => {
      localStorage.setItem("userId", r.data.id);
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
          ? "You don't have an account? Sign up now"
          : "Already have an account? Login"}
      </Link>
    </Page>
  );
};

export default SessionPage;
