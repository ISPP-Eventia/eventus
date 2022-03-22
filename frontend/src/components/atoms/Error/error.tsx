import React from "react";

const Component = (props: { error: string }) => {
  return <span className="font-bold text-error">{props.error}</span>;
};

export default Component;
