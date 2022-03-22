import { Form, Input, Button, DatePicker, InputNumber } from "antd";
import { EventFormValues } from "types";

export interface EventFormProps {
  initialValues?: Partial<EventFormValues>;
  onSubmit: (values: EventFormValues) => void;
}

const EventForm = (props: EventFormProps) => {
  const { initialValues, onSubmit } = props;
  const required = [{ required: true, message: "Este campo es obligatorio" }];
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
        <Input placeholder="Título del evento" />
      </Form.Item>

      <Form.Item name="fromTo" label="Fecha" rules={required}>
        <DatePicker.RangePicker showTime={true} style={{ width: "100%" }} />
      </Form.Item>

      <Form.Item name="description" label="Descripción">
        <Input.TextArea placeholder="Descripción del evento" />
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
