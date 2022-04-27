import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";

import { useQuery } from "react-query";
import { paymentApi } from "api";
import { Loader } from "./atoms";
import SetupForm from "./SetupForm";

// Make sure to call loadStripe outside of a component’s render to avoid
// recreating the Stripe object on every render.
// This is your test publishable API key.
const stripePromise = loadStripe(
  "pk_test_51KiLGlGL8A1xVoNtYB3BREtLiZ6z2VZzRStRg1DtB1LDkUHWC8Ins3whKwCnJEDhKpJNTuIZabwKNLGU8LAYa3Ly00dOx0qyda"
);

export default function StripeApp() {
  const { isLoading, data } = useQuery("initialPayment", () => {
    return paymentApi.getPaymentIntent().then((res) => res.data);
  });

  const clientSecret = data?.client_secret;

  const appearance: any = {
    theme: "stripe",
  };
  const options = {
    clientSecret,
    appearance,
  };

  return (
    <div className="App">
      {isLoading && <Loader />}
      {clientSecret && (
        <Elements options={options} stripe={stripePromise}>
          <SetupForm />
        </Elements>
      )}
    </div>
  );
}
