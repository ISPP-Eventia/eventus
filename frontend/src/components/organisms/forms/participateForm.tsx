import React from "react";

import { participationApi } from "api";

import { ModalDrawer } from "components/organisms";

const Component = (props: { event?: any }) => {
  const [error, setError] = React.useState<boolean>(false);
  const onSubmit = () => {
    participationApi
      .createParticipation(props.event.id)
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
      {error && (
        <span className="text-error">{"You are already participating"}</span>
      )}
    </ModalDrawer>
  );
};

export default Component;
