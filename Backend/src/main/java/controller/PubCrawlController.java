package controller;

import com.Pubrunda.PubCrawl;
import repository.PubCrawlRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PubCrawlController {

    private final PubCrawlRepository repository;

    PubCrawlController(PubCrawlRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/pubCrawls")
    List<PubCrawl> all() {
        return repository.findAll();
    }

    @PostMapping("/pubCrawls")
    PubCrawl newPubCrawl(@RequestBody PubCrawl newPubCrawl) {
        return repository.save(newPubCrawl);
    }

}
