package com.dimitarrradev.photoapp.api.users.controller;

import com.dimitarrradev.photoapp.api.users.model.CreateUserRequestModel;
import com.dimitarrradev.photoapp.api.users.model.UserDto;
import com.dimitarrradev.photoapp.api.users.model.CreateUserResponseModel;
import com.dimitarrradev.photoapp.api.users.service.UsersService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final ModelMapper mapper;

    public UsersController(UsersService usersService, ModelMapper mapper) {
        this.usersService = usersService;
        this.mapper = mapper;
    }

    @GetMapping("/status/check")
    public String status() {
        return "Working";
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userModel) {
        UserDto userDetails = mapper.map(userModel, UserDto.class);
        UserDto userDto = usersService.create(userDetails);

        CreateUserResponseModel body = mapper.map(userDto, CreateUserResponseModel.class);

        return ResponseEntity.created(null).body(body);
    }

}
