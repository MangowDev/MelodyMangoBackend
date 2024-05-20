package com.mangomelody.mangomelodybackend.auth;

import com.mangomelody.mangomelodybackend.model.entities.UsersEntity;
import com.mangomelody.mangomelodybackend.model.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class AuthService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UsersEntity register(UsersEntity user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public String login(String username, String password) {
        UsersEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username.");
        }
        if (password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be empty.");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password.");
        }
        return jwtUtil.generateToken(user);
    }
}