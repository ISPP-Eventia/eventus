import { useRef, useState } from "react";
import { Button } from "antd";
import { Style } from "@mui/icons-material";

import { participationApi } from "api";

import { ModalDrawer } from "components/organisms";
import { Error } from "components/atoms";

const Component = (props: { event?: any; callback: () => void }) => {
  const [error, setError] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);

  const closeModalRef = useRef<any>(null);
  const onSubmit = () => {
    setError("");
    setLoading(true);
    participationApi
      .createParticipation(props.event.id)
      .then((response) => {
        if (closeModalRef.current) {
          closeModalRef.current();
        }
        participationApi.getTicket(response.data.id);
        props.callback();
      })
      .catch((e) => setError(e?.response?.data?.error ?? ""))
      .finally(() => setLoading(false));
  };

  return (
    <ModalDrawer
      title="Participar"
      opener={{
        title: `${props.event?.price}€`,
        color: "primary",
        icon: <Style />,
      }}
      onClose={(closeFn) => {
        closeModalRef.current = closeFn;
      }}
    >
      <>
        <Button
          type="primary"
          onClick={onSubmit}
          style={{ width: "100%" }}
          disabled={loading}
          loading={loading}
        >
          Participar {props.event?.price}€
        </Button>
        {error && <Error error={error} />}
      </>
    </ModalDrawer>
  );
};

export default Component;
