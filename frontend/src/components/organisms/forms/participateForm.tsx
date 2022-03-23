import React from "react";

import { participationApi } from "api";

import { ModalDrawer } from "components/organisms";
import { Error } from "components/atoms";

const Component = (props: { event?: any; callback: () => void }) => {
  const [error, setError] = React.useState<boolean>(false);

  const onSubmit = () => {
    setError(false);
    participationApi
      .createParticipation(props.event.id)
      .then(() => {
        props.callback();
      })
      .catch((e) => setError(true));
  };

  return (
    <ModalDrawer
      title="Participar"
      opener={{
        title: `Participar ${props.event?.price}€`,
        color: "primary",
      }}
      actions={[
        {
          title: `Participar ${props.event?.price}€`,
          onClick: onSubmit,
          color: "primary",
        },
      ]}
    >
      {error && <Error error="No se ha podido registrar su participación" />}
    </ModalDrawer>
  );
};

export default Component;
