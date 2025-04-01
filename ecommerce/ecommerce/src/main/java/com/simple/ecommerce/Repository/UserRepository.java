package com.simple.ecommerce.Repository;

import com.simple.ecommerce.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Add this method to find a user by username
     Optional<User> findByUsername(String username);
}