package com.pruebas.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebas.library.TestDataUtil;
import com.pruebas.library.dto.BookDto;
import com.pruebas.library.model.Book;
import com.pruebas.library.service.BookService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerIntegrationTests {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/978-1-2345-6789-0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    @Transactional
    public void testThatUpdateBookReturnsHttpStatus200Ok() throws Exception {
        Book testBookEntityA = TestDataUtil.createTestBookA(null);
        Book savedBook = bookService.createUpdateBook(
                testBookEntityA.getIsbn(), testBookEntityA
        );

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setIsbn(savedBook.getIsbn());
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatCreateBookReturnsCreatedBook() throws Exception {
        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/978-1-2345-6789-0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Attic")
        );
    }

    @Test
    @Transactional
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        Book testBookEntityA = TestDataUtil.createTestBookA(null);
        Book savedBook = bookService.createUpdateBook(
                testBookEntityA.getIsbn(), testBookEntityA
        );

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setIsbn(savedBook.getIsbn());
        testBookA.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn() )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );
    }


    @Test
    public void testThatListBooksReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsBook() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("The Shadow in the Attic")
        );
    }

    @Test
    @Transactional
    public void testThatGetBookReturnsHttpStatus200OkWhenBookExists() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus404WhenBookDoesntExist() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @Transactional
    public void testThatPartialUpdateBookReturnsHttpStatus200Ok() throws Exception {
        Book testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @Transactional
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception {
        Book testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );
    }

    @Test
    public void testThatDeleteNonExistingBookReturnsHttpStatus204NoContent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/kjsbdfjdfsk")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteExistingBookReturnsHttpStatus204NoContent() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
