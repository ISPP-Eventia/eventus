import React from "react";
import { SubmitHandler, useForm } from "react-hook-form";

import { ModalDrawer } from "components/organisms";
import { Input } from "components/atoms/Input";
import { SponsorFormField } from "types";

const Component = (props: { event?:any }) => {
  const {
    control,
    getValues,
    formState: { errors },
  } = useForm<SponsorFormField>({ mode: "onChange" });

  const [sent, setSent] = React.useState(false);

  const onSubmit: SubmitHandler<SponsorFormField> = (data) => {
    console.log(data)
    // TODO: send data to server
    // if (Object.keys(errors).length === 0)
    // console.log(data)
    //sponsorApi
    //.createSponsor(data)
    //.then(() => onSubmitSuccess())
    //.catch((error) => onSubmitFailed(utils.parseErrors(error)));
    //}
  };

  return (
    <ModalDrawer
      title="Sponsor"
      opener={{
        title: "Sponsor Offer",
        color: "success",
      }}
      actions={
        [
        {
          title: "Sponsor Offer",
          onClick: () => onSubmit(getValues()),
          color: "success",
        },
      ]}
    >
  <Input.Text control={control}  name="Price" />
  &nbsp;
  <Input.File control={control}  name="" />

      &nbsp;
    </ModalDrawer>
  );
};

export default Component;