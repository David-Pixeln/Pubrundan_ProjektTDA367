package com.Pubrunda.repositories;

import com.Pubrunda.models.Profile;
import com.Pubrunda.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{
    Profile findByUserId(long id);
}