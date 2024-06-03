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
            if (user.getUserRole() != null) {
                _user.setUserRole(user.getUserRole());
            }
            if (user.getSpotifyToken() != null) {
                _user.setSpotifyToken(user.getSpotifyToken());
            }
            if (user.getRefreshToken() != null) {
                _user.setRefreshToken(user.getRefreshToken());
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
            if (user.getUserRole() != null) {
                userData.setUserRole(user.getUserRole());
            }
            if (user.getSpotifyToken() != null) {
                userData.setSpotifyToken(user.getSpotifyToken());
            }
            if (user.getRefreshToken() != null) {
                userData.setRefreshToken(user.getRefreshToken());
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

    @CrossOrigin(origins = "http://localhost:5173/")
    @PutMapping("/username/{username}/refresh-token")
    public ResponseEntity<UsersEntity> refreshSpotifyToken(@PathVariable("username") String username) {
        UsersEntity userData = usersRepository.findByUsername(username);

        if (userData != null) {
            String newAccessToken = requestNewAccessToken(userData.getRefreshToken());

            if (newAccessToken != null) {
                userData.setSpotifyToken(newAccessToken);
                return new ResponseEntity<>(usersRepository.save(userData), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos auxiliares para manejar el token de Spotify

    private String extractAccessTokenFromResponse(String responseBody) throws JSONException {
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject.getString("access_token");
    }

    private String requestNewAccessToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("f9687dfd6adc42c4b9422bcb7d1f9d41", "c8b34fe86d47495bbf38acbbb48b6a48");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange("https://accounts.spotify.com/api/token", HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                return extractAccessTokenFromResponse(response.getBody());
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
