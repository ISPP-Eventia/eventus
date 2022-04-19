package com.eventus.backend.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventus.backend.models.Media;

@Repository
public interface MediaRepository extends CrudRepository<Media, Long> {

	List<Media> findAll(Pageable p);

	@Query("SELECT m.id FROM Media m WHERE m.owner.id=:id")
	List<Long> findByUserId(Pageable p, @Param("id") Long id);
	
}
