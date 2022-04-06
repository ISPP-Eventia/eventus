import { useRef, useState } from "react";
import { Form, Button, InputNumber, Input } from "antd";

import { SponsorshipFormValues } from "types";
import { sponsorshipApi } from "api";

import { Error } from "components/atoms";
import { ModalDrawer } from "components/organisms";

const Component = (props: { event?: any; callback: () => void }) => {
  const [error, setError] = useState<boolean>(false);
  const required = [{ required: true, message: "Campo obligatorio" }];
  const closeModalRef = useRef<any>(null);

  const handleSubmit = (values: SponsorshipFormValues) => {
    setError(false);
    sponsorshipApi
      .createSponsorship({ ...values, eventId: props.event.id })
      .then(() => {
        if (closeModalRef.current) {
          closeModalRef.current();
        }
        props.callback();
      })
      .catch(() => {
        setError(true);
      });
  };

  return (
    <ModalDrawer
      title="Patrocinador"
      opener={{
        title: "Patrocinar",
        color: "success",
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

        <Form.Item>
          <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
            Patrocinar
          </Button>
        </Form.Item>
        {error && <Error error="No se pudo crear el patrocinio" />}
      </Form>
    </ModalDrawer>
  );
};

export default Component;
