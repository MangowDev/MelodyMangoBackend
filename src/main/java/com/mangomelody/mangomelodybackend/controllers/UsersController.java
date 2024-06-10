package com.mangomelody.mangomelodybackend.controllers;

import com.mangomelody.mangomelodybackend.model.entities.UsersEntity;
import com.mangomelody.mangomelodybackend.model.repositories.UsersRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping
    public List<UsersEntity> getAllUsers() {
        return (List<UsersEntity>) usersRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping("/{userId}")
    public ResponseEntity<UsersEntity> getUserById(@PathVariable("userId") int userId) {
        Optional<UsersEntity> userData = usersRepository.findById(userId);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping("/username/{username}")
    public ResponseEntity<UsersEntity> getUserByUsername(@PathVariable("username") String username) {
        UsersEntity userData = usersRepository.findByUsername(username);

        if (userData != null) {
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping("/search/{username}")
    public ResponseEntity<List<UsersEntity>> searchUsersByUsername(@PathVariable("username") String username) {
        List<UsersEntity> users = usersRepository.findByUsernameContaining(username);

        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @PostMapping
    public ResponseEntity<UsersEntity> createUser(@RequestBody UsersEntity user) {
        try {
            UsersEntity _user = usersRepository.save(user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @PutMapping("/{userId}")
    public ResponseEntity<UsersEntity> updateUser(@PathVariable("userId") int userId, @RequestBody UsersEntity user) {
        Optional<UsersEntity> userData = usersRepository.findById(userId);

        if (userData.isPresent()) {
            UsersEntity _user = userData.get();

            if (user.getUsername() != null) {
                _user.setUsername(user.getUsername());
            }
            if (user.getEmail() != null) {
                _user.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                _user.setPassword(user.getPassword());
            }
            if (user.getProfileState() != null) {
                _user.setProfileState(user.getProfileState());
            }

            return new ResponseEntity<>(usersRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @PutMapping("/username/{username}")
    public ResponseEntity<UsersEntity> updateUserByUsername(@PathVariable("username") String username, @RequestBody UsersEntity user) {
        UsersEntity userData = usersRepository.findByUsername(username);

        if (userData != null) {

            if (user.getUsername() != null) {
                userData.setUsername(user.getUsername());
            }
            if (user.getEmail() != null) {
                userData.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                userData.setPassword(user.getPassword());
            }
            if (user.getProfileState() != null) {
                userData.setProfileState(user.getProfileState());
            }

            return new ResponseEntity<>(usersRepository.save(userData), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") int userId) {
        try {
            usersRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
