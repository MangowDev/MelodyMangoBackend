package com.mangomelody.mangomelodybackend.model.repositories;

import com.mangomelody.mangomelodybackend.model.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, Integer> {
    UsersEntity findByUsername(String username);

}