package com.mangomelody.mangomelodybackend.model.entities;

import com.mangomelody.mangomelodybackend.model.entities.roles.FriendshipStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "friendships", schema = "MangoMelody")
public class FriendshipsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "friendship_id", nullable = false)
    private int friendshipId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user1_id", referencedColumnName = "user_id")
    private UsersEntity user1;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user2_id", referencedColumnName = "user_id")
    private UsersEntity user2;

    @Basic
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status", nullable = false)
    private FriendshipStatus status = FriendshipStatus.PENDING;

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

    public UsersEntity getUser1() {
        return user1;
    }

    public void setUser1(UsersEntity user1) {
        this.user1 = user1;
    }

    public UsersEntity getUser2() {
        return user2;
    }

    public void setUser2(UsersEntity user2) {
        this.user2 = user2;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendshipsEntity that = (FriendshipsEntity) o;
        return friendshipId == that.friendshipId && Objects.equals(user1, that.user1) && Objects.equals(user2, that.user2) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(friendshipId, user1, user2, status);
    }
}
