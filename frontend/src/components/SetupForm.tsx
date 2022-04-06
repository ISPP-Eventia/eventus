import React, {useState} from 'react';
import {useStripe, useElements, PaymentElement} from '@stripe/react-stripe-js';
import { paymentApi } from "api";
import { useQuery } from "react-query";
import { EventUs } from "types";

const SetupForm = () => {
  const stripe = useStripe();
  const elements = useElements();

  const [errorMessage, setErrorMessage] = useState<any>(null);
  const { isLoading, data: payments } = useQuery("stripe", () =>
    paymentApi
      .getPaymentMethods()
      .then((response) => response.data as EventUs[])
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

    const {error} = await stripe.confirmSetup({
      //`Elements` instance that was used to create the Payment Element
      elements,
      confirmParams: {
        return_url: window.location.href+'/status',
      }
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
    <form onSubmit={handleSubmit}>
      <PaymentElement />
      <button disabled={!stripe}>Submit</button>
      {/* Show error message to your customers */}
      {errorMessage && <div>{errorMessage}</div>}
    </form>
  )
};

export default SetupForm;
