package com.eventus.backend.controllers;

import com.eventus.backend.models.ExampleModel;
import com.eventus.backend.services.ExampleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    ExampleService service;

    public ExampleController(ExampleService service) {
        this.service = service;
    }

    @GetMapping("/example/{id}")
    ExampleModel getExampleById(@PathVariable Long id) {
        return this.service.findExample(id);
    }

    @PostMapping("/example")
    ExampleModel postExample(@RequestBody ExampleModel example) {
        return this.service.saveExample(example);
    }
}
