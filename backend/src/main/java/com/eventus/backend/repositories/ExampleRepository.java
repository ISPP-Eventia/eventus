package com.eventus.backend.repositories;

import com.eventus.backend.models.ExampleModel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends CrudRepository<ExampleModel, Long>{
}
