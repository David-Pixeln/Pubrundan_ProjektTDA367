package com.Pubrunda.controllers;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("${api.base-path}/event")
public class EventTestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executorService.shutdown();

            try {
                executorService.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.error(e.toString());
            }
        }));
    }

    @GetMapping("/time")
    @CrossOrigin
    public SseEmitter streamDateTime() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitter.onCompletion(() -> LOGGER.info("SseEmitter completed"));
        emitter.onTimeout(() -> LOGGER.info("SseEmitter timed out"));
        emitter.onError((e) -> LOGGER.error("SseEmitter error", e));

        executorService.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    emitter.send(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
                    sleep(1, emitter);
                }

                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        LOGGER.info("Controller exits");

        return emitter;
    }

    private void sleep(long seconds, SseEmitter sseEmitter) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            sseEmitter.completeWithError(e);
        }
    }

}
