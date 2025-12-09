package com.invoice.Invoice_management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalPrice;

    @CreationTimestamp
    @Column(name = "create_at", nullable=false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "pay_at")
    private LocalDateTime payAt;

    public enum OrderStatus {
        CREATED,
        PAYED,
        CANCELLED,
        TRANSFERED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order(double totalPrice, Customer customer, User user) {
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.user = user;
        this.status = OrderStatus.CREATED;
    }
}
