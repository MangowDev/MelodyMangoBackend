package com.mangomelody.mangomelodybackend.controllers;

import com.mangomelody.mangomelodybackend.model.repositories.FriendshipsRepository;
import com.mangomelody.mangomelodybackend.model.entities.FriendshipsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipsController {

    @Autowired
    private FriendshipsRepository friendshipsRepository;

    // Obtener todas las relaciones de amistad
    @GetMapping
    public List<FriendshipsEntity> getAllFriendships() {
        return (List<FriendshipsEntity>) friendshipsRepository.findAll();
    }

    // Obtener una relación de amistad por su id
    @GetMapping("/{friendshipId}")
    public ResponseEntity<FriendshipsEntity> getFriendshipById(@PathVariable("friendshipId") int friendshipId) {
        Optional<FriendshipsEntity> friendshipData = friendshipsRepository.findById(friendshipId);

        if (friendshipData.isPresent()) {
            return new ResponseEntity<>(friendshipData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear una nueva relación de amistad
    @PostMapping
    public ResponseEntity<FriendshipsEntity> createFriendship(@RequestBody FriendshipsEntity friendship) {
        try {
            FriendshipsEntity _friendship = friendshipsRepository.save(friendship);
            return new ResponseEntity<>(_friendship, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar una relación de amistad por su id
    @DeleteMapping("/{friendshipId}")
    public ResponseEntity<HttpStatus> deleteFriendship(@PathVariable("friendshipId") int friendshipId) {
        try {
            friendshipsRepository.deleteById(friendshipId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
