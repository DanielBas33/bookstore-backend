package com.pruebas.library.repository;

import com.pruebas.library.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Author entities in the database.
 */
@Repository
public interface AuthorRepository extends ListCrudRepository<Author, Long> {

    /**
     * Retrieves authors whose age is less than the specified age.
     *
     * @param age the maximum age for authors to be retrieved
     * @return an Iterable of authors whose age is less than the specified age
     */
    Iterable<Author> ageLessThan(int age);

    /**
     * Retrieves authors whose age is greater than the specified age using a custom JPQL query.
     *
     * @param age the minimum age for authors to be retrieved
     * @return an Iterable of authors whose age is greater than the specified age
     */
    @Query("SELECT a FROM Author a WHERE a.age > ?1")
    Iterable<Author> findAuthorsWithAgeGreaterThan(int age);
}
