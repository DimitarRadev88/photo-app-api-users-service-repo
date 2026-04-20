package com.dimitarrradev.photoapp.api.users.config;


import com.dimitarrradev.photoapp.api.users.model.AlbumDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("albums-ws")
public interface AlbumsServiceClient {

    @GetMapping("users/{userId}/albums")
    List<AlbumDto> getAlbums(@PathVariable String userId);

}
