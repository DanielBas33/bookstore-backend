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

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;

    private Mapper<Author, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        Author author = authorMapper.mapFrom(authorDto);
        Author savedAuthor = authorService.save(author);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);
    }

    @GetMapping
    public List<AuthorDto> listAuthors() {
        List<Author> authors = authorService.findAll();
        return authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<Author> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(author -> {
            AuthorDto authorDto = authorMapper.mapTo(author);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto) {

        if(!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorDto.setId(id);
        Author author = authorMapper.mapFrom(authorDto);
        Author savedAuthor = authorService.save(author);
        return new ResponseEntity<>(
                authorMapper.mapTo(savedAuthor),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto
    ) {
        if(!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Author author = authorMapper.mapFrom(authorDto);
        Author updatedAuthor = authorService.partialUpdate(id, author);
        return new ResponseEntity<>(
                authorMapper.mapTo(updatedAuthor),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id) {
        authorService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
