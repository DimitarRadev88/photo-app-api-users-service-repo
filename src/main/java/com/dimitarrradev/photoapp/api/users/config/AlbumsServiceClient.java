package com.dimitarrradev.photoapp.api.users.config;


import com.dimitarrradev.photoapp.api.users.model.AlbumDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@FeignClient("albums-ws")
public interface AlbumsServiceClient {

    @GetMapping("users/{userId}/albums")
    @Retry(name = "albums-ws")
    @CircuitBreaker(name="albums-ws", fallbackMethod = "getAlbumsFallback")
    List<AlbumDto> getAlbums(@PathVariable String userId);

    default List<AlbumDto> getAlbumsFallback(String userId, Throwable exception) {
        System.out.println("Param = " + userId);
        System.out.println("Exception took place: " + exception.getMessage());
        return Collections.emptyList();
    }

}
