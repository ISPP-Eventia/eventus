// PaymentStatus.jsx

import React, { useState, useEffect } from "react";
import { Elements, useStripe } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
const stripePromise = loadStripe(
  "pk_test_51KiLGlGL8A1xVoNtYB3BREtLiZ6z2VZzRStRg1DtB1LDkUHWC8Ins3whKwCnJEDhKpJNTuIZabwKNLGU8LAYa3Ly00dOx0qyda"
);
const PaymentStatus = () => {
  const stripe = useStripe();
  const [message, setMessage] = useState<any>(null);

  useEffect(() => {
    if (!stripe) {
      return;
    }

    // Retrieve the "setup_intent_client_secret" query parameter appended to
    // your return_url by Stripe.js
    const clientSecret = new URLSearchParams(window.location.search).get(
      "setup_intent_client_secret"
    );

    if (!clientSecret) return;

    // Retrieve the SetupIntent
    stripe.retrieveSetupIntent(clientSecret).then(({ setupIntent }) => {
      if (!setupIntent) return;
      // Inspect the SetupIntent `status` to indicate the status of the payment
      // to your customer.
      //
      // Some payment methods will [immediately succeed or fail][0] upon
      // confirmation, while others will first enter a `processing` state.
      //
      // [0]: https://stripe.com/docs/payments/payment-methods#payment-notification
      switch (setupIntent.status) {
        case "succeeded":
          setMessage("Success! Your payment method has been saved.");
          break;

        case "processing":
          setMessage(
            "Processing payment details. We'll update you when processing is complete."
          );
          break;

        case "requires_payment_method":
          // Redirect your user back to your payment page to attempt collecting
          // payment again
          setMessage(
            "Failed to process payment details. Please try another payment method."
          );
          break;
      }
    });
  }, [stripe]);

  return <h1>{message}</h1>;
};

export default () => (
  <Elements stripe={stripePromise}>
    <PaymentStatus />
  </Elements>
);
