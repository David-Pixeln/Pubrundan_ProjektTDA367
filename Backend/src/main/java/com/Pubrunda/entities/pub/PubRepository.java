package com.Pubrunda.entities.pub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PubRepository extends JpaRepository<Pub, Long> {
}
