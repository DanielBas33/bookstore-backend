package com.pruebas.library;

import com.pruebas.library.auth.model.AuthenticationRequest;
import com.pruebas.library.auth.model.RegisterRequest;
import com.pruebas.library.dto.AuthorDto;
import com.pruebas.library.dto.BookDto;
import com.pruebas.library.model.Author;
import com.pruebas.library.model.Book;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Jesse A Casey")
                .age(24)
                .build();
    }

    public static Book createTestBookA(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .price(12.4)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(authorDto)
                .price(12.4)
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-1")
                .title("Beyond the Horizon")
                .author(author)
                .price(12.4)
                .build();
    }

    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-2")
                .title("The Last Ember")
                .author(author)
                .price(12.4)
                .build();
    }

    public static RegisterRequest createRegisterRequest() {
        return RegisterRequest
                .builder()
                .email("UserA@junit.com")
                .firstname("UserA-Firstname")
                .lastname("UserA-Lastname")
                .password("$2a$12$7DXgitRWFcE/NBok6CFu..7MjHStikyIyhoaCGB23ErT9afio7i.K")
                .build();
    }

    public static AuthenticationRequest createAuthenticationRequest() {
        return AuthenticationRequest
                .builder()
                .email("UserA@junit.com")
                .password("$2a$12$7DXgitRWFcE/NBok6CFu..7MjHStikyIyhoaCGB23ErT9afio7i.K")
                .build();
    }
}
