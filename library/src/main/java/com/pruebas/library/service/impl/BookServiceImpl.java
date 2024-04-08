package com.pruebas.library.service.impl;

import com.pruebas.library.model.Book;
import com.pruebas.library.repository.BookRepository;
import com.pruebas.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the BookService interface for managing Book entities.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * Constructs a new BookServiceImpl with the specified BookRepository.
     *
     * @param bookRepository The repository for Book entities.
     */
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Creates or updates a book with the specified ISBN.
     *
     * @param isbn The ISBN of the book to create or update.
     * @param book The book details to save.
     * @return The created or updated book.
     */
    @Override
    public Book createUpdateBook(String isbn, Book book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    /**
     * Retrieves all books from the database.
     *
     * @return List of all books.
     */
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves all books from the database with pagination support.
     *
     * @param pageable Pagination information.
     * @return Page of books.
     */
    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    /**
     * Retrieves a book by ISBN from the database.
     *
     * @param isbn The ISBN of the book to retrieve.
     * @return Optional containing the book if found, otherwise empty.
     */
    @Override
    public Optional<Book> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }

    /**
     * Checks if a book with the specified ISBN exists in the database.
     *
     * @param isbn The ISBN of the book to check.
     * @return true if the book exists, false otherwise.
     */
    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    /**
     * Partially updates a book with the specified ISBN.
     *
     * @param isbn The ISBN of the book to update.
     * @param book The book details to update.
     * @return The updated book.
     * @throws RuntimeException if the book to update does not exist.
     */
    @Override
    public Book partialUpdate(String isbn, Book book) {
        book.setIsbn(isbn);

        return bookRepository.findById(isbn)
                .map(existingBook -> {
                    Optional.ofNullable(book.getTitle()).ifPresent(existingBook::setTitle);
                    // Add more fields to update here as needed
                    return bookRepository.save(existingBook);
                })
                .orElseThrow(() -> new RuntimeException("Book does not exist"));
    }

    /**
     * Deletes a book with the specified ISBN from the database.
     *
     * @param isbn The ISBN of the book to delete.
     */
    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
