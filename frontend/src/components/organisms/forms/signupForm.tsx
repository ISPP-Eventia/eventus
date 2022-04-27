import { Form, Input, Button, DatePicker, Checkbox } from "antd";
import moment from "moment";
import { Link } from "react-router-dom";

import { SignupFormValues } from "types";
import utils from "utils";

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
        rules={[{ required: true, message: "Es Obligatorio" }]}
      >
        <Input placeholder="Introduce tu nombre" disabled={props.disabled} />
      </Form.Item>

      <Form.Item
        name="lastName"
        label="Apellidos"
        rules={[{ required: true, message: "Es Obligatorio" }]}
      >
        <Input
          placeholder="Introduce tus apellidos"
          disabled={props.disabled}
        />
      </Form.Item>
      <Form.Item
        name="birthDate"
        label="Fecha de nacimiento"
        rules={[{ required: true, message: "Es Obligatorio" }]}
      >
        <DatePicker
          disabledDate={(date) => date.isAfter(utils.date.getDateYearsAgo(16))}
          defaultValue={moment(utils.date.getDateYearsAgo(16))}
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
        rules={[
          { required: true, message: "Es Obligatorio" },
          {
            min: 8,
            message: "Es demasiado corta, debe tener al menos 8 caracteres",
          },
        ]}
      >
        <Input
          type="password"
          placeholder="Introduce tu contraseña"
          disabled={props.disabled}
        />
      </Form.Item>

      <Form.Item
        name="checkbox"
        valuePropName="checked"
        rules={[
          {
            validator: (_, value) =>
              value
                ? Promise.resolve()
                : Promise.reject(new Error("Debe aceptar los términos")),
          },
        ]}
      >
        <Checkbox disabled={props.disabled}>
          Acepto los{" "}
          <Link target="_blank" to={"/terms"}>
            Términos y condiciones
          </Link>
        </Checkbox>
      </Form.Item>

      {!props.disabled && (
        <Form.Item>
          <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
            Registrarse
          </Button>
        </Form.Item>
      )}
    </Form>
  );
};

export default SignupForm;
