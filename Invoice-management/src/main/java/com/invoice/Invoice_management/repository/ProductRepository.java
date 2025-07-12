package com.invoice.Invoice_management.repository;


import com.invoice.Invoice_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> getProductById(Long id);
}
