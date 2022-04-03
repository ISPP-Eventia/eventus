import Page from "../page";
import Edit from "components/templates/editEvent/EditEvent";
import { useParams } from "react-router";
import { useQuery } from "react-query";
import { userApi } from "api";
import { User } from "types";
import { Loader } from "components/atoms";
import EditProfile from "components/templates/editProfile/editProfile";

const EditUserPage = () => {

  const { isLoading: loadingEvent, data: user } = useQuery("user", () =>
    userApi.getUserDetails().then((response) => {
      return response.data as User;
    })
  );

  return (
    <div></div>
  );
};

export default EditUserPage;
