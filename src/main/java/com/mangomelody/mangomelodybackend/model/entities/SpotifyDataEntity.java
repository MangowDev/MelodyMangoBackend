package com.mangomelody.mangomelodybackend.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.Objects;

@Entity
@Table(name = "spotify_data", schema = "MangoMelody")
public class SpotifyDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id", nullable = false)
    private Integer dataId;

    @NotBlank(message = "The Spotify username can't be empty")
    @NotNull
    @Size(min = 1, max = 150, message = "The Spotify username can't be longer than 150 characters")
    @Column(name = "spotify_username", nullable = false, length = 150)
    private String spotifyUsername;

    @Email
    @NotBlank(message = "The Spotify email can't be empty")
    @NotNull
    @Size(min = 1, max = 150, message = "The Spotify email can't be longer than 100 characters")
    @Column(name = "spotify_email", nullable = false, unique = true, length = 150)
    private String spotifyEmail;

    @Size(min = 1, max = 255, message = "The Spotify image can't be longer than 255 characters")
    @Column(name = "spotify_image", nullable = true, length = 255)
    private String spotifyImage;

    @Size(max = 255, message = "The Spotify token can't be longer than 255 characters")
    @Column(name = "spotify_token", length = 255)
    private String spotifyToken;

    @Size(max = 255, message = "The refresh token can't be longer than 255 characters")
    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UsersEntity userId;

    // Getters and setters
    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
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

    public String getSpotifyImage() {
        return spotifyImage;
    }

    public void setSpotifyImage(String spotifyImage) {
        this.spotifyImage = spotifyImage;
    }


    public String getSpotifyToken() {
        return spotifyToken;
    }

    public void setSpotifyToken(String spotifyToken) {
        this.spotifyToken = spotifyToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UsersEntity getUserId() {
        return userId;
    }

    public void setUserId(UsersEntity userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpotifyDataEntity that = (SpotifyDataEntity) o;
        return Objects.equals(dataId, that.dataId) &&
                Objects.equals(spotifyUsername, that.spotifyUsername) &&
                Objects.equals(spotifyEmail, that.spotifyEmail) &&
                Objects.equals(spotifyImage, that.spotifyImage) &&
                Objects.equals(spotifyToken, that.spotifyToken) &&
                Objects.equals(refreshToken, that.refreshToken) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataId, spotifyUsername, spotifyEmail, spotifyImage, spotifyToken, refreshToken, userId);
    }
}
