import { FieldProps } from "..";
import ControlledInput from "../controlledInput";

const Component = (props: FieldProps) => {
  return <ControlledInput {...props} type="text" />;
};

export default Component;
