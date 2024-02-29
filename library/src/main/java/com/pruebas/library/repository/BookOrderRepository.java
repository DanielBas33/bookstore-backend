package com.pruebas.library.repository;

import com.pruebas.library.model.BookOrder;
import com.pruebas.library.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookOrderRepository extends ListCrudRepository<BookOrder, Long> {

    List<BookOrder> findByUser(User user);

}
