package com.eventus.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Image;
import com.eventus.backend.models.Sponsor;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.SponsorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SponsorService implements ISponsorService{
    
    private SponsorRepository sponsorRepository;
    private UserService userService;
    private EventService eventService;

    @Autowired
    public SponsorService(SponsorRepository sponsorRepo,UserService userService, EventService eventService){
        this.sponsorRepository = sponsorRepo;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Override
    public void delete(Sponsor sponsor) {
        sponsorRepository.delete(sponsor);
    }

    @Override
    public void deleteById(Long id) {
        sponsorRepository.deleteById(id);
    }

    @Override
    public List<Sponsor> findAll(Pageable p) {
        return sponsorRepository.findAll(p);
    }

    @Override
    public List<Sponsor> findSponsorByEventId(Long eventId, Pageable p) {
        
        return sponsorRepository.findSponsorByEventId(eventId,p);
    }

    @Override
    public Optional<Sponsor> findSponsorById(Long id) {
        return sponsorRepository.findById(id);
    }

    @Override
    public List<Sponsor> findSponsorByUserId(Long userId, Pageable p) {
        return sponsorRepository.findSponsorByUserId(userId,p);
    }

    @Override
    public Sponsor save(Sponsor sponsor) {
        return sponsorRepository.save(sponsor);
    }

    @Override
    public Sponsor create(Map<String,String> params) {
        Sponsor entity = new Sponsor();
        Event event = eventService.findById(Long.valueOf(params.get("event"))).orElse(null);
        User user = userService.findUserById(Long.valueOf(params.get("user"))).orElse(null);
        if(event != null && user != null){
            entity.setEvent(event);
            entity.setUser(user);
        }
        entity.setAccepted(false);
        // entity.setImages(new ArrayList<Image>());
        entity.setName(params.get("name"));
        entity.setQuantity(Double.valueOf(params.get("quantity")));
        return sponsorRepository.save(entity);
    }

    @Override
    public Sponsor update(Map<String, String> params, Long sponsorId) {
        Sponsor newSponsor = this.findSponsorById(sponsorId).orElse(null);
        if(newSponsor != null){
            newSponsor.setAccepted(Boolean.valueOf(params.get("isAccepted")));
            newSponsor.setQuantity(Double.valueOf(params.get("quantity")));
            //
            // When Image functionality is implemented: 
            //
            // List<Image> images = new ArrayList<Image>();
            // String[] imagesArr = params.get("images").split(",");
            // for(String imageId: imagesArr){
            //     images.add(imageService.findById(Long.valueOf(imageId)));
            // }
            //
            return newSponsor;
        }
        return null;
    }

    

    
}
