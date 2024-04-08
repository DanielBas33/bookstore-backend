package com.pruebas.library.service;

import com.pruebas.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Book entities.
 */
public interface BookService {

    /**
     * Creates or updates a book with the specified ISBN.
     *
     * @param isbn The ISBN of the book to create or update.
     * @param book The book details to save.
     * @return The created or updated book.
     */
    Book createUpdateBook(String isbn, Book book);

    /**
     * Retrieves all books from the database.
     *
     * @return List of all books.
     */
    List<Book> findAll();

    /**
     * Retrieves all books from the database with pagination support.
     *
     * @param pageable Pagination information.
     * @return Page of books.
     */
    Page<Book> findAll(Pageable pageable);

    /**
     * Retrieves a book by ISBN from the database.
     *
     * @param isbn The ISBN of the book to retrieve.
     * @return Optional containing the book if found, otherwise empty.
     */
    Optional<Book> findOne(String isbn);

    /**
     * Checks if a book with the specified ISBN exists in the database.
     *
     * @param isbn The ISBN of the book to check.
     * @return true if the book exists, false otherwise.
     */
    boolean isExists(String isbn);

    /**
     * Partially updates an existing book with the specified ISBN.
     *
     * @param isbn The ISBN of the book to update.
     * @param book The book details to update.
     * @return The updated book.
     * @throws RuntimeException if the book to update does not exist.
     */
    Book partialUpdate(String isbn, Book book);

    /**
     * Deletes a book with the specified ISBN from the database.
     *
     * @param isbn The ISBN of the book to delete.
     */
    void delete(String isbn);

}
