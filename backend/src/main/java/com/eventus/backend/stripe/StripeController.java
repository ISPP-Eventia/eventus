package com.eventus.backend.stripe;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/paymentintent")
    public ResponseEntity<String> payment(@RequestBody PaymentIntentObj paymentIntentDto) throws StripeException {
        Collection l = stripeService.createPaymentIntent(paymentIntentDto);
        System.out.println(l);
        // String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirm(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = stripeService.confirmPaymentIntent(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = stripeService.cancelPaymentIntent(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }
}
