package com.eventus.backend.services;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.SponsorshipRepository;
import com.stripe.exception.StripeException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SponsorshipService implements ISponsorshipService{
    
    private final SponsorshipRepository sponsorRepository;
    private final EventService eventService;
    private final StripeService stripeService;

    @Autowired
    public SponsorshipService(SponsorshipRepository sponsorRepo, EventService eventService, StripeService stripeService){
        this.sponsorRepository = sponsorRepo;
        this.eventService = eventService;
        this.stripeService = stripeService;
    }

    @Override
    public void delete(Sponsorship sponsor) {
        sponsorRepository.delete(sponsor);
    }

    @Override
    public void deleteById(Long id) {
        sponsorRepository.deleteById(id);
    }

    @Override
    public List<Sponsorship> findAll(Pageable p) {
        return sponsorRepository.findAll(p);
    }

    @Override
    public List<Sponsorship> findSponsorByEventId(Long eventId, Pageable p,User user) {
        Event event=eventService.findById(eventId);
        Validate.isTrue(event!=null,"Event does not exits");
        if(event.getOrganizer().getId().equals(user.getId())||user.isAdmin()){
            return sponsorRepository.findSponsorByEventId(eventId,p);
        }else{
            return sponsorRepository.findByEventAndState(eventId,true,p);
        }
    }

    @Override
    public Sponsorship findSponsorById(Long id) {
        return sponsorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Sponsorship> findSponsorByUserId(Long userId, Pageable p) {
        return sponsorRepository.findSponsorByUserId(userId,p);
    }

    @Override
    public void save(Sponsorship sponsor) {
        sponsorRepository.save(sponsor);
    }

    @Override
    public void create(Map<String,String> params,User user) {
        String eventId =params.get("eventId");
        String quantity=params.get("quantity");
        Validate.isTrue(StringUtils.isNotBlank(eventId)&&StringUtils.isNumeric(eventId),"Incorrect format for eventId");
        Validate.isTrue(StringUtils.isNotBlank(quantity)&& NumberUtils.isCreatable(quantity),"Quantity should be a double");
        Sponsorship entity = new Sponsorship();
        Event event = eventService.findById(Long.valueOf(eventId));
        if(event != null && user != null){
            entity.setEvent(event);
            entity.setUser(user);
            entity.setName(params.get("name"));
            entity.setQuantity(Double.valueOf(params.get("quantity")));
            entity.setAccepted(null);
            sponsorRepository.save(entity);
        }
        // entity.setImages(new ArrayList<Image>());
        
    }

    @Override
    public void resolveSponsorship(boolean b, Long sId, User user) throws StripeException {
        Sponsorship sponsor = this.sponsorRepository.findById(sId).orElse(null);
        Validate.isTrue(sponsor!=null,"Sponsor id doesnt exits");
        Validate.isTrue(sponsor.getEvent().getOrganizer().getId().equals(user.getId())||user.isAdmin(),"User must be event organizer");
        Boolean paid = false;
        if(b){
            stripeService.createSponsorshipPayment(sponsor);
            paid = true;
        }
        if(paid) sponsor.setAccepted(true);
        this.sponsorRepository.save(sponsor);

    }

    @Override
    public List<Sponsorship> findByEventAndState(Long eventId,String state, Pageable p) {
        boolean b;
        if(state.equals("accepted")){
            b=true;
        }else if(state.equals("denied")){
            b=false;
        }else{
            return null;
        }
        return this.sponsorRepository.findByEventAndState(eventId,b,p);
    }

    

    

    
}
