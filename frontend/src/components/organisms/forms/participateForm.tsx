import { useRef, useState } from "react";

import { participationApi } from "api";

import { ModalDrawer } from "components/organisms";
import { Error } from "components/atoms";
import { API_URL } from "api/axios";

const Component = (props: { event?: any; callback: () => void }) => {
  const [error, setError] = useState<boolean>(false);
  const closeModalRef = useRef<any>(null);
  const onSubmit = () => {
    setError(false);
    participationApi
      .createParticipation(props.event.id)
      .then((response) => {
        if (closeModalRef.current) {
          closeModalRef.current();
        }
        participationApi.getTicket(response.data.id);
        props.callback();
      })
      .catch((e) => setError(true));
  };

  return (
    <ModalDrawer
      title="Participar"
      opener={{
        title: `Participar por ${props.event?.price} €`,
        color: "primary",
      }}
      actions={[
        {
          title: `Participar por ${props.event?.price} €`,
          onClick: onSubmit,
          color: "primary",
        },
      ]}
      onClose={(closeFn) => {
        closeModalRef.current = closeFn;
      }}
    >
      {error && <Error error="Ya estás participando en este evento" />}
    </ModalDrawer>
  );
};

export default Component;
