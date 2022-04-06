import { useRef, useState } from "react";
import { Button } from "antd";

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
      .then((response) => {
        if (closeModalRef.current) {
          closeModalRef.current();
        }
        participationApi.getTicket(response.data.id);
        props.callback();
      })
      .catch(() => setError(true));
  };

  return (
    <ModalDrawer
      title="Participar"
      opener={{
        title: `Participar ${props.event?.price}€`,
        color: "primary",
      }}
      onClose={(closeFn) => {
        closeModalRef.current = closeFn;
      }}
    >
      <>
        <Button type="primary" onClick={onSubmit} style={{ width: "100%" }}>
          Participar {props.event?.price}€
        </Button>
        {error && <Error error="Ya estás participando en este evento" />}
      </>
    </ModalDrawer>
  );
};

export default Component;
