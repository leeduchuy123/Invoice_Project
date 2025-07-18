package com.invoice.Invoice_management.service;

import com.invoice.Invoice_management.dto.OrderCreateRequest;
import com.invoice.Invoice_management.dto.OrderDTO;
import com.invoice.Invoice_management.entity.*;
import com.invoice.Invoice_management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(OrderCreateRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order(0, customer, user); // tạm set total = 0
        order.setOrderDetails(new ArrayList<>());
        order = orderRepository.save(order);        // để có id cho orderDetail

        double total = 0;
        for (OrderCreateRequest.OrderDetailRequest item : request.getOrderDetails()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderDetail detail = new OrderDetail(order, product, item.getQuantity());
            total += detail.getTotal_per_product();

            orderDetailRepository.save(detail);
        }

        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    public OrderDTO convertToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getTotalPrice(),
                order.getCustomer().getFullname(),
                order.getCreatedAt().toString()
        );
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
