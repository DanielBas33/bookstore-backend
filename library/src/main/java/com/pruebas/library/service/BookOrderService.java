package com.pruebas.library.service;

import com.pruebas.library.model.BookOrder;
import com.pruebas.library.model.User;

import java.util.List;

public interface BookOrderService {
    List<BookOrder> getOrders(User user);
}
