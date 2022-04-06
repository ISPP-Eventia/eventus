import { Error } from "components/atoms";
import Page from "./page";

const ErrorPage = (props: { errorMessage?: string }) => {
  return (
    <Page title="Error">
      <Error
        error={
          props.errorMessage ||
          "Algo ha salido mal, puede que lo que buscabas no exista"
        }
      />
    </Page>
  );
};

export default ErrorPage;
