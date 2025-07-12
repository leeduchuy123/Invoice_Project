package com.invoice.Invoice_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = "Name of product can not be empty")
    private String name;

    @NotNull
    @DecimalMin(value="0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotNull
    private String unit;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    public Product(String name, double price, String unit) {
        this.name = name;
        this.price = price;
        this.unit = unit;
    }
}
