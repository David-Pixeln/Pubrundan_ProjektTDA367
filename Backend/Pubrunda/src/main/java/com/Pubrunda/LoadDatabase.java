package com.Pubrunda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.PubRepository;

import java.time.LocalTime;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PubRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Pub("HubbenPuben", LocalTime.now(), LocalTime.now())));
        };
    }
}