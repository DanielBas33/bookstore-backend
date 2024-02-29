package com.pruebas.library.repository;

import com.pruebas.library.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
