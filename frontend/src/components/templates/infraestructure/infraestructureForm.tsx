import React from "react";
import { useNavigate } from "react-router";
import { Card, Typography } from "@mui/material";
import ControlledInput from "components/atoms/Input/controlledInput";
import { Input } from "components/atoms/Input";


const InfraestructureForm = (props: {
    //TODO
  })  => {
    
    const control= {
          //TODO
        };


    const navigate = useNavigate();
  
    return (
      <Card className="flex cursor-pointer flex-col hover:shadow-xl">
        {/* {<Input.TextInput
            control={control}
            name="username"
            rules={{
              required: "This field is required",
              pattern: {
                value: /^[a-z0-9]+$/,
                message: "Use lowercase letters and numbers",
              },
            }}
          />} */}
      </Card>
    );
  };
  
  export default InfraestructureForm;
  