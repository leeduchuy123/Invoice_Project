package com.invoice.Invoice_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private double totalPrice;
    private String customerName;
    private String createdAt;
    private String status;
    private List<OrderDetailDTO> orderDetails;
}
