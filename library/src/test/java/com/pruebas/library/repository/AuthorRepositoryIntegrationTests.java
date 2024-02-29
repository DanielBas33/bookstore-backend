package com.pruebas.library.repository;

import com.pruebas.library.model.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.pruebas.library.TestDataUtil;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AuthorRepositoryIntegrationTests {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        authorRepository.save(author);
        Optional<Author> result = authorRepository.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        authorRepository.save(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();
        authorRepository.save(authorC);

        Iterable<Author> result = authorRepository.findAll();
        assertThat(result)
                .hasSize(3).
                containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author authorA = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorA);
        authorA.setName("UPDATED");
        authorRepository.save(authorA);
        Optional<Author> result = authorRepository.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author authorA = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorA);
        authorRepository.deleteById(authorA.getId());
        Optional<Author> result = authorRepository.findById(authorA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        Author testAuthorAEntity = TestDataUtil.createTestAuthorA();
        authorRepository.save(testAuthorAEntity);
        Author testAuthorBEntity = TestDataUtil.createTestAuthorB();
        authorRepository.save(testAuthorBEntity);
        Author testAuthorCEntity = TestDataUtil.createTestAuthorC();
        authorRepository.save(testAuthorCEntity);

        Iterable<Author> result = authorRepository.ageLessThan(50);
        assertThat(result).containsExactly(testAuthorBEntity, testAuthorCEntity);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        Author testAuthorAEntity = TestDataUtil.createTestAuthorA();
        authorRepository.save(testAuthorAEntity);
        Author testAuthorBEntity = TestDataUtil.createTestAuthorB();
        authorRepository.save(testAuthorBEntity);
        Author testAuthorCEntity = TestDataUtil.createTestAuthorC();
        authorRepository.save(testAuthorCEntity);

        Iterable<Author> result = authorRepository.findAuthorsWithAgeGreaterThan(50);
        assertThat(result).containsExactly(testAuthorAEntity);
    }


}
