import { userApi } from "api";
import { Loader } from "components/atoms";
import EditProfile from "components/templates/editProfile/editProfile";
import { useQuery } from "react-query";
import { SignupFormValues } from "types";
import Page from "../page";

const EditProfilePage = () => {

  const { isLoading: loadingProfile, data: profile } = useQuery(
    "user",
    () =>
      userApi
        .getUserDetails()
        .then((response) => response.data as SignupFormValues)
  );
  return (
    <Page title="Nuevo Perfil">
      {loadingProfile || !profile ? (
        <Loader />
      ) : (
        <EditProfile profile={profile} />
      )}
    </Page>
  );
};

export default EditProfilePage;
