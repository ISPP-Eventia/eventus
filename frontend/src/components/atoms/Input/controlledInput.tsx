import { FormLabel, TextField } from "@mui/material";
import { Controller } from "react-hook-form";
import { InputProps } from ".";

const ControlledInput = (props: InputProps) => {
  return (
    <>
      {props.useFormLabel && <FormLabel>{props.name.toUpperCase()}</FormLabel>}
      <Controller
        name={props.name}
        control={props.control}
        rules={props.rules}
        render={({ field, fieldState }) => (
          <TextField
            className="mt-6 grid w-full grid-cols-1 gap-5 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5"
            inputRef={field.ref}
            autoComplete="off"
            {...field}
            type={props.type}
            label={props.name}
            required={props.rules?.required}
            error={fieldState.invalid}
            helperText={fieldState.error?.message}
          />
        )}
      />
    </>
  );
};

export default ControlledInput;
