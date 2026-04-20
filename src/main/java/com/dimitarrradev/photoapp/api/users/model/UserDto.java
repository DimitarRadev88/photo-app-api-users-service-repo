package com.dimitarrradev.photoapp.api.users.model;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 65644312125212L;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private String password;
    private String encryptedPassword;
    private List<AlbumDto> albumDtos;

    public UserDto() {
    }

    public UserDto(Long id, String firstName, String lastName, String email, String userId, String password, String encryptedPassword, List<AlbumDto> albumDtos) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.encryptedPassword = encryptedPassword;
        this.albumDtos = albumDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<AlbumDto> getAlbumDtos() {
        return albumDtos;
    }

    public void setAlbumDto(List<AlbumDto> albumDtos) {
        this.albumDtos = albumDtos;
    }
}
