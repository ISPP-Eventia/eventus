import React from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { Form, Input, Button, InputNumber, Upload} from "antd";
import { UploadOutlined } from '@ant-design/icons';
import { ModalDrawer, SponsorshipForm } from "components/organisms";
import { SponsorshipFormValues } from "types";

const Component = (props: { event?:any }) => {
  const {
    control,
    getValues,
    formState: { errors },
  } = useForm<SponsorshipFormValues>({ mode: "onChange" });

  const [sent, setSent] = React.useState(false);
  const normFile = (e: any) => {
    console.log('Upload event:', e);
    if (Array.isArray(e)) {
      return e;
    }
    return e && e.fileList;
  };

  const onSubmit: SubmitHandler<SponsorshipFormValues> = (data) => {
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
      <Form
      labelCol={{ span: 4 }}
      wrapperCol={{ span: 24 }}
      layout="vertical"
      style={{ maxWidth: "500px", margin: "0 auto" }}
      onFinish={onSubmit}
    >

      <Form.Item name="price" label="Price">
        <InputNumber
          min={0}
          style={{ width: "100%" }}
        />
      </Form.Item>

      <Form.Item
        name="upload"
        label="Upload"
        valuePropName="fileList"
        getValueFromEvent={normFile}
      >
        <Upload name="logo" action="/upload.do" listType="picture" >
          <Button style={{ width: "100%" }} icon={<UploadOutlined />}>Click to upload</Button>
        </Upload>
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" style={{ width: "100%" }}>
          Sponsor Offer
        </Button>
      </Form.Item>
    </Form>
    </ModalDrawer>
  );
};

export default Component;