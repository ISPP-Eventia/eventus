import React, { useState, useEffect } from "react";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";

import CheckoutForm from "./CheckoutForm";
import "./stripe.css";

// Make sure to call loadStripe outside of a component’s render to avoid
// recreating the Stripe object on every render.
// This is your test publishable API key.
const stripePromise = loadStripe("pk_test_51KiLGlGL8A1xVoNtYB3BREtLiZ6z2VZzRStRg1DtB1LDkUHWC8Ins3whKwCnJEDhKpJNTuIZabwKNLGU8LAYa3Ly00dOx0qyda");

export default function App() {
    
  const [clientSecret, setClientSecret] = useState("");
  console.log(clientSecret)
  useEffect(() => {
    // Create PaymentIntent as soon as the page loads
    fetch("http://localhost:8080/stripe/paymentintent", {
      method: "POST",
      headers: { "Content-Type": "application/json" , "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWyiwuVrJSSi1LzSspLVbSUcpMLFGyMjQzsbC0MDG0NNdRSs1NzMwBKilITMrJd0gH8fSS83OBSlMrCsBKLQ3MDUwtzWsBlIdTJk0AAAA.WzmHWwBPYuWJscUD6pF0krJ46f4zL9cxN8jPUWHIq2o"},
      body: JSON.stringify({ "amount": 1100, "description": "testing strip", "currency": "EUR" }),
    })
      .then((res) => res.json())
      .then((data) => setClientSecret(data.client_secret));
  }, []);

  const appearance : any = {
    theme: 'stripe',
  };
  const options = {
    clientSecret,
    appearance,
  };

  return (
    <div className="App">
      {clientSecret && (
        <Elements options={options} stripe={stripePromise}>
          <CheckoutForm />
        </Elements>
      )}
    </div>
  );
}