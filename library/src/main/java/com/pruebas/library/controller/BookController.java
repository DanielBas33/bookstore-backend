package com.pruebas.library.controller;

import com.pruebas.library.dto.BookDto;
import com.pruebas.library.model.Book;
import com.pruebas.library.mappers.Mapper;
import com.pruebas.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller class for handling operations related to books.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final Mapper<Book, BookDto> bookMapper;

    /**
     * Constructs a new BookController.
     *
     * @param bookMapper  The mapper used for converting between Book and BookDto.
     * @param bookService The service for managing Book entities.
     */
    public BookController(Mapper<Book, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    /**
     * Creates or fully updates a book based on the provided ISBN.
     *
     * @param isbn    The ISBN of the book to create or update.
     * @param bookDto The DTO representing the book details.
     * @return ResponseEntity containing the updated or newly created BookDto.
     */
    @PutMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        Book book = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        Book savedBook = bookService.createUpdateBook(isbn, book);
        BookDto savedUpdatedBookDto = bookMapper.mapTo(savedBook);

        if (bookExists) {
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.CREATED);
        }
    }

    /**
     * Partially updates a book based on the provided ISBN.
     *
     * @param isbn    The ISBN of the book to partially update.
     * @param bookDto The DTO representing the book details to update.
     * @return ResponseEntity containing the updated BookDto.
     */
    @PatchMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book book = bookMapper.mapFrom(bookDto);
        Book updatedBook = bookService.partialUpdate(isbn, book);
        return new ResponseEntity<>(bookMapper.mapTo(updatedBook), HttpStatus.OK);
    }

    /**
     * Retrieves all books from the database.
     *
     * @return List of BookDto representing all books.
     */
    @GetMapping
    public List<BookDto> listBooks() {
        List<Book> books = bookService.findAll();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific book by its ISBN.
     *
     * @param isbn The ISBN of the book to retrieve.
     * @return ResponseEntity containing the BookDto if found, or NOT_FOUND status if not found.
     */
    @GetMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<Book> foundBook = bookService.findOne(isbn);
        return foundBook.map(book -> new ResponseEntity<>(bookMapper.mapTo(book), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a book from the database by its ISBN.
     *
     * @param isbn The ISBN of the book to delete.
     * @return ResponseEntity with NO_CONTENT status upon successful deletion.
     */
    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
