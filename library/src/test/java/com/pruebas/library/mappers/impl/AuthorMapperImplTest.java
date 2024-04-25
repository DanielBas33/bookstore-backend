package com.pruebas.library.mappers.impl;

import com.pruebas.library.TestDataUtil;
import com.pruebas.library.dto.AuthorDto;
import com.pruebas.library.mappers.Mapper;
import com.pruebas.library.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperImplTest {

    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AuthorMapperImpl authorMapper;

    @BeforeEach
    void setUp () {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void shouldMapAuthorToAuthorDTO() {

    }

    @Test
    public void shouldMapAuthorDTOToAuthor() {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();

        Author author = authorMapper.mapFrom(testAuthorA);
        System.out.println(testAuthorA);
        assertEquals(testAuthorA.getName(), author.getName());
    }

}