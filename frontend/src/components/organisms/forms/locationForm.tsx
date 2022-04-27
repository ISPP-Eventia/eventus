import { Form, Input, Button, InputNumber } from "antd";

import { LocationFormValues } from "types";
import { UploadForm } from "./uploadForm";

export interface LocationFormProps {
  initialValues?: Partial<LocationFormValues>;
  onSubmit: (values: LocationFormValues) => void;
}

const LocationForm = (props: LocationFormProps) => {
  const { initialValues, onSubmit } = props;
  const required = [{ required: true, message: "Es Obligatorio" }];

  return (
    <Form
      labelCol={{ span: 4 }}
      wrapperCol={{ span: 24 }}
      layout="vertical"
      style={{ maxWidth: "500px", marginTop: "20px" }}
      onFinish={onSubmit}
      initialValues={initialValues}
    >
      <Form.Item name="name" label="Nombre" rules={required}>
        <Input placeholder="Nombre del alojamiento" />
      </Form.Item>

      <Form.Item name="description" label="Descripción" rules={required}>
        <Input.TextArea placeholder="Descripción del alojamiento" />
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
            marginRight: "15px",
          }}
        >
          <InputNumber
            placeholder="37.12352"
            style={{ width: "100%" }}
            min={-90}
            max={90}
          />
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
          <InputNumber
            placeholder="-5.921732"
            style={{ width: "100%" }}
            min={-180}
            max={180}
          />
        </Form.Item>
      </Form.Item>

      <Form.Item name="price" label="Precio" rules={required}>
        <InputNumber
          addonAfter="€"
          min={0}
          placeholder="100"
          style={{ width: "100%" }}
        />
      </Form.Item>

      <Form.Item name="media" label="Media">
        <UploadForm />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
          Crear
        </Button>
      </Form.Item>
    </Form>
  );
};

export default LocationForm;
