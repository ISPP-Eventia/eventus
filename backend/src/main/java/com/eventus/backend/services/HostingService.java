package com.eventus.backend.services;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Hosting;
import com.eventus.backend.models.Location;
import com.eventus.backend.repositories.HostingRepository;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HostingService implements IHostingService {

    private HostingRepository hostingRepository;
    private LocationService locationService;
    private EventService eventService;


    @Autowired
    public HostingService(HostingRepository hostingRepository, LocationService locationService, EventService eventService){
        this.hostingRepository = hostingRepository;
        this.locationService = locationService;
        this.eventService = eventService;
    }



    @Override
    public void create(Map<String, String> params) {
        Hosting entity = new Hosting();
        Event event = eventService.findById(Long.valueOf(params.get("event")));
        Location location = locationService.findById(Long.valueOf(params.get("location")));
        if(event != null) entity.setEvent(event);
        if(location != null) entity.setLocation(location);
        entity.setPrice(Double.valueOf(params.get("price")));
        hostingRepository.save(entity);
    }

    @Override
    public void delete(Hosting hosting) {
        hostingRepository.delete(hosting);
        
    }

    @Override
    public void deleteById(Long id) {
        hostingRepository.deleteById(id);
        
    }

    @Override
    public List<Hosting> findAll(Pageable p) {
        return hostingRepository.findAll(p);
    }

    @Override
    public Hosting findById(Long id) {
        return hostingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Hosting> findByEventId(Long eventId, Pageable p) {
        return hostingRepository.findByEventId(eventId,p);
    }

    @Override
    public List<Hosting> findByLocationId(Long locationId, Pageable p) {
        return hostingRepository.findByLocationId(locationId,p);
    }

    @Override
    public void save(Hosting hosting) {
        hostingRepository.save(hosting);
    }

    @Override
    public void update(Map<String, String> params, Long hostingId) {
        Hosting hosting = hostingRepository.findById(hostingId).orElse(null);
        if(hosting!=null){
            hosting.setPrice(Double.valueOf(params.get("price")));
        }
        hostingRepository.save(hosting);
    }


    @Override
    public void resolveHosting(boolean b, Long sId) {
        Hosting hosting = this.hostingRepository.findById(sId).orElse(null);
        if(hosting!=null){
            hosting.setAccepted(b);
            this.hostingRepository.save(hosting);
        }else{
            throw new IllegalArgumentException();
        }
        
    }

    @Override
    public List<Hosting> findByEventAndState(Long eventId,String state, Pageable p) {
        boolean b;
        if(state.equals("accepted")){
            b=true;
        }else if(state.equals("denied")){
            b=false;
        }else{
            return null;
        }
        return this.hostingRepository.findByEventAndState(eventId,b,p);
    }


    @Override
    public void resolveSponsorship(boolean isAccepted, Long id) {
        Hosting hosting = this.hostingRepository.findById(id).orElse(null);
        Validate.isTrue(hosting!=null);
        hosting.setAccepted(isAccepted);
        this.hostingRepository.save(hosting);
    }
    
    
}
