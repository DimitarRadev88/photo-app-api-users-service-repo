package com.dimitarrradev.photoapp.api.users.service;

import com.dimitarrradev.photoapp.api.users.config.AlbumsServiceClient;
import com.dimitarrradev.photoapp.api.users.dao.UsersRepository;
import com.dimitarrradev.photoapp.api.users.model.UserDto;
import com.dimitarrradev.photoapp.api.users.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
//    private final RestClient restClient;
    private final AlbumsServiceClient albumsClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UsersService(
            UsersRepository usersRepository, 
            ModelMapper mapper, 
            PasswordEncoder passwordEncoder, 
//            @Qualifier("loadBalancedClient") RestClient restClient
            AlbumsServiceClient albumsClient
    ) {
        this.usersRepository = usersRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
//        this.restClient = restClient;
        this.albumsClient = albumsClient;
    }
    

    public UserDto create(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        UserEntity userEntity = mapper.map(userDetails, UserEntity.class);

        UserEntity saved = usersRepository.save(userEntity);

        return mapper.map(saved, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository
                .findByEmail(username)
                .map(o -> new User(
                        o.getEmail(),
                        o.getEncryptedPassword(),
                        true,
                        true,
                        true,
                        true,
                        new ArrayList<>())
                )
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserDto getUserDetails(String username) {

        UserEntity userEntity = usersRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        UserDto map = mapper.map(userEntity, UserDto.class);

//        map.setAlbumDto(restClient.get().uri("users/{userId}/albums", userEntity.getUserId()).accept(MediaType.APPLICATION_JSON).retrieve().body(AlbumResponseModel.class));

        map.setAlbumDto(albumsClient.getAlbums(map.getUserId()));
        
        return map;
    }
}
