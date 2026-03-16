package com.dimitarrradev.photoapp.api.users.dao;

import com.dimitarrradev.photoapp.api.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {
}
