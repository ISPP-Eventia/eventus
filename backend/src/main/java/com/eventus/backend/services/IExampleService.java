package com.eventus.backend.services;

import com.eventus.backend.models.ExampleModel;

public interface IExampleService {
    ExampleModel findExample(Long id);
    ExampleModel saveExample(ExampleModel example);
}
