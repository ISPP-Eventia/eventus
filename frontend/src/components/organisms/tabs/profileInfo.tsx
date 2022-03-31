import React, { useState } from "react";
import { useQuery } from "react-query";
import { SubmitHandler } from "react-hook-form";
import { Button }from "antd";

import { userApi } from "api";
import { User, UserFormValues } from "types";

import { ProfileForm } from "components/organisms";
import { Loader } from "components/atoms";
import { Error } from "components/atoms";

const ProfileInfoTab = () => {
  const [clickedButton, setClickedButton] = useState('0');
  const [error, setError] = useState<string>("");

  const buttonHandler = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    const button: HTMLButtonElement = event.currentTarget;
    setClickedButton(button.name);
  };

  const { isLoading, data: info } = useQuery("user", () =>
    userApi
      .getUserDetails()
      .then((response) => response.data as UserFormValues)
  );

  const onChange: SubmitHandler<User> = (data) => {
    if (data?.id && clickedButton === "1")
        userApi
          .updateUser(data)
          .then()
          .catch((e) => {
        setError(e?.response?.data?.error ?? "");
      });
};


  if (clickedButton === '0'){
    return isLoading || !info ? (<Loader />) : <section><Button type="primary" name="1" onClick={buttonHandler}>Editar</Button>
    <ProfileForm onSubmit={onChange} editMode={true} initialValues={info}/>
    {error && <Error error={error} />}</section>;
  }else{
    return isLoading || !info ? (<Loader />) : <section><Button type="primary" name="0" onClick={buttonHandler}>Volver a mi información</Button>
    <ProfileForm onSubmit={onChange} editMode={false} initialValues={info}/>
    {error && <Error error={error} />} </section>;
  }
    
};

export default ProfileInfoTab;
