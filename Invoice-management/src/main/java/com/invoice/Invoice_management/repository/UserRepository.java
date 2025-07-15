package com.invoice.Invoice_management.repository;

import com.invoice.Invoice_management.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE "
    + "LOWER(u.fullname) LIKE LOWER(CONCAT('%', :token, '%')) OR "
    + "LOWER(u.email) LIKE LOWER(CONCAT('%', :token, '%'))"
    )
    List<User> findUsersByToken(String token);

    Optional<User> findByEmail(String email);
}
