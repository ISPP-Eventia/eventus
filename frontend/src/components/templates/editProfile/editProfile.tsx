import { SignupFormValues } from "types";
import { Error } from "components/atoms";
import { SignupForm } from "components/organisms";
import { useState } from "react";

export interface EditProfileProps {
  profile: SignupFormValues;
}

const EditProfile = (props: EditProfileProps) => {
  const { profile } = props;

  const [error, setError] = useState<string>("");

  return (
    <>
      <SignupForm initialValues={profile} editMode={true}/>
      {error && <Error error={error} />}
    </>
  );
};

export default EditProfile;