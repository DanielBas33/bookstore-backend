package com.pruebas.library.dto;

import com.pruebas.library.model.Inventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private String isbn;

    private String title;

    private AuthorDto author;

    private Double price;

    private Inventory inventory;

}