import { useRef, useState } from "react";

import { participationApi } from "api";

import { ModalDrawer } from "components/organisms";
import { Error } from "components/atoms";

const Component = (props: { event?: any; callback: () => void }) => {
  const [error, setError] = useState<boolean>(false);
  const closeModalRef = useRef<any>(null);
  const onSubmit = () => {
    setError(false);
    participationApi
      .createParticipation(props.event.id)
      .then(() => {
        if (closeModalRef.current) {
          closeModalRef.current();
        }
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
      onClose={(closeFn) => {
        closeModalRef.current = closeFn;
      }}
    >
      {error && <Error error="Couldn't create a participation" />}
    </ModalDrawer>
  );
};

export default Component;
