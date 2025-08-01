package com.sysc.workshop.user.repository;

import com.sysc.workshop.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID userId);

   User findByEmail(String email);
}