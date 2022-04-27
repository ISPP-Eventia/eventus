package com.eventus.backend.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.eventus.backend.models.Coordinates;
import com.eventus.backend.models.Event;
import com.eventus.backend.models.Hosting;
import com.eventus.backend.models.Location;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.HostingService;
import com.eventus.backend.services.LocationService;
import com.eventus.backend.services.SponsorshipService;
import com.eventus.backend.services.StripeService;
import com.eventus.backend.services.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.SetupIntent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StripeServiceTests {
	
	@Autowired
	protected StripeService stripeService;	
	@Autowired
	protected UserService userService;
	@Autowired
	protected LocationService locationService;
	@Autowired
	protected EventService eventService;
	@Autowired
	protected HostingService hostingService;
	@Autowired
	protected SponsorshipService sponsorService;
	
	User user;
	Hosting hosting;
	Location location;
	Coordinates coordinates;
	Event event;
	Sponsorship sponsorship;
	
	@Before
	public void setUp() throws DataAccessException, StripeException {		
		user = new User();
		user.setId(3000L);
	    user.setFirstName("Carlos Jesus");
	    user.setLastName("Villadiego");
	    user.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
	    user.setEmail("carlosjesus@email.example");
	    user.setPassword("$up3r$€cr3tP4s$w0rd");
	    userService.saveUser(user);
	    
	    event = new Event();
	    event.setDescription("event description");
		event.setStartDate(LocalDateTime.parse("2022-06-21T19:30:00.000000"));
		event.setEndDate(LocalDateTime.parse("2022-06-21T20:30:00.000000"));
		event.setOrganizer(user);
		event.setPrice(3.0);
		event.setTitle("Event");
		eventService.save(event);
	    
	    coordinates = new Coordinates();
	    coordinates.setLatitude(12.0);
	    coordinates.setLongitude(30.0);	    
	    
	    location = new Location();
	    location.setName("Location");
	    location.setOwner(user);
	    location.setCoordinates(coordinates);
	    location.setDescription("Description");
	    location.setPrice(40.0);
	    locationService.save(location);
	    
	    hosting = new Hosting();
	    hosting.setPrice(30.0);
	    hosting.setAccepted(false);
	    hosting.setLocation(location);
	    hosting.setEvent(event);
	    hostingService.save(hosting);
	    
	    sponsorship = new Sponsorship();
	    sponsorship.setEvent(event);
	    sponsorship.setName("Sponsor");
	    sponsorship.setQuantity(20.0);
	    sponsorship.setUser(user);
	    sponsorship.setAccepted(false);
	    sponsorService.save(sponsorship);
	    
	}
	
	@Test
	@Transactional
	public void createCustomerTest() throws StripeException {
	    String customerId = stripeService.createCustomer(user);
	    assertTrue(customerId!=null);
	    assertTrue(customerId.contains("cus_"));
	}
	
	@Test
	@Transactional
	public void addAndGetPaymentMethodTest() throws DataAccessException, StripeException {		
		User userCreated = userService.findByEmail("carlosjesus@email.example").get();
	    assertEquals(user.getFirstName(), userCreated.getFirstName());		
	    SetupIntent setupIntent = stripeService.addPaymentMethod(user);
	    Map<String, Object> params = new HashMap<>();
		params.put("payment_method", "pm_card_visa");
		SetupIntent updatedSetupIntent = setupIntent.confirm(params);	    
	    PaymentMethodCollection paymentMethods = stripeService.getPaymentMethods(user);
	    
	    assertTrue(setupIntent.getPaymentMethodTypes().contains("card"));
	    assertEquals(userCreated.getCustomerId(), setupIntent.getCustomer());
	    assertEquals(updatedSetupIntent.getPaymentMethodTypes().get(0), paymentMethods.getData().get(0).getType());
	}
	
	@Test
    @Transactional
    public void createNewPaymentIntent() throws StripeException {
		SetupIntent setupIntent = stripeService.addPaymentMethod(user);
	    Map<String, Object> params = new HashMap<>();
		params.put("payment_method", "pm_card_visa");
		setupIntent.confirm(params);
		
        Map<String, String> m = new HashMap<>();
        m.put("amount", "100");
        
		PaymentIntent payment = this.stripeService.createNewPaymentIntent(m, user);
		assertEquals(100L, payment.getAmount());
	}
	
	@Test
	@Transactional
	public void createHostingPaymentTest() throws StripeException {
		SetupIntent setupIntent = stripeService.addPaymentMethod(user);
	    Map<String, Object> params = new HashMap<>();
		params.put("payment_method", "pm_card_visa");
		setupIntent.confirm(params);
		
        PaymentIntent payment = stripeService.createHostingPayment(hosting);
        assertEquals(3000L, payment.getAmount());
	}
	
	@Test
	@Transactional
	public void createSponsorshipPaymentTest() throws StripeException {
		SetupIntent setupIntent = stripeService.addPaymentMethod(user);
	    Map<String, Object> params = new HashMap<>();
		params.put("payment_method", "pm_card_visa");
		setupIntent.confirm(params);
		
        PaymentIntent payment = stripeService.createSponsorshipPayment(sponsorship);
        assertEquals(2000L, payment.getAmount());
	}

}