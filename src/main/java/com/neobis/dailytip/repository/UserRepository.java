package com.neobis.dailytip.repository;

import com.neobis.dailytip.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT a FROM Users a WHERE a.email = :email ")
   Optional <Users> findByEmail(@Param("email") String email);

    @Query("SELECT a FROM Users a WHERE a.email = :email ")
    Users findUsersByEmail(@Param("email") String email);
}
