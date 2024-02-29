package com.pruebas.library.dto;

import com.pruebas.library.model.BookOrderQuantity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookOrderDto {

    private Long id;

    private List<BookOrderQuantity> quantities = new ArrayList<>();

    private UserDto user;

}