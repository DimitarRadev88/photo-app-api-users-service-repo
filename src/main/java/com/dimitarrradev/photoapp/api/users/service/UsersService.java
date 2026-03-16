package com.dimitarrradev.photoapp.api.users.service;

import com.dimitarrradev.photoapp.api.users.dao.UsersRepository;
import com.dimitarrradev.photoapp.api.users.model.UserDto;
import com.dimitarrradev.photoapp.api.users.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto create(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        UserEntity userEntity = mapper.map(userDetails, UserEntity.class);

        UserEntity saved = usersRepository.save(userEntity);

        return mapper.map(saved, UserDto.class);
    }

}
