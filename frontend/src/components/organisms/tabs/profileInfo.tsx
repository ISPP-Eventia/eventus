import React, { useState } from "react";
import { ProfileForm } from "components/organisms";
import { Button }from "antd";

const onSignup = () => {
  console.log("Pepe")
};



const ProfileInfoTab = () => {
  const [clickedButton, setClickedButton] = useState('Volver');

  const buttonHandler = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    const button: HTMLButtonElement = event.currentTarget;
    setClickedButton(button.name);
  };

  if (clickedButton === 'Volver'){
    return <section><Button type="primary" name="Editar" onClick={buttonHandler}>Editar</Button>
    <ProfileForm onSubmit={onSignup} editMode={true} />
    </section>;
  }else{
    return <section><Button type="primary" name="Volver" onClick={buttonHandler}>Volver a mi información</Button>
    <ProfileForm onSubmit={onSignup} editMode={false} />
    </section>;
  }
    
};

export default ProfileInfoTab;
