package com.pruebas.library.service;

import com.pruebas.library.model.BookOrder;
import com.pruebas.library.model.User;

import java.util.List;

/**
 * Service interface for managing BookOrder entities.
 */
public interface BookOrderService {

    /**
     * Retrieves all book orders associated with the specified user.
     *
     * @param user The user for whom to retrieve orders.
     * @return List of BookOrder representing all orders for the user.
     */
    List<BookOrder> getOrders(User user);
}
