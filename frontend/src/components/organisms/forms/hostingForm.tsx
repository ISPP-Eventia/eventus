import React from "react";

import { ModalDrawer } from "components/organisms";
import { Location } from "types";

const Component = (props: { location?: Location }) => {
  const onSubmit = () => {
    // TODO: send data to server
  };

  return (
    <ModalDrawer
      title="Solicitar"
      opener={{
        title: `Solicitar Alojamiento ${props.location?.price}€`,
        color: "primary",
      }}
      actions={[
        {
          title: `Solicitar Alojamiento ${props.location?.price}€`,
          onClick: onSubmit,
          color: "primary",
        },
      ]}
    >
      {/*TODO add event input*/}
    </ModalDrawer>
  );
};

export default Component;
