package com.mangomelody.mangomelodybackend.model.repositories;

import com.mangomelody.mangomelodybackend.model.entities.SpotifyDataEntity;
import com.mangomelody.mangomelodybackend.model.entities.UsersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpotifyDataRepository extends CrudRepository<SpotifyDataEntity, Integer> {

    @Query("SELECT f FROM SpotifyDataEntity f WHERE f.userId.userId = :userId")
    List<SpotifyDataEntity> findSpotifyDataByUserId(int userId);

}

