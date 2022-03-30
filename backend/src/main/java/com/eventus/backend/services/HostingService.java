package com.eventus.backend.services;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Hosting;
import com.eventus.backend.models.Location;
import com.eventus.backend.repositories.HostingRepository;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HostingService implements IHostingService {

    private final HostingRepository hostingRepository;
    private final LocationService locationService;
    private final EventService eventService;


    @Autowired
    public HostingService(HostingRepository hostingRepository, LocationService locationService, EventService eventService){
        this.hostingRepository = hostingRepository;
        this.locationService = locationService;
        this.eventService = eventService;
    }



    @Override
    public void create(Map<String, String> params) {

        String eventId= params.get("eventId");
        String locationId= params.get("locationId");
        String price = params.get("price");
        Validate.isTrue(StringUtils.isNotBlank(eventId)&&StringUtils.isNumeric(eventId),"Incorrect format for eventId");
        Validate.isTrue(StringUtils.isNotBlank(locationId)&&StringUtils.isNumeric(locationId),"Incorrect format for locationId");
        Validate.isTrue(NumberUtils.isCreatable(price),"Price should be a double");
        Validate.isTrue(findHostingByEventIdAndLocationId(Long.valueOf(eventId),Long.valueOf(locationId))==null,"There is already a hosting with this event and location");
        Event event = eventService.findById(Long.valueOf(eventId));
        Location location = locationService.findById(Long.valueOf(locationId));
        Validate.isTrue(event!=null,"Event not found");
        Validate.isTrue(location!=null,"Location not found");
        Hosting entity = new Hosting();
        entity.setEvent(event);
        entity.setLocation(location);
        entity.setPrice(Double.valueOf(params.get("price")));
        entity.setAccepted(null);
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
    public List<Hosting> findByEventId(Long eventId, Pageable p, Long userId) {
        Event event=eventService.findById(eventId);
        Validate.isTrue(event!=null,"Event does not exits");
        if(event.getOrganizer().getId().equals(userId)) {
            return hostingRepository.findByEventId(eventId,p);
        }else {
            return hostingRepository.findByEventAndState(eventId, true, p);
        }
    }

    @Override
    public List<Hosting> findByLocationId(Long locationId, Pageable p,Long userId) {
        Location location=locationService.findById(locationId);
        Validate.isTrue(location!=null,"Location does not exits");
        if (location.getOwner().getId().equals(userId)) {
            return hostingRepository.findByLocationId(locationId,p);
        }else{
            return hostingRepository.findByLocationAndState(locationId, true, p);
        }

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
            hostingRepository.save(hosting);
        }
    }


    @Override
    public void resolveHosting(boolean b, Long sId) {
        Hosting hosting = this.hostingRepository.findById(sId).orElse(null);
        Validate.isTrue(hosting != null);
        Validate.isTrue(hosting.getEvent() != null && hosting.getEvent().getOrganizer().getId().equals(sId));
        hosting.setAccepted(b);
        this.hostingRepository.save(hosting);


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
    public Hosting findHostingByEventIdAndLocationId(Long eventId,Long locationId){
        return this.hostingRepository.findByEventAndLocation(eventId,locationId).orElse(null);
    }
    
}
