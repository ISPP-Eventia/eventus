import { Form, Input, Button, DatePicker } from "antd";
import { SignupFormValues } from "types";

export interface UserFormProps {
  initialValues?: Partial<SignupFormValues>;
  disabled?: boolean;
  onSubmit: (values: SignupFormValues) => void;
}

const SignupForm = (props: UserFormProps) => {
  const { initialValues, onSubmit } = props;
  return (
    <Form
      autoComplete="off"
      labelCol={{ span: 10 }}
      wrapperCol={{ span: 24 }}
      layout="vertical"
      style={{ maxWidth: "500px", marginTop: "20px" }}
      onFinish={onSubmit}
      initialValues={initialValues}
    >
      <Form.Item
        name="firstName"
        label="Nombre"
        rules={[{ required: true, message: "Required Field" }]}
      >
        <Input placeholder="Introduce tu nombre" disabled={props.disabled} />
      </Form.Item>

      <Form.Item
        name="lastName"
        label="Apellidos"
        rules={[{ required: true, message: "Required Field" }]}
      >
        <Input
          placeholder="Introduce tus apellidos"
          disabled={props.disabled}
        />
      </Form.Item>
      <Form.Item
        name="birthDate"
        label="Fecha de nacimiento"
        rules={[{ required: true, message: "Required Field" }]}
      >
        <DatePicker
          disabledDate={(date) => date.isAfter(new Date(), "day")}
          style={{ width: "100%" }}
          showTime={false}
          disabled={props.disabled}
        />
      </Form.Item>

      <Form.Item
        name="email"
        label="Email"
        rules={[{ required: true, message: "Invalid Email", type: "email" }]}
      >
        <Input placeholder="Introduce tu Email" disabled={props.disabled} />
      </Form.Item>

      <Form.Item
        name="password"
        label="Contraseña"
        rules={[{ required: true, message: "Required Field" }]}
      >
        <Input
          type="password"
          placeholder="Introduce tu contraseña"
          disabled={props.disabled}
        />
      </Form.Item>

      {!props.disabled && (
        <Form.Item>
          <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
            Confirmar
          </Button>
        </Form.Item>
      )}
    </Form>
  );
};

export default SignupForm;
