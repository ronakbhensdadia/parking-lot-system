package com.parking.repository;

import com.parking.entity.EUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<EUser, Long> {
    
    Optional<EUser> findByEmail(String email);
}