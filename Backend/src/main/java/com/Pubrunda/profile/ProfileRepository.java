package com.Pubrunda.profile;

import com.Pubrunda.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{

    Profile findByUserId(long id);

}