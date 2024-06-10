package com.mangomelody.mangomelodybackend.controllers;

import com.mangomelody.mangomelodybackend.model.dtos.FriendshipsDto;
import com.mangomelody.mangomelodybackend.model.entities.UsersEntity;
import com.mangomelody.mangomelodybackend.model.repositories.FriendshipsRepository;
import com.mangomelody.mangomelodybackend.model.entities.FriendshipsEntity;
import com.mangomelody.mangomelodybackend.model.repositories.UsersRepository;
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

    @Autowired
    private UsersRepository usersRepository;

    // Obtener todas las relaciones de amistad
    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping
    public List<FriendshipsEntity> getAllFriendships() {
        return (List<FriendshipsEntity>) friendshipsRepository.findAll();
    }

    // Obtener una relación de amistad por su id
    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping("/{friendshipId}")
    public ResponseEntity<FriendshipsEntity> getFriendshipById(@PathVariable("friendshipId") int friendshipId) {
        Optional<FriendshipsEntity> friendshipData = friendshipsRepository.findById(friendshipId);

        if (friendshipData.isPresent()) {
            return new ResponseEntity<>(friendshipData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear una nueva relación de amistad usando DTO
    @CrossOrigin(origins = "http://localhost:5173/")
    @PostMapping
    public ResponseEntity<FriendshipsEntity> createFriendship(@RequestBody FriendshipsDto friendshipsDto) {
        try {
            Optional<UsersEntity> user1 = usersRepository.findById(friendshipsDto.getUser1Id());
            Optional<UsersEntity> user2 = usersRepository.findById(friendshipsDto.getUser2Id());

            if (user1.isPresent() && user2.isPresent()) {
                FriendshipsEntity friendship = new FriendshipsEntity();
                friendship.setUser1(user1.get());
                friendship.setUser2(user2.get());
                friendship.setStatus(friendshipsDto.getStatus());
                FriendshipsEntity _friendship = friendshipsRepository.save(friendship);
                return new ResponseEntity<>(_friendship, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar el estado de una relación de amistad por su id
    @CrossOrigin(origins = "http://localhost:5173/")
    @PutMapping("/{friendshipId}")
    public ResponseEntity<FriendshipsEntity> updateFriendshipStatus(
            @PathVariable("friendshipId") int friendshipId,
            @RequestBody FriendshipsEntity friendshipDetails) {
        Optional<FriendshipsEntity> friendshipData = friendshipsRepository.findById(friendshipId);

        if (friendshipData.isPresent()) {
            FriendshipsEntity friendship = friendshipData.get();
            friendship.setStatus(friendshipDetails.getStatus());
            return new ResponseEntity<>(friendshipsRepository.save(friendship), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una relación de amistad por su id
    @CrossOrigin(origins = "http://localhost:5173/")
    @DeleteMapping("/{friendshipId}")
    public ResponseEntity<HttpStatus> deleteFriendship(@PathVariable("friendshipId") int friendshipId) {
        try {
            friendshipsRepository.deleteById(friendshipId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener los datos de una relación de amistad entre dos usuarios
    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping("/details")
    public ResponseEntity<FriendshipsEntity> getFriendshipDetails(
            @RequestParam("user1Id") int user1Id,
            @RequestParam("user2Id") int user2Id) {
        Optional<UsersEntity> user1 = usersRepository.findById(user1Id);
        Optional<UsersEntity> user2 = usersRepository.findById(user2Id);

        if (user1.isPresent() && user2.isPresent()) {
            Optional<FriendshipsEntity> friendship = friendshipsRepository.findFriendshipBetweenUsers(user1.get(), user2.get());
            if (friendship.isPresent()) {
                return new ResponseEntity<>(friendship.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Verificar si existe una relación de amistad entre dos usuarios
    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping("/exists")
    public ResponseEntity<Boolean> friendshipExists(
            @RequestParam("user1Id") int user1Id,
            @RequestParam("user2Id") int user2Id) {
        Optional<UsersEntity> user1 = usersRepository.findById(user1Id);
        Optional<UsersEntity> user2 = usersRepository.findById(user2Id);

        if (user1.isPresent() && user2.isPresent()) {
            Optional<FriendshipsEntity> friendship = friendshipsRepository.findFriendshipBetweenUsers(user1.get(), user2.get());
            return new ResponseEntity<>(friendship.isPresent(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    // Obtener todas las relaciones de amistad que involucren a un usuario específico
    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FriendshipsEntity>> getFriendshipsByUserId(@PathVariable("userId") int userId) {
        try {
            List<FriendshipsEntity> friendships = friendshipsRepository.findFriendshipsByUserId(userId);
            if (friendships.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(friendships, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
