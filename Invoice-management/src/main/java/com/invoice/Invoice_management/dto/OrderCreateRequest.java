package com.invoice.Invoice_management.dto;

import com.invoice.Invoice_management.entity.OrderDetail;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class OrderCreateRequest {
    private Long customerId;
    private Long userId;
    private List<OrderDetailRequest> orderDetails;

    @Getter @Setter
    public static class OrderDetailRequest {
        private Long productId;
        private int quantity;
    }
}
