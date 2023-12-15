package com.Pubrunda.entities.friendList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendListRepository extends JpaRepository<FriendList, Long>, JpaSpecificationExecutor<FriendList> {

}
