import { FieldProps } from "..";
import ControlledInput from "../controlledInput";

const Component = (props: FieldProps) => {
  return <ControlledInput {...props} type="file" />;
};

export default Component;
