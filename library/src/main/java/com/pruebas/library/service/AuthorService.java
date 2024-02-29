package com.pruebas.library.service;

import com.pruebas.library.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author save(Author author);

    List<Author> findAll();

    Optional<Author> findOne(Long id);

    boolean isExists(Long id);

    Author partialUpdate(Long id, Author author);

    void delete(Long id);
}
