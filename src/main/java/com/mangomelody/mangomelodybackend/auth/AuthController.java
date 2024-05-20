package com.mangomelody.mangomelodybackend.auth;

import com.mangomelody.mangomelodybackend.model.entities.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register")
    public ResponseEntity<UsersEntity> register(@RequestBody UsersEntity user) {
        try {
            UsersEntity newUser = authService.register(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while registering user", e);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsersEntity user) {
        try {
            String token = authService.login(user.getUsername(), user.getPassword());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while logging in", e);
        }
    }
}
