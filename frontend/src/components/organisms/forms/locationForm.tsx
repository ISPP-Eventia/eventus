import { Form, Input, Button, DatePicker, InputNumber } from "antd";

import { LocationFormValues } from "types";

export interface LocationFormProps {
  initialValues?: Partial<LocationFormValues>;
  onSubmit: (values: LocationFormValues) => void;
}

const LocationForm = (props: LocationFormProps) => {
  const { initialValues, onSubmit } = props;
  const required = [{ required: true, message: "Required Field" }];

  return (
    <Form
      labelCol={{ span: 4 }}
      wrapperCol={{ span: 24 }}
      layout="vertical"
      style={{ maxWidth: "500px", marginTop: "20px" }}
      onFinish={onSubmit}
      initialValues={initialValues}
    >
      <Form.Item name="name" label="Name" rules={required}>
        <Input placeholder="Location name" />
      </Form.Item>

      <Form.Item name="description" label="Description">
        <Input.TextArea placeholder="Location description" />
      </Form.Item>
      <Form.Item>
        <Form.Item
          name="latitude"
          label="Latitud"
          rules={[{ required: true }]}
          style={{
            display: "inline-block",
            width: "calc(50% - 8px)",
            margin: 0,
            marginRight: "15px"
          }}
        >
          <InputNumber placeholder="37.12352" style={{ width: "100%" }} />
        </Form.Item>
        <Form.Item
          name="longitude"
          label="Longitud"
          rules={[{ required: true }]}
          style={{
            display: "inline-block",
            width: "calc(50% - 8px)",
            margin: 0,
          }}
        >
          <InputNumber placeholder="-5.921732" style={{ width: "100%" }} />
        </Form.Item>
      </Form.Item>

      <Form.Item name="price" label="Price" rules={required}>
        <InputNumber
          addonAfter="€"
          min={0}
          placeholder="100"
          style={{ width: "100%" }}
        />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
          Create
        </Button>
      </Form.Item>
    </Form>
  );
};

export default LocationForm;
