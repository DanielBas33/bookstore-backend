package com.pruebas.library.repository;

import com.pruebas.library.model.BookOrder;
import com.pruebas.library.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing BookOrder entities in the database.
 */
@Repository
public interface BookOrderRepository extends ListCrudRepository<BookOrder, Long> {

    /**
     * Retrieves a list of book orders associated with a specific user.
     *
     * @param user the user for which to retrieve book orders
     * @return a List of book orders associated with the specified user
     */
    List<BookOrder> findByUser(User user);

}
