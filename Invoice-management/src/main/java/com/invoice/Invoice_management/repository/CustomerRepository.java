package com.invoice.Invoice_management.repository;

import com.invoice.Invoice_management.dto.CustomerDTO;
import com.invoice.Invoice_management.entity.Customer;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE " +
            "LOWER(c.fullname) LIKE LOWER(CONCAT('%', :token, '%')) OR " +
            "LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :token ,'%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :token ,'%'))"
    )
    List<Customer> findCustomersByToken(@Param("token") String token);

    Optional<Customer> findCustomerById(Long id);
}
