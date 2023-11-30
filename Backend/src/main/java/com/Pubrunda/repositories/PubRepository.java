package com.Pubrunda.repositories;

import com.Pubrunda.models.Pub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PubRepository extends JpaRepository<Pub, Long> {
}
