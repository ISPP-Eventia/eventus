import { SignupFormValues } from "types";
import { userApi } from "api";
import { Error } from "components/atoms";
import { SignupForm } from "components/organisms";
import { useMemo, useState } from "react";
import { useNavigate } from "react-router";
import moment from "moment";
import utils from "utils";

export interface EditProfileProps {
  profile: SignupFormValues;
}

const EditProfile = (props: EditProfileProps) => {
  const { profile } = props;

  const [error, setError] = useState<string>("");
  const navigate = useNavigate();
  const loggedUserId = localStorage.getItem("userId");
  const handleSubmit = (values: SignupFormValues) => {
  const profileBody = utils.parsers.signupFormValuesToUser(values);
  console.log(profile)
  userApi
  .updateUser(profileBody)
  .then(() => {
      navigate("/profile");
    })
    .catch((e) => {
    setError(e?.response?.data?.error ?? "");
    });
   };
  
  const initialValues: any = useMemo(
    () => ({
      ...profile,
      birthDate: moment(profile.birthDate ?? ""),
    }),
    [profile]
  );
  return (
    <>
      <SignupForm initialValues={initialValues} editMode={false} onSubmit={handleSubmit}/>
      {error && <Error error={error} />}
    </>
  );
};

export default EditProfile;
