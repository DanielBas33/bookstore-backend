package com.pruebas.library.controller;

import com.pruebas.library.dto.AuthorDto;
import com.pruebas.library.model.Author;
import com.pruebas.library.mappers.Mapper;
import com.pruebas.library.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller class for managing operations related to authors.
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<Author, AuthorDto> authorMapper;

    /**
     * Constructs a new AuthorController with the specified services.
     *
     * @param authorService The service for managing Author entities.
     * @param authorMapper  The mapper used for converting between Author and AuthorDto.
     */
    public AuthorController(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    /**
     * Creates a new author in the database.
     *
     * @param authorDto The DTO representing the author details.
     * @return ResponseEntity containing the created AuthorDto and HttpStatus.CREATED.
     */
    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        Author author = authorMapper.mapFrom(authorDto);
        Author savedAuthor = authorService.save(author);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);
    }

    /**
     * Retrieves all authors from the database.
     *
     * @return List of AuthorDto representing all authors.
     */
    @GetMapping
    public List<AuthorDto> listAuthors() {
        List<Author> authors = authorService.findAll();
        return authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific author by ID from the database.
     *
     * @param id The ID of the author to retrieve.
     * @return ResponseEntity containing the AuthorDto if found, or HttpStatus.NOT_FOUND if not found.
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<Author> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(author -> new ResponseEntity<>(authorMapper.mapTo(author), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing author with the specified ID in the database.
     *
     * @param id         The ID of the author to update.
     * @param authorDto  The DTO containing updated author details.
     * @return ResponseEntity containing the updated AuthorDto and HttpStatus.OK if successful,
     *         or HttpStatus.NOT_FOUND if the author with the specified ID does not exist.
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorDto.setId(id);
        Author author = authorMapper.mapFrom(authorDto);
        Author savedAuthor = authorService.save(author);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.OK);
    }

    /**
     * Partially updates an existing author with the specified ID in the database.
     *
     * @param id         The ID of the author to update.
     * @param authorDto  The DTO containing the partial author details to update.
     * @return ResponseEntity containing the updated AuthorDto and HttpStatus.OK if successful,
     *         or HttpStatus.NOT_FOUND if the author with the specified ID does not exist.
     */
    @PatchMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Author author = authorMapper.mapFrom(authorDto);
        Author updatedAuthor = authorService.partialUpdate(id, author);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
    }

    /**
     * Deletes an author with the specified ID from the database.
     *
     * @param id The ID of the author to delete.
     * @return ResponseEntity with HttpStatus.NO_CONTENT upon successful deletion,
     *         or HttpStatus.NOT_FOUND if the author with the specified ID does not exist.
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id) {
        authorService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
