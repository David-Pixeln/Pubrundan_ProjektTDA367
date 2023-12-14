package com.Pubrunda.entities.pub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PubRepository extends JpaRepository<Pub, Long>, JpaSpecificationExecutor<Pub> {
}
