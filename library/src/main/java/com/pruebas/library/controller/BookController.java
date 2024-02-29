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

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final Mapper<Book, BookDto> bookMapper;

    public BookController(Mapper<Book, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        Book book = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        Book savedBook = bookService.createUpdateBook(isbn, book);
        BookDto savedUpdatedBookDto = bookMapper.mapTo(savedBook);

        if(bookExists){
            return new ResponseEntity(savedUpdatedBookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity(savedUpdatedBookDto, HttpStatus.CREATED);
        }
    }

    @PatchMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ){
        if(!bookService.isExists(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book book = bookMapper.mapFrom(bookDto);
        Book updatedBook = bookService.partialUpdate(isbn, book);
        return new ResponseEntity<>(
                bookMapper.mapTo(updatedBook),
                HttpStatus.OK);

    }

    @GetMapping
    public List<BookDto> listBooks() {
        List<Book> books = bookService.findAll();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<Book> foundBook = bookService.findOne(isbn);
        return foundBook.map(book -> {
            BookDto bookDto = bookMapper.mapTo(book);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
