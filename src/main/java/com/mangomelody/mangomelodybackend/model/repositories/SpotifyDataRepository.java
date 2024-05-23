package com.mangomelody.mangomelodybackend.model.repositories;

import com.mangomelody.mangomelodybackend.model.entities.SpotifyDataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotifyDataRepository extends CrudRepository<SpotifyDataEntity, Integer> {
}