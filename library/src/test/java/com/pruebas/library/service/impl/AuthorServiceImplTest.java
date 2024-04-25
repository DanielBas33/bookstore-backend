package com.pruebas.library.service.impl;

import com.pruebas.library.TestDataUtil;
import com.pruebas.library.model.Author;
import com.pruebas.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthorServiceImplTest {

    // Service that is getting tested
    @InjectMocks
    private AuthorServiceImpl authorService;

    // Declare dependencies
    @Mock
    private AuthorRepository authorRepository;


    @BeforeEach
    void setUp () {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_author() {
        // Given
        Author testAuthorA = TestDataUtil.createTestAuthorA();

        // Mock the calls
        Mockito.when(authorRepository.save(testAuthorA)).thenReturn(testAuthorA);
        // When
        Author author = authorService.save(testAuthorA);

        // Then
        assertEquals(author.getName(),testAuthorA.getName());
        verify(authorRepository,times(1)).save(testAuthorA);
    }

    @Test
    public void should_return_all_authors() {
        // Given
        Author testAuthorA = TestDataUtil.createTestAuthorA();
        List<Author> authors = new ArrayList<>();
        authors.add(testAuthorA);

        //Mock the calls
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        // When
        List<Author> expectedAuthors = authorService.findAll();

        // Then
        assertEquals(authors.size(),expectedAuthors.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void should_return_author_by_id() {
        // Given
        Long authorId = 1L;
        Author testAuthorA = TestDataUtil.createTestAuthorA();

        // Mock the calls
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(testAuthorA));

        // When
        Optional<Author> expectedAuthor = authorService.findOne(authorId);



        // Then
        assertTrue(expectedAuthor.isPresent());
        assertEquals(testAuthorA.getName(), expectedAuthor.get().getName());
        verify(authorRepository, times(1)).findById(authorId);
    }

}