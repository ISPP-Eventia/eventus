import { useMemo, useState } from "react";
import { Button, Typography } from "@mui/material";
import { useQuery } from "react-query";
import moment from "moment";

import { userApi } from "api";
import { SignupFormValues } from "types";
import utils from "utils";

import { SignupForm } from "components/organisms";
import { Error, Loader } from "components/atoms";

const MyProfileTab = () => {
  const loggedUserId = localStorage.getItem("userId");

  const [edit, setEdit] = useState(false);
  const [error, setError] = useState<string>("");

  const {
    isLoading,
    data: profile,
    refetch,
  } = useQuery("user", () =>
    userApi.getUserDetails().then((r) => r.data as SignupFormValues)
  );

  const onEdit = (values: SignupFormValues) => {
    const profileBody = utils.parsers.signupFormValuesToUser(values);
    profileBody.id = Number(loggedUserId) || -1;
    userApi
      .updateUser(profileBody)
      .then(() => {
        setEdit(false);
        refetch();
      })
      .catch((e) => setError(e?.response?.data?.error ?? ""));
  };

  const initialValues: any = useMemo(
    () => ({
      ...profile,
      birthDate: moment(profile?.birthDate ?? ""),
    }),
    [profile]
  );

  return (
    <section>
      <div className="flex items-center justify-between">
        <Typography variant="h4">Mi Perfil</Typography>
        <Button
          variant="contained"
          color={edit ? "error" : "primary"}
          onClick={() => setEdit(!edit)}
        >
          {edit ? "Cancelar" : "Editar"}
        </Button>
      </div>
      {isLoading ? (
        <Loader />
      ) : (
        <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
          <SignupForm
            disabled={!edit}
            initialValues={initialValues}
            onSubmit={onEdit}
          />
          <Error error={error} />
        </section>
      )}
    </section>
  );
};

export default MyProfileTab;
