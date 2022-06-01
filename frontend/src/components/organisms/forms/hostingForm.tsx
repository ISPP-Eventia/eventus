import { useRef, useState } from "react";
import { Typography } from "@mui/material";
import { Gite } from "@mui/icons-material";
import { Button, Divider, Form } from "antd";

import { Hosting } from "types";
import { hostingApi } from "api";

import { Error } from "components/atoms";
import { ModalDrawer } from "components/organisms";

export interface HostingProps {
  hosting: Hosting;
  onSubmit: () => void;
}
const Component = (props: HostingProps) => {
  const [error, setError] = useState<boolean>(false);
  const closeModalRef = useRef<any>(null);
  const handleSubmit = () => {
    hostingApi
      .createHosting(props.hosting)
      .then(() => {
        if (closeModalRef.current) {
          closeModalRef.current();
        }
        props.onSubmit();
      })
      .catch(setError);
  };

  const eventId = localStorage.getItem("eventId");
  return (
    <ModalDrawer
      title="Solicitar alojamiento"
      opener={{
        title: "Solicitar alojamiento",
        color: "primary",
        icon: <Gite />,
        disable: !eventId,
      }}
      onClose={(closeFn) => {
        closeModalRef.current = closeFn;
      }}
    >
      <Form
        labelCol={{ span: 24 }}
        wrapperCol={{ span: 24 }}
        layout="vertical"
        onFinish={handleSubmit}
      >
        <Typography variant="body1" color="textSecondary">
          ¿Listo para solicitar alojamiento?, enviaremos la solicitud al dueño
          y, si la acepta, tu evento se actualizará con la localización del
          lugar.
        </Typography>
        <Divider />
        <Form.Item>
          <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
            Solicitar alojamiento por {props.hosting.price} €
          </Button>
        </Form.Item>
        {error && <Error error="Ya has enviado una petición de alojamiento" />}
      </Form>
    </ModalDrawer>
  );
};

export default Component;
