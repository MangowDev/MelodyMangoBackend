package com.mangomelody.mangomelodybackend.model.dtos;

import com.mangomelody.mangomelodybackend.model.entities.roles.FriendshipStatus;

public class FriendshipsDto {
    private int user1Id;
    private int user2Id;
    private FriendshipStatus status = FriendshipStatus.PENDING;

    // Getters y Setters
    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
