import React from "react";
import { SubmitHandler, useForm } from "react-hook-form";

import { participationType } from "types";
import { Button } from "@mui/material";

import { Modal } from "components/molecules";
import { ModalDrawer } from "components/organisms";

const Component = (props: {
  initialParticipation?: participationType.Participation;
  refetch: () => void;
}) => {
  const {
    control,
    getValues,
    trigger,
    clearErrors,
    formState: { errors },
    reset,
  } = useForm<participationType.Participation>({ mode: "onChange" });

  const [sent, setSent] = React.useState(false);

  React.useEffect(() => {
    reset({});
    clearErrors();
    trigger();
    if (props.initialParticipation) {
      control._defaultValues = {
        user_Id: props.initialParticipation?.user_Id,
        event_Id: props.initialParticipation?.event_Id,
        buy_date: props.initialParticipation?.buy_date,
        ticket: props.initialParticipation?.ticket,
        price: props.initialParticipation?.price,
      };
    }
    
  }, [props.initialParticipation, control, reset, clearErrors, trigger]);

  const onSubmitFailed = (e: any) => {
    clearErrors();
  };

  const onSubmitSuccess = () => {
    setSent(!sent);
    props.refetch();
    reset({});
  };

  const onSubmit: SubmitHandler<participationType.Participation> = (data) => {
  //if (Object.keys(errors).length === 0)
     // console.log(data)
    //participationApi
    //.createParticipation(data)
    //.then(() => onSubmitSuccess())
    //.catch((error) => onSubmitFailed(utils.parseErrors(error)));
    //}      
  };

  return (
    <Button onSubmit={() => onSubmit(getValues())} variant="contained" color="primary" fullWidth>
      <div>+ Participate 5€</div>
    </Button>
  );
};

export default Component;