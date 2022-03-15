import React from "react";

import { Modal, SwipeableDrawer } from "components/molecules";

const Component = (props: {
  title: string;
  opener: { title: string; color?: "primary" | "secondary" | "success" };
  children: JSX.Element | JSX.Element[] | string;
  actions?: { title: string; onClick: () => void }[];
}) => {
  return (
    <>
      <div className="hidden md:inline">
        <Modal {...props}>TEST CONTENT</Modal>
      </div>
      <div className="md:hidden">
        <SwipeableDrawer {...props}>TEST CONTENT</SwipeableDrawer>
      </div>
    </>
  );
};

export default Component;
