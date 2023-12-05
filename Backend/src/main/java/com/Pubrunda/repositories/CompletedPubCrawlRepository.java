package com.Pubrunda.repositories;

import com.Pubrunda.completedPubCrawls.CompletedPubCrawl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedPubCrawlRepository extends JpaRepository<CompletedPubCrawl, Long> {
}
