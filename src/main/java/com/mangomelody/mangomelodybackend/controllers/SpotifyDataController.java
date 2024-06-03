package com.mangomelody.mangomelodybackend.controllers;

import com.mangomelody.mangomelodybackend.model.dtos.SpotifyDataDto;
import com.mangomelody.mangomelodybackend.model.entities.SpotifyDataEntity;
import com.mangomelody.mangomelodybackend.model.entities.UsersEntity;
import com.mangomelody.mangomelodybackend.model.repositories.SpotifyDataRepository;
import com.mangomelody.mangomelodybackend.model.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/spotify-data")
public class SpotifyDataController {

    @Autowired
    private SpotifyDataRepository spotifyDataRepository;

    @Autowired
    private UsersRepository usersRepository;

    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping
    public List<SpotifyDataEntity> getAllSpotifyData() {
        return (List<SpotifyDataEntity>) spotifyDataRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping("/id/{dataId}")
    public ResponseEntity<SpotifyDataEntity> getSpotifyDataById(@PathVariable("dataId") int dataId) {
        Optional<SpotifyDataEntity> spotifyData = spotifyDataRepository.findById(dataId);

        if (spotifyData.isPresent()) {
            return new ResponseEntity<>(spotifyData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @CrossOrigin(origins = "http://localhost:5173/")
    @PostMapping
    public ResponseEntity<SpotifyDataEntity> createSpotifyData(@RequestBody SpotifyDataDto spotifyDataDto) {
        try {
            Optional<UsersEntity> user = usersRepository.findById(spotifyDataDto.getUserId());

            if (user.isPresent()) {
                SpotifyDataEntity spotifyData = new SpotifyDataEntity();
                spotifyData.setSpotifyUsername(spotifyDataDto.getSpotifyUsername());
                spotifyData.setSpotifyEmail(spotifyDataDto.getSpotifyEmail());
                spotifyData.setUserId(user.get());
                SpotifyDataEntity _spotifyData = spotifyDataRepository.save(spotifyData);
                return new ResponseEntity<>(_spotifyData, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @PutMapping("/{dataId}")
    public ResponseEntity<SpotifyDataEntity> updateSpotifyData(@PathVariable("dataId") int dataId, @RequestBody SpotifyDataEntity spotifyData) {
        Optional<SpotifyDataEntity> spotifyDataOptional = spotifyDataRepository.findById(dataId);

        if (spotifyDataOptional.isPresent()) {
            SpotifyDataEntity _spotifyData = spotifyDataOptional.get();

            if (spotifyData.getSpotifyUsername() != null) {
                _spotifyData.setSpotifyUsername(spotifyData.getSpotifyUsername());
            }
            if (spotifyData.getSpotifyEmail() != null) {
                _spotifyData.setSpotifyEmail(spotifyData.getSpotifyEmail());
            }
            if (spotifyData.getSpotifyToken() != null) {
                _spotifyData.setSpotifyToken(spotifyData.getSpotifyToken());
            }
            if (spotifyData.getRefreshToken() != null) {
                _spotifyData.setRefreshToken(spotifyData.getRefreshToken());
            }
            if (spotifyData.getUserId() != null) {
                _spotifyData.setUserId(spotifyData.getUserId());
            }

            return new ResponseEntity<>(spotifyDataRepository.save(_spotifyData), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @DeleteMapping("/{dataId}")
    public ResponseEntity<HttpStatus> deleteSpotifyData(@PathVariable("dataId") int dataId) {
        try {
            spotifyDataRepository.deleteById(dataId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SpotifyDataEntity>> getSpotifyDataByUserId(@PathVariable("userId") int userId) {
        List<SpotifyDataEntity> spotifyDataList = spotifyDataRepository.findSpotifyDataByUserId(userId);

        if (!spotifyDataList.isEmpty()) {
            return new ResponseEntity<>(spotifyDataList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
