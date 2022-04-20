import React, { useState } from "react";
import {
  useStripe,
  useElements,
  PaymentElement,
} from "@stripe/react-stripe-js";
import { paymentApi } from "api";
import { useQuery } from "react-query";
import { PaymentMethod } from "types";
import { Button } from "@mui/material";
import { PaymentTable } from "./templates";

const SetupForm = () => {
  const stripe = useStripe();
  const elements = useElements();

  const [errorMessage, setErrorMessage] = useState<any>(null);
  const [showPayment, setShowPayment] = useState(false);
  const { isLoading, data: payments } = useQuery("stripe", () =>
    paymentApi.getPaymentMethods().then((response) => response.data)
  );
  const handleSubmit = async (event: any) => {
    // We don't want to let default form submission happen here,
    // which would refresh the page.
    event.preventDefault();

    if (!stripe || !elements) {
      // Stripe.js has not yet loaded.
      // Make sure to disable form submission until Stripe.js has loaded.
      return;
    }

    const { error } = await stripe.confirmSetup({
      //`Elements` instance that was used to create the Payment Element
      elements,
      confirmParams: {
        return_url: window.location.href + "/status",
      },
    });

    if (error) {
      // This point will only be reached if there is an immediate error when
      // confirming the payment. Show error to your customer (for example, payment
      // details incomplete)
      setErrorMessage(error.message);
    } else {
      // Your customer will be redirected to your `return_url`. For some payment
      // methods like iDEAL, your customer will be redirected to an intermediate
      // site first to authorize the payment, then redirected to the `return_url`.
    }
  };
  return (
    <div>
      <PaymentTable payments={payments} loading={isLoading} />
      <br />
      {showPayment ? (
        <form>
          <PaymentElement />
          <br />
          <Button
            variant="contained"
            color="primary"
            disabled={!stripe}
            onClick={handleSubmit}
          >
            Añadir
          </Button>
          {/* Show error message to your customers */}
          {errorMessage && <div>{errorMessage}</div>}
        </form>
      ) : (
        <Button
          variant="contained"
          color="primary"
          onClick={() => setShowPayment(true)}
        >
          Añadir método
        </Button>
      )}
    </div>
  );
};

export default SetupForm;
