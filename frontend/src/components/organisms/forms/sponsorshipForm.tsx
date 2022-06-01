import { useRef, useState } from "react";
import { Typography } from "@mui/material";
import { AttachMoney } from "@mui/icons-material";
import { Form, Button, InputNumber, Input, Divider } from "antd";

import { SponsorshipFormValues } from "types";
import { sponsorshipApi } from "api";

import { Error } from "components/atoms";
import { ModalDrawer } from "components/organisms";
import { UploadForm } from "./uploadForm";

const Component = (props: { event?: any; callback: () => void }) => {
  const [error, setError] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);

  const required = [{ required: true, message: "Campo obligatorio" }];
  const closeModalRef = useRef<any>(null);

  const handleSubmit = (values: SponsorshipFormValues) => {
    setError(false);
    setLoading(true);
    sponsorshipApi
      .createSponsorship({
        ...values,
        eventId: props.event.id,
        mediaIds: values.media.map((m) => m.id).join(","),
        media: undefined,
      })
      .then(() => {
        if (closeModalRef.current) {
          closeModalRef.current();
        }
        props.callback();
      })
      .catch(() => {
        setError(true);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return (
    <ModalDrawer
      title="Patrocinar evento"
      opener={{
        color: "success",
        icon: <AttachMoney />,
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
        <Form.Item name="name" label="Mensaje" rules={required}>
          <Input style={{ width: "100%" }} />
        </Form.Item>
        <Form.Item name="quantity" label="Cantidad" rules={required}>
          <InputNumber min={0} style={{ width: "100%" }} />
        </Form.Item>
        <Form.Item name="media" label="Media">
          <UploadForm />
        </Form.Item>

        <Typography variant="body1" color="textSecondary">
          Nota: Mostraremos las ofertas ordenadas por cantidad ofrecida.
        </Typography>
        <Divider />
        <Form.Item>
          <Button
            type="ghost"
            style={{
              background: "#3c8c50",
              borderColor: "#3c8c50",
              color: "white",
            }}
            htmlType="submit"
            className="w-full"
            disabled={loading}
            loading={loading}
          >
            Patrocinar
          </Button>
        </Form.Item>
        {error && <Error error="No se pudo crear el patrocinio" />}
      </Form>
    </ModalDrawer>
  );
};

export default Component;
