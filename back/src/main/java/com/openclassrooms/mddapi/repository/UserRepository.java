package com.openclassrooms.mddapi.repository;


import com.openclassrooms.mddapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository of the users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
    public User findByUsername(String username);
}