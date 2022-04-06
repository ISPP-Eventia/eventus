package com.eventus.backend.controllers;

import java.util.Map;

import com.eventus.backend.models.User;
import com.eventus.backend.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
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
            return new ResponseEntity<>(paymentStr, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Could not create the payment.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/initial")
    public ResponseEntity<String> addPaymentMethod(@AuthenticationPrincipal User user){
        SetupIntent paymentIntent = null;
        try {
            paymentIntent = stripeService.addPaymentMethod(user);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        if(paymentIntent != null){
            String paymentStr = paymentIntent.toJson();
            return new ResponseEntity<>(paymentStr, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Could not create the payment.", HttpStatus.BAD_REQUEST);
        }
        
    }


    @GetMapping("/paymentmethods")
    public ResponseEntity<String> getPaymentMethods(@AuthenticationPrincipal User user) throws StripeException{
        PaymentMethodCollection list = stripeService.getPaymentMethods(user);
        String listStr = list.toJson();
        return new ResponseEntity<>(listStr, HttpStatus.OK);
    }

}
