package com.mangomelody.mangomelodybackend.model.repositories;

import com.mangomelody.mangomelodybackend.model.entities.UsersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, Integer> {
    UsersEntity findByUsername(String username);

    @Query("SELECT u FROM UsersEntity u WHERE u.username LIKE %:username%")
    List<UsersEntity> findByUsernameContaining(@Param("username") String username);
}

