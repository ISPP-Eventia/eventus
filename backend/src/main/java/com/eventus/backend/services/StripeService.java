package com.eventus.backend.services;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.User;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListPaymentMethodsParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodListParams;
import com.stripe.param.SetupIntentCreateParams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class StripeService {

    @Value("${stripe.key.secret}")
    String secretKey;

    public PaymentIntent createNewPaymentIntent(Map<String,String> paymentIntentObj, User user) throws StripeException {
        Stripe.apiKey = secretKey;
        PaymentMethodCollection paymentMethods = getPaymentMethods(user);
        if(!paymentMethods.getData().isEmpty()){
          PaymentIntentCreateParams paymentParams =
          PaymentIntentCreateParams.builder()
            .setAmount(Long.valueOf(paymentIntentObj.get("amount")))
            .setCurrency("eur")
            .setCustomer(user.getCustomerId())
            .setPaymentMethod(paymentMethods.getData().get(0).getId())
            .setConfirm(true)
            .setOffSession(true)
            .build();

          PaymentIntent payment = null;
          try {
            PaymentIntent.create(paymentParams);
          } catch (CardException err) {
            System.out.println("Error code is : " + err.getCode());
            String paymentIntentId = err.getStripeError().getPaymentIntent().getId();
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            System.out.println(paymentIntent.getId());
          }
          return payment;
        }else{
          return null;
        }
          
    }
    

    public String createCustomer(User user) {
      Stripe.apiKey = secretKey;

      CustomerCreateParams params = CustomerCreateParams.builder()
        .setEmail(user.getEmail())
        .setName(user.getFirstName() + " " + user.getLastName())
        .build();

      Customer customer = null;
      try {
        customer = Customer.create(params);
        return customer.getId();
      } catch (StripeException e) {
        e.printStackTrace();
      }
      return "";
    }

    public SetupIntent createInitialPaymentIntent(User user) throws StripeException {
      Stripe.apiKey = secretKey;
      SetupIntentCreateParams  params =
      SetupIntentCreateParams .builder()
        .setCustomer(user.getCustomerId())
        .addPaymentMethodType("card")
        .build();
      return SetupIntent.create(params);
    }
    


    public PaymentMethodCollection getPaymentMethods(User user) {
      try {
        Customer customer = Customer.retrieve(user.getCustomerId());
        CustomerListPaymentMethodsParams params =
          CustomerListPaymentMethodsParams.builder()
            .setType(CustomerListPaymentMethodsParams.Type.CARD)
            .build();
        PaymentMethodCollection paymentMethods = customer.listPaymentMethods(params);
        return paymentMethods;
      } catch (StripeException e) {
        e.printStackTrace();
        return null;
      }
    }


    //  public PaymentIntent confirmPaymentIntent(String id) throws StripeException {
    //     Stripe.apiKey = secretKey;
    //     PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
    //     Map<String, Object> params = new HashMap<>();
    //     params.put("payment_method", "pm_card_visa");
    //     paymentIntent.confirm(params);
    //     return paymentIntent;
    // }

    // public PaymentIntent cancelPaymentIntent(String id) throws StripeException {
    //     Stripe.apiKey = secretKey;
    //     PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
    //     paymentIntent.cancel();
    //     return paymentIntent;
    // }

    

    // public Account createAccount(Map<String,String> createParams) throws StripeException{
    //     Stripe.apiKey = secretKey;
    //     Map<String, Object> cardPayments = new HashMap<>();
    //     cardPayments.put("requested", true);
    //     Map<String, Object> transfers = new HashMap<>();
    //     transfers.put("requested", true);
    //     Map<String, Object> capabilities = new HashMap<>();
    //     capabilities.put("card_payments", cardPayments);
    //     capabilities.put("transfers", transfers);
    //     Map<String, Object> params = new HashMap<>();
    //     params.put("type", "express");
    //     params.put("country", "ES");
    //     params.put("email", createParams.get("email"));
    //     params.put("capabilities", capabilities);
    //     return Account.create(params);
    // }

    // public AccountCollection findAllAccounts(Integer size) throws StripeException{
    //     Stripe.apiKey = secretKey;
    //     Map<String, Object> params = new HashMap<>();
    //     params.put("limit", size);
    //     return Account.list(params);
    // }

    // public Transfer createTransfer(TransferCreateParams createParams) throws StripeException{
    //     Stripe.apiKey = secretKey;
    //     Map<String, Object> params = new HashMap<>();
    //     params.put("amount", createParams.getAmount()*100);
    //     params.put("currency", "eur");
    //     params.put("destination", createParams.getDestination());
    //     params.put("transfer_group", createParams.getTransferGroup());
    //     params.put("description", createParams.getDescription());
    //     return Transfer.create(params);
    // }

    // public TransferCollection findAllTransfers(Integer size) throws StripeException{
    //     Stripe.apiKey = secretKey;
    //     Map<String, Object> params = new HashMap<>();
    //     params.put("limit", size);
    //     return Transfer.list(params);
    // }


    // // public ExternalAccount createExternalAccount() throws StripeException{

    // // }

    // public Token createAccountToken(User user) throws StripeException{
    //     Stripe.apiKey = secretKey;

    //     Map<String, Object> individual = new HashMap<>();
    //     individual.put("first_name", user.getFirstName());
    //     individual.put("last_name", user.getLastName());
    //     Map<String, Object> account = new HashMap<>();
    //     account.put("individual", individual);
    //     account.put("tos_shown_and_accepted", true);
    //     Map<String, Object> params = new HashMap<>();
    //     params.put("account", account);
    //     params.put("account_token", createPIIToken());

    //     return Token.create(params);
    // }

    // public Customer createCustomer(User user) throws StripeException{
    //     Stripe.apiKey = secretKey;

    //     Map<String, Object> params = new HashMap<>();
    //     params.put("name", user.getFirstName() + " " + user.getLastName());
    //     params.put("email", user.getEmail());
    //     params.put("description", "Usuario: " + user.getUsername());

    //     return  Customer.create(params);    

    // }

    // public Token createPIIToken() throws StripeException{
    //     Stripe.apiKey = secretKey;

    //     Map<String, Object> pii = new HashMap<>();
    //     pii.put("id_number", String.valueOf(0 + (int)(Math.random() * ((1000000000 - 0) + 1))));
    //     Map<String, Object> params = new HashMap<>();
    //     params.put("pii", pii);

    //     return Token.create(params);

    // }

}
