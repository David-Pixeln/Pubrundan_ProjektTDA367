package com.Pubrunda.entities.pubcrawl;

public class PubCrawlNotFoundException extends RuntimeException {
    PubCrawlNotFoundException(Long id) {
        super("Could not find pubcrawl " + id);
    }
}
