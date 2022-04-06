package com.eventus.backend.controllers;

import java.util.List;
import java.util.Map;


import com.eventus.backend.models.User;
import com.eventus.backend.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.SetupIntent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stripe")
@CrossOrigin(origins = "http://localhost:3000")
public class StripeController {

    
    StripeService stripeService;

    @Autowired
    public StripeController(StripeService stripeService){
        this.stripeService = stripeService;
    }


    @PostMapping("/new")
    public ResponseEntity<String> createNew(@RequestBody Map<String,String> paymentIntentDto, @AuthenticationPrincipal User user) {
        PaymentIntent paymentIntent = null;
        try {
            paymentIntent = stripeService.createNewPaymentIntent(paymentIntentDto, user);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        if(paymentIntent != null){
            String paymentStr = paymentIntent.toJson();
            return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Could not create the payment.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/initial")
    public ResponseEntity<String> createIntial(@AuthenticationPrincipal User user){
        SetupIntent paymentIntent = null;
        try {
            paymentIntent = stripeService.createInitialPaymentIntent(user);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        if(paymentIntent != null){
            String paymentStr = paymentIntent.toJson();
            return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Could not create the payment.", HttpStatus.BAD_REQUEST);
        }
        
    }


    @GetMapping("/paymentmethods")
    public ResponseEntity<String> getPaymentMethods(@AuthenticationPrincipal User user) throws StripeException{
        PaymentMethodCollection list = stripeService.getPaymentMethods(user);
        String listStr = list.toJson();
        return new ResponseEntity<>(listStr, HttpStatus.OK);
    }

    // @PostMapping("/confirm/{id}")
    // public ResponseEntity<String> confirm(@PathVariable("id") String id) throws StripeException {
    //     PaymentIntent paymentIntent = stripeService.confirmPaymentIntent(id);
    //     String paymentStr = paymentIntent.toJson();
    //     return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    // }

    // @PostMapping("/cancel/{id}")
    // public ResponseEntity<String> cancel(@PathVariable("id") String id) throws StripeException {
    //     PaymentIntent paymentIntent = stripeService.cancelPaymentIntent(id);
    //     String paymentStr = paymentIntent.toJson();
    //     return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    // }


    
    // @PostMapping("/accounts")
    // public ResponseEntity<String> createAccount(@RequestBody Map<String,String> params) throws StripeException {
    //     Account account = stripeService.createAccount(params);
    //     String accountStr = account.toJson();
    //     return new ResponseEntity<String>(accountStr, HttpStatus.OK);
    // }

}
