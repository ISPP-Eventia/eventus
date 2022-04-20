import { useRef, useState } from "react";
import { Button } from "antd";
import { Receipt } from "@mui/icons-material";

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
        title: `${props.event?.price}€`,
        color: "primary",
        icon: <Receipt />,
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
