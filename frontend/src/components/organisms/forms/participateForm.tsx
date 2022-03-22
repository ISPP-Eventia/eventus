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
      title="Participate"
      opener={{
        title: `Participate ${props.event?.price}€`,
        color: "primary",
      }}
      actions={[
        {
          title: `Participate ${props.event?.price}€`,
          onClick: onSubmit,
          color: "primary",
        },
      ]}
    >
      {error && <Error error="Couldn't create a participation" />}
    </ModalDrawer>
  );
};

export default Component;
