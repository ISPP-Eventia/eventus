// PaymentStatus.jsx

import React, { useEffect } from "react";
import { Elements, useStripe } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import Notification from "components/atoms/Notification/notification";
import { Severity } from "components/atoms/Notification/severity";
import { useNavigate } from "react-router";

const stripePromise = loadStripe(
  "pk_test_51KiLGlGL8A1xVoNtYB3BREtLiZ6z2VZzRStRg1DtB1LDkUHWC8Ins3whKwCnJEDhKpJNTuIZabwKNLGU8LAYa3Ly00dOx0qyda"
);
const PaymentStatus = () => {
  const stripe = useStripe();
  const [notifications, setNotifications] = React.useState<
    { type: Severity; message: string }[]
  >([]);

  const navigate = useNavigate();

  const notify = (type: Severity, message: string) => {
    setNotifications((prev) => [...prev, { type, message }]);
  };

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
          notify?.("success", "Success! Your payment method has been saved.");
          break;

        case "processing":
          notify?.(
            "info",
            "Processing payment details. We'll update you when processing is complete."
          );
          break;

        case "requires_payment_method":
          // Redirect your user back to your payment page to attempt collecting
          // payment again
          notify?.(
            "error",
            "Failed to process payment details. Please try another payment method."
          );
          break;
      }
      navigate("/profile/payments");
    });
  }, [navigate, stripe]);

  return (
    <div id="Notifications">
      {notifications?.map((notification) => (
        <Notification
          severity={notification.type}
          message={notification.message}
        />
      ))}
    </div>
  );
};

const Component = () => (
  <Elements stripe={stripePromise}>
    <PaymentStatus />
  </Elements>
);

export default Component;
