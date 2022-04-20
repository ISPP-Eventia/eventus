import { Form, Input, Button, DatePicker, InputNumber } from "antd";

import { EventFormValues } from "types";

export interface EventFormProps {
  initialValues?: Partial<EventFormValues>;
  onSubmit: (values: EventFormValues) => void;
}

const EventForm = (props: EventFormProps) => {
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
      <Form.Item name="title" label="Título" rules={required}>
        <Input placeholder="Título de evento" />
      </Form.Item>

      <Form.Item name="fromTo" label="Fecha" rules={required}>
        <DatePicker.RangePicker
          style={{ width: "100%" }}
          disabledDate={(date) => date.isBefore(new Date(), "day")}
          showTime={{
            format: "HH:mm",
          }}
        />
      </Form.Item>

      <Form.Item name="description" label="Descripción" rules={required}>
        <Input.TextArea placeholder="Descripción de evento" />
      </Form.Item>

      <Form.Item name="price" label="Precio" rules={required}>
        <InputNumber
          addonAfter="€"
          min={0}
          placeholder="100"
          style={{ width: "100%" }}
        />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
          Crear
        </Button>
      </Form.Item>
    </Form>
  );
};

export default EventForm;
