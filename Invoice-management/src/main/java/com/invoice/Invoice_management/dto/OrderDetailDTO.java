package com.invoice.Invoice_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private String productName;
    private int quantity;
    private double price;
    private double totalPerProduct;
}
