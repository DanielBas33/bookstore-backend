package com.pruebas.library.repository;

import com.pruebas.library.model.Book;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Book entities.
 */
@Repository
public interface BookRepository extends ListCrudRepository<Book, String>,
        PagingAndSortingRepository<Book, String> {
}