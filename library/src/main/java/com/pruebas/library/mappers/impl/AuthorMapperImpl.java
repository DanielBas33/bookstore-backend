package com.pruebas.library.mappers.impl;

import com.pruebas.library.dto.AuthorDto;
import com.pruebas.library.model.Author;
import com.pruebas.library.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<Author, AuthorDto> {

    private final ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public Author mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }
}