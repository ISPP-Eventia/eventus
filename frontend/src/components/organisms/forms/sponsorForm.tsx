import React from "react";

import { ModalDrawer } from "components/organisms";

const Component = (props: { event?: any }) => {
  const onSubmit = () => {
    // TODO: send data to server
    // if (Object.keys(errors).length === 0)
    // console.log(data)
    //sponsorApi
    //.createSponsor(data)
    //.then(() => onSubmitSuccess())
    //.catch((error) => onSubmitFailed(utils.parseErrors(error)));
    //}
  };

  return (
    <ModalDrawer
      title="Sponsor"
      opener={{
        title: `Sponsor Offer`,
        color: "success",
      }}
      actions={[
        {
          title: `Sponsor Offer`,
          onClick: onSubmit,
          color: "primary",
        },
      ]}
    ></ModalDrawer>
  );
};

export default Component;