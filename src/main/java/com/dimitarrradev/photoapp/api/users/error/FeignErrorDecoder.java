package com.dimitarrradev.photoapp.api.users.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new BadRequestException();
            case 404 -> new ResponseStatusException(HttpStatus.valueOf(response.status()), methodKey.contains("getAlbums")
                    ? "Users albums not found"
                    : response.reason()
            );
            default -> new Exception();
        };
    }

}
