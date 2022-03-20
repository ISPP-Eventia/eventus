import Text from "./Text/TextInput";
import File from "./File/FileInput";

export type FieldProps = {
  name: string;
  control: any;
  error?: string;
  rules?: any;
};

export type InputProps = FieldProps & {
  type?: "text" | "password" |"file";
  useFormLabel?: boolean;
};

export const Input = { Text, File };