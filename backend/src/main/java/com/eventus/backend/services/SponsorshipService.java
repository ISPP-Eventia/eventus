package com.eventus.backend.services;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.SponsorshipRepository;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SponsorshipService implements ISponsorshipService{
    
    private SponsorshipRepository sponsorRepository;
    private UserService userService;
    private EventService eventService;

    @Autowired
    public SponsorshipService(SponsorshipRepository sponsorRepo,UserService userService, EventService eventService){
        this.sponsorRepository = sponsorRepo;
        this.eventService = eventService;
        this.userService = userService;
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
    public List<Sponsorship> findSponsorByEventId(Long eventId, Pageable p) {
        
        return sponsorRepository.findSponsorByEventId(eventId,p);
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
    public void create(Map<String,String> params) {
        String eventId =params.get("eventId");
        String quantity=params.get("quantity");
        Validate.isTrue(StringUtils.isNotBlank(eventId)&&StringUtils.isNumeric(eventId));
        Validate.isTrue(StringUtils.isNotBlank(quantity)&&StringUtils.isNumeric(quantity));
        Sponsorship entity = new Sponsorship();
        Event event = eventService.findById(Long.valueOf(eventId));
        User user = userService.findUserById(1L);
        if(event != null && user != null){
            entity.setEvent(event);
            entity.setUser(user);
        }
        // entity.setImages(new ArrayList<Image>());
        entity.setName(params.get("name"));
        entity.setQuantity(Double.valueOf(params.get("quantity")));
        sponsorRepository.save(entity);
    }

    @Override
    public void update(Map<String, String> params, Long sponsorId) {
        Sponsorship newSponsor = this.findSponsorById(sponsorId);
        if(newSponsor != null){
            String quantity=params.get("quantity");
            Validate.isTrue(StringUtils.isNotBlank(quantity)&&StringUtils.isNumeric(quantity));
            newSponsor.setQuantity(Double.valueOf(quantity));
            newSponsor.setName(params.get("name"));
            //
            // When Image functionality is implemented: 
            //
            // List<Image> images = new ArrayList<Image>();
            // String[] imagesArr = params.get("images").split(",");
            // for(String imageId: imagesArr){
            //     images.add(imageService.findById(Long.valueOf(imageId)));
            // }
            //
            sponsorRepository.save(newSponsor);
        }
    }

    @Override
    public void resolveSponsorship(boolean b, Long sId) {
        Sponsorship sponsor = this.sponsorRepository.findById(sId).orElse(null);
        if(sponsor!=null){
            sponsor.setAccepted(b);
            this.sponsorRepository.save(sponsor);
        }else{
            throw new IllegalArgumentException();
        }
        
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
