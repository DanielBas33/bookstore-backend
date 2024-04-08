package com.pruebas.library.repository;

import com.pruebas.library.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities in the database.
 */
@Repository
public interface UserRepository extends ListCrudRepository<User, Integer> {

    /**
     * Find a user by their email address.
     *
     * @param email the email address of the user to find
     * @return an Optional containing the user if found, or empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user exists by their email address.
     *
     * @param email the email address to check
     * @return true if a user with the specified email exists, false otherwise
     */
    Boolean existsByEmail(String email);
}
