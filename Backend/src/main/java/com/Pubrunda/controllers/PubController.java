package com.Pubrunda.controllers;

import com.Pubrunda.models.Pub;
import com.Pubrunda.repositories.PubRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PubController {

    private final PubRepository repository;

    PubController(PubRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/pubs")
    List<Pub> all() {
        return repository.findAll();
    }

    @PostMapping("/pubs")
    Pub newPub(@RequestBody Pub newPub) {
        return repository.save(newPub);
    }

}
