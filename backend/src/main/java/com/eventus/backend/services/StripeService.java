package com.eventus.backend.services;

import java.util.Map;

import com.eventus.backend.models.Hosting;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListPaymentMethodsParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.SetupIntentCreateParams;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class StripeService {

    public static final String PAYMENT_METHOD_ERROR = "El usuario debe tener al menos un metodo de pago.";
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
            return null;
          }
          return payment;
        }else{
          return null;
        }
          
    }
    

    public String createCustomer(User user) throws StripeException {
      Stripe.apiKey = secretKey;

      CustomerCreateParams params = CustomerCreateParams.builder()
        .setEmail(user.getEmail())
        .setName(user.getFirstName() + " " + user.getLastName())
        .build();

      Customer customer = null;
      customer = Customer.create(params);
      return customer.getId();
      
    }

    public SetupIntent addPaymentMethod(User user) throws StripeException {
      Stripe.apiKey = secretKey;
      SetupIntentCreateParams  params =
      SetupIntentCreateParams .builder()
        .setCustomer(user.getCustomerId())
        .addPaymentMethodType("card")
        .build();
      return SetupIntent.create(params);
    }
    

    public PaymentIntent createHostingPayment(Hosting hosting) throws StripeException {

      Stripe.apiKey = secretKey;
      PaymentMethodCollection paymentMethods = getPaymentMethods(hosting.getEvent().getOrganizer());
      Validate.isTrue(!paymentMethods.getData().isEmpty(), PAYMENT_METHOD_ERROR);
      if(!paymentMethods.getData().isEmpty()){
        PaymentIntentCreateParams paymentParams =
        PaymentIntentCreateParams.builder()
          .setAmount(Long.valueOf((int) (hosting.getPrice() *100)))
          .setCurrency("eur")
          .setDescription("HOSTING " + hosting.getEvent().getTitle())
          .setCustomer(hosting.getEvent().getOrganizer().getCustomerId())
          .setPaymentMethod(paymentMethods.getData().get(0).getId())
          .setConfirm(true)
          .setOffSession(true)
          .build();

        return PaymentIntent.create(paymentParams);
      }else{
        return null;
      }
    }




    public PaymentIntent createSponsorshipPayment(Sponsorship sponsorship) throws StripeException {
      Stripe.apiKey = secretKey;
      PaymentMethodCollection paymentMethods = getPaymentMethods(sponsorship.getUser());
      Validate.isTrue(!paymentMethods.getData().isEmpty(), PAYMENT_METHOD_ERROR);
      if(!paymentMethods.getData().isEmpty()){
        PaymentIntentCreateParams paymentParams =
        PaymentIntentCreateParams.builder()
          .setAmount(Long.valueOf((int) (sponsorship.getQuantity() *100)))
          .setCurrency("eur")
          .setDescription("SPONSORSHIP " + sponsorship.getEvent().getTitle())
          .setCustomer(sponsorship.getUser().getCustomerId())
          .setPaymentMethod(paymentMethods.getData().get(0).getId())
          .setConfirm(true)
          .setOffSession(true)
          .build();

        return PaymentIntent.create(paymentParams);
      }else{
        return null;
      }
    }




    public PaymentIntent createParticipationPayment(Participation participation) throws StripeException {
      Stripe.apiKey = secretKey;
      PaymentMethodCollection paymentMethods = getPaymentMethods(participation.getUser());
      Validate.isTrue(!paymentMethods.getData().isEmpty(), PAYMENT_METHOD_ERROR);
      if(!paymentMethods.getData().isEmpty()){
        PaymentIntentCreateParams paymentParams =
        PaymentIntentCreateParams.builder()
          .setAmount(Long.valueOf((int) (participation.getPrice() *100)))
          .setCurrency("eur")
          .setDescription("PARTICIPATION " + participation.getEvent().getTitle())
          .setCustomer(participation.getUser().getCustomerId())
          .setPaymentMethod(paymentMethods.getData().get(0).getId())
          .setConfirm(true)
          .setOffSession(true)
          .build();

        return PaymentIntent.create(paymentParams);
      }else{
        return null;
      }
    }


    public PaymentMethodCollection getPaymentMethods(User user) throws StripeException {
      
      Customer customer = Customer.retrieve(user.getCustomerId());
      CustomerListPaymentMethodsParams params =
        CustomerListPaymentMethodsParams.builder()
          .setType(CustomerListPaymentMethodsParams.Type.CARD)
          .build();

      return customer.listPaymentMethods(params);
      
    }

}
