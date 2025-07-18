package com.invoice.Invoice_management.controller;

import com.invoice.Invoice_management.dto.OrderCreateRequest;
import com.invoice.Invoice_management.dto.OrderDTO;
import com.invoice.Invoice_management.entity.Order;
import com.invoice.Invoice_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderCreateRequest request) {
        Order createdOrder = orderService.createOrder(request);
        OrderDTO responseDTO = orderService.convertToDTO(createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        String notification = "Đã xóa thành công hóa đơn: " + id;
        return notification;
    }
}
