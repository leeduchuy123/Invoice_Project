package com.invoice.Invoice_management.service;

import com.invoice.Invoice_management.dto.CustomerDTO;
import com.invoice.Invoice_management.entity.Customer;
import com.invoice.Invoice_management.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRespository;

    //Helper method to convert Entity <-> DTO
    private CustomerDTO toDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO(customer.getFullname(), customer.getAddress(), customer.getPhoneNumber(), customer.getEmail());
        customerDTO.setId(customer.getId());
        return customerDTO;
    }
    private Customer toEntity(CustomerDTO customerDTO) {
        return new Customer(customerDTO.getFullname(), customerDTO.getAddress(), customerDTO.getPhoneNumber(), customerDTO.getEmail());
    }

    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = toEntity(customerDTO);
        Customer savedCustomer = customerRespository.save(customer);
        return toDTO(savedCustomer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerRespository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setFullname(customerDTO.getFullname());
            customer.setAddress(customerDTO.getAddress());
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
            customer.setEmail(customerDTO.getEmail());
            Customer updatedCustomer = customerRespository.save(customer);
            return toDTO(updatedCustomer);
        } else {
            return null;
        }
    }

    public void deleteCustomer(Long id) {
        customerRespository.deleteById(id);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRespository.findAll();
        return customers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<CustomerDTO> findCustomersByToken(String token) {
        List<Customer> customers = customerRespository.findCustomersByToken(token);
        return customers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<CustomerDTO> getCustomerById(Long id) {
        return Optional.ofNullable(customerRespository.findCustomerById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id)));
    }
}
