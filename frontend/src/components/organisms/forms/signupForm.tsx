import { Form, Input, Button, DatePicker } from "antd";

import { SignupFormValues } from "types";

const SignupForm = (props: {
  onSubmit: (values: SignupFormValues) => void;
}) => {
  return (
    <Form
      labelCol={{ span: 10 }}
      wrapperCol={{ span: 24 }}
      layout="vertical"
      style={{ maxWidth: "500px", marginTop: "20px" }}
      onFinish={props.onSubmit}
    >
      <Form.Item
        name="firstName"
        label="Nombre"
        rules={[{ required: true, message: "Required Field" }]}
      >
        <Input placeholder="Introduce tu nombre" />
      </Form.Item>

      <Form.Item
        name="lastName"
        label="Apellidos"
        rules={[{ required: true, message: "Required Field" }]}
      >
        <Input placeholder="Introduce tus apellidos" />
      </Form.Item>

      <Form.Item
        name="birthDate"
        label="Fecha de nacimiento"
        rules={[{ required: true, message: "Required Field" }]}
      >
        <DatePicker style={{ width: "100%" }} showTime={false} />
      </Form.Item>

      <Form.Item
        name="email"
        label="Email"
        rules={[{ required: true, message: "Invalid Email", type: "email" }]}
      >
        <Input placeholder="Introduce tu Email" />
      </Form.Item>

      <Form.Item
        name="password"
        label="Contraseña"
        rules={[{ required: true, message: "Required Field" }]}
      >
        <Input type="password" placeholder="Introduce tu contraseña" />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
          Sign Up
        </Button>
      </Form.Item>
    </Form>
  );
};

export default SignupForm;
