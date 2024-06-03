package com.mangomelody.mangomelodybackend.model.repositories;

import com.mangomelody.mangomelodybackend.model.entities.FriendshipsEntity;
import com.mangomelody.mangomelodybackend.model.entities.UsersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipsRepository extends CrudRepository<FriendshipsEntity, Integer> {
    @Query("SELECT f FROM FriendshipsEntity f WHERE (f.user1 = :user1 AND f.user2 = :user2) OR (f.user1 = :user2 AND f.user2 = :user1)")
    Optional<FriendshipsEntity> findFriendshipBetweenUsers(UsersEntity user1, UsersEntity user2);

    @Query("SELECT f FROM FriendshipsEntity f WHERE f.user1.userId = :userId OR f.user2.userId = :userId")
    List<FriendshipsEntity> findFriendshipsByUserId(int userId);
}