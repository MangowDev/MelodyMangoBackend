package com.mangomelody.mangomelodybackend.model.entities;

import com.mangomelody.mangomelodybackend.model.entities.roles.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "users", schema = "MangoMelody")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Basic
    @NotBlank(message = "The username can't be empty")
    @NotNull
    @Size(min = 1, max = 30, message = "The username can't be longer than 30 characters")
    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Basic
    @NotNull
    @NotBlank(message = "The password can't be empty")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Basic
    @Email
    @NotNull
    @NotBlank(message = "The email can't be empty")
    @Size(min = 1, max = 50, message = "The email can't be longer than 50 characters")
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "user_role", nullable = false)
    private UserRole userRole = UserRole.USER;

    @Basic
    @Column(name = "spotify_token", length = 255, nullable = true)
    private String spotifyToken;

    @Basic
    @Column(name = "refresh_token", length = 255, nullable = true)
    private String refreshToken;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return userId == that.userId && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(email, that.email) && Objects.equals(spotifyToken, that.spotifyToken) && userRole == that.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, email, spotifyToken, userRole);
    }
}
