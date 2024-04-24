package com.pruebas.library.service.impl;

import com.pruebas.library.model.Author;
import com.pruebas.library.repository.AuthorRepository;
import com.pruebas.library.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of the AuthorService interface for managing Author entities.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Constructs a new AuthorServiceImpl with the specified AuthorRepository.
     *
     * @param authorRepository The repository for Author entities.
     */
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Saves an author to the database.
     *
     * @param author The author to save.
     * @return The saved author.
     */
    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    /**
     * Retrieves all authors from the database.
     *
     * @return List of all authors.
     */
    @Override
    public List<Author> findAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an author by ID from the database.
     *
     * @param id The ID of the author to retrieve.
     * @return Optional containing the author if found, otherwise empty.
     */
    @Override
    public Optional<Author> findOne(Long id) {
        return authorRepository.findById(id);
    }

    /**
     * Checks if an author with the specified ID exists in the database.
     *
     * @param id The ID of the author to check.
     * @return true if the author exists, false otherwise.
     */
    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    /**
     * Partially updates an existing author with the specified ID.
     *
     * @param id     The ID of the author to update.
     * @param author The author details to update.
     * @return The updated author.
     * @throws RuntimeException if the author does not exist.
     */
    @Override
    public Author partialUpdate(Long id, Author author) {

        return authorRepository.findById(id)
                .map(existingAuthor -> {
                    Optional.ofNullable(author.getName()).ifPresent(existingAuthor::setName);
                    Optional.ofNullable(author.getAge()).ifPresent(existingAuthor::setAge);
                    return authorRepository.save(existingAuthor);
                })
                .orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

    /**
     * Deletes an author with the specified ID from the database.
     *
     * @param id The ID of the author to delete.
     */
    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
