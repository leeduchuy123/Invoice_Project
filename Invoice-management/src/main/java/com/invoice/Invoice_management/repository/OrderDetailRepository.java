package com.invoice.Invoice_management.repository;

import com.invoice.Invoice_management.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
