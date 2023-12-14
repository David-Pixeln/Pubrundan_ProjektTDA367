package com.Pubrunda.entities.friendRequest;

import com.Pubrunda.entities.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>, JpaSpecificationExecutor<FriendRequest> {
}