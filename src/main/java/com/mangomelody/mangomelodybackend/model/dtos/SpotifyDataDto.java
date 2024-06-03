package com.mangomelody.mangomelodybackend.model.dtos;

import jakarta.persistence.*;

public class SpotifyDataDto {
    private int userId;
    private String spotifyUsername;
    private String spotifyEmail;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSpotifyUsername() {
        return spotifyUsername;
    }

    public void setSpotifyUsername(String spotifyUsername) {
        this.spotifyUsername = spotifyUsername;
    }

    public String getSpotifyEmail() {
        return spotifyEmail;
    }

    public void setSpotifyEmail(String spotifyEmail) {
        this.spotifyEmail = spotifyEmail;
    }

}
