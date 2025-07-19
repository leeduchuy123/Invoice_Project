package com.invoice.Invoice_management.service;

import com.invoice.Invoice_management.dto.OrderCreateRequest;
import com.invoice.Invoice_management.dto.OrderDTO;
import com.invoice.Invoice_management.dto.OrderDetailDTO;
import com.invoice.Invoice_management.entity.*;
import com.invoice.Invoice_management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            order.getOrderDetails().add(detail); //cập nhật lại danh sách
        }

        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    public OrderDTO convertToDTO(Order order) {
        List<OrderDetailDTO> orderDetailDTOs = order.getOrderDetails().stream()
                .map(detail -> new OrderDetailDTO(
                        detail.getProduct().getName(),
                        detail.getQuantity(),
                        detail.getProduct().getPrice(),
                        detail.getTotal_per_product()
                ))
                .collect(Collectors.toList());
        return new OrderDTO(
                order.getId(),
                order.getTotalPrice(),
                order.getCustomer().getFullname(),
                order.getCreatedAt().toString(),
                order.getStatus().name(),
                orderDetailDTOs
        );
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public List<OrderDTO> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public Page<OrderDTO> getOrdersByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Order> orderPage = orderRepository.findAll(pageable);

        return orderPage.map(this::convertToDTO);   // giữ phân trang, chỉ chuyển đổi từng item
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> searchOrdersByCustomerName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Order> orders = orderRepository.findByCustomerNameContainingIgnoreCase(keyword, pageable);

        return orders.map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> searchOrdersByProductName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Order> orders = orderRepository.findByProductNameContainingIgnoreCase(keyword, pageable);

        return orders.map(this::convertToDTO);
    }
}
