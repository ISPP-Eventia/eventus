import { Form, Input, Button } from "antd";

import { LoginFormValues } from "types";

const LoginForm = (props: { onSubmit: (values: LoginFormValues) => void }) => {
  return (
    <Form
      labelCol={{ span: 10 }}
      wrapperCol={{ span: 24 }}
      layout="vertical"
      style={{ maxWidth: "500px", marginTop: "20px" }}
      onFinish={props.onSubmit}
    >
      <Form.Item
        name="email"
        label="Email"
        rules={[
          {
            required: true,
            message: "Invalid Email",
            type: "email",
          },
        ]}
      >
        <Input placeholder="Introduce tu Email" />
      </Form.Item>

      <Form.Item
        name="password"
        label="Contraseña"
        rules={[{ required: true, message: "Es Obligatorio" }]}
      >
        <Input type="password" placeholder="Introduce tu contraseña" />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
          Iniciar sesión
        </Button>
      </Form.Item>
    </Form>
  );
};

export default LoginForm;
