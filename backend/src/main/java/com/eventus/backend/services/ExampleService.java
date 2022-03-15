package com.eventus.backend.services;

import com.eventus.backend.models.ExampleModel;
import com.eventus.backend.repositories.ExampleRepository;

import org.springframework.stereotype.Service;

@Service
public class ExampleService implements IExampleService {

    ExampleRepository repository;

    public ExampleService(ExampleRepository repository) {
        this.repository = repository;
    }

    @Override
    public ExampleModel findExample(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public ExampleModel saveExample(ExampleModel example) {
        return this.repository.save(example);
    }

}
