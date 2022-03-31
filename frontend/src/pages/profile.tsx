import { useLocation, useNavigate } from "react-router";

import Page from "./page";
import { ProfileForm } from "components/organisms";
import { Link } from "react-router-dom";
import { SignupFormValues } from "types";


const ProfilePage = () => {
  const navigate = useNavigate();
  const action = useLocation().pathname.split("/")[1];

  const onSignup = () => {
      console.log("Pepe")
  };

  return (
    <Page title="Mi perfil">
      {action === "login" ? (
        <ProfileForm onSubmit={onSignup} editMode={false} />
      ) : (
        <ProfileForm onSubmit={onSignup} editMode={false} />
      )}
    </Page>
  );
};

export default ProfilePage;