package com.eventus.backend.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.eventus.backend.models.User;
import com.eventus.backend.services.StripeService;
import com.eventus.backend.services.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.SetupIntent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StripeServiceTests {
	
	@Autowired
	protected StripeService stripeService;
	
	@Autowired
	protected UserService userService;
	
	User user;
	
	@Test
	@Transactional
	public void createCustomerTest() throws StripeException {
		user = new User();
	    user.setFirstName("Pepe");
	    user.setLastName("Rodriguez");
	    user.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
	    user.setEmail("peperod@email.example");
	    user.setPassword("$up3r$€cr3tP4s$w0rd");
	    
	    String customerId = stripeService.createCustomer(user);	    
	    assertTrue(customerId!=null);
	    assertTrue(customerId.contains("cus_"));
	}
	
	@Test
	@Transactional
	public void addPaymentMethodTest() throws DataAccessException, StripeException {
		user = new User();
	    user.setFirstName("Pepe");
	    user.setLastName("Rodriguez");
	    user.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
	    user.setEmail("peperod@email.example");
	    user.setPassword("$up3r$€cr3tP4s$w0rd");	    
	    userService.saveUser(user);
	    
	    User userCreated = userService.findByEmail("peperod@email.example").get();
	    assertEquals(user.getFirstName(), userCreated.getFirstName());
	    
	    SetupIntent method = stripeService.addPaymentMethod(user);
	    assertTrue(method.getPaymentMethodTypes().contains("card"));
	    assertEquals(userCreated.getCustomerId(), method.getCustomer());
	}
	
//	@Test
//	@Transactional
//	public void getPaymentMethodTest() throws DataAccessException, StripeException {
//		user = new User();
//	    user.setFirstName("Pepe");
//	    user.setLastName("Rodriguez");
//	    user.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
//	    user.setEmail("peperod@email.example");
//	    user.setPassword("$up3r$€cr3tP4s$w0rd");	    
//	    userService.saveUser(user);
//	    SetupIntent intent = stripeService.addPaymentMethod(user);
//	    
//	    PaymentMethodCollection paymentMethods = stripeService.getPaymentMethods(user);
//	    System.out.println(paymentMethods.getData().get(0).getType());
//	    assertEquals(intent.getPaymentMethodTypes().get(0), paymentMethods.getData().get(0).getType());
//		
//	}
	
//	@Test
//    @Transactional
//    public void createNewPaymentIntent() throws StripeException {
//		user = new User();
//        user.setFirstName("Pepe");
//        user.setLastName("Rodriguez");
//        user.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
//        user.setEmail("peperod@email.example");
//        user.setPassword("$up3r$€cr3tP4s$w0rd");
//        
//        Map<String, String> m = new HashMap<>();
//        m.put("amount", "100");
//        
//		this.stripeService.createNewPaymentIntent(null, user);
//		
//	}
	
//	@Test
//	@Transactional
//	public void createHostingPaymentTest() {
//		
//	}
	
//	@Test
//	@Transactional
//	public void createSponsorshipPaymentTest() {
//		
//	}
	
//	@Test
//	@Transactional
//	public void createParticipationPaymentTest() {
//		
//	}

}