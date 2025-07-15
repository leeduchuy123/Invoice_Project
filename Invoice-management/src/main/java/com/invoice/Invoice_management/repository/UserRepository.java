package com.invoice.Invoice_management.repository;

import com.invoice.Invoice_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE "
    + "LOWER(u.username) LIKE LOWER(CONCAT('%', :token, '%')) OR "
    + "LOWER(u.email) LIKE LOWER(CONCAT('%', :token, '%'))"
    )
    List<User> findUsersByToken(String token);

    User findByUsername(String username);
}
