package com.dimitarrradev.photoapp.api.users.model;

public record AlbumDto(
    String albumId,
    String userId,
    String name,
    String description
) {
}
