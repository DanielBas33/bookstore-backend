package com.pruebas.library.repository;


import com.pruebas.library.TestDataUtil;
import com.pruebas.library.model.Author;
import com.pruebas.library.model.Book;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class BookRepositoryIntegrationTests {

    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    @Transactional
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);
        bookRepository.save(book);
        Optional<Book> result = bookRepository.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();

        Book bookA = TestDataUtil.createTestBookA(author);
        bookRepository.save(bookA);

        Book bookB = TestDataUtil.createTestBookB(author);
        bookRepository.save(bookB);

        Book bookC = TestDataUtil.createTestBookC(author);
        bookRepository.save(bookC);

        Iterable<Book> result = bookRepository.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
    }

    @Test
    @Transactional
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthorA();

        Book bookA = TestDataUtil.createTestBookA(author);
        bookRepository.save(bookA);

        bookA.setTitle("UPDATED");
        bookRepository.save(bookA);

        Optional<Book> result = bookRepository.findById(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthorA();

        Book bookA = TestDataUtil.createTestBookA(author);
        bookRepository.save(bookA);

        bookRepository.deleteById(bookA.getIsbn());

        Optional<Book> result = bookRepository.findById(bookA.getIsbn());
        assertThat(result).isEmpty();
    }
}
