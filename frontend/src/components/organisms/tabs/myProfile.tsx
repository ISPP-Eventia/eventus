import React, { useMemo } from "react";
import { useQuery } from "react-query";
import { useNavigate } from "react-router";
import moment from "moment";

import { userApi } from "api";
import { SignupFormValues } from "types";

import { SignupForm } from "components/organisms";
import { Loader } from "components/atoms";
import { Button, Typography } from "@mui/material";

const MyProfileTab = () => {

  const navigate = useNavigate();
  const loggedUserId = localStorage.getItem("userId");

  const { isLoading, data: profile } = useQuery("user", () =>  
    userApi
      .getUserDetails()
      .then((response) => response.data as SignupFormValues)
  );
  
  const handleSubmit = () => {
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
      <Typography variant="h4">Mi Perfil</Typography>
      {isLoading ? (
        <Loader />
      ) : (
        <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
          <SignupForm editMode={true} initialValues={initialValues} onSubmit={handleSubmit}/>
          </section>
      )}
      <Button
      variant="contained"
      color="primary"
      onClick={() => navigate(`/profile/${loggedUserId}/edit`)}
    >
      IR A EDITAR
    </Button>
    </section>
  );
    
};

export default MyProfileTab;
