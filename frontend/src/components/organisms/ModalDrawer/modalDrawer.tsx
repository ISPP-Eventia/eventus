import React from "react";

import { Modal, SwipeableDrawer } from "components/molecules";

const Component = (props: {
  title: string;
  opener: { title: string; color?: "primary" | "secondary" | "success" };
  children: React.ReactNode;
  actions?: { title: string; onClick: () => void }[];
}) => {
  return (
    <>
      <div className="hidden md:inline">
        <Modal {...props}></Modal>
      </div>
      <div className="md:hidden">
        <SwipeableDrawer {...props}></SwipeableDrawer>
      </div>
    </>
  );
};

export default Component;
