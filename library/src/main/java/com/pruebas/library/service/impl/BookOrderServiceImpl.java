package com.pruebas.library.service.impl;

import com.pruebas.library.model.BookOrder;
import com.pruebas.library.model.User;
import com.pruebas.library.repository.BookOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the BookOrderService interface for managing BookOrder entities.
 */
@Service
public class BookOrderServiceImpl implements com.pruebas.library.service.BookOrderService {

    private final BookOrderRepository bookOrderRepository;

    /**
     * Constructs a new BookOrderServiceImpl with the specified BookOrderRepository.
     *
     * @param bookOrderRepository The repository for BookOrder entities.
     */
    public BookOrderServiceImpl(BookOrderRepository bookOrderRepository) {
        this.bookOrderRepository = bookOrderRepository;
    }

    /**
     * Retrieves all book orders associated with the specified user.
     *
     * @param user The user for whom to retrieve orders.
     * @return List of BookOrder representing all orders for the user.
     */
    @Override
    public List<BookOrder> getOrders(User user) {
        return bookOrderRepository.findByUser(user);
    }
}
