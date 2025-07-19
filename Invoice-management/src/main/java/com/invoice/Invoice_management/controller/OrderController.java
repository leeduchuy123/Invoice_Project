package com.invoice.Invoice_management.controller;

import com.invoice.Invoice_management.dto.OrderCreateRequest;
import com.invoice.Invoice_management.dto.OrderDTO;
import com.invoice.Invoice_management.entity.Order;
import com.invoice.Invoice_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderDTOs = orderService.getAllOrder();
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<OrderDTO>> getOrdersByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "1") int size
    ) {
        Page<OrderDTO> pagedOrders = orderService.getOrdersByPage(page, size);
        return ResponseEntity.ok(pagedOrders);
    }

    @GetMapping("/searchByCusName")
    public ResponseEntity<Page<OrderDTO>> searchOrdersByCustomerName(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size
    ) {
        Page<OrderDTO> result =  orderService.searchOrdersByCustomerName(keyword, page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/searchByProductName")
    public ResponseEntity<Page<OrderDTO>> searchOrdersByProductName(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size
    ) {
        Page<OrderDTO> result = orderService.searchOrdersByProductName(keyword, page, size);
        return ResponseEntity.ok(result);
    }
}
