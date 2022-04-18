package com.eventus.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventus.backend.models.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

}
