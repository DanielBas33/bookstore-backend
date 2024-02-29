package com.pruebas.library.controller;


import com.pruebas.library.dto.BookOrderDto;
import com.pruebas.library.mappers.Mapper;
import com.pruebas.library.model.BookOrder;
import com.pruebas.library.model.User;
import com.pruebas.library.service.BookOrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class BookOrderController {

   private final BookOrderService bookOrderService;
    private final Mapper<BookOrder, BookOrderDto> bookOrderMapper;

    public BookOrderController(Mapper<BookOrder, BookOrderDto> bookOrderMapper, BookOrderService bookOrderService) {
        this.bookOrderMapper = bookOrderMapper;
        this.bookOrderService = bookOrderService;
    }

    @GetMapping
    public List<BookOrderDto> getOrders(@AuthenticationPrincipal User user) {
        List<BookOrder> bookOrders = bookOrderService.getOrders(user);
        return bookOrders.stream()
                .map(bookOrderMapper::mapTo)
                .collect(Collectors.toList());
    }

}
