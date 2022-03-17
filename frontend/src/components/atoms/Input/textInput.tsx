import React from "react";

import { FieldProps } from ".";
import ControlledInput from "./controlledInput";


const TextInput = (props: FieldProps) => {
  return <ControlledInput {...props} type="text" />;
};

export default TextInput;