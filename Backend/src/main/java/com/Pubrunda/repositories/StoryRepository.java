package com.Pubrunda.repositories;

import com.Pubrunda.models.Comment;
import com.Pubrunda.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
}
