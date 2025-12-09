package com.invoice.Invoice_management.service;

import com.invoice.Invoice_management.dto.ProductDTO;
import com.invoice.Invoice_management.entity.Product;
import com.invoice.Invoice_management.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    //Helper method to convert Entity <-> DTO
    private ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO(product.getName(), product.getPrice(), product.getUnit());
        productDTO.setId(product.getId());
        return productDTO;
    }
    private Product toEntity(ProductDTO productDTO) {
        return new Product(productDTO.getName(), productDTO.getPrice(), productDTO.getUnit());
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setUnit(productDTO.getUnit());
            Product updatedProduct = productRepository.save(product);
            return toDTO(updatedProduct);
        } else {
            return null;
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return Optional.ofNullable(productRepository.getProductById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id)));
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        //Tao 1 luong stream -> chuyen het thanh DTO -> thu thap tat ca cac phan tu duoc chuyen toi vao 1 List
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
