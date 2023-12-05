package com.Pubrunda.completedPub;

import com.Pubrunda.completedPub.CompletedPub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedPubRepository extends JpaRepository<CompletedPub, Long> {
}
