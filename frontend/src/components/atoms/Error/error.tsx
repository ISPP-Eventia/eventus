const Component = (props: { error: string }) => {
  return <pre className="font-bold text-error">{props.error}</pre>;
};

export default Component;
