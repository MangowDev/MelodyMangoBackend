package com.mangomelody.mangomelodybackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/callback")
    public ResponseEntity<?> handleSpotifyCallback(@RequestBody Map<String, String> payload) {
        // Tu lógica aquí para manejar el callback de Spotify
        return new ResponseEntity<>("Callback received", HttpStatus.OK);
    }
}
