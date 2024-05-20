package com.mangomelody.mangomelodybackend.model.repositories;

import com.mangomelody.mangomelodybackend.model.entities.FriendshipsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipsRepository extends CrudRepository<FriendshipsEntity, Integer> {
}