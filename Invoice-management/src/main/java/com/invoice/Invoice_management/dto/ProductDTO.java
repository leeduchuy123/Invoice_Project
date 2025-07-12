package com.invoice.Invoice_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String unit;

    public ProductDTO(String name, Double price, String unit) {
        this.name = name;
        this.price = price;
        this.unit = unit;
    }
}

