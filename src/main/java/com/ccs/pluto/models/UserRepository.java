package com.ccs.pluto.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    int countByUserid(String id);

    Optional<User> findByEmail(String email);
}
