package com.eventus.backend.stripe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.eventus.backend.models.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountCollection;
import com.stripe.model.ExternalAccount;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.model.Transfer;
import com.stripe.model.TransferCollection;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.TransferCreateParams;
import com.stripe.param.AccountLinkCreateParams.Collect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import antlr.collections.List;

@Service
public class StripeService {




    @Value("${stripe.key.secret}")
    String secretKey;

    public Collection createPaymentIntent(PaymentIntentObj paymentIntentObj) throws StripeException {
        Stripe.apiKey = secretKey;
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentIntentObj.getAmount()*100);
        params.put("currency", paymentIntentObj.getCurrency());
        params.put("description", paymentIntentObj.getDescription());
        ArrayList payment_method_types = new ArrayList();
        payment_method_types.add("card");
        params.put("payment_method_types", payment_method_types);


        TransferCreateParams transferParams =
        TransferCreateParams.builder()
            .setAmount(7000L)
            .setCurrency("eur")
            .setDestination("acct_1KjJHU4h0FyUY0zS")
            .setTransferGroup("PARTICIPATION")
            .build();

        Transfer transfer = Transfer.create(transferParams);

        TransferCreateParams secondTransferParams =
        TransferCreateParams.builder()
            .setAmount(2000L)
            .setCurrency("eur")
            .setDestination("acct_1KjJHU4h0FyUY0zS")
            .setTransferGroup("PARTICIPATION")
            .build();

        Transfer secondTransfer = Transfer.create(secondTransferParams);
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        Collection l = new ArrayList<>();
        l.add(secondTransfer);
        l.add(paymentIntent);
        return l;
    }
    
     public PaymentIntent confirmPaymentIntent(String id) throws StripeException {
        Stripe.apiKey = secretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", "pm_card_visa");
        paymentIntent.confirm(params);
        return paymentIntent;
    }

    public PaymentIntent cancelPaymentIntent(String id) throws StripeException {
        Stripe.apiKey = secretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        paymentIntent.cancel();
        return paymentIntent;
    }

    public Account createAccount(AccountCreateParams createParams) throws StripeException{
        Stripe.apiKey = secretKey;
        Map<String, Object> cardPayments = new HashMap<>();
        cardPayments.put("requested", true);
        Map<String, Object> transfers = new HashMap<>();
        transfers.put("requested", true);
        Map<String, Object> capabilities = new HashMap<>();
        capabilities.put("card_payments", cardPayments);
        capabilities.put("transfers", transfers);
        Map<String, Object> params = new HashMap<>();
        params.put("type", "standard");
        params.put("country", "ESP");
        params.put("email", createParams.getEmail());
        params.put("capabilities", capabilities);
        return Account.create(params);
    }

    public AccountCollection findAllAccounts(Integer size) throws StripeException{
        Stripe.apiKey = secretKey;
        Map<String, Object> params = new HashMap<>();
        params.put("limit", size);
        return Account.list(params);
    }

    public Transfer createTransfer(TransferCreateParams createParams) throws StripeException{
        Stripe.apiKey = secretKey;
        Map<String, Object> params = new HashMap<>();
        params.put("amount", createParams.getAmount()*100);
        params.put("currency", "eur");
        params.put("destination", createParams.getDestination());
        params.put("transfer_group", createParams.getTransferGroup());
        params.put("description", createParams.getDescription());
        return Transfer.create(params);
    }

    public TransferCollection findAllTransfers(Integer size) throws StripeException{
        Stripe.apiKey = secretKey;
        Map<String, Object> params = new HashMap<>();
        params.put("limit", size);
        return Transfer.list(params);
    }


    // public ExternalAccount createExternalAccount() throws StripeException{

    // }

    public Token createAccountToken(User user) throws StripeException{
        Stripe.apiKey = secretKey;

        Map<String, Object> individual = new HashMap<>();
        individual.put("first_name", user.getFirstName());
        individual.put("last_name", user.getLastName());
        Map<String, Object> account = new HashMap<>();
        account.put("individual", individual);
        account.put("tos_shown_and_accepted", true);
        Map<String, Object> params = new HashMap<>();
        params.put("account", account);
        params.put("account_token", createPIIToken());

        return Token.create(params);
    }

    public Token createPIIToken() throws StripeException{
        Stripe.apiKey = secretKey;

        Map<String, Object> pii = new HashMap<>();
        pii.put("id_number", String.valueOf(0 + (int)(Math.random() * ((1000000000 - 0) + 1))));
        Map<String, Object> params = new HashMap<>();
        params.put("pii", pii);

        return Token.create(params);

    }

}
