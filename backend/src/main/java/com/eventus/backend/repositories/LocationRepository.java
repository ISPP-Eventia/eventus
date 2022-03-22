package com.eventus.backend.repositories;

import java.util.List;

import com.eventus.backend.models.Location;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long>{
    
    List<Location> findAll(Pageable p);
    List<Location> findByOwnerId(Long ownerId, Pageable p);
}
