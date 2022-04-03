import { Form, Input, Button, DatePicker } from "antd";

import { SignupFormValues } from "types";

export interface UserFormProps {
  initialValues?: Partial<SignupFormValues>;
  editMode: boolean;
  //TODO
  //quitar el ?
  onSubmit?: (values: SignupFormValues) => void;
}


const SignupForm = (props: UserFormProps) => {
  const { initialValues, onSubmit } = props;
  return (
    <Form
      labelCol={{ span: 10 }}
      wrapperCol={{ span: 24 }}
      layout="vertical"
      style={{ maxWidth: "500px", marginTop: "20px" }}
      onFinish={props.onSubmit}
      initialValues={initialValues}
    >
      <Form.Item
        name="firstName"
        label="Nombre"
        rules={[{ required: true, message: "Required Field" }]}
      >
      {(!props.editMode) ? <Input placeholder="Introduce tu nombre" /> : <Input placeholder="Introduce tu nombre" disabled/>}
        
      </Form.Item>

      <Form.Item
        name="lastName"
        label="Apellidos"
        rules={[{ required: true, message: "Required Field" }]}
      >
      {(!props.editMode) ? <Input placeholder="Introduce tus apellidos" /> :< Input placeholder="Introduce tus apellidos" disabled/>}
      </Form.Item>

      {/* <Form.Item
        name="birthDate"
        label="Fecha de nacimiento"
        rules={[{ required: true, message: "Required Field" }]}
      >
        {(!props.editMode) ? <DatePicker style={{ width: "100%" }} showTime={false}/> : <DatePicker style={{ width: "100%" }} showTime={false} disabled/>}
        
      </Form.Item> */}

      <Form.Item
        name="email"
        label="Email"
        rules={[{ required: true, message: "Invalid Email", type: "email" }]}
      >
        {(!props.editMode) ? <Input placeholder="Introduce tu Email" /> : <Input placeholder="Introduce tu Email" disabled/>}
        
      </Form.Item>

      <Form.Item
        name="password"
        label="Contraseña"
        rules={[{ required: true, message: "Required Field" }]}
      >
        {(!props.editMode) ? <Input type="password" placeholder="Introduce tu contraseña" /> : <Input type="password" placeholder="Introduce tu contraseña" disabled/>}
      </Form.Item>
      
      {(!props.editMode) ? <Form.Item>
        <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
          Sign Up
        </Button>
      </Form.Item> : <></>}
      
    </Form>
  );
};

export default SignupForm;
