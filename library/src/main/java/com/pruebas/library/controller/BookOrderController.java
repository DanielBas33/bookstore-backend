package com.pruebas.library.controller;

import com.pruebas.library.dto.BookOrderDto;
import com.pruebas.library.mappers.Mapper;
import com.pruebas.library.model.BookOrder;
import com.pruebas.library.model.User;
import com.pruebas.library.service.BookOrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for managing operations related to book orders.
 */
@RestController
@RequestMapping("/orders")
public class BookOrderController {

    private final BookOrderService bookOrderService;
    private final Mapper<BookOrder, BookOrderDto> bookOrderMapper;

    /**
     * Constructs a new BookOrderController with the specified services.
     *
     * @param bookOrderMapper  The mapper used for converting between BookOrder and BookOrderDto.
     * @param bookOrderService The service for managing BookOrder entities.
     */
    public BookOrderController(Mapper<BookOrder, BookOrderDto> bookOrderMapper, BookOrderService bookOrderService) {
        this.bookOrderMapper = bookOrderMapper;
        this.bookOrderService = bookOrderService;
    }

    /**
     * Retrieves all book orders for the authenticated user.
     *
     * @param user The authenticated user for whom orders are retrieved.
     * @return List of BookOrderDto representing all orders for the user.
     */
    @GetMapping
    public List<BookOrderDto> getOrders(@AuthenticationPrincipal User user) {
        List<BookOrder> bookOrders = bookOrderService.getOrders(user);
        return bookOrders.stream()
                .map(bookOrderMapper::mapTo)
                .collect(Collectors.toList());
    }

    // TODO: Implement route to create a new book order (POST method)
    // @PostMapping
    // public ResponseEntity<BookOrderDto> createOrder(...) { ... }

    // TODO: Implement route to update an existing book order (PUT method)
    // @PutMapping("/{orderId}")
    // public ResponseEntity<BookOrderDto> updateOrder(...) { ... }

}
