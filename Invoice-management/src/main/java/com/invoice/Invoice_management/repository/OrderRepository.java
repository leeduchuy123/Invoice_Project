package com.invoice.Invoice_management.repository;

import com.invoice.Invoice_management.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
