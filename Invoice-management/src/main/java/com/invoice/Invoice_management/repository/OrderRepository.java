package com.invoice.Invoice_management.repository;

import com.invoice.Invoice_management.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT o FROM Order o " +
            "JOIN o.customer c " +
            "WHERE LOWER(c.fullname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Order> findByCustomerNameContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o " +
            "JOIN o.orderDetails od " +
            "JOIN od.product p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%'))")
    Page<Order> findByProductNameContainingIgnoreCase(@Param("productName") String productName, Pageable pageable);

    @Query("SELECT SUM(o.totalPrice) FROM Order o " +
            "WHERE o.createdAt BETWEEN :start AND :end")
    Double sumRevenueBetween(@Param("start") LocalDateTime start, @Param("end")LocalDateTime end);
}
