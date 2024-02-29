package com.pruebas.library.mappers.impl;

import com.pruebas.library.dto.BookDto;
import com.pruebas.library.dto.BookOrderDto;
import com.pruebas.library.mappers.Mapper;
import com.pruebas.library.model.Book;
import com.pruebas.library.model.BookOrder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookOrderMapperImpl implements Mapper<BookOrder,BookOrderDto> {
    private final ModelMapper modelMapper;

    public BookOrderMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookOrderDto mapTo(BookOrder bookOrder) {
        return modelMapper.map(bookOrder, BookOrderDto.class);
    }

    @Override
    public BookOrder mapFrom(BookOrderDto bookOrderDto) {
        return modelMapper.map(bookOrderDto, BookOrder.class);
    }
}
