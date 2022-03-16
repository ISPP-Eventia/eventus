import React from "react";

import { ModalDrawer } from "components/organisms";

const Component = (props: { event?: any }) => {
  const onSubmit = () => {
    // TODO: send data to server
    // if (Object.keys(errors).length === 0)
    // console.log(data)
    //participationApi
    //.createParticipation(data)
    //.then(() => onSubmitSuccess())
    //.catch((error) => onSubmitFailed(utils.parseErrors(error)));
    //}
    console.log("Participate on the event: " + props.event.id);
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
    ></ModalDrawer>
  );
};

export default Component;
