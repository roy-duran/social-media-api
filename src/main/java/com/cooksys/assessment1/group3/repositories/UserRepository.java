package com.cooksys.assessment1.group3.repositories;

import com.cooksys.assessment1.group3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId);

    Optional<User> findAllByDeletedFalse();

    // Uses Embedded Credentials Username
    Optional<User> findByCredentialsUsername(String username);

//    List<User> findAllByDeleted();
}
