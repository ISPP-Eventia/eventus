import { ReactNode, useState } from "react";

import { Opener } from "types";

import { Modal, SwipeableDrawer } from "components/molecules";

const Component = (props: {
  title: string;
  opener: Opener;
  children?: ReactNode;
  actions?: {
    title: string;
    color?: "primary" | "secondary" | "success";
    onClick: () => void;
  }[];
  onClose: (closeFunction: (open: boolean) => void) => void;
}) => {
  const [open, setOpen] = useState(false);

  props.onClose(setOpen);

  return (
    <>
      <div className="hidden md:inline">
        <Modal open={open} setOpen={setOpen} {...props}></Modal>
      </div>
      <div className="md:hidden">
        <SwipeableDrawer
          open={open}
          setOpen={setOpen}
          {...props}
        ></SwipeableDrawer>
      </div>
    </>
  );
};

export default Component;
