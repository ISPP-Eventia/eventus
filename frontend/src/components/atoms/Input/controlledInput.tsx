import React from "react";

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