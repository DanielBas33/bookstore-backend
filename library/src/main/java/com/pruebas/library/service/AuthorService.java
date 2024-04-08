package com.pruebas.library.service;

import com.pruebas.library.model.Author;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Author entities.
 */
public interface AuthorService {

    /**
     * Saves an author to the database.
     *
     * @param author The author to save.
     * @return The saved author.
     */
    Author save(Author author);

    /**
     * Retrieves all authors from the database.
     *
     * @return List of all authors.
     */
    List<Author> findAll();

    /**
     * Retrieves an author by ID from the database.
     *
     * @param id The ID of the author to retrieve.
     * @return Optional containing the author if found, otherwise empty.
     */
    Optional<Author> findOne(Long id);

    /**
     * Checks if an author with the specified ID exists in the database.
     *
     * @param id The ID of the author to check.
     * @return true if the author exists, false otherwise.
     */
    boolean isExists(Long id);

    /**
     * Partially updates an existing author with the specified ID.
     *
     * @param id     The ID of the author to update.
     * @param author The author details to update.
     * @return The updated author.
     * @throws RuntimeException if the author to update does not exist.
     */
    Author partialUpdate(Long id, Author author);

    /**
     * Deletes an author with the specified ID from the database.
     *
     * @param id The ID of the author to delete.
     */
    void delete(Long id);
}
