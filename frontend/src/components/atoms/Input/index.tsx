import TextInput from "./textInput";

export type FieldProps = {
  name: string;
  control: any;
  error?: string;
  rules?: any;
};

export type RadioOption = {
  label: string;
  value: string;
};

export type RadioProps = FieldProps & {
  options: RadioOption[];
};

export type InputProps = FieldProps & {
  type?: "text" | "password";
  useFormLabel?: boolean;
};

export const Input = { TextInput };

