package com.pruebas.library.service.impl;

import com.pruebas.library.model.BookOrder;
import com.pruebas.library.model.User;
import com.pruebas.library.repository.BookOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookOrderServiceImpl implements com.pruebas.library.service.BookOrderService {

    private final BookOrderRepository bookOrderRepository;

    public BookOrderServiceImpl(BookOrderRepository bookOrderRepository) {this.bookOrderRepository = bookOrderRepository;}

    @Override
    public List<BookOrder> getOrders(User user) {
        return bookOrderRepository.findByUser(user);
    }
}
