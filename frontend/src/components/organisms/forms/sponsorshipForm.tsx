import React from "react";
import { Form, Button, InputNumber } from "antd";

import { SponsorshipFormValues } from "types";
import { sponsorshipApi } from "api";

import { Error } from "components/atoms";
import { ModalDrawer } from "components/organisms";

const Component = (props: { event?: any; callback: () => void }) => {
  const [error, setError] = React.useState<boolean>(false);
  const required = [{ required: true, message: "Required Field" }];

  const handleSubmit = (values: SponsorshipFormValues) => {
    setError(false);
    sponsorshipApi
      .createSponsorship({ ...values, eventId: props.event.id })
      .then(() => {
        props.callback();
      })
      .catch((e) => {
        setError(true);
      });
  };

  return (
    <ModalDrawer
      title="Sponsor"
      opener={{
        title: "Sponsor Offer",
        color: "success",
      }}
    >
      <Form
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 24 }}
        layout="vertical"
        onFinish={handleSubmit}
      >
        <Form.Item name="quantity" label="Quantity" rules={required}>
          <InputNumber min={0} style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
            Sponsor Offer
          </Button>
        </Form.Item>
        {error && <Error error="Couldn't create the sponsorship" />}
      </Form>
    </ModalDrawer>
  );
};

export default Component;
