import React, { useMemo, useState } from "react";
import { useQuery } from "react-query";
import { useNavigate } from "react-router";

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

  //TODO 
  //DELETE DUMMY
  const d = new Date();
  const dummy: SignupFormValues = {
    firstName: "pepe",
    lastName: "pepe",
    birthDate: d,
    email: "pepe",
    password: "pepe",
  }



  return (
    <section>
      <Typography variant="h4">Mi Perfil</Typography>
      {isLoading ? (
        <Loader />
      ) : (
        <section className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
          <SignupForm editMode={false} initialValues={profile}/>
          </section>
      )}
      <Button
      variant="contained"
      color="primary"
      onClick={() => navigate(`/profile/${loggedUserId}/edit`)}
    >
      Editar
    </Button>
    </section>
  );
    
};

export default MyProfileTab;
