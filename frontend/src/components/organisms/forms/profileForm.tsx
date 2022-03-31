import { Form, Input, Button, DatePicker } from "antd";

import { User } from "types";

const ProfileForm = (props: {
  editMode: boolean,
  initialValues?: Partial<User>;
  onSubmit: (values: User) => void;
}) => {
  console.log(props.editMode)
  return (
    <Form
      labelCol={{ span: 10 }}
      wrapperCol={{ span: 24 }}
      layout="vertical"
      style={{ maxWidth: "500px", marginTop: "20px" }}
      onFinish={props.onSubmit}
      initialValues={props.initialValues}
    >
      <Form.Item
        name="firstName"
        label="Nombre"
        rules={[{ required: true, message: "Required Field" }]}
      >
        {(props.editMode) ? <Input placeholder="Introduce tu nombre" readOnly disabled/> :  <Input placeholder="Introduce tu nombre"/>}
      </Form.Item>

      <Form.Item
        name="lastName"
        label="Apellidos"
        rules={[{ required: true, message: "Required Field" }]}
      >
        {(props.editMode) ? <Input placeholder="Introduce tus apellidos" readOnly disabled/> :  <Input placeholder="Introduce tus apellidos"/>}
      </Form.Item>

      <Form.Item
        name="birthDate"
        label="Fecha de nacimiento"
        rules={[{ required: true, message: "Required Field" }]}
      >
        {(props.editMode) ? <DatePicker style={{ width: "100%" }} showTime={false} disabled/> :  <DatePicker style={{ width: "100%" }} showTime={false} />}
      </Form.Item>

      <Form.Item
        name="email"
        label="Email"
        rules={[{ required: true, message: "Invalid Email", type: "email" }]}
      >
        {(props.editMode) ? <Input placeholder="Introduce tu Email" readOnly disabled/> :  <Input placeholder="Introduce tu Email"/>}
      </Form.Item>

      <Form.Item
        name="password"
        label="Contraseña"
        rules={[{ required: true, message: "Required Field" }]}
      >
        {(props.editMode) ? <Input type="password" placeholder="Introduce tu contraseña" readOnly disabled/> :  <Input type="password" placeholder="Introduce tu contraseña" />}
      </Form.Item>

      <Form.Item
        name="confirmPassword"
        label="Confirmar contraseña"
        rules={[{ required: true, message: "Required Field" }]}
      >
        {(props.editMode) ? <Input type="password" placeholder="Confirma tu contraseña" readOnly disabled/> :  <Input type="password" placeholder="Confirma tu contraseña" />}
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
          Confirmar
        </Button>
      </Form.Item>
    </Form>

  );
};

export default ProfileForm;